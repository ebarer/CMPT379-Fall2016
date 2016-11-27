package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import lexicalAnalyzer.Keyword;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForCloneSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;

public class ArrayReverseSCG implements SimpleCodeGenerator {
	Type subtype;
	
	public ArrayReverseSCG(Type t) {
		this.subtype = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
			
		Labeller labeller = new Labeller("reverse-array");
		String startLabel 		= labeller.newLabel();
		String allocateLabel 	= labeller.newLabel("allocate");
		String recordLabel  	= labeller.newLabel("create-record");
		String copyLabel  		= labeller.newLabel("copy");
		String loopLabel  		= labeller.newLabel("loop");
		String joinLabel  		= labeller.newLabel("join");
		
		// Generators
		ArrayAllocateSCG allocateSCG = new ArrayAllocateSCG();
		OpcodeForLoadSCG loadSCG = new OpcodeForLoadSCG(subtype);
		OpcodeForCloneSCG storeSCG = new OpcodeForCloneSCG(subtype);
	
		chunk.add(ASMOpcode.Label, startLabel);
		
		// ARRAY_TEMP_1 == new array address (copying to)
		// ARRAY_TEMP_2 == old array address (copying from)
		// ARRAY_TEMP_3 == loop index
		// ARRAY_TEMP_4 == subtype size
		// ARRAY_TEMP_5 == copy TO offset
		// ARRAY_TEMP_6 == copy FROM offset
		
		// Store address of OLD_ARRAY in ARRAY_TEMP_2
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);

		// Allocate and store address of NEW_ARRAY in ARRAY_TEMP_1 
		chunk.add(ASMOpcode.Label, allocateLabel);
		chunk.append(allocateSCG.generate(Keyword.CLONE));
		
	// Create record for NEW_ARRAY
		chunk.add(ASMOpcode.Label, recordLabel);
		
		// Type Identifier
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 7);
		chunk.add(ASMOpcode.StoreI);
		
		// Status
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		// Subtype Size
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		// Length
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
	// Reverse copy OLD_ARRAY to NEW_ARRAY
		chunk.add(ASMOpcode.Label, copyLabel);
		
		// Setup loop index
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		// Setup subtype size
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);		
		
		// Setup copy TO offset 
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, -1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.Multiply);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		// Setup copy FROM offset
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		// Start data (reverse) copying loop
		chunk.add(ASMOpcode.Label, loopLabel);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinLabel);
		
			// Update loop counter
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 1);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.StoreI);
			
			// Copy value
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
			chunk.add(ASMOpcode.LoadI);
			chunk.append(loadSCG.generate());
			chunk.append(storeSCG.generate());
			
			// Update offsets
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.StoreI);	
		
			chunk.add(ASMOpcode.Jump, loopLabel);
		
		chunk.add(ASMOpcode.Label, joinLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
