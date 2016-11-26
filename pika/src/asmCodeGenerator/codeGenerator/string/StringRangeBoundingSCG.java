package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class StringRangeBoundingSCG implements SimpleCodeGenerator {
	
	// EXPECTED:
	//		INDEX_TEMP_1 	== address of string being indexed
	//		INDEX_TEMP_2 	== START index
	//		INDEX_TEMP_3 	== END index
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		// Check if START index is negative
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		// Check if START index is less than END index
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		// Check if END index is larger than string length
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);		// String length
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
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
