package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class RationalComparisonOperatorSCG implements SimpleCodeGenerator {	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		// Store in temp
		RationalStackToTempSCG scg = new RationalStackToTempSCG();
		chunk.append(scg.generate(2));
		chunk.append(scg.generate(1));
		
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Multiply);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Multiply);
		chunk.add(ASMOpcode.Subtract);
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
	
}