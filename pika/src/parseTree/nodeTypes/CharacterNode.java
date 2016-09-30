package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.CharacterToken;
import tokens.Token;

public class CharacterNode extends ParseNode {
	public CharacterNode(Token token) {
		super(token);
		assert(token instanceof CharacterToken);
	}
	public CharacterNode(ParseNode node) {
		super(node);
	}

////////////////////////////////////////////////////////////
// attributes
	
	public Character getValue() {
		return characterToken().getValue();
	}

	public CharacterToken characterToken() {
		return (CharacterToken)token;
	}	

///////////////////////////////////////////////////////////
// accept a visitor
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visit(this);
	}

}
