package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import tokens.LextantToken;
import tokens.Token;
import semanticAnalyzer.signatures.FunctionSignature;

public class BinaryOperatorNode extends OperatorNode {
	protected FunctionSignature signature;

	public BinaryOperatorNode(Token token) {
		super(token);
		assert(token instanceof LextantToken);
	}

	public BinaryOperatorNode(ParseNode node) {
		super(node);
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Lextant getOperator() {
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
	
	public static BinaryOperatorNode withChildren(Token token, ParseNode left, ParseNode right) {
		BinaryOperatorNode node = new BinaryOperatorNode(token);
		node.appendChild(left);
		node.appendChild(right);
		return node;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//delegates
	
	public boolean isComparator() {
		Lextant operator = getOperator();
		return (operator == Punctuator.LESS_OR_EQUAL || operator == Punctuator.LESS ||
				operator == Punctuator.EQUAL || operator == Punctuator.NOT_EQUAL ||
				operator == Punctuator.GREATER || operator == Punctuator.GREATER_OR_EQUAL);
	}
	
	public boolean isBooleanOperator() {
		Lextant operator = getOperator();
		return (operator == Punctuator.AND || operator == Punctuator.OR);
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
