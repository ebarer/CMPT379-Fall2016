package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import semanticAnalyzer.types.*;

public class CastToBoolSCG implements SimpleCodeGenerator {
	public Type exprType;
	
	public CastToBoolSCG(Type t) {
		exprType = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.And);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
