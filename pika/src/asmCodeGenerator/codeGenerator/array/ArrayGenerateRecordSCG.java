package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.ArrayType;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

public class ArrayGenerateRecordSCG implements SimpleCodeGenerator {
	private Type subtype;
	
	public ArrayGenerateRecordSCG(Type subtype) {
		this.subtype = subtype;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 7);
		chunk.add(ASMOpcode.StoreI);								// type identifier
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		if (subtype == PrimitiveType.STRING || subtype instanceof ArrayType) {
			chunk.add(ASMOpcode.PushI, 4);
		} else {
			chunk.add(ASMOpcode.PushI, 0);
		}
		chunk.add(ASMOpcode.StoreI);								// subtype-is-reference
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, subtype.getSize());
		chunk.add(ASMOpcode.StoreI);								// subtype size
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);								// length
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {		
		return generate();
	}
	
}
