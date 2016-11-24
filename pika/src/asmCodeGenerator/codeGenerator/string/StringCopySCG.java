package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.types.*;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForCloneSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;

public class StringCopySCG implements SimpleCodeGenerator {
	ASMCodeChunk chunk = new ASMCodeChunk();

	@Override
	public ASMCodeChunk generate() {
		Labeller labeller = new Labeller("copy-string");
		String startLabel  		= labeller.newLabel("");
		String dataLabel  		= labeller.newLabel("data");
		String loopCopyLabel  	= labeller.newLabel("loop-copy");
		String joinCopyLabel  	= labeller.newLabel("join-copy");
	
		chunk.add(ASMOpcode.Label, startLabel);
		
		// STRING_ADDR_1 		== new string address (cloning to)
		// STRING_ADDR_2 		== old string address (cloning)
		// STRING_TEMP_1	 	== new string copy offset
		// STRING_TEMP_2 		== old string copy offset
		// STRING_COPY_START 	== loop start
		// STRING_COPY_END 		== loop end
		// ??					== loop index
		
		//FIXME: 	loop between STRING_COPY_START and STRING_COPY_END
		//			STRING_TEMP_1 = STRING_ADDR_1 + 12 + i
		//			STRING_TEMP_2 = STRING_ADDR_2 + 12 + STRING_COPY_START + i
		
	// Start data cloning loop
		chunk.add(ASMOpcode.Label, dataLabel);
		chunk.add(ASMOpcode.Label, loopCopyLabel);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpFalse, joinCopyLabel);
		
		// Update loop counter
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.StoreI);
		
		// Update offset
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.StoreI);
		
		// Copy value across
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		OpcodeForLoadSCG scg1 = new OpcodeForLoadSCG(PrimitiveType.CHARACTER);
		chunk.append(scg1.generate());
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.Exchange);
		OpcodeForCloneSCG scg2 = new OpcodeForCloneSCG(PrimitiveType.CHARACTER);
		chunk.append(scg2.generate());
			
		chunk.add(ASMOpcode.Jump, loopCopyLabel);
		chunk.add(ASMOpcode.Label, joinCopyLabel);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
