package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;
import semanticAnalyzer.types.*;

public class OpcodeForStoreFunctionSCG implements SimpleCodeGenerator {
	public Type type;
	
	public OpcodeForStoreFunctionSCG(Type t) {
		type = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type == PrimitiveType.FLOATING) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreF);
		}
		else if(type == PrimitiveType.RATIONAL) {
			RationalStackToTempSCG scg = new RationalStackToTempSCG();
			chunk.append(scg.generate());
			chunk.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
			
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			
			RationalTempToRationalSCG scg2 = new RationalTempToRationalSCG();
			chunk.append(scg2.generate());
		}
		else if(type == PrimitiveType.BOOLEAN) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreC);
		}
		else if(type == PrimitiveType.CHARACTER) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreC);
		}
		else if(type == PrimitiveType.STRING) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type == PrimitiveType.VOID) {
			
		}
		else if(type instanceof ArrayType) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type instanceof LambdaType) {
			chunk.add(ASMOpcode.PushD, RunTime.STACK_POINTER);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
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
