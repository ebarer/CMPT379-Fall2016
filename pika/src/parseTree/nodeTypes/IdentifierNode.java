package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import logging.PikaLogger;
import symbolTable.Binding;
import symbolTable.Scope;
import tokens.IdentifierToken;
import tokens.Token;

public class IdentifierNode extends ParseNode {
	private Binding binding;
	private Scope declarationScope;

	public IdentifierNode(Token token) {
		super(token);
		assert(token instanceof IdentifierToken);
		this.binding = null;
	}
	public IdentifierNode(ParseNode node) {
		super(node);
		
		if(node instanceof IdentifierNode) {
			this.binding = ((IdentifierNode) node).binding;
		} else {
			this.binding = null;
		}
		
		initChildren();
	}
	
////////////////////////////////////////////////////////////
// attributes
	
	public IdentifierToken identifierToken() {
		return (IdentifierToken)token;
	}

	public void setBinding(Binding binding) {
		this.binding = binding;
	}
	public Binding getBinding() {
		return binding;
	}
	
	public Boolean isMutable() {
		return (binding != null) ? binding.isMutable() : null;
	}
	
	
////////////////////////////////////////////////////////////
// convenience factory

	public static IdentifierNode withChildren(Token token, ParseNode index) {
		IdentifierNode node = new IdentifierNode(token);
		node.appendChild(index);
		return node;
	}
	
////////////////////////////////////////////////////////////
// Speciality functions

	public Binding findVariableBinding() {
		String identifier = token.getLexeme();
		boolean checkStatic = false;

		for(ParseNode current : pathToRoot()) {
			if(current.containsBindingOf(identifier)) {
				declarationScope = current.getScope();
				Binding binding = current.bindingOf(identifier);
				
				if (current instanceof ProgramNode) {
					return binding;
				}
				
				if (!checkStatic || binding.isStatic()) {
					return binding;
				}
			}
			
			if (current instanceof LambdaNode) {
				// Handle recursive call in declaration
				if (current.getParent() instanceof DeclarationNode) {
					current = current.getParent();
					if (identifier.equals(current.child(0).getToken().getLexeme())) {
						if (current.getParent().containsBindingOf(identifier)) {
							declarationScope = current.getScope();
							return current.getParent().bindingOf(identifier);	
						}
					}
				}

				checkStatic = true;
			}
		}
		useBeforeDefineError();
		return Binding.nullInstance();
	}

	public Scope getDeclarationScope() {
		findVariableBinding();
		return declarationScope;
	}
	public void useBeforeDefineError() {
		PikaLogger log = PikaLogger.getLogger("compiler.semanticAnalyzer.identifierNode");
		Token token = getToken();
		log.severe("identifier " + token.getLexeme() + " used before defined at " + token.getLocation());
	}
	
///////////////////////////////////////////////////////////
// accept a visitor
		
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
