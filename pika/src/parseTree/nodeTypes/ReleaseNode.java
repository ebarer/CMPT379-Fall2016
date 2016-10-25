package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ReleaseNode extends ParseNode {

	public ReleaseNode(Token token) {
		super(token);
		assert(token instanceof LextantToken);
	}

	public ReleaseNode(ParseNode node) {
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

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static ReleaseNode withChild(Token token, ParseNode child) {
		ReleaseNode node = new ReleaseNode(token);
		node.appendChild(child);
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
