package asmCodeGenerator.codeGenerator.opcodeManipulation;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import semanticAnalyzer.types.*;

public class OpcodeForPopulateSCG implements SimpleCodeGenerator {
	public Type type;
	
	public OpcodeForPopulateSCG(Type t) {
		type = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER || type == TypeLiteral.INTEGER) {
			chunk.add(ASMOpcode.PushI, 0);
		}
		else if(type == PrimitiveType.FLOATING || type == TypeLiteral.FLOATING) {
			chunk.add(ASMOpcode.PushF, 0.0);
		}
		else if(type == PrimitiveType.RATIONAL || type == TypeLiteral.RATIONAL) {
			chunk.add(ASMOpcode.PushI, 0);
			chunk.add(ASMOpcode.PushI, 1);
		}
		else if(type == PrimitiveType.BOOLEAN || type == TypeLiteral.BOOLEAN) {
			chunk.add(ASMOpcode.PushI, 0);
		}
		else if(type == PrimitiveType.CHARACTER || type == TypeLiteral.CHARACTER) {
			chunk.add(ASMOpcode.PushI, 0);
		}
		else if(type == PrimitiveType.STRING || type == TypeLiteral.STRING) {
			chunk.add(ASMOpcode.PushI, 0);
		}
		else if(type instanceof ArrayType) {
			chunk.add(ASMOpcode.PushI, 0);
		}
		else if(type instanceof LambdaType) {
			chunk.add(ASMOpcode.PushI, 0);
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
