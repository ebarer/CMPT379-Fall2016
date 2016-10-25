package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;

public class ArrayLengthSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();

		// Check if index is negative
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
