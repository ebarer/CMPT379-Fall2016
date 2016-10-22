package semanticAnalyzer;

import java.util.Arrays;
import java.util.List;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import logging.PikaLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.AssignmentNode;
import parseTree.nodeTypes.BinaryOperatorNode;
import parseTree.nodeTypes.BlockNode;
import parseTree.nodeTypes.BooleanConstantNode;
import parseTree.nodeTypes.CastNode;
import parseTree.nodeTypes.CharacterNode;
import parseTree.nodeTypes.MainBlockNode;
import parseTree.nodeTypes.DeclarationNode;
import parseTree.nodeTypes.ErrorNode;
import parseTree.nodeTypes.FloatingConstantNode;
import parseTree.nodeTypes.IdentifierNode;
import parseTree.nodeTypes.IfNode;
import parseTree.nodeTypes.IntegerConstantNode;
import parseTree.nodeTypes.NewlineNode;
import parseTree.nodeTypes.PrintStatementNode;
import parseTree.nodeTypes.ProgramNode;
import parseTree.nodeTypes.RationalOperatorNode;
import parseTree.nodeTypes.SpaceNode;
import parseTree.nodeTypes.StringNode;
import parseTree.nodeTypes.UnaryOperatorNode;
import parseTree.nodeTypes.WhileNode;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.signatures.FunctionSignatures;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
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
			IdentifierNode target = (IdentifierNode) node.child(0);
			target.setBinding(target.findVariableBinding());
			target.setType(target.getBinding().getType());
			Type targetType = target.getBinding().getType();
			
			if (target.isMutable() == null) {
				return;
			}
			
			// Check for immutability
			if (!target.isMutable()) {
				String targetName = target.getToken().getLexeme();
				logError("Cannot assign to value: '" + targetName + "' is a 'const' constant.");
				return;
			}
			
			ParseNode expression = node.child(1);
			Type expressionType = expression.getType();
			
			// Check for type-match
			if (targetType != expressionType) {
				logError("Cannot assign value of type '" + expressionType + "' to type '" + targetType + "'");
			}
			
			node.setType(targetType);			
		}
	}
	@Override
	public void visitLeave(IfNode node) {
		assert node.nChildren() >= 2;
	}
	@Override
	public void visitLeave(WhileNode node) {
		assert node.nChildren() >= 2;
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

		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
		
		if (signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
		} else {
			typeCheckError(node, childTypes);
			node.setType(PrimitiveType.ERROR);
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

		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);
		
		if(signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
		} else {
			typeCheckError(node, childTypes);
			node.setType(PrimitiveType.ERROR);
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

		FunctionSignature signature = FunctionSignatures.signature(Punctuator.PIPE, childTypes);
		
		if(signature.accepts(childTypes)) {
			node.setSignature(signature);
			node.setType(signature.resultType());
		} else {
			logError("cannot cast type '" + childTypes.get(0) + "' to type '" + childTypes.get(1) + "'");
			node.setType(PrimitiveType.ERROR);
		}
	}


	///////////////////////////////////////////////////////////////////////////
	// simple leaf nodes
	@Override
	public void visit(BooleanConstantNode node) {
		node.setType(PrimitiveType.BOOLEAN);
	}
	@Override
	public void visit(ErrorNode node) {
		node.setType(PrimitiveType.ERROR);
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
	///////////////////////////////////////////////////////////////////////////
	// IdentifierNodes, with helper methods
	@Override
	public void visit(IdentifierNode node) {
		if(!isBeingDeclared(node) && !isBeingAssigned(node)) {		
			Binding binding = node.findVariableBinding();
			node.setType(binding.getType());
			node.setBinding(binding);
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
		System.exit(0);
	}
}