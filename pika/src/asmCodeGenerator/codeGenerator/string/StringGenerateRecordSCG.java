package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class StringGenerateRecordSCG implements SimpleCodeGenerator {
	
	// EXPECTED:
	//		STRING_ADDR_1 	== string address
	//		STRING_SIZE_1	== string length
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 6);
		chunk.add(ASMOpcode.StoreI);								// type identifier
		
		// status byte = 8:
		//			immutable 			= 1
		//			non-reference type 	= 0
		//			not deleted 		= 0
		// 			non-permanent 		= 0
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.StoreI);								// status
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);								// length
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
	
}
