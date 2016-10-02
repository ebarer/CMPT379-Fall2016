package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import tokens.LextantToken;
import tokens.Token;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

public class CastNode extends ParseNode {
	protected PrimitiveType castType;
	protected FunctionSignature signature;

	public CastNode(Token token) {
		super(token);
		assert(token.isLextant(Punctuator.PIPE));
	}

	public CastNode(ParseNode node) {
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
	
	public static CastNode withChildren(Token token, ParseNode left, PrimitiveType right) {
		CastNode node = new CastNode(token);
		node.appendChild(left);
		node.castType = right;
		//node.signature = FunctionSignatures.signature(token.getLexeme(), left.getType(), right.getLexeme());
		return node;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//delegates
	
	public Type getExpressionType() {
		return this.child(0).getType();
	}
	public Type getCastType() {
		return this.castType;
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
