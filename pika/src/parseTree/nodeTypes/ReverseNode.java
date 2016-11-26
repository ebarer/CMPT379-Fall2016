package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ReverseNode extends ParseNode {

	public ReverseNode(Token token) {
		super(token);
	}

	public ReverseNode(ParseNode node) {
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

	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static ReverseNode withChildren(Token token, ParseNode string) {
		ReverseNode node = new ReverseNode(token);
		node.appendChild(string);
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
