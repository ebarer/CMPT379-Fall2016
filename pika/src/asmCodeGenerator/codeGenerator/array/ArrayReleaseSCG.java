package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.*;

public class ArrayReleaseSCG implements SimpleCodeGenerator {
	boolean debug = false;
	
	@Override
	public ASMCodeChunk generate() {
		return null;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length >= 1;
		Type type = (Type)var[0];
		
		ASMCodeChunk chunk = new ASMCodeChunk();

		Labeller labeller = new Labeller("release-stmt");
		String startLabel 		 = labeller.newLabel();
		String loopLabel  		 = labeller.newLabel("loop");
		String joinLoopLabel	 = labeller.newLabel("join-loop");
		String joinDeletedLabel  = labeller.newLabel("join-deleted");
		String joinLabel  		 = labeller.newLabel("join");

		chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		// Check permanent-status bit
		chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.BTAnd);
		chunk.add(ASMOpcode.JumpTrue, joinLabel);
		
		// Check is-deleted-status bit
		chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 2);
		chunk.add(ASMOpcode.BTAnd);
		chunk.add(ASMOpcode.JumpTrue, joinLabel);
		
		// TODO: Check the subtype-is-reference bit (recurse)
		if (type instanceof ArrayType) {
			if (((ArrayType) type).getSubtype() instanceof ArrayType) {
				Type subType = ((ArrayType) type).getSubtype();
				
				// Store current address on stack
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
				chunk.add(ASMOpcode.LoadI);
				
				// Get array length
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);	// 1 = ARRAY ADDR
				chunk.add(ASMOpcode.LoadI);
				chunk.add(ASMOpcode.PushI, 12);
				chunk.add(ASMOpcode.Add);
				chunk.add(ASMOpcode.LoadI);
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_2);	// 2 = ARRAY LENGTH
				chunk.add(ASMOpcode.Exchange);
				chunk.add(ASMOpcode.StoreI);
				
				// Get subtype size
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_3);	// 3 = SUBTYPE SIZE (ARRAY = 8)
				chunk.add(ASMOpcode.PushI, 8);
				chunk.add(ASMOpcode.StoreI);
				
				// Offset counter (start after Array record)
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_4);	// 4 = OFFSET FROM BASE
				chunk.add(ASMOpcode.PushI, 16);
				chunk.add(ASMOpcode.StoreI);
				
				// Start recurse loop
				chunk.add(ASMOpcode.Label, loopLabel);
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_2);
				chunk.add(ASMOpcode.LoadI);
				chunk.add(ASMOpcode.JumpFalse, joinLoopLabel);
					
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
					chunk.add(ASMOpcode.LoadI);
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_4);
					chunk.add(ASMOpcode.LoadI);
					chunk.add(ASMOpcode.Add);
					chunk.append(generate(subType));
					
					// Modify array offset
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_4);
					chunk.add(ASMOpcode.LoadI);
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_3);
					chunk.add(ASMOpcode.LoadI);
					chunk.add(ASMOpcode.Add);
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_4);
					chunk.add(ASMOpcode.Exchange);
					chunk.add(ASMOpcode.StoreI);
					
					// Update loop counter
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_2);
					chunk.add(ASMOpcode.LoadI);
					chunk.add(ASMOpcode.PushI, 1);
					chunk.add(ASMOpcode.Subtract);
					chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_2);
					chunk.add(ASMOpcode.Exchange);
					chunk.add(ASMOpcode.StoreI);
					
					if (debug) {
						chunk.add(ASMOpcode.PushD, RunTime.ARRAY_RECURSE_RELEASED);
						chunk.add(ASMOpcode.PushD, RunTime.STRING_PRINT_FORMAT);
						chunk.add(ASMOpcode.Printf);
					}
					
					chunk.add(ASMOpcode.Jump, loopLabel);
				chunk.add(ASMOpcode.Label, joinLoopLabel);
				
				// Retreive current address from stack
				chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
				chunk.add(ASMOpcode.Exchange);
				chunk.add(ASMOpcode.StoreI);
			}
		}

		// Set is-deleted-status bit
		chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.Duplicate);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 2);
		chunk.add(ASMOpcode.BTOr);
		chunk.add(ASMOpcode.StoreI);
		
		// Deallocate memory
		chunk.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Call, MemoryManager.MEM_MANAGER_DEALLOCATE);
		chunk.add(ASMOpcode.Jump, joinLabel);

		// DEBUG: Release
		if (debug) { 
			chunk.add(ASMOpcode.Label, joinDeletedLabel);
			chunk.add(ASMOpcode.PushD, RunTime.ALREADY_RELEASED);
			chunk.add(ASMOpcode.PushD, RunTime.STRING_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		
		chunk.add(ASMOpcode.Label, joinLabel);
		
		return chunk;
	}
	
}
