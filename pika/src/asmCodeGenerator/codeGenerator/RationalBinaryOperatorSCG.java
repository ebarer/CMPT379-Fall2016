package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import lexicalAnalyzer.*;
import semanticAnalyzer.types.*;

public class RationalBinaryOperatorSCG implements SimpleCodeGenerator {
	public Punctuator punctuator;
	
	public RationalBinaryOperatorSCG(Punctuator p) {
		punctuator = p;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		// Store in temp
		RationalStackToTempSCG scg = new RationalStackToTempSCG();
		chunk.append(scg.generate(2));
		chunk.append(scg.generate(1));
				
		if (punctuator == Punctuator.ADD || punctuator == Punctuator.SUBTRACT) {
			ASMOpcode opcode = (punctuator == Punctuator.ADD) ? ASMOpcode.Add : ASMOpcode.Subtract;
			
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Multiply);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Multiply);
			chunk.add(opcode);
			
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Multiply);
		}
		else if (punctuator == Punctuator.MULTIPLY) {
			ASMOpcode opcode = ASMOpcode.Multiply;
			
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(opcode);
			
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(opcode);
		}
		else if (punctuator == Punctuator.DIVISION) {			
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Multiply);
			
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_NUMERATOR_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Multiply);
		}
		
		// Store in temp
		chunk.append(scg.generate(1));
		
		// Find GCD
		chunk.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
		
		// Return values to accumulator
		RationalTempToStackSCG scg1 = new RationalTempToStackSCG();
		chunk.append(scg1.generate(1));
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
	
}