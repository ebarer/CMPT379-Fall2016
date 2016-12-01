package asmCodeGenerator.codeGenerator.cast;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import semanticAnalyzer.types.*;

public class CastIntToCharSCG implements SimpleCodeGenerator {
	public Type exprType;
	
	public CastIntToCharSCG(Type t) {
		exprType = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushI, 127);
		chunk.add(ASMOpcode.BTAnd);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
