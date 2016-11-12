package semanticAnalyzer;

import parseTree.*;


public class SemanticAnalyzer {
	ParseNode ASTree;
	
	public static ParseNode analyze(ParseNode ASTree) {
		SemanticAnalyzer analyzer = new SemanticAnalyzer(ASTree);
		
		analyzer.preprocess();
		analyzer.analyze();

		return ASTree;
	}
	public SemanticAnalyzer(ParseNode ASTree) {
		this.ASTree = ASTree;
	}
	
	public void preprocess() {
		SemanticPreprocessorVisitor spv = new SemanticPreprocessorVisitor(); 
		ASTree.accept(spv);
	}
	
	public void analyze() {
		SemanticAnalysisVisitor sav = new SemanticAnalysisVisitor();
		ASTree.accept(sav);
		sav.promoter.promote();
	}
}
