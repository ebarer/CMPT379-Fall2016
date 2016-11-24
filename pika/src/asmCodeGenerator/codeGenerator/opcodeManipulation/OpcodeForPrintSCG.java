package asmCodeGenerator.codeGenerator.opcodeManipulation;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.print.PrintArraySCG;
import asmCodeGenerator.codeGenerator.print.PrintBooleanSCG;
import asmCodeGenerator.codeGenerator.print.PrintRationalSCG;
import asmCodeGenerator.codeGenerator.rational.RationalMemToStackSCG;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;
import semanticAnalyzer.types.*;

public class OpcodeForPrintSCG implements SimpleCodeGenerator {
	public Type type;
	
	public OpcodeForPrintSCG(Type t) {
		type = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER || type == TypeLiteral.INTEGER) {
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.INTEGER_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.FLOATING || type == TypeLiteral.FLOATING) {
			chunk.add(ASMOpcode.LoadF);
			chunk.add(ASMOpcode.PushD, RunTime.FLOATING_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.RATIONAL || type == TypeLiteral.RATIONAL) {
			RationalMemToStackSCG ratStack = new RationalMemToStackSCG();
			chunk.append(ratStack.generate());
			PrintRationalSCG ratPrint = new PrintRationalSCG();
			chunk.append(ratPrint.generate());
		}
		else if(type == PrimitiveType.BOOLEAN || type == TypeLiteral.BOOLEAN) {
			chunk.add(ASMOpcode.LoadC);
			PrintBooleanSCG scg = new PrintBooleanSCG();
			chunk.append(scg.generate());
			chunk.add(ASMOpcode.PushD, RunTime.BOOLEAN_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.CHARACTER || type == TypeLiteral.CHARACTER) {
			chunk.add(ASMOpcode.LoadC);
			chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.STRING || type == TypeLiteral.STRING) {
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 12);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.PushD, RunTime.STRING_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		else if(type instanceof ArrayType) {
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Exchange);
			
			chunk.add(ASMOpcode.LoadI);
			Type subType = ((ArrayType) type).getSubtype();
			PrintArraySCG scg = new PrintArraySCG();
			chunk.append(scg.generate(subType));

			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
		}
		else if (type instanceof LambdaType) {
			// Load address and remove
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Pop);
			chunk.add(ASMOpcode.PushD, RunTime.LAMBDA_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
		}
		else {
			assert false: "Type " + type + " unimplemented in opcodeForPrint()";
		}
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
