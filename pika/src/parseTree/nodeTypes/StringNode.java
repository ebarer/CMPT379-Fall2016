package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import symbolTable.Binding;
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

///////////////////////////////////////////////////////////
// accept a visitor
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visit(this);
	}

}
