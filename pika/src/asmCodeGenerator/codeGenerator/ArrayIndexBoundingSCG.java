package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class ArrayIndexBoundingSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();

		// Check if index is negative
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		// Check if index is larger than array length
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);		// Array length
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Subtract);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);  // Check if index is bigger than length
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
