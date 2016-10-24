package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import logging.PikaLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.*;
import semanticAnalyzer.types.*;
import symbolTable.Binding;
import symbolTable.Scope;
import tokens.LextantToken;
import tokens.Token;

class SemanticAnalysisVisitor extends ParseNodeVisitor.Default {
	@Override
	public void visitLeave(ParseNode node) {
		throw new RuntimeException("Node class unimplemented in SemanticAnalysisVisitor: " + node.getClass());
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// constructs larger than statements
	@Override
	public void visitEnter(ProgramNode node) {
		enterProgramScope(node);
	}
	public void visitLeave(ProgramNode node) {
		leaveScope(node);
	}
	
	public void visitEnter(MainBlockNode node) {
	}
	public void visitLeave(MainBlockNode node) {
	}
	
	public void visitEnter(BlockNode node) {
		enterSubscope(node);
	}
	public void visitLeave(BlockNode node) {
		leaveScope(node);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for scoping.
	private void enterProgramScope(ParseNode node) {
		Scope scope = Scope.createProgramScope();
		node.setScope(scope);
	}
	private void enterSubscope(ParseNode node) {
		Scope baseScope = node.getLocalScope();
		Scope scope = baseScope.createSubscope();
		node.setScope(scope);
	}		
	private void leaveScope(ParseNode node) {
		node.getScope().leave();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// statements, declarations, and assignments
	@Override
	public void visitLeave(PrintStatementNode node) {
	}
	@Override
	public void visitLeave(DeclarationNode node) {
		if (node.child(0) instanceof IdentifierNode) {
			IdentifierNode identifier = (IdentifierNode) node.child(0);
			ParseNode initializer = node.child(1);
			
			Type declarationType = initializer.getType();
			node.setType(declarationType);
			identifier.setType(declarationType);
			
			Boolean mutable = node.getToken().isLextant(Keyword.VAR);
			
			addBinding(identifier, declarationType, mutable);			
		}
	}
	@Override
	public void visitLeave(AssignmentNode node) {
		if (node.child(0) instanceof IdentifierNode) {
			// Define target
			IdentifierNode target = (IdentifierNode) node.child(0);
			target.setBinding(target.findVariableBinding());
			
			Type targetType = target.getBinding().getType();
			if (targetType instanceof ArrayType && target.isIndexed()) {
				targetType = ((ArrayType)targetType).getSubtype();
			}
			target.setType(targetType);
			
			if (target.isMutable() == null) {
				return;
			}
			
			// Check for immutability
			if (!target.isMutable() && !target.isIndexed()) {
				String targetName = target.getToken().getLexeme();
				logError("Cannot assign to value: '" + targetName + "' is a 'const' constant.");
				return;
			}
			
			ParseNode expression = node.child(1);
			Type expressionType = expression.getType();
			
			// Check for type-match, ignore if both are ArrayType
			if ((targetType instanceof ArrayType) && (expressionType instanceof ArrayType)) {				
				if (!((ArrayType)targetType).equals(expressionType)) {
					logError("Cannot assign value of type '" + expressionType.infoString() + "' to type '" + targetType.infoString() + "'");
				}
			} else {
				if (targetType != expressionType) {
					logError("Cannot assign value of type '" + expressionType + "' to type '" + targetType + "'");
				}
			}
			
			node.setType(expressionType);
			target.setType(expressionType);
		}
	}
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
			node.setType(PrimitiveType.ERROR);
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
		// TODO: Move to FunctionSignature?
		if ((childTypes.get(0) instanceof ArrayType) && (childTypes.get(1) instanceof ArrayType)) {
			if (((ArrayType)childTypes.get(0)).equals(childTypes.get(1))) {
				FunctionSignature signature = FunctionSignatures.signaturesOf(operator).get(0);
				node.setSignature(signature);		
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
				node.setType(PrimitiveType.ERROR);
			}
		} else {
			FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
			
			if (signature.accepts(childTypes)) {
				node.setSignature(signature);
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
				node.setType(PrimitiveType.ERROR);
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
		// TODO: Move to FunctionSignature?
		if (operator == Keyword.LENGTH) {
			if (childTypes.get(0) instanceof ArrayType) {
				FunctionSignature signature = FunctionSignatures.signaturesOf(operator).get(0);
				node.setSignature(signature);		
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
				node.setType(PrimitiveType.ERROR);
			}
		} else {
			FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
			
			if(signature.accepts(childTypes)) {
				node.setSignature(signature);
				node.setType(signature.resultType());
			} else {
				typeCheckError(node, childTypes);
				node.setType(PrimitiveType.ERROR);
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

		// TODO: Move to FunctionSignature?
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
				// TODO: Perform promotions
				
				// Check that all values are of same type
				Type t = childTypes.get(0);
				int numType = Collections.frequency(childTypes, t);
				if (!(t instanceof ArrayType) && numType != node.nChildren()) {
					typeCheckError(node, childTypes);
					node.setType(PrimitiveType.ERROR);
				}
			
				// Set type
				node.setSubtype(childTypes.get(0));
			} else {
				if (childTypes.get(0) != PrimitiveType.INTEGER) {
					typeCheckError(node, childTypes);
					node.setType(PrimitiveType.ERROR);
				}
			}
		}
		
		if (operator == Keyword.CLONE) {
			assert node.nChildren() == 1;

			if (!(childTypes.get(0) instanceof ArrayType)) {
				typeCheckError(node, childTypes);
				node.setType(PrimitiveType.ERROR);
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
		if(!isBeingDeclared(node) && !isBeingAssigned(node)) {		
			node.setBinding(node.findVariableBinding());
			
			Type type = node.getBinding().getType();
			if (type instanceof ArrayType && node.isIndexed()) {
				type = ((ArrayType)type).getSubtype();
			}
			node.setType(type);
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
	private void addBinding(IdentifierNode identifierNode, Type type, Boolean mutable) {
		Scope scope = identifierNode.getLocalScope();
		Binding binding = scope.createBinding(identifierNode, type);
		binding.setMutability(mutable);
		identifierNode.setBinding(binding);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// error logging/printing
	private void typeCheckError(ParseNode node, List<Type> operandTypes) {
		Token token = node.getToken();
		
		logError("operator " + token.getLexeme() + " not defined for types " 
				 + operandTypes  + " at " + token.getLocation());	
	}
	private void logError(String message) {
		PikaLogger log = PikaLogger.getLogger("compiler.semanticAnalyzer");
		log.severe(message);
		// TODO: System.exit(0);
	}
}