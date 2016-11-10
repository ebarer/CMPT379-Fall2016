package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.types.*;
import tokens.Token;

public class LambdaTypeNode extends ParseNode {
	
	public LambdaTypeNode(Token token) {
		super(token);
		this.setType(new LambdaType());
	}

	public LambdaTypeNode(ParseNode node) {
		super(node);
		initChildren();
	}

	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Type returnType() {
		return ((LambdaType)this.getType()).getReturnType();
	}
	public void setReturnType(Type type) {
		((LambdaType)this.getType()).setReturnType(type);
	}
	public void addChildType(Type type) {
		((LambdaType)this.getType()).addType(type);
	}
	
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
		
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}

}
