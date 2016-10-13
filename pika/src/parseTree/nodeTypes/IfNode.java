package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import tokens.LextantToken;
import tokens.Token;

public class IfNode extends ParseNode {
	
	public IfNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.IF));
	}
	
	public IfNode(ParseNode node) {
		super(node);
		initChildren();
	}
	
	public IfNode(Token token, ParseNode condition, ParseNode blockStatement) {
		super(token);
		assert(token.isLextant(Keyword.IF));
		
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
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static IfNode withElse(Token token, ParseNode condition, ParseNode blockStatement, ParseNode elseStatement) {
		IfNode node = new IfNode(token, condition, blockStatement);
		node.appendChild(elseStatement);
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
