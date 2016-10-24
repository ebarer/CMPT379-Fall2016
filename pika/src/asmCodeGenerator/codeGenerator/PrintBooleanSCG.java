package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import asmCodeGenerator.Labeller;

public class PrintBooleanSCG implements SimpleCodeGenerator {
	ASMCodeChunk chunk = new ASMCodeChunk();

	@Override
	public ASMCodeChunk generate() {
		
		Labeller labeller = new Labeller("print-boolean");
		String trueLabel = labeller.newLabel("true");
		String endLabel = labeller.newLabel("join");

		chunk.add(ASMOpcode.JumpTrue, trueLabel);
		chunk.add(ASMOpcode.PushD, RunTime.BOOLEAN_FALSE_STRING);
		chunk.add(ASMOpcode.Jump, endLabel);
		chunk.add(ASMOpcode.Label, trueLabel);
		chunk.add(ASMOpcode.PushD, RunTime.BOOLEAN_TRUE_STRING);
		chunk.add(ASMOpcode.Label, endLabel);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
}
