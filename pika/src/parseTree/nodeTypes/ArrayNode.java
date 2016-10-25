package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.LextantToken;
import tokens.Token;
import semanticAnalyzer.types.*;

public class ArrayNode extends ParseNode {
	boolean isEmpty = false;
	
	public ArrayNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.NEW, Keyword.CLONE));
	}
	public ArrayNode(ParseNode node) {
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

	public void setSubtype(Type subtype) {
		ArrayType type = new ArrayType();
		type.setSubtype(subtype);
		super.setType(type);		
	}
	public Type getSubtype() {
		return ((ArrayType)super.getType()).getSubtype();
	}

	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public int getAllocateSize() {
		int headerSize = this.getType().getSize();
		int typeSize = this.getSubtype().getSize();
		int arraySize = this.nChildren();
		int size = headerSize + (typeSize * arraySize);
		return size;
	}
	public int getOffset(int i) {
		int headerSize = 16;
		int typeSize = this.getSubtype().getSize();
		int offset = headerSize + (typeSize * i);
		return offset;
	}
	
	
////////////////////////////////////////////////////////////
// convenience factory

	public static ArrayNode withChildren(Token token, ParseNode cloneNode, boolean isEmpty) {
		ArrayNode node = new ArrayNode(token);
		node.appendChild(cloneNode);
		node.isEmpty = isEmpty;
		return node;
	}
	
	public static ArrayNode withChildren(Token token, Type type, ParseNode sizeNode, boolean isEmpty) {
		ArrayNode node = new ArrayNode(token);
		node.appendChild(sizeNode);
		node.setType(type);
		node.isEmpty = isEmpty;
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
