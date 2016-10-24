package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import parseTree.nodeTypes.ArrayNode;

public class ArrayGenerateRecordSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		return null;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length == 2;
		if (var[0] instanceof ArrayNode) {
			ArrayNode node = (ArrayNode)var[0];
			boolean isReference = (Boolean)var[1];
			ASMCodeChunk chunk = new ASMCodeChunk();
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 7);
			chunk.add(ASMOpcode.StoreI);								// type identifier
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 4);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.PushI, (isReference) ? 4 : 0);
			chunk.add(ASMOpcode.StoreI);								// subtype-is-reference
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 8);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.PushI, node.getSubtype().getSize());
			chunk.add(ASMOpcode.StoreI);								// subtype size
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 12);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);								// length
			
			return chunk;
		}
		
		return null;
	}
	
}
