package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;

public class StringReverseCopySCG implements SimpleCodeGenerator {
	
	// EXPECTED:
	// 		STRING_ADDR_1 		== new string address (cloning to)
	// 		STRING_ADDR_2 		== old string address (cloning from)
	// 		STRING_OFFSET_1	 	== new string copy offset
	// 		STRING_OFFSET_2 	== old string copy offset
	// 		STRING_COPY_START 	== loop start/index
	// 		STRING_COPY_END 	== loop end
	//		STRING_SIZE_1		== string length
	
	// DEF: 	loop between STRING_COPY_START and STRING_COPY_END
	//			STRING_OFFSET_1 = STRING_ADDR_1 + 12 + STRING_OFFSET_1
	//			STRING_OFFSET_2 = STRING_ADDR_2 + 12 + STRING_COPY_START
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("copy-string");
		String startLabel  		= labeller.newLabel();
		String loopCopyLabel  	= labeller.newLabel("loop-copy");
		String joinCopyLabel  	= labeller.newLabel("join-copy");
		
		// Configure offsets
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, -1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);		
		
	// Start data cloning loop
		chunk.add(ASMOpcode.Label, startLabel);
		chunk.add(ASMOpcode.Label, loopCopyLabel);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_END);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.JumpFalse, joinCopyLabel);
		
		// Update loop index (increment STRING_COPY_START)
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		// Copy value across
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.LoadC);
		chunk.add(ASMOpcode.StoreC);
		
		// Update offsets
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, -1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
			
		chunk.add(ASMOpcode.Jump, loopCopyLabel);
		chunk.add(ASMOpcode.Label, joinCopyLabel);
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
