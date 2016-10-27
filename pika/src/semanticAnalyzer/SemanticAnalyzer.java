package semanticAnalyzer;

import parseTree.*;


public class SemanticAnalyzer {
	ParseNode ASTree;
	
	public static ParseNode analyze(ParseNode ASTree) {
		SemanticAnalyzer analyzer = new SemanticAnalyzer(ASTree);
		return analyzer.analyze();
	}
	public SemanticAnalyzer(ParseNode ASTree) {
		this.ASTree = ASTree;
	}
	
	public ParseNode analyze() {
		SemanticAnalysisVisitor sav = new SemanticAnalysisVisitor();
		ASTree.accept(sav);
		sav.promoter.promote();
		return ASTree;
	}
}
