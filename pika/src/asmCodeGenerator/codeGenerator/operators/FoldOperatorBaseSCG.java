package asmCodeGenerator.codeGenerator.operators;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreSCG;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class FoldOperatorBaseSCG extends FoldOperatorSCG {
	private Type baseType;
	private ASMCodeFragment base;
	
	public FoldOperatorBaseSCG(Type baseT, Type arrT, Type outT) {
		super(arrT, outT);
		this.baseType = baseT;
		this.base = null;
	}
	public void setBase(ASMCodeFragment base) {
		this.base = base;
	}
	
	public ASMCodeFragment generate() {
		assert array != null;
		assert lambda != null;
		
		ASMCodeFragment code = new ASMCodeFragment(CodeType.GENERATES_VALUE);
		return code;
	}

}
