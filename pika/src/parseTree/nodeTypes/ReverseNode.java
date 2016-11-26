package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ReverseNode extends ParseNode {
	SimpleCodeGenerator scg;

	public ReverseNode(Token token) {
		super(token);
		scg = null;
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
	
	public void setSCG(SimpleCodeGenerator scg) {
		this.scg = scg;
	}
	public SimpleCodeGenerator getSCG() {
		return scg;
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
