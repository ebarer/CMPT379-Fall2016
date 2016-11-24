package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class ArrayStackToTempSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		return generate(1);
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) { 		
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		String temp = RunTime.ARRAY_TEMP_1;
		
		if (var[0] instanceof Integer && ((Integer) var[0]).intValue() == 2) { 
			temp = RunTime.ARRAY_TEMP_2;
		}
		
		chunk.add(ASMOpcode.PushD, temp);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		return chunk;
	}
	
}
