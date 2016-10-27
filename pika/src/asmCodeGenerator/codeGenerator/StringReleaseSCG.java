package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class StringReleaseSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();

		Labeller labeller = new Labeller("release-stmt");
		String startLabel 		 = labeller.newLabel("");
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
//		chunk.add(ASMOpcode.Label, joinDeletedLabel);
//		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_ALREADY_RELEASED);
//		chunk.add(ASMOpcode.PushD, RunTime.STRING_PRINT_FORMAT);
//		chunk.add(ASMOpcode.Printf);
		
		chunk.add(ASMOpcode.Label, joinLabel);
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {		
		return generate();
	}
	
}
