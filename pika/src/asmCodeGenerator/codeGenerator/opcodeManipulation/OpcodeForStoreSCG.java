package asmCodeGenerator.codeGenerator.opcodeManipulation;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.rational.RationalStackToTempSCG;
import asmCodeGenerator.codeGenerator.rational.RationalTempToRationalSCG;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;
import semanticAnalyzer.types.*;

public class OpcodeForStoreSCG implements SimpleCodeGenerator {
	public Type type;
	
	public OpcodeForStoreSCG(Type t) {
		type = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER) {
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type == PrimitiveType.FLOATING) {
			chunk.add(ASMOpcode.StoreF);
		}
		else if(type == PrimitiveType.RATIONAL) {
			RationalStackToTempSCG scg = new RationalStackToTempSCG();
			chunk.append(scg.generate());
			chunk.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
			
			RationalTempToRationalSCG scg2 = new RationalTempToRationalSCG();
			chunk.append(scg2.generate());
		}
		else if(type == PrimitiveType.BOOLEAN) {
			chunk.add(ASMOpcode.StoreC);
		}
		else if(type == PrimitiveType.CHARACTER) {
			chunk.add(ASMOpcode.StoreC);
		}
		else if(type == PrimitiveType.STRING) {
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type instanceof ArrayType) {
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type instanceof LambdaType) {
			chunk.add(ASMOpcode.StoreI);
		}
		else {
			assert false: "Type " + type + " unimplemented in opcodeForStore()";
		}
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
