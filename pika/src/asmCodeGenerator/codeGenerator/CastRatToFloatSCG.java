package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import semanticAnalyzer.types.*;

public class CastRatToFloatSCG implements SimpleCodeGenerator {
	public Type exprType;
	
	public CastRatToFloatSCG(Type t) {
		exprType = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.ConvertF);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.ConvertF);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.FDivide);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
