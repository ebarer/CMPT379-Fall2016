package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class WhileNode extends ParseNode {
	
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
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
