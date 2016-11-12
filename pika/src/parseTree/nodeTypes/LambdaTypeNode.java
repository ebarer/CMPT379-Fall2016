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
	//accept a visitor
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visit(this);
	}

}
