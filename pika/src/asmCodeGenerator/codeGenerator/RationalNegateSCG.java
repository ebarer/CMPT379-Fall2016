package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class RationalNegateSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		return generate(1);
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("rat");
		String joinLabel  = labeller.newLabel("join");
		
		// Store values in temp
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Check if denominator is negative, move to numerator
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpPos, joinLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Negate);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Negate);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, joinLabel);
		
		// Load values from temp
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.LoadI);
		
		return chunk;
	}
	
}
