package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class RationalTempToStackSCG implements SimpleCodeGenerator {

	@Override
	public ASMCodeChunk generate() {
		return generate(1);
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		String numerator = RunTime.RATIONAL_TEMP_NUMERATOR_1;
		String denominator = RunTime.RATIONAL_TEMP_DENOMINATOR_1;
		
		if (var[0] instanceof Integer && ((Integer) var[0]).intValue() == 2) { 
			numerator = RunTime.RATIONAL_TEMP_NUMERATOR_2;
			denominator = RunTime.RATIONAL_TEMP_DENOMINATOR_2;
		}
		
		chunk.add(ASMOpcode.PushD, numerator);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, denominator);
		chunk.add(ASMOpcode.LoadI);
		
		return chunk;
	}

}