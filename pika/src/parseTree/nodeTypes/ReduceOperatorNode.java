package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.codeGenerator.operators.ReduceOperatorSCG;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ReduceOperatorNode extends OperatorNode {
	ReduceOperatorSCG scg;

	public ReduceOperatorNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.REDUCE));
		this.scg = null;
	}

	public ReduceOperatorNode(ParseNode node) {
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

	public void setSCG(ReduceOperatorSCG scg) {
		this.scg = scg;
	}
	public ReduceOperatorSCG getSCG() {
		return this.scg;
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static ReduceOperatorNode withChildren(Token token, ParseNode left, ParseNode right) {
		ReduceOperatorNode node = new ReduceOperatorNode(token);
		node.appendChild(left);
		node.appendChild(right);
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
