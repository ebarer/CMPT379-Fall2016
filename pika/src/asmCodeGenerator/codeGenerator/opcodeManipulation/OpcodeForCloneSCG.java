package asmCodeGenerator.codeGenerator.opcodeManipulation;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;
import semanticAnalyzer.types.*;

public class OpcodeForCloneSCG implements SimpleCodeGenerator {
	public Type type;
	
	public OpcodeForCloneSCG(Type t) {
		type = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER || type == TypeLiteral.INTEGER) {
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type == PrimitiveType.FLOATING || type == TypeLiteral.FLOATING) {
			chunk.add(ASMOpcode.StoreF);
		}
		else if(type == PrimitiveType.RATIONAL || type == TypeLiteral.RATIONAL) {
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.PushI, 4);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
		}
		else if(type == PrimitiveType.BOOLEAN || type == TypeLiteral.BOOLEAN) {
			chunk.add(ASMOpcode.StoreC);
		}
		else if(type == PrimitiveType.CHARACTER || type == TypeLiteral.CHARACTER) {
			chunk.add(ASMOpcode.StoreC);
		}
		else if(type == PrimitiveType.STRING || type == TypeLiteral.STRING) {
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
