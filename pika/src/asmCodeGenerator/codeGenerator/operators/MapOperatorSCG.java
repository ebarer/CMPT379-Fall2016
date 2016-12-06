package asmCodeGenerator.codeGenerator.operators;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.array.ArrayAllocateSCG;
import asmCodeGenerator.codeGenerator.array.ArrayGenerateRecordSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreFunctionSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreSCG;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class MapOperatorSCG {
	private Type inType;
	private Type outType;
	private ASMCodeFragment oldArray;
	private ASMCodeFragment lambda;
	
	public MapOperatorSCG(Type in, Type out) {
		this.inType = in;
		this.outType = out;
		this.oldArray = null;
		this.lambda = null;
	}
	public void setArray(ASMCodeFragment array) {
		this.oldArray = array;
	}
	public void setLambda(ASMCodeFragment lambda) {
		this.lambda = lambda;
	}
	
	public ASMCodeFragment generate() {
		assert oldArray != null;
		assert lambda != null;
		
		ASMCodeFragment code = new ASMCodeFragment(CodeType.GENERATES_VALUE);
			
		Labeller labeller = new Labeller("map-operator");
		String startLabel 		= labeller.newLabel();
		String allocateLabel 	= labeller.newLabel("allocate");
		String loopLabel  		= labeller.newLabel("loop");
		String joinLabel  		= labeller.newLabel("join");
		
		// Generators
		ArrayAllocateSCG allocateSCG = new ArrayAllocateSCG();
		ArrayGenerateRecordSCG recordSCG = new ArrayGenerateRecordSCG(outType);
		OpcodeForLoadSCG loadArgSCG = new OpcodeForLoadSCG(inType);
		OpcodeForStoreFunctionSCG storeArgSCG = new OpcodeForStoreFunctionSCG(inType);
		OpcodeForLoadSCG loadResultSCG = new OpcodeForLoadSCG(outType);
		OpcodeForStoreSCG storeResultSCG = new OpcodeForStoreSCG(outType);

		code.add(Label, startLabel);
		
		code.add(PushD, RunTime.ARRAY_TEMP_2);
		code.append(oldArray);
		code.add(StoreI);
		
		// Setup temp values
		code.add(PushD, RunTime.ARRAY_TEMP_4);
		code.add(PushD, RunTime.ARRAY_TEMP_2);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);
		code.add(StoreI);	
		
		// Determine allocation size of new array
		code.add(PushD, RunTime.ARRAY_TEMP_2);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);		// Array length
		code.add(Duplicate); 	// Duplicate for storage in record
		code.add(PushI, outType.getSize());
		code.add(Multiply);
		
		// Store allocated array address in ARRAY_TEMP_1
		code.add(Label, allocateLabel);
		code.addChunk(allocateSCG.generate());
		code.addChunk(recordSCG.generate());
		
		// Define loop invariants (index)
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(PushI, 0);
		code.add(StoreI);
		
		// Loop through each element, call Lambda, store result in new array
		code.add(Label, loopLabel);
		code.add(PushD, RunTime.ARRAY_TEMP_4);
		code.add(LoadI);
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(LoadI);
		code.add(Subtract);
		code.add(JumpFalse, joinLabel);
		
		// Push argument onto Frame Stack						
			// Move Stack Pointer
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, inType.getSize());
			code.add(Subtract);
			code.add(StoreI);
			
			// Put argument value
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(LoadI);
			code.add(PushI, inType.getSize());
			code.add(Multiply);
			code.add(Add);
			code.addChunk(loadArgSCG.generate());
			code.addChunk(storeArgSCG.generate());
			
			// Preserve ARRAY_TEMP_1
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(LoadI);
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(LoadI);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(LoadI);
			code.add(PushD, RunTime.ARRAY_TEMP_4);
			code.add(LoadI);
			
			// Push lambda and call
			code.append(lambda);
			code.add(CallV);
			
			// Return ARRAY_TEMP_1
			code.add(PushD, RunTime.ARRAY_TEMP_4);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(Exchange);
			code.add(StoreI);
			
			// Get and store return value
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(LoadI);
			code.add(PushI, outType.getSize());
			code.add(Multiply);
			code.add(Add);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.addChunk(loadResultSCG.generate());
			code.addChunk(storeResultSCG.generate());
			
			// Move the Stack Pointer up by the size of the return value
			code.add(PushD, RunTime.STACK_POINTER, "%% restore stack pointer");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, outType.getSize());
			code.add(Add);
			code.add(StoreI);
			
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(LoadI);
		code.add(PushI, 1);
		code.add(Add);
		code.add(StoreI);
		
		code.add(Jump, loopLabel);
		code.add(Label, joinLabel);
		
		code.add(PushD, RunTime.ARRAY_TEMP_1);
		code.add(LoadI);

		return code;
	}

}
