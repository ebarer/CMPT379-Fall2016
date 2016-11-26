package parseTree;

import parseTree.nodeTypes.*;

// Visitor pattern with pre- and post-order visits
public interface ParseNodeVisitor {
	
	// non-leaf nodes: visitEnter and visitLeave
	void visitEnter(ParseNode node);
	void visitLeave(ParseNode node);
	
	void visitEnter(ProgramNode node);
	void visitLeave(ProgramNode node);
	
	void visitEnter(FunctionDefinitionNode node);
	void visitLeave(FunctionDefinitionNode node);
	
	void visitEnter(MainBlockNode node);
	void visitLeave(MainBlockNode node);
	
	void visitEnter(BlockNode node);
	void visitLeave(BlockNode node);
	
	void visitEnter(IdentifierNode node);
	void visitLeave(IdentifierNode node);
	
	void visitEnter(IndexNode node);
	void visitLeave(IndexNode node);

	void visitEnter(DeclarationNode node);
	void visitLeave(DeclarationNode node);
	
	void visitEnter(AssignmentNode node);
	void visitLeave(AssignmentNode node);
	
	void visitEnter(ReleaseNode node);
	void visitLeave(ReleaseNode node);
	
	void visitEnter(FunctionInvocationNode node);
	void visitLeave(FunctionInvocationNode node);
	
	void visitEnter(CallNode node);
	void visitLeave(CallNode node);
	
	void visitEnter(ReturnNode node);
	void visitLeave(ReturnNode node);
	
	void visitEnter(IfNode node);
	void visitLeave(IfNode node);
	
	void visitEnter(WhileNode node);
	void visitLeave(WhileNode node);
	
	void visitEnter(ControlNode node);
	void visitLeave(ControlNode node);
	
	void visitEnter(BinaryOperatorNode node);
	void visitLeave(BinaryOperatorNode node);
	
	void visitEnter(RationalOperatorNode node);
	void visitLeave(RationalOperatorNode node);
	
	void visitEnter(UnaryOperatorNode node);
	void visitLeave(UnaryOperatorNode node);
	
	void visitEnter(ReverseNode node);
	void visitLeave(ReverseNode node);
	
	void visitEnter(CastNode node);
	void visitLeave(CastNode node);
	
	void visitEnter(ArrayNode node);
	void visitLeave(ArrayNode node);
	
	void visitEnter(LambdaNode node);
	void visitLeave(LambdaNode node);
	
	void visitEnter(LambdaParamTypeNode node);
	void visitLeave(LambdaParamTypeNode node);
	
	void visitEnter(LambdaParamNode node);
	void visitLeave(LambdaParamNode node);
	
	void visitEnter(PrintStatementNode node);
	void visitLeave(PrintStatementNode node);

	// leaf nodes: visitLeaf only
	void visit(LambdaTypeNode node);
	void visit(BooleanConstantNode node);
	void visit(IntegerConstantNode node);
	void visit(FloatingConstantNode node);
	void visit(CharacterNode node);
	void visit(StringNode node);
	void visit(NewlineNode node);
	void visit(TabNode node);
	void visit(SpaceNode node);
	void visit(ErrorNode node);

	
	public static class Default implements ParseNodeVisitor
	{
		public void defaultVisit(ParseNode node) {	}
		public void defaultVisitEnter(ParseNode node) {
			defaultVisit(node);
		}
		public void defaultVisitLeave(ParseNode node) {
			defaultVisit(node);
		}		
		public void defaultVisitForLeaf(ParseNode node) {
			defaultVisit(node);
		}
		public void visitEnter(ParseNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ParseNode node) {
			defaultVisitLeave(node);
		}
		
		
		public void visitEnter(ProgramNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ProgramNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(FunctionDefinitionNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(FunctionDefinitionNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(MainBlockNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(MainBlockNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(BlockNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(BlockNode node) {
			defaultVisitLeave(node);
		}
		
		
		public void visitEnter(IdentifierNode node) {
			defaultVisitForLeaf(node);
		}
		public void visitLeave(IdentifierNode node) {
			defaultVisitForLeaf(node);
		}
		public void visitEnter(IndexNode node) {
			defaultVisitForLeaf(node);
		}
		public void visitLeave(IndexNode node) {
			defaultVisitForLeaf(node);
		}
		
		
		public void visitEnter(DeclarationNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(DeclarationNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(AssignmentNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(AssignmentNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ReleaseNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ReleaseNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(FunctionInvocationNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(FunctionInvocationNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(CallNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(CallNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ReturnNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ReturnNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(IfNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(IfNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(WhileNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(WhileNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ControlNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ControlNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(BinaryOperatorNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(BinaryOperatorNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(RationalOperatorNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(RationalOperatorNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(UnaryOperatorNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(UnaryOperatorNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ReverseNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ReverseNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(CastNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(CastNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ArrayNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ArrayNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(LambdaNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(LambdaNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(LambdaParamTypeNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(LambdaParamTypeNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(LambdaParamNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(LambdaParamNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(PrintStatementNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(PrintStatementNode node) {
			defaultVisitLeave(node);
		}
		

		public void visit(LambdaTypeNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(BooleanConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(IntegerConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(FloatingConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(CharacterNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(StringNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(NewlineNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(TabNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(SpaceNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(ErrorNode node) {
			defaultVisitForLeaf(node);
		}
	}
}
