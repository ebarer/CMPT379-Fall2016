package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import asmCodeGenerator.codeGenerator.operators.MapOperatorSCG;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class MapOperatorNode extends OperatorNode {
	MapOperatorSCG scg;

	public MapOperatorNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.MAP));
		this.scg = null;
	}

	public MapOperatorNode(ParseNode node) {
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

	public void setSCG(MapOperatorSCG scg) {
		this.scg = scg;
	}
	public MapOperatorSCG getSCG() {
		return this.scg;
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static MapOperatorNode withChildren(Token token, ParseNode left, ParseNode right) {
		MapOperatorNode node = new MapOperatorNode(token);
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
