package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class ArrayOffsetSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		// Store array index in INDEX_TEMP_2
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Store array base in INDEX_TEMP_1
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Check index bounds are valid
		ArrayIndexBoundingSCG scg = new ArrayIndexBoundingSCG();
		chunk.append(scg.generate());
		
		// Generate Offset
		// Total Offset = Record + Index
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);		// Subtype Size
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Multiply);		// Index Offset = Subtype * Index
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);			// Record Offset
		chunk.add(ASMOpcode.Add);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
