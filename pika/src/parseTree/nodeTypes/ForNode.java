package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ForNode extends ParseNode {
	SimpleCodeGenerator scg;
	
	String loopLabel;
	String bodyLabel;
	String incLabel;
	String joinLabel;
	
	public ForNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.INDEX, Keyword.ELEMENT));
		scg = null;
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
	public boolean isForElement() {
		if (token.isLextant(Keyword.ELEMENT)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setLabels(String loop, String body, String inc, String join) {
		this.loopLabel = loop;
		this.bodyLabel = body;
		this.incLabel = inc;
		this.joinLabel = join;
	}
	public String getLoopLabel() {
		return this.loopLabel;
	}
	public String getBodyLabel() {
		return this.bodyLabel;
	}
	public String getIncrementLabel() {
		return this.incLabel;
	}
	public String getJoinLabel() {
		return this.joinLabel;
	}
	
	public void setSCG(SimpleCodeGenerator scg) {
		this.scg = scg;
	}
	public SimpleCodeGenerator getSCG() {
		return scg;
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
