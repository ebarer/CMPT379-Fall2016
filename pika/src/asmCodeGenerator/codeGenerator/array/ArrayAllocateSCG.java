package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;

public class ArrayAllocateSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		// allocationSize = header + arraySize
		// Add 16 bytes for header record
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);
		
		// Allocate memory for array
		chunk.add(ASMOpcode.Call, MemoryManager.MEM_MANAGER_ALLOCATE);
		
		// Store heap address in stack
		ArrayStackToTempSCG scg = new ArrayStackToTempSCG();
		chunk.append(scg.generate());
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length == 1;
		if (var[0] instanceof Lextant) {
			ASMCodeChunk chunk = new ASMCodeChunk();
			Lextant operator = (Lextant)var[0]; 
			
			// If operator is NEW, arraySize on stack
			// Get arraySize by using cloneNode
			if (operator == Keyword.CLONE) {
				// Get cloneNode subtype size
				chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
				chunk.add(ASMOpcode.LoadI);
				chunk.add(ASMOpcode.PushI, 8);
				chunk.add(ASMOpcode.Add);
				chunk.add(ASMOpcode.LoadI);
				
				// Get cloneNode length
				chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
				chunk.add(ASMOpcode.LoadI);
				chunk.add(ASMOpcode.PushI, 12);
				chunk.add(ASMOpcode.Add);
				chunk.add(ASMOpcode.LoadI);
				
				chunk.add(ASMOpcode.Multiply);
			}
			
			// allocationSize = header + arraySize
			// Add 16 bytes for header record
			chunk.add(ASMOpcode.PushI, 16);
			chunk.add(ASMOpcode.Add);
			
			// Allocate memory for array
			chunk.add(ASMOpcode.Call, MemoryManager.MEM_MANAGER_ALLOCATE);
			
			// Store heap address in stack
			ArrayStackToTempSCG scg = new ArrayStackToTempSCG();
			chunk.append(scg.generate());
			
			return chunk;
		}
		
		return null;
	}
	
}
