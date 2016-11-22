package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import logging.PikaLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import promoter.Promoter;
import semanticAnalyzer.signatures.*;
import semanticAnalyzer.types.*;
import symbolTable.Binding;
import symbolTable.Scope;
import tokens.LextantToken;
import tokens.Token;

class SemanticAnalysisVisitor extends ParseNodeVisitor.Default {
	public Promoter promoter = new Promoter();
	
	@Override
	public void visitLeave(ParseNode node) {
		throw new RuntimeException("Node class unimplemented in SemanticAnalysisVisitor: " + node.getClass());
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// constructs larger than statements
	@Override
	public void visitEnter(ProgramNode node) {
		enterScope(node);
	}
	public void visitLeave(ProgramNode node) {
		leaveScope(node);
	}
	
	public void visitEnter(MainBlockNode node) {
		enterScope(node);
	}
	public void visitLeave(MainBlockNode node) {
		leaveScope(node);
	}
	
	public void visitEnter(BlockNode node) {
		enterScope(node);
	}
	public void visitLeave(BlockNode node) {
		leaveScope(node);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for scoping.
	private void enterScope(ParseNode node) {
		node.getScope().enter();
	}
	private void leaveScope(ParseNode node) {
		node.getScope().leave();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// functionDefinitions, lambdas, call/return statements
	@Override
	public void visitLeave(FunctionDefinitionNode node) {
		
	}
	@Override
	public void visitLeave(LambdaNode node) {
		
	}
	@Override
	public void visitLeave(CallNode node) {
		if (!(node.child(0) instanceof FunctionInvocationNode)) {
			typeCheckError(node, Arrays.asList(node.child(0).getType()));
		}
	}
	@Override
	public void visitLeave(FunctionInvocationNode node) {
		if (node.child(0).getType() instanceof LambdaType) {
			LambdaType type = (LambdaType)node.child(0).getType();
			Type returnType = type.getReturnType();
			
			if (returnType == PrimitiveType.VOID) {
				ParseNode parent = node.getParent();
				while (parent != null && !(parent instanceof CallNode)) {
					parent = parent.getParent();
				}
				
				if (parent == null || !(parent instanceof CallNode)) {
					functionInvocationError(node, "Cannot use VOID function as an expression");
					return;
				}
			}

			verifyFunctionArguments(node, type.getSignature());
		} else {
			functionInvocationError(node, "Invoking function on non-Lambda type");
			return;
		}
	}
	private void verifyFunctionArguments(ParseNode node, FunctionSignature signature) {
		FunctionInvocationNode function = null;

		if (node instanceof CallNode) {
			function = (FunctionInvocationNode) node.child(0);
		} else if (node instanceof FunctionInvocationNode) {
			function = (FunctionInvocationNode) node;
		}
		
		if (signature == null) {
			functionInvocationError(node, "No signature defined for function");
			return;
		}
		
		// Get child types
		List<Type> childTypes = new ArrayList<Type>();
		function.getChildren().forEach((child) -> childTypes.add(child.getType()));
		childTypes.remove(0); // Remove identifier node
		
		// Check that number of arguments is the same
		if (signature.accepts(childTypes)) {
			node.setType(signature.resultType());
		} else {
			typeCheckError(function, childTypes);
		}
	}
	
	@Override
	public void visitLeave(ReturnNode node) {
		ParseNode parent = node.getParent();
		while (parent != null && !(parent instanceof LambdaNode)) {
			parent = parent.getParent();
		}
		
		if (parent == null) {
			Token token = node.getToken();
			logError("Cannot call return statement outside of a Lambda at " + token.getLocation());
			
			node.setType(PrimitiveType.ERROR);
			return;
		}
		
		Type lambdaReturnType = ((LambdaNode) parent).getReturnType();
		Type returnType;
		String returnTypeString = "";
		
		// Handle void return
		if (node.nChildren() == 0) {
			returnType = PrimitiveType.VOID;
		} else {
			returnType = node.child(0).getType();
		}
		
		if (returnType.equals(lambdaReturnType)) {
			node.setType(returnType);
		} else {
			node.setType(PrimitiveType.ERROR);
			
			Token token = node.getToken();
			returnTypeString = returnType.infoString();
			logError("Cannot return type " + returnTypeString + " for Lambda with return type "
					 + lambdaReturnType.infoString() + " at " + token.getLocation());
		}
	}

	
	///////////////////////////////////////////////////////////////////////////
	// declarations, and assignments
	@Override
	public void visitLeave(DeclarationNode node) {
		if (node.child(1) instanceof LambdaNode) return;

		if (node.child(0) instanceof IdentifierNode) {
			IdentifierNode identifier = (IdentifierNode) node.child(0);
			ParseNode initializer = node.child(1);
			
			Type declarationType = initializer.getType();
			node.setType(declarationType);
			identifier.setType(declarationType);
			
			Boolean mutable = node.getToken().isLextant(Keyword.VAR);
			
			if (initializer instanceof LambdaNode) {
				FunctionSignature signature = ((LambdaNode) initializer).getSignature();
				addBinding(identifier, declarationType, mutable, signature);
			} else {
				addBinding(identifier, declarationType, mutable);
			}
		}
	}
	@Override
	public void visitLeave(AssignmentNode node) {
		ParseNode tempNode = node.child(0);
		while (!(tempNode instanceof IdentifierNode) && tempNode.nChildren() > 0) {
			tempNode = tempNode.child(0);
		}
		
		if (tempNode instanceof IdentifierNode) { 
			// Configure identifier		
			IdentifierNode target = (IdentifierNode) tempNode;
			target.setBinding(target.findVariableBinding());
			target.setType(target.getBinding().getType());
			
			// Check for immutability
			if (target.isMutable() != null) {
				if (!target.isMutable() && !(node.child(0) instanceof IndexNode)) {
					String targetName = target.getToken().getLexeme();
					logError("Cannot assign to variable '" + targetName + "'.");
					return;
				}
			}
		}
		
		// Ensure target is a targetable entity
		ParseNode target = node.child(0);
		if (!(target instanceof IdentifierNode || target instanceof IndexNode)) {
			List<Type> childTypes = new ArrayList<Type>();
			node.getChildren().forEach((child) -> childTypes.add(child.getType()));
			typeCheckError(node, childTypes);
			return;
		}

		// Check for type-match
		Type targetType = node.child(0).getType();
		Type expressionType = node.child(1).getType();

		if ((targetType instanceof ArrayType) && (expressionType instanceof ArrayType)) {				
			if (!((ArrayType) targetType).equals(expressionType)) {
				
				typeCheckError(node, Arrays.asList(targetType, expressionType));
				return;
			}
		} else if ((targetType instanceof LambdaType) && (expressionType instanceof LambdaType)) {
			if (!((LambdaType) targetType).equals(expressionType)) {
				typeCheckError(node, Arrays.asList(targetType, expressionType));
				return;
			}
		} else {
			if (targetType != expressionType) {
				typeCheckError(node, Arrays.asList(targetType, expressionType));
				return;
			}
		}
		
		node.setType(expressionType);
	}
	@Override
	public void visitLeave(ReleaseNode node) {
		assert node.nChildren() == 1;
		Type type = node.child(0).getType();
		if (type != PrimitiveType.STRING && !(type instanceof ArrayType)) {
			typeCheckError(node, Arrays.asList(node.child(0).getType()));
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// control strucutures
	@Override
	public void visitLeave(IfNode node) {
		assert node.nChildren() >= 2;
		if (node.child(0).getType() != PrimitiveType.BOOLEAN) {
			typeCheckError(node, Arrays.asList(node.child(0).getType()));
		}
	}
	@Override
	public void visitLeave(WhileNode node) {
		assert node.nChildren() >= 2;
		if (node.child(0).getType() != PrimitiveType.BOOLEAN) {
			typeCheckError(node, Arrays.asList(node.child(0).getType()));
		}
	}

	
	///////////////////////////////////////////////////////////////////////////
	// expressions
	@Override
	public void visitLeave(RationalOperatorNode node) {
		assert node.nChildren() == 2;
		ParseNode left  = node.child(0);
		ParseNode right = node.child(1);
		List<Type> childTypes = Arrays.asList(left.getType(), right.getType());
		
		Lextant operator = operatorFor(node);

		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
		
		if (signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
		} else {
			typeCheckError(node, childTypes);
		}
	}
	private Lextant operatorFor(RationalOperatorNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}
	@Override
	public void visitLeave(BinaryOperatorNode node) {
		assert node.nChildren() == 2;
		ParseNode left  = node.child(0);
		ParseNode right = node.child(1);
		List<Type> childTypes = Arrays.asList(left.getType(), right.getType());
		
		Lextant operator = operatorFor(node);
		if ((childTypes.get(0) instanceof ArrayType) && (childTypes.get(1) instanceof ArrayType)) {
			if (((ArrayType)childTypes.get(0)).equals(childTypes.get(1))) {
				FunctionSignature signature = FunctionSignatures.signaturesOf(operator).get(0);
				node.setSignature(signature);		
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
			}
		} else {			
			FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
			
			if (signature.accepts(childTypes)) {
				node.setSignature(signature);
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
			}
		}
	}
	private Lextant operatorFor(BinaryOperatorNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}
	@Override
	public void visitLeave(UnaryOperatorNode node) {
		assert node.nChildren() == 1;
		ParseNode left  = node.child(0);
		List<Type> childTypes = Arrays.asList(left.getType());
		
		Lextant operator = operatorFor(node);
		if (operator == Keyword.LENGTH) {
			if (childTypes.get(0) instanceof ArrayType) {
				FunctionSignature signature = FunctionSignatures.signaturesOf(operator).get(0);
				node.setSignature(signature);		
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
			}
		} else {
			FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
			
			if(signature.accepts(childTypes)) {
				node.setSignature(signature);
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
			}
		}
	}
	private Lextant operatorFor(UnaryOperatorNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}
	@Override
	public void visitLeave(CastNode node) {
		assert node.nChildren() == 1;
		List<Type> childTypes = Arrays.asList(node.getExpressionType(), node.getCastType());

		if ((childTypes.get(0) instanceof ArrayType) && (childTypes.get(1) instanceof ArrayType)) {
			if (((ArrayType)childTypes.get(0)).equals(childTypes.get(1))) {
				FunctionSignature signature = FunctionSignatures.signaturesOf(Punctuator.PIPE).get(0);
				node.setSignature(signature);		
				node.setType(childTypes.get(1));
			} else {
				logError("cannot cast type '" + childTypes.get(0).infoString() + "' to type '" + childTypes.get(1).infoString() + "'");
				node.setType(PrimitiveType.ERROR);
			}
		} else {
			FunctionSignature signature = FunctionSignatures.signature(Punctuator.PIPE, childTypes);
			
			if(signature.accepts(childTypes)) {
				node.setSignature(signature);
				node.setType(signature.resultType());
			} else {
				logError("cannot cast type '" + childTypes.get(0) + "' to type '" + childTypes.get(1) + "'");
				node.setType(PrimitiveType.ERROR);
			}
		}
	}
	@Override
	public void visitLeave(ArrayNode node) {
		List<Type> childTypes = new ArrayList<Type>();
		node.getChildren().forEach((child) -> childTypes.add(child.getType()));
		
		Lextant operator = operatorFor(node);
		
		if (operator == Keyword.NEW) {
			if (!node.isEmpty()) {				
				// Check that all values are of same type
				Type t = childTypes.get(0);
				for (ParseNode childNode : node.getChildren()) {
					if (!(t.equals(childNode.getType()))) {
						typeCheckError(node, childTypes);
						return;
					}
				}
				
				if (node instanceof ArrayNode) {
					node.setSubtype(childTypes.get(0));
				}
			} else {
				Type subtype = node.getType();
				while (subtype instanceof ArrayType) {
					subtype = ((ArrayType) subtype).getSubtype();
				}
				
				if (subtype == PrimitiveType.VOID) {
					typeCheckError(node, Arrays.asList(node.getType()));
					return;
				}
				
				if (childTypes.get(0) != PrimitiveType.INTEGER) {
					typeCheckError(node, childTypes);
					return;
				}
			}
		}
		
		if (operator == Keyword.CLONE) {
			assert node.nChildren() == 1;

			if (!(childTypes.get(0) instanceof ArrayType)) {
				typeCheckError(node, childTypes);
				return;
			}

			node.setType(childTypes.get(0));
		}
	}
	private Lextant operatorFor(ArrayNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}

	
	///////////////////////////////////////////////////////////////////////////
	// simple leaf nodes
	@Override
	public void visit(BooleanConstantNode node) {
		node.setType(PrimitiveType.BOOLEAN);
	}
	@Override
	public void visit(IntegerConstantNode node) {
		node.setType(PrimitiveType.INTEGER);
	}
	@Override
	public void visit(FloatingConstantNode node) {
		node.setType(PrimitiveType.FLOATING);
	}
	@Override
	public void visit(CharacterNode node) {
		node.setType(PrimitiveType.CHARACTER);
	}
	@Override
	public void visit(StringNode node) {
		node.setType(PrimitiveType.STRING);
	}
	@Override
	public void visit(NewlineNode node) {
	}
	@Override
	public void visit(SpaceNode node) {
	}
	@Override
	public void visit(ErrorNode node) {
		node.setType(PrimitiveType.ERROR);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// IdentifierNodes, with helper methods
	@Override
	public void visitLeave(IdentifierNode node) {
		if(!isBeingDeclared(node) && !isBeingAssigned(node) &&
		   !isFunctionIdentifier(node) && !isFunctionParameter(node)) {		
			node.setBinding(node.findVariableBinding());
			node.setType(node.getBinding().getType());
		}
		// else parent DeclarationNode does the processing.
	}
	private boolean isBeingDeclared(IdentifierNode node) {
		ParseNode parent = node.getParent();
		return (parent instanceof DeclarationNode) && (node == parent.child(0));
	}
	private boolean isBeingAssigned(IdentifierNode node) {
		ParseNode parent = node.getParent();
		return (parent instanceof AssignmentNode) && (node == parent.child(0));
	}
	private boolean isFunctionIdentifier(IdentifierNode node) {
		ParseNode parent = node.getParent();
		return (parent instanceof FunctionDefinitionNode) && (node == parent.child(0));
	}
	private boolean isFunctionParameter(IdentifierNode node) {
		ParseNode parent = node.getParent();
		return (parent instanceof LambdaParamNode) && (node == parent.child(0));
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// IndexNode
	@Override
	public void visitLeave(IndexNode node) {
		assert node.nChildren() >= 2;
		
		if (!(node.child(0).getType() instanceof ArrayType)) {
			typeCheckError(node, Arrays.asList(node.child(0).getType()));
			return;
		}
		
		if (node.child(1).getType() != PrimitiveType.INTEGER) {
			typeCheckError(node, Arrays.asList(node.child(0).getType()));
			return;
		}
		
		// TODO: Check before calling subtype()
		Type subtype = ((ArrayType)node.child(0).getType()).getSubtype();
		if (subtype instanceof TypeLiteral) {
			subtype = ((TypeLiteral) subtype).getType();
		}
		node.setType(subtype);
	}

	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for binding
	private void addBinding(IdentifierNode identifierNode, Type type, Boolean mutable) {
		Scope scope = identifierNode.getLocalScope();
		Binding binding = scope.createBinding(identifierNode, type);
		binding.setMutability(mutable);
		identifierNode.setBinding(binding);
	}
	private void addBinding(IdentifierNode identifierNode, Type type, Boolean mutable, FunctionSignature signature) {
		Scope scope = identifierNode.getLocalScope();
		Binding binding = scope.createBinding(identifierNode, type);
		binding.setMutability(mutable);
		binding.setSignature(signature);
		identifierNode.setBinding(binding);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// error logging/printing
	private void typeCheckError(ParseNode node, List<Type> operandTypes) {
		Token token = node.getToken();
		
		// Check promotion
		if (node instanceof OperatorNode && promoter.promotable((OperatorNode) node)) return;
		if (node instanceof FunctionInvocationNode && promoter.promotable((FunctionInvocationNode) node)) return;
		if (node instanceof ArrayNode && promoter.promotable((ArrayNode) node)) return;
		if (node instanceof IndexNode && promoter.promotable((IndexNode) node)) return;
		if (node instanceof AssignmentNode && promoter.promotable(node)) return;
		
		List<String> errorTypes = new ArrayList<String>();
		operandTypes.forEach((child) -> errorTypes.add(child.infoString()));
		logError("operator " + token.getLexeme() + " not defined for types " 
				 + errorTypes  + " at " + token.getLocation());
		
		node.setType(PrimitiveType.ERROR);
	}
	private void functionInvocationError(ParseNode node, String message) {
		Token token = node.getToken();
		logError(message + " at " + token.getLocation());
		node.setType(PrimitiveType.ERROR);
	}
	private void logError(String message) {
		PikaLogger log = PikaLogger.getLogger("compiler.semanticAnalyzer");
		log.severe(message);
	}
}
