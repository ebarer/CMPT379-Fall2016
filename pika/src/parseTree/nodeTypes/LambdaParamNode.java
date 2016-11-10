package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.types.*;
import tokens.Token;

public class LambdaParamNode extends ParseNode {

	public LambdaParamNode(Token token) {
		super(token);
	}

	public LambdaParamNode(ParseNode node) {
		super(node);
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static LambdaParamNode withChildren(Token token, Type type, ParseNode identifier) {
		LambdaParamNode node = new LambdaParamNode(token);
		node.setType(type);
		node.appendChild(identifier);
		return node;
	}
	
	public static LambdaParamNode withChildren(Token token, Type type, ParseNode identifier, ParseNode child) {
		LambdaParamNode node = new LambdaParamNode(token);
		node.setType(type);
		node.appendChild(identifier);
		node.appendChild(child);
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
