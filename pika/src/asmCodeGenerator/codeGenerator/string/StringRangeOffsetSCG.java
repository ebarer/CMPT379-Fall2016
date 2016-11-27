package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class StringRangeOffsetSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();

		// Store string END index in INDEX_TEMP_3
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Store string START index in INDEX_TEMP_2
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Store address of string being indexed in INDEX_TEMP_1
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Check index bounds are valid
		StringRangeBoundingSCG scg1 = new StringRangeBoundingSCG();
		chunk.append(scg1.generate());
		
		// Define string size for allocation
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.StoreI);
		
		// Create new string			
		StringAllocateSCG scg2 = new StringAllocateSCG();
		chunk.append(scg2.generate());
		
		// Setup string record
		StringGenerateRecordSCG scg3 = new StringGenerateRecordSCG();
		chunk.append(scg3.generate());
		
		// Define copy variables
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_2);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_END);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreI);
		
		StringCopySCG scg4 = new StringCopySCG();
		chunk.append(scg4.generate());
		
		// Push address of dynamically allocated string on stack
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
			
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
