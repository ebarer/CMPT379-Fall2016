package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.codeGenerator.operators.ZipOperatorSCG;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ZipOperatorNode extends OperatorNode {
	ZipOperatorSCG scg;

	public ZipOperatorNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.ZIP));
		this.scg = null;
	}

	public ZipOperatorNode(ParseNode node) {
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

	public void setSCG(ZipOperatorSCG scg) {
		this.scg = scg;
	}
	public ZipOperatorSCG getSCG() {
		return this.scg;
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
			
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
