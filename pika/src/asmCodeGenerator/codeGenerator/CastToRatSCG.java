package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import semanticAnalyzer.types.*;
import asmCodeGenerator.runtime.*;

public class CastToRatSCG implements SimpleCodeGenerator {
	public Type exprType;
	
	public CastToRatSCG(Type t) {
		exprType = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if (exprType == PrimitiveType.CHARACTER ||
			exprType == PrimitiveType.INTEGER) {
			chunk.add(ASMOpcode.PushI, 1);
		}
		
		if (exprType == PrimitiveType.FLOATING) {
			chunk.add(ASMOpcode.PushI, 223092870);
			chunk.add(ASMOpcode.ConvertF);
			chunk.add(ASMOpcode.FMultiply);
			chunk.add(ASMOpcode.ConvertI);
			chunk.add(ASMOpcode.PushI, 223092870);
			
			RationalStackToTempSCG scg = new RationalStackToTempSCG();
			chunk.append(scg.generate());
			
			chunk.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
			
			RationalTempToStackSCG scg2 = new RationalTempToStackSCG();
			chunk.append(scg2.generate());
		}
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
