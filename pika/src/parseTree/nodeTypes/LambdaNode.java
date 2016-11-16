package parseTree.nodeTypes;

import java.util.List;

import asmCodeGenerator.Labeller;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.*;
import tokens.LextantToken;
import tokens.Token;

public class LambdaNode extends ParseNode {
	protected FunctionSignature signature;
	
	String startLabel;
	String exitHandshakeLabel;
	String exitErrorLabel;
	
	public LambdaNode(Token token) {
		super(token);
	}

	public LambdaNode(ParseNode node) {
		super(node);
		initChildren();
	}

	
	////////////////////////////////////////////////////////////
	// attributes
	
	public Type getReturnType() {
		return ((LambdaType) this.getType()).getReturnType();
	}
	public LextantToken lextantToken() {
		return (LextantToken)token;
	}
	
	public void setSignature(FunctionSignature signature) {
		this.signature = signature;
	}
	public FunctionSignature getSignature() {
		return signature;
	}
	
	public void generateLabels() {
		Labeller labeller;
		
		if (this.getParent() instanceof FunctionDefinitionNode) {
			 labeller = new Labeller("function");				
		} else {
			 labeller = new Labeller("lambda");
		}
		
		// Generate labels
		this.startLabel = labeller.newLabel();
		this.exitHandshakeLabel = labeller.newLabel("exit-handshake");
		this.exitErrorLabel = labeller.newLabel("exit-error");
	}
	public String getStartLabel() {
		return this.startLabel;
	}
	public String getExitHandshakeLabel() {
		return this.exitHandshakeLabel;
	}
	public String getExitErrorLabel() {
		return this.exitErrorLabel;
	}
	
	public int getFrameSize() {
		int size = 8;		// 4 bytes for Dynamic Link, 4 bytes for Return Address

		List<ParseNode> localVars = this.child(1).getChildren();
		for (ParseNode child : localVars) {
			size += child.getType().getSize();
		}

		return size;
	}
	
	public int getArgSize() {
		int size = 0;
		
		List<ParseNode> paramArgs = this.child(0).getChildren();
		for (ParseNode child : paramArgs) {
			size += child.getType().getSize();
		}
		
		return size;
	}
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static LambdaNode withChildren(Token token, ParseNode lambdaTypes, ParseNode block) {
		LambdaNode node = new LambdaNode(token);
		node.appendChild(lambdaTypes);
		node.appendChild(block);
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
