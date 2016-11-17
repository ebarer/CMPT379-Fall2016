package semanticAnalyzer;

import java.util.ArrayList;
import java.util.List;
import asmCodeGenerator.codeStorage.ASMOpcode;
import logging.PikaLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.*;
import semanticAnalyzer.types.*;
import symbolTable.Binding;
import symbolTable.Scope;
import tokens.Token;

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

			Type functionType = lambda.getType();
			FunctionSignature signature = ((LambdaNode) node.child(1)).getSignature();
			
			node.setSignature(signature);
			node.setType(functionType);
			identifier.setType(functionType);
			
			addBinding(identifier, functionType, lambda.getStartLabel(), signature);		
		}		
	}

	
	///////////////////////////////////////////////////////////////////////////
	// lambda
	@Override
	public void visitEnter(LambdaNode node) {
		node.generateLabels();
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
			if (paramType == PrimitiveType.VOID) {
				node.setType(PrimitiveType.ERROR);
				Token token = node.getToken();
				logError("Parameter cannot be defined as type VOID at " + token.getLocation());
				return;
			}
			
			FunctionSignature signature = null;
			if (paramType instanceof LambdaType) {
				signature = ((LambdaType) paramType).getSignature();
			}
			
			addBinding(identifier, paramType, null, signature);
		}
	}

	
	/////////////////////////////////////////////////////////////////////////
	// lambdaType
	public void visit(LambdaTypeNode node) {
	}
	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for types
	private Type getType(ParseNode node) {
		Type t = node.getType();

		if (t instanceof PrimitiveType) {
			return t;
		}
		if (t instanceof TypeLiteral) {
			return ((TypeLiteral)t).getType();
		}
		if (t instanceof ArrayType) {
			return t;
		}
		if (t instanceof LambdaType) {
			return t;
		}
		
		return PrimitiveType.ERROR;
	}

	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for binding
	private void addBinding(IdentifierNode identifierNode, Type type, String label, FunctionSignature signature) {
		Scope scope = identifierNode.getLocalScope();
		Binding binding = scope.createBinding(identifierNode, type);
		binding.setMutability(false);
		binding.setSignature(signature);
		binding.setLabel(label);
		identifierNode.setBinding(binding);
	}

	
	///////////////////////////////////////////////////////////////////////////
	// error logging/printing
	private void logError(String message) {
		PikaLogger log = PikaLogger.getLogger("compiler.semanticAnalyzer");
		log.severe(message);
	}
}
