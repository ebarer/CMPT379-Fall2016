package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class StringAllocateSCG implements SimpleCodeGenerator {
	
	// EXPECTED:
	//		STRING_SIZE_1	== length of string to be allocated

	// RETURNS:
	//		STRING_ADDR_1 	== newly allocated memory space
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		// allocationSize = header + stringSize + '0'
		// Add 12 bytes for header record
		// Load stringSize from STRING_SIZE_1
		// Add 1 byte for null terminator
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Add);
		
		// Allocate memory for string
		chunk.add(ASMOpcode.Call, MemoryManager.MEM_MANAGER_ALLOCATE);
		
		// Store string address in STRING_ADDR_1
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Set last memory location to null terminator (0)
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreC);
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
	
}
