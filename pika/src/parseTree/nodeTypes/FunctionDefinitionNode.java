package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.signatures.FunctionSignature;
import symbolTable.Binding;
import symbolTable.Scope;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import logging.PikaLogger;
import tokens.LextantToken;
import tokens.Token;

public class FunctionDefinitionNode extends ParseNode {
	protected FunctionSignature signature;
	private Binding binding;
	private Scope parameterScope;
	String functionLabel;
	
	public FunctionDefinitionNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.FUNCTION));
		this.binding = null;
	}
	
	public FunctionDefinitionNode(ParseNode node) {
		super(node);
		
		if(node instanceof FunctionDefinitionNode) {
			this.binding = ((FunctionDefinitionNode) node).binding;
		} else {
			this.binding = null;
		}
		
		initChildren();
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public void setBinding(Binding binding) {
		this.binding = binding;
	}
	public Binding getBinding() {
		return binding;
	}
	
	public Lextant getReturnType() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}
	
	public void setSignature(FunctionSignature signature) {
		this.signature = signature;
	}
	public FunctionSignature getSignature() {
		return signature;
	}
	
	public void setLabel(String label) {
		this.functionLabel = label;
	}
	public String getLabel() {
		return functionLabel;
	}
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static FunctionDefinitionNode withChildren(Token token, ParseNode identifier, ParseNode lambda) {
		FunctionDefinitionNode node = new FunctionDefinitionNode(token);
		node.appendChild(identifier);
		node.appendChild(lambda);
		return node;
	}
	
	
	////////////////////////////////////////////////////////////
	//Speciality functions
	
	public Binding findVariableBinding() {
		String identifier = token.getLexeme();
		
		for(ParseNode current : pathToRoot()) {
			if(current.containsBindingOf(identifier)) {
				parameterScope = current.getScope();
				return current.bindingOf(identifier);
			}
		}
		useBeforeDefineError();
		return Binding.nullInstance();
	}

	public Scope getParameterScope() {
		findVariableBinding();
		return parameterScope;
	}
	public void useBeforeDefineError() {
		PikaLogger log = PikaLogger.getLogger("compiler.semanticAnalyzer.functionDefinitionNode");
		Token token = getToken();
		log.severe("functionDefinition " + token.getLexeme() + " used before defined at " + token.getLocation());
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
