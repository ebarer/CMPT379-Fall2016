package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ForNode extends ParseNode {
	String loopLabel;
	String bodyLabel;
	String joinLabel;
	
	public ForNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.INDEX, Keyword.ELEMENT));
	}
	
	public ForNode(ParseNode node) {
		super(node);
		initChildren();
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Lextant getDeclarationType() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}
	
	public void setLabels(String loop, String body, String join) {
		this.loopLabel = loop;
		this.bodyLabel = body;
		this.joinLabel = join;
	}
	public String getLoopLabel() {
		return this.loopLabel;
	}
	public String getBodyLabel() {
		return this.bodyLabel;
	}
	public String getJoinLabel() {
		return this.joinLabel;
	}
	
	
	////////////////////////////////////////////////////////////
	//convenience factory
	
	public static ForNode withChildren(Token token, ParseNode identifier, ParseNode sequence, ParseNode block) {
		ForNode node = new ForNode(token);
		node.appendChild(sequence);
		node.appendChild(identifier);
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
