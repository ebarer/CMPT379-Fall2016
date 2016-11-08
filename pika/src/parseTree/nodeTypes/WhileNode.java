package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.Labeller;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class WhileNode extends ParseNode {
	String loopLabel;
	String joinLabel;
	
	public WhileNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.WHILE));
	}
	
	public WhileNode(ParseNode node) {
		super(node);
		initChildren();
	}
	
	public WhileNode(Token token, ParseNode condition, ParseNode blockStatement) {
		super(token);
		assert(token.isLextant(Keyword.WHILE));
		
		this.appendChild(condition);
		this.appendChild(blockStatement);
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Lextant getDeclarationType() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}
	
	public void setLabels(String loop, String join) {
		this.loopLabel = loop;
		this.joinLabel = join;
	}
	public String getLoopLabel() {
		return this.loopLabel;
	}
	public String getJoinLabel() {
		return this.joinLabel;
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
