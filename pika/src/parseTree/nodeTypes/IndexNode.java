package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class IndexNode extends ParseNode {
	protected Type indexedItemType = PrimitiveType.ANY;
	protected FunctionSignature signature;

	public IndexNode(Token token) {
		super(token);
		signature = null;
	}

	public IndexNode(ParseNode node) {
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
	
	public void setIndexType(Type t) {
		this.indexedItemType = t;
	}
	public Type getIndexType() {
		return this.indexedItemType;
	}
	
	public void setSignature(FunctionSignature signature) {
		this.signature = signature;
	}
	public FunctionSignature getSignature() {
		return signature;
	}

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static IndexNode withChildren(Token token, ParseNode child, ParseNode index) {
		IndexNode node = new IndexNode(token);
		node.appendChild(child);
		node.appendChild(index);
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
