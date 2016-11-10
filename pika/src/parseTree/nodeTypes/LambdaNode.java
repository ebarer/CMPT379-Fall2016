package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.signatures.FunctionSignature;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class LambdaNode extends ParseNode {
	protected FunctionSignature signature;
	
	public LambdaNode(Token token) {
		super(token);
	}

	public LambdaNode(ParseNode node) {
		super(node);
		initChildren();
	}

	
	////////////////////////////////////////////////////////////
	// attributes
	
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
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static LambdaNode withChildren(Token token, ParseNode lambdaTypes, ParseNode block) {
		LambdaNode node = new LambdaNode(token);
		node.appendChild(lambdaTypes);
		node.appendChild(block);
		return node;
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
		
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}

}
