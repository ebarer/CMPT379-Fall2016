package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import tokens.LextantToken;
import tokens.Token;

public class AssignmentNode extends ParseNode {
	
	public AssignmentNode(Token token) {
		super(token);
		assert(token.isLextant(Punctuator.ASSIGN));
	}
	
	public AssignmentNode(ParseNode node) {
		super(node);
		initChildren();
	}
	
	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Lextant getDeclarationType() {
		return lextantToken().getLextant();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}	
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static AssignmentNode withChildren(Token token, ParseNode target, ParseNode expression) {
		AssignmentNode node = new AssignmentNode(token);
		node.appendChild(target);
		node.appendChild(expression);
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
