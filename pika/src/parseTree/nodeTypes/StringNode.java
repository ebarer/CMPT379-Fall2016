package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.StringToken;
import tokens.Token;

public class StringNode extends ParseNode {	
	public StringNode(Token token) {
		super(token);
		assert(token instanceof StringToken);
	}
	public StringNode(ParseNode node) {
		super(node);
	}

////////////////////////////////////////////////////////////
// attributes
	
	public String getValue() {
		return stringToken().getValue();
	}

	public StringToken stringToken() {
		return (StringToken)token;
	}
	
	public IdentifierNode getIdentifier() {
		if (this.getParent().child(0) instanceof IdentifierNode) {
			return (IdentifierNode) this.getParent().child(0);
		} else {
			return null;
		}
	}

///////////////////////////////////////////////////////////
// accept a visitor
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visit(this);
	}

}
