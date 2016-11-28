package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.codeGenerator.operators.FoldOperatorSCG;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class FoldOperatorNode extends OperatorNode {
	FoldOperatorSCG scg;

	public FoldOperatorNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.FOLD));
		this.scg = null;
	}

	public FoldOperatorNode(ParseNode node) {
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

	public void setSCG(FoldOperatorSCG scg) {
		this.scg = scg;
	}
	public FoldOperatorSCG getSCG() {
		return this.scg;
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static FoldOperatorNode withChildren(Token token, ParseNode left) {
		FoldOperatorNode node = new FoldOperatorNode(token);
		node.appendChild(left);
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
