package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import asmCodeGenerator.codeGenerator.array.ArrayLengthSCG;
import asmCodeGenerator.codeGenerator.array.ArrayOffsetSCG;
import asmCodeGenerator.codeGenerator.array.ArrayReverseSCG;
import asmCodeGenerator.codeGenerator.operators.FoldOperatorBaseSCG;
import asmCodeGenerator.codeGenerator.operators.FoldOperatorSCG;
import asmCodeGenerator.codeGenerator.operators.MapOperatorSCG;
import asmCodeGenerator.codeGenerator.operators.ReduceOperatorSCG;
import asmCodeGenerator.codeGenerator.operators.ZipOperatorSCG;
import asmCodeGenerator.codeGenerator.string.StringReverseSCG;
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
		if (!(node.getParent() instanceof ForNode)) {
			enterScope(node);
		}
	}
	public void visitLeave(BlockNode node) {
		if (!(node.getParent() instanceof ForNode)) {
			leaveScope(node);
		}
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
			
			FunctionSignature signature = null;
			if (initializer instanceof LambdaNode) {
				signature = ((LambdaNode) initializer).getSignature();
			}
			
			// If node is static and not a global
			if (node.isStatic() && !(node.getParent() instanceof ProgramNode)) {
				addGlobalBinding(identifier, declarationType, mutable, signature);
			} else {
				addBinding(identifier, declarationType, mutable, signature);
			}
		} else {
			List<Type> childTypes = new ArrayList<Type>();
			node.getChildren().forEach((child) -> childTypes.add(child.getType()));
			typeCheckError(node, childTypes);
			return;
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
	@Override
	public void visitEnter(ForNode node) {
		enterScope(node);
	}
	@Override
	public void visitLeave(ForNode node) {
		leaveScope(node);
		
		assert node.nChildren() >= 3;
		
		Type seqType = node.child(0).getType();
		if (!(seqType == PrimitiveType.STRING || seqType instanceof ArrayType)) {
			typeCheckError(node, Arrays.asList(node.child(1).getType()));
		}
	}
	public void visitLeave(ControlNode node) {
		ParseNode parent = node.getParent();
		while (!(parent instanceof WhileNode || parent instanceof ForNode)) {
			if (parent.getParent() == null) {
				Token token = node.getToken();
				logError("operator " + token.getLexeme() + " only allowed inside the body" +
						 " of a while loop or for loop, at " + token.getLocation());
				node.setType(PrimitiveType.ERROR);
				return;
			}
			
			parent = parent.getParent();
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// expressions
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
	@Override
	public void visitLeave(FoldOperatorNode node) {
		assert node.nChildren() >= 2;
		ParseNode c1 = node.child(0);
		ParseNode c2 = node.child(node.nChildren() - 1);
		List<Type> childTypes = Arrays.asList(c1.getType(), c2.getType());
		
		// First expression must be an array type
		if (childTypes.get(0) instanceof ArrayType) {
			Type arrSubtype = ((ArrayType)childTypes.get(0)).getSubtype();
			// Last expression must be a two-argument-type lambda
			if (childTypes.get(1) instanceof LambdaType) {
				List<Type> lambdaTypes = ((LambdaType)childTypes.get(1)).getTypeList();
				Type returnType = ((LambdaType)childTypes.get(1)).getReturnType();
				
				if (lambdaTypes.size() == 2) {
					if (node.nChildren() == 3) {
						Type baseType = node.child(1).getType();
						if (lambdaTypes.get(0) == baseType && lambdaTypes.get(1) == arrSubtype && returnType == baseType) {
							FoldOperatorSCG foldSCG = new FoldOperatorBaseSCG(baseType, arrSubtype, returnType);
							node.setSCG(foldSCG);
							node.setType(baseType);
							return;
						}
					} else {
						if (lambdaTypes.get(0) == arrSubtype && lambdaTypes.get(1) == arrSubtype && returnType == arrSubtype) {
							FoldOperatorSCG foldSCG = new FoldOperatorSCG(arrSubtype, returnType);
							node.setSCG(foldSCG);
							node.setType(arrSubtype);
							return;
						}
					}
				}
			}
		}
		
		typeCheckError(node, childTypes);
		return;
	}
	@Override
	public void visitLeave(MapOperatorNode node) {
		assert node.nChildren() == 2;
		ParseNode left  = node.child(0);
		ParseNode right = node.child(1);
		List<Type> childTypes = Arrays.asList(left.getType(), right.getType());
		
		// First expression must be an array type
		if (childTypes.get(0) instanceof ArrayType) {
			Type arrSubtype = ((ArrayType)childTypes.get(0)).getSubtype();
			// Second expression must be a one-argument-type lambda
			// where the argument has the type of the array’s subtype
			if (childTypes.get(1) instanceof LambdaType) {
				List<Type> lambdaTypes = ((LambdaType)childTypes.get(1)).getTypeList();
				if (lambdaTypes.size() == 1 && lambdaTypes.get(0) == arrSubtype) {
					Type returnType = ((LambdaType)childTypes.get(1)).getReturnType();
					Type mapType = ArrayType.withSubtype(returnType);
					MapOperatorSCG mapSCG = new MapOperatorSCG(arrSubtype, returnType);
					node.setSCG(mapSCG);
					node.setType(mapType);
					return;
				}
			}
		}
		
		typeCheckError(node, childTypes);
		return;
	}
	@Override
	public void visitLeave(ReduceOperatorNode node) {
		assert node.nChildren() == 2;
		ParseNode left  = node.child(0);
		ParseNode right = node.child(1);
		List<Type> childTypes = Arrays.asList(left.getType(), right.getType());
		
		// First expression must be an array type
		if (childTypes.get(0) instanceof ArrayType) {
			Type arrSubtype = ((ArrayType)childTypes.get(0)).getSubtype();
			// Second expression must be a one-argument-type lambda
			// where the argument has the type of the array’s subtype
			// and the return type is boolean
			if (childTypes.get(1) instanceof LambdaType) {
				List<Type> lambdaTypes = ((LambdaType)childTypes.get(1)).getTypeList();
				if (lambdaTypes.size() == 1 && lambdaTypes.get(0) == arrSubtype) {
					Type returnType = ((LambdaType)childTypes.get(1)).getReturnType();
					if (returnType == PrimitiveType.BOOLEAN) {
						Type reduceType = childTypes.get(0);
						ReduceOperatorSCG reduceSCG = new ReduceOperatorSCG(arrSubtype, returnType);
						node.setSCG(reduceSCG);
						node.setType(reduceType);
						return;
					}
				}
			}
		}
		
		typeCheckError(node, childTypes);
		return;
	}
	@Override
	public void visitLeave(ReverseOperatorNode node) {
		assert node.nChildren() == 1;
		Type operandType = node.child(0).getType();
		
		if (operandType == PrimitiveType.STRING) {
			node.setType(operandType);
			node.setSCG(new StringReverseSCG());
			return;
		}
		
		if (operandType instanceof ArrayType) {
			node.setType(operandType);
			node.setSCG(new ArrayReverseSCG(((ArrayType) operandType).getSubtype()));
			return;
		}
		
		typeCheckError(node, Arrays.asList(operandType));
	}
	@Override
	public void visitLeave(ZipOperatorNode node) {
		assert node.nChildren() == 3;
		ParseNode c1 = node.child(0);
		ParseNode c2 = node.child(1);
		ParseNode c3 = node.child(2);
		List<Type> childTypes = Arrays.asList(c1.getType(), c2.getType(), c3.getType());
		
		// First two expression arguments must be arrays
		if (childTypes.get(0) instanceof ArrayType) {
			if (childTypes.get(1) instanceof ArrayType) {
				Type arrSubtype1 = ((ArrayType)childTypes.get(0)).getSubtype();
				Type arrSubtype2 = ((ArrayType)childTypes.get(1)).getSubtype();
				
				// Third expression is a lambda which takes two arguments
				// with argument types the subtypes of the first and second arrays
				if (childTypes.get(2) instanceof LambdaType) {
					List<Type> lambdaTypes = ((LambdaType)childTypes.get(2)).getTypeList();
					if (lambdaTypes.size() == 2) {
						if (lambdaTypes.get(0) == arrSubtype1 && lambdaTypes.get(1) == arrSubtype2) {
							Type returnType = ((LambdaType)childTypes.get(2)).getReturnType();
							Type zipType = ArrayType.withSubtype(returnType);
							ZipOperatorSCG zipSCG = new ZipOperatorSCG(arrSubtype1, arrSubtype2, returnType);
							node.setSCG(zipSCG);
							node.setType(zipType);
							return;						
						}
					}
				}
			}
		}
		
		typeCheckError(node, childTypes);
		return;
	}
	@Override
	public void visitLeave(UnaryOperatorNode node) {
		assert node.nChildren() == 1;
		ParseNode left  = node.child(0);
		List<Type> childTypes = Arrays.asList(left.getType());
		Lextant operator = operatorFor(node);
		
		// Handle array length
		if (childTypes.get(0) instanceof ArrayType) {
			if (operator == Keyword.LENGTH) {
				FunctionSignature signature = new FunctionSignature(new ArrayLengthSCG(), new ArrayType(), PrimitiveType.INTEGER);
				node.setSignature(signature);		
				node.setType(signature.resultType());
				return;
			}
		}
	
		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
		
		if (signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
		} else {
			typeCheckError(node, childTypes);
		}
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
				logError("cannot cast type '" + childTypes.get(0).infoString() + "' to type '" + childTypes.get(1).infoString() + "'");
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
		if(isForIdentifier(node)) {
			ParseNode parent = node.getParent();
			if (parent.getToken().isLextant(Keyword.INDEX)) {
				node.setType(PrimitiveType.INTEGER);
				addBinding(node, PrimitiveType.INTEGER, false);
				return;
			} else if (parent.getToken().isLextant(Keyword.ELEMENT)) {
				Type identifierType = parent.child(0).getType();
				if (identifierType instanceof ArrayType) {
					identifierType = ((ArrayType) identifierType).getSubtype();
				}
				if (identifierType == PrimitiveType.STRING) {
					identifierType = PrimitiveType.CHARACTER;
				}
				
				node.setType(identifierType);
				addBinding(node, identifierType, false);
				return;
			}
		} 
		
		if(!isBeingDeclared(node) && !isBeingAssigned(node) && 
		   !isFunctionIdentifier(node) && !isFunctionParameter(node)) {		
			node.setBinding(node.findVariableBinding());
			node.setType(node.getBinding().getType());
			return;
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
	private boolean isForIdentifier(IdentifierNode node) {
		ParseNode parent = node.getParent();
		return (parent instanceof ForNode) && (node == parent.child(1));
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// IndexNode
	@Override
	public void visitLeave(IndexNode node) {
		assert node.nChildren() >= 2;
		
		List<Type> childTypes = new ArrayList<Type>();
		node.getChildren().forEach((child) -> childTypes.add(child.getType()));
		
		Lextant operator = operatorFor(node);
		
		// Ensure index is correct type
		if (childTypes.get(1) == PrimitiveType.INTEGER) {
			if (childTypes.get(0) instanceof ArrayType) {
				if (node.nChildren() == 2) {
					Type subtype = ((ArrayType) childTypes.get(0)).getSubtype();
					if (subtype instanceof TypeLiteral) {
						subtype = ((TypeLiteral) subtype).getType();
					}
					
					FunctionSignature signature = new FunctionSignature(new ArrayOffsetSCG(), new ArrayType(), PrimitiveType.INTEGER, subtype);
					
					node.setType(subtype);
					node.setIndexType(childTypes.get(0));
					node.setSignature(signature);
					return;
				}
			}
			
			if (childTypes.get(0) == PrimitiveType.STRING) {
				FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
				
				if (node.nChildren() > 2 && childTypes.get(2) != PrimitiveType.INTEGER) {
					typeCheckError(node, childTypes);
					return;
				}
				
				node.setType(signature.resultType());
				node.setIndexType(childTypes.get(0));
				node.setSignature(signature);
				return;
			}
		}
		
		typeCheckError(node, childTypes);
		return;
	}

	///////////////////////////////////////////////////////////////////////////
	// helper methods
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
	private void addGlobalBinding(IdentifierNode identifierNode, Type type, Boolean mutable, FunctionSignature signature) {
		Scope globalScope = identifierNode.getGlobalScope();
		Scope localScope = identifierNode.getLocalScope();
		
		Binding binding = globalScope.createStaticGlobalBinding(identifierNode, type);
		localScope.createStaticEmptyBinding(identifierNode, binding);

		binding.setStatic(true);
		binding.setMutability(mutable);
		binding.setSignature(signature);
		identifierNode.setBinding(binding);
	}
	private Lextant operatorFor(ParseNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
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
		
		if (node instanceof FunctionInvocationNode) {
			logError("lambda not defined for types " + errorTypes  + " at " + token.getLocation());
		} else {
			logError("operator " + token.getLexeme() + " not defined for types " 
				 + errorTypes  + " at " + token.getLocation());
		}
		
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
