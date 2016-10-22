package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.PrimitiveType;
import asmCodeGenerator.Labeller;

public class PrintRationalSCG implements SimpleCodeGenerator {

	@Override
	public ASMCodeChunk generate() {
		return null;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("print-rational");
		String startLabel = labeller.newLabel("");
		String skipFractionLabel = labeller.newLabel("skip-fraction");
		String skipWholeLabel = labeller.newLabel("skip-whole");
		String skipNegateLabel = labeller.newLabel("skip-negate");
		String skipFractionNegateLabel = labeller.newLabel("skip-fraction-negate");
		String joinLabel= labeller.newLabel("join");
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		// Whole Number
		RationalTempToStackSCG scg = new RationalTempToStackSCG();
		chunk.append(scg.generate(1));
		
		DivisionByZeroSCG scg1 = new DivisionByZeroSCG(PrimitiveType.INTEGER);
		chunk.append(scg1.generate());
		
		chunk.add(ASMOpcode.Divide);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_WHOLE);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Fraction
		chunk.append(scg.generate(1));
		chunk.add(ASMOpcode.Remainder);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_NUMERATOR);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_DENOMINATOR);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Determine what needs to be printed
		// Check for 0 in fraction numerator
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_NUMERATOR);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpFalse, skipFractionLabel);
		
		// Check for 0 in whole number
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_WHOLE);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpFalse, skipWholeLabel);
		
		// Numerator and Whole nonzero, print entire fraction
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_DENOMINATOR);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_NUMERATOR);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Duplicate);
		chunk.add(ASMOpcode.JumpPos, skipNegateLabel);
		chunk.add(ASMOpcode.Negate);
		chunk.add(ASMOpcode.Label, skipNegateLabel);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_WHOLE);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_FORMAT);
		chunk.add(ASMOpcode.Jump, joinLabel);
		
		// Numerator zero, only print whole number
		chunk.add(ASMOpcode.Label, skipFractionLabel);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_WHOLE);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INTEGER_PRINT_FORMAT);
		chunk.add(ASMOpcode.Jump, joinLabel);
		
		// Whole number zero, only print fraction
		chunk.add(ASMOpcode.Label, skipWholeLabel);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_DENOMINATOR);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_PRINT_NUMERATOR);
		chunk.add(ASMOpcode.LoadI);
		
		chunk.add(ASMOpcode.Duplicate);
		chunk.add(ASMOpcode.JumpPos, skipFractionNegateLabel);
		chunk.add(ASMOpcode.Negate);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_NEG_FRACTION_PRINT_FORMAT);
		chunk.add(ASMOpcode.Jump, joinLabel);
		
		chunk.add(ASMOpcode.Label, skipFractionNegateLabel);
		chunk.add(ASMOpcode.PushD, RunTime.RATIONAL_FRACTION_PRINT_FORMAT);
		chunk.add(ASMOpcode.Jump, joinLabel);
		
		chunk.add(ASMOpcode.Label, joinLabel);		
		chunk.add(ASMOpcode.Printf);
		
		return chunk;
	}

}
