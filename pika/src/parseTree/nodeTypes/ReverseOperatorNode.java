package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ReverseOperatorNode extends ParseNode {
	SimpleCodeGenerator scg;

	public ReverseOperatorNode(Token token) {
		super(token);
		scg = null;
	}

	public ReverseOperatorNode(ParseNode node) {
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
	
	public void setSCG(SimpleCodeGenerator scg) {
		this.scg = scg;
	}
	public SimpleCodeGenerator getSCG() {
		return scg;
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static ReverseOperatorNode withChildren(Token token, ParseNode string) {
		ReverseOperatorNode node = new ReverseOperatorNode(token);
		node.appendChild(string);
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
