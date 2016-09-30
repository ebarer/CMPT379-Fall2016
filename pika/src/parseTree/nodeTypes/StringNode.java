package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import symbolTable.Binding;
import tokens.StringToken;
import tokens.Token;

public class StringNode extends ParseNode {
	private String identifier;
	
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
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getIdentifier() {
		return identifier;
	}

///////////////////////////////////////////////////////////
// accept a visitor
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visit(this);
	}

}
