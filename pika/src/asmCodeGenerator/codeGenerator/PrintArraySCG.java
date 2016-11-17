package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.*;
import asmCodeGenerator.Labeller;

public class PrintArraySCG implements SimpleCodeGenerator {

	@Override
	public ASMCodeChunk generate() {
		return null;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length >= 1;
		Type type = (Type)var[0];

		ASMCodeChunk chunk = new ASMCodeChunk();
		
		//chunk.add(ASMOpcode.Label, RunTime.SUB_PRINT_ARRAY);

		Labeller labeller = new Labeller("print-array");
		String startLabel  = labeller.newLabel("");
		String loopLabel  = labeller.newLabel("loop");
		String joinLabel  = labeller.newLabel("join");
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		// ADDRESS OF ARRAY 		--> ARRAY_TEMP_1
		ArrayStackToTempSCG scg1 = new ArrayStackToTempSCG();
		chunk.append(scg1.generate());
		
		// Get array length
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);	// 1 = ARRAY LENGTH
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Get subtype size
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);	// 2 = SUBTYPE SIZE
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Offset counter (start after Array record)
		chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);	// 3 = OFFSET FROM BASE
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.StoreI);
		
		// Print open bracket '['
		chunk.add(ASMOpcode.PushI, 91);
		chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
		chunk.add(ASMOpcode.Printf);
		
		// Start printing loop
			chunk.add(ASMOpcode.Label, loopLabel);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinLabel);
	
			// Print value at array[index]
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			
			OpcodeForPrintSCG scg = new OpcodeForPrintSCG(type);
			chunk.append(scg.generate());
			
			// Modify array offset
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			
			// Update loop counter
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 1);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinLabel);
			
			// If not last element, print comma ',' and space ' '
			chunk.add(ASMOpcode.PushI, 44);
			chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
			chunk.add(ASMOpcode.PushI, 32);
			chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
			
			chunk.add(ASMOpcode.Jump, loopLabel);
	
			chunk.add(ASMOpcode.Label, joinLabel);
		
		// Print closed bracket ']'
		chunk.add(ASMOpcode.PushI, 93);
		chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
		chunk.add(ASMOpcode.Printf);
		
		return chunk;
	}

}
