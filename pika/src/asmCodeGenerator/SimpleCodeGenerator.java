//package asmCodeGenerator;
//
//import asmCodeGenerator.codeStorage.ASMCodeFragment;
//import lexicalAnalyzer.Lextant;
//import parseTree.ParseNode;
//
//public interface SimpleCodeGenerator {
//
//	public default ASMCodeFragment generate(ParseNode n, Lextant operator, ASMCodeFragment ...childrenCode) {
//		return null;
//	}
//	
//}
//
//
//else if (variant instanceof SimpleCodeGenerator) {
//	SimpleCodeGenerator generator = (SimpleCodeGenerator) variant;
//	code.append(generator.generate());
//}