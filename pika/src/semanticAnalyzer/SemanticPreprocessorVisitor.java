package semanticAnalyzer;

import java.util.ArrayList;
import java.util.List;
import asmCodeGenerator.codeStorage.ASMOpcode;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.*;
import semanticAnalyzer.types.*;
import symbolTable.Binding;
import symbolTable.Scope;

class SemanticPreprocessorVisitor extends ParseNodeVisitor.Default {
	
	@Override
	public void visitLeave(ParseNode node) {
		throw new RuntimeException("Node class unimplemented in SemanticAnalysisVisitor: " + node.getClass());
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// constructs larger than statements
	@Override
	public void visitEnter(ProgramNode node) {
		Scope scope = Scope.createProgramScope();
		node.setScope(scope);
	}

	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for scoping.
	private void createParameterScope(ParseNode node) {
		Scope scope = Scope.createParameterScope();
		node.setScope(scope);
	}
	private void createProcedureScope(ParseNode node) {
		Scope baseScope = node.getLocalScope();
		Scope scope = baseScope.createSubscope();
		node.setScope(scope);
	}
	private void enterScope(ParseNode node) {
		node.getScope().enter();
	}
	private void leaveScope(ParseNode node) {
		node.getScope().leave();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// functionDefinitions
	@Override	
	public void visitLeave(FunctionDefinitionNode node) {
		if (node.child(0) instanceof IdentifierNode && node.child(1) instanceof LambdaNode) {
			IdentifierNode identifier = (IdentifierNode) node.child(0);
			LambdaNode lambda = (LambdaNode) node.child(1);

			Type functionType = getType(lambda.child(0));
			FunctionSignature signature = ((LambdaNode) node.child(1)).getSignature();
			
			node.setSignature(signature);
			node.setType(functionType);
			identifier.setType(functionType);
			
			addBinding(identifier, functionType);			
		}		
	}

	
	///////////////////////////////////////////////////////////////////////////
	// lambda
	@Override
	public void visitEnter(LambdaNode node) {
		createParameterScope(node);
		enterScope(node);
	}
	@Override
	public void visitLeave(LambdaNode node) {		
		if (node.child(0) instanceof LambdaParamTypeNode) {
			LambdaParamTypeNode params = (LambdaParamTypeNode) node.child(0);
			
			List<Type> childTypes = new ArrayList<Type>();
			LambdaType lambdaType = new LambdaType();

			params.getChildren().forEach((child) -> {
				lambdaType.addType(child.getType());
				childTypes.add(getType(child));
			});
			
			Type returnType = getType(params);
			
			FunctionSignature signature = new FunctionSignature(ASMOpcode.Nop, childTypes, returnType);
			lambdaType.setReturnType(returnType);
			
			node.setSignature(signature);
			node.setType(lambdaType);
		}
		
		leaveScope(node);
	}	
	@Override
	public void visitLeave(LambdaParamNode node) {
		if (node.child(0) instanceof IdentifierNode) {
			IdentifierNode identifier = (IdentifierNode) node.child(0);

			Type paramType = getType(node);
			addBinding(identifier, paramType);
		}
	}

	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for types
	private Type getType(ParseNode node) {
		Type t = node.getType();
		
		if (t instanceof ArrayType || t instanceof LambdaType) {
			return t;
		}
		if (t instanceof TypeLiteral) {
			return PrimitiveType.withTypeLiteral((TypeLiteral)t);
		}
		
		return PrimitiveType.ERROR;
	}

	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for binding
	private void addBinding(IdentifierNode identifierNode, Type type) {
		Scope scope = identifierNode.getLocalScope();
		Binding binding = scope.createBinding(identifierNode, type);
		binding.setMutability(false);
		identifierNode.setBinding(binding);
	}
}
