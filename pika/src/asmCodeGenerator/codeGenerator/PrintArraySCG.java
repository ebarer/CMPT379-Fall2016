package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.*;
import asmCodeGenerator.Labeller;

public class PrintArraySCG implements SimpleCodeGenerator {

	@Override
	public ASMCodeChunk generate() {
		return null;
	}

	// TODO: Make into a subroutine and store values on stack instead of using temp?
	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length >= 1;
		Type type = (Type)var[0];

		ASMCodeChunk chunk = new ASMCodeChunk();
		
		//chunk.add(ASMOpcode.Label, RunTime.SUB_PRINT_ARRAY);

		Labeller labeller = new Labeller("print-array");
		String startLabel  = labeller.newLabel("");
		String loopLabel  = labeller.newLabel("loop");
		String joinLabel  = labeller.newLabel("join");
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		// ADDRESS OF ARRAY 		--> ARRAY_TEMP_1
		ArrayStackToTempSCG scg1 = new ArrayStackToTempSCG();
		chunk.append(scg1.generate());
		
		// Get array length
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);	// 1 = ARRAY LENGTH
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Get subtype size
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);	// 2 = SUBTYPE SIZE
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		// Offset counter (start after Array record)
		chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);	// 3 = OFFSET FROM BASE
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.StoreI);
		
		// Print open bracket '['
		chunk.add(ASMOpcode.PushI, 91);
		chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
		chunk.add(ASMOpcode.Printf);
		
		// Start printing loop
			chunk.add(ASMOpcode.Label, loopLabel);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinLabel);
	
			// Print value at array[index]
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.append(opcodesForPrint(type));
			
			// Modify array offset
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			
			// Update loop counter
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 1);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinLabel);
			
			// If not last element, print comma ',' and space ' '
			chunk.add(ASMOpcode.PushI, 44);
			chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
			chunk.add(ASMOpcode.PushI, 0);
			chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
			chunk.add(ASMOpcode.Printf);
			
			chunk.add(ASMOpcode.Jump, loopLabel);
	
			chunk.add(ASMOpcode.Label, joinLabel);
		
		// Print closed bracket ']'
		chunk.add(ASMOpcode.PushI, 93);
		chunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
		chunk.add(ASMOpcode.Printf);
		
		return chunk;
	}

	private ASMCodeChunk opcodesForPrint(Type type) {
		ASMCodeChunk innerChunk = new ASMCodeChunk();
		
		if(type == PrimitiveType.INTEGER || type == TypeLiteral.INTEGER) {
			innerChunk.add(ASMOpcode.LoadI);
			innerChunk.add(ASMOpcode.PushD, RunTime.INTEGER_PRINT_FORMAT);
			innerChunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.FLOATING || type == TypeLiteral.FLOATING) {
			innerChunk.add(ASMOpcode.LoadF);
			innerChunk.add(ASMOpcode.PushD, RunTime.FLOATING_PRINT_FORMAT);
			innerChunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.RATIONAL || type == TypeLiteral.RATIONAL) {
			RationalMemToStackSCG ratStack = new RationalMemToStackSCG();
			innerChunk.append(ratStack.generate());
			PrintRationalSCG ratPrint = new PrintRationalSCG();
			innerChunk.append(ratPrint.generate());
		}
		else if(type == PrimitiveType.BOOLEAN || type == TypeLiteral.BOOLEAN) {
			innerChunk.add(ASMOpcode.LoadC);
			PrintBooleanSCG scg = new PrintBooleanSCG();
			innerChunk.append(scg.generate());
			innerChunk.add(ASMOpcode.PushD, RunTime.BOOLEAN_PRINT_FORMAT);
			innerChunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.CHARACTER || type == TypeLiteral.CHARACTER) {
			innerChunk.add(ASMOpcode.LoadC);
			innerChunk.add(ASMOpcode.PushD, RunTime.CHARACTER_PRINT_FORMAT);
			innerChunk.add(ASMOpcode.Printf);
		}
		else if(type == PrimitiveType.STRING || type == TypeLiteral.STRING) {
			innerChunk.add(ASMOpcode.LoadI);
			innerChunk.add(ASMOpcode.PushD, RunTime.STRING_PRINT_FORMAT);
			innerChunk.add(ASMOpcode.Printf);
		}
		else if(type instanceof ArrayType) {
			innerChunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			innerChunk.add(ASMOpcode.LoadI);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			innerChunk.add(ASMOpcode.LoadI);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);
			innerChunk.add(ASMOpcode.LoadI);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			innerChunk.add(ASMOpcode.LoadI);
			innerChunk.add(ASMOpcode.Exchange);
			
			innerChunk.add(ASMOpcode.LoadI);
			Type subType = ((ArrayType) type).getSubtype();
			innerChunk.append(generate(subType));

			innerChunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_3);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.StoreI);
			innerChunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_2);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.StoreI);
			innerChunk.add(ASMOpcode.PushD, RunTime.PRINT_TEMP_1);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.StoreI);
			innerChunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			innerChunk.add(ASMOpcode.Exchange);
			innerChunk.add(ASMOpcode.StoreI);
		}
		else {
			assert false: "Type " + type + " unimplemented in opcodeForPrint()";
		}
		
		return innerChunk;
	}
}
