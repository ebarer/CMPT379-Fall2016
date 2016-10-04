package optimizer;

import java.util.List;

import asmCodeGenerator.codeStorage.ASMCodeChunk;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMInstruction;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;

public class Optimizer {
	private ASMCodeFragment fragment;
	
	public static ASMCodeFragment optimize(ASMCodeFragment fragment) {
		Optimizer optimizer = new Optimizer(fragment);
		return optimizer.optimize();
	}
	public Optimizer(ASMCodeFragment fragment) {
		this.fragment = fragment;
	}
	
	public ASMCodeFragment optimize() {
		ASMCodeFragment[] fragments = splitDirectives(fragment);
		ASMCodeFragment returnFragment = new ASMCodeFragment(CodeType.GENERATES_VOID);
		
		// Merge fragments
		returnFragment.append(fragments[0]);
		returnFragment.append(fragments[1]);
		
		return returnFragment;
	}
	
	private ASMCodeFragment[] splitDirectives(ASMCodeFragment fragment) {
		ASMCodeFragment fragments[] = {
			new ASMCodeFragment(CodeType.GENERATES_VOID),
			new ASMCodeFragment(CodeType.GENERATES_VOID),
		};
		
		List<ASMCodeChunk> chunks = fragment.getChunks();

		for (ASMCodeChunk chunk : chunks) {
			if (chunk == chunks.get(0)) {
				fragments[0].addChunk(chunk);
				continue;
			}
			
			for (ASMInstruction instruction : chunk.getInstructions()) {
				ASMOpcode opcode = instruction.getOpcode();
				Object operand = instruction.getArgument();
				String comment = instruction.getComment();
				
				if (opcode == ASMOpcode.Nop) {
					continue;
				} else if (opcode.isDataDirective()) {
					fragments[0].add(opcode, operand, comment);				
				} else {
					fragments[1].add(opcode, operand, comment);
				}
			}
		}
		
		return fragments;
	}
}
