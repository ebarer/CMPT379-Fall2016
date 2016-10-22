package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.*;

public class DivisionByZeroSCG implements SimpleCodeGenerator {
	public Type type;
	
	public DivisionByZeroSCG(Type t) {
		type = t;
	}

	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.Duplicate);
		chunk.add((type == PrimitiveType.FLOATING) ?
					ASMOpcode.JumpFZero : ASMOpcode.JumpFalse,
					RunTime.DIVIDE_BY_ZERO_RUNTIME_ERROR);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
