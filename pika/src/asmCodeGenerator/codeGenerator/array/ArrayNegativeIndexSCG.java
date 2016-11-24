package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class ArrayNegativeIndexSCG implements SimpleCodeGenerator {
	ASMCodeChunk chunk = new ASMCodeChunk();

	@Override
	public ASMCodeChunk generate() {
		chunk.add(ASMOpcode.Duplicate);
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
}
