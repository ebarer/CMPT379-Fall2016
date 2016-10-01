package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.IntegerToken;
import tokens.Token;

public class IntegerConstantNode extends ParseNode {
	public IntegerConstantNode(Token token) {
		super(token);
		assert(token instanceof IntegerToken);
	}
	public IntegerConstantNode(ParseNode node) {
		super(node);
	}

////////////////////////////////////////////////////////////
// attributes
	
	public int getValue() {
		return integerToken().getValue();
	}

	public IntegerToken integerToken() {
		return (IntegerToken)token;
	}	

///////////////////////////////////////////////////////////
// accept a visitor
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visit(this);
	}

}
