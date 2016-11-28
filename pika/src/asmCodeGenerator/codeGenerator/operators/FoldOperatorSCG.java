package asmCodeGenerator.codeGenerator.operators;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.array.ArrayAllocateSCG;
import asmCodeGenerator.codeGenerator.array.ArrayGenerateRecordSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreSCG;

import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class FoldOperatorSCG {
	private Type baseType;
	private Type arrType;
	private Type outType;
	private ASMCodeFragment base;
	private ASMCodeFragment array;
	private ASMCodeFragment lambda;
	
	public FoldOperatorSCG(Type baseT, Type arrT, Type outT) {
		this.baseType = baseT;
		this.arrType = arrT;		
		this.outType = outT;
		this.base = null;
		this.array = null;
		this.lambda = null;
	}
	public void setBase(ASMCodeFragment base) {
		this.base = base;
	}
	public void setArray(ASMCodeFragment array) {
		this.array = array;
	}
	public void setLambda(ASMCodeFragment lambda) {
		this.lambda = lambda;
	}
	
	public ASMCodeFragment generate() {
		assert array != null;
		assert lambda != null;
		
		ASMCodeFragment code = new ASMCodeFragment(CodeType.GENERATES_VALUE);
			
		Labeller labeller = new Labeller("zip-operator");
		String startLabel 		= labeller.newLabel();
		String loopLabel  		= labeller.newLabel("loop");
		String joinLabel  		= labeller.newLabel("join");
		
		// Generators
		OpcodeForLoadSCG loadArrSCG = new OpcodeForLoadSCG(arrType);
		OpcodeForStoreSCG storeArrSCG = new OpcodeForStoreSCG(arrType);
		OpcodeForLoadSCG loadBaseSCG = new OpcodeForLoadSCG(baseType);
		OpcodeForStoreSCG storeBaseSCG = new OpcodeForStoreSCG(baseType);
		OpcodeForLoadSCG loadResultSCG = new OpcodeForLoadSCG(outType);
		OpcodeForStoreSCG storeResultSCG = new OpcodeForStoreSCG(outType);

		code.add(Label, startLabel);
		
		// FIXME: do we need to allocate space for the new item?
		if (base != null) {
			code.add(PushD, RunTime.INDEX_TEMP_1);
			code.append(base);
			code.add(StoreI);
		} else {
			//code.add(PushD, RunTime.INDEX_TEMP_1);
			//code.add(StoreI);
		}
		
		code.add(PushD, RunTime.INDEX_TEMP_2);
		code.append(array);
		code.add(StoreI);
		
		// Define loop invariants (index)
		code.add(PushD, RunTime.INDEX_TEMP_3);
		code.add(PushI, 0);
		code.add(StoreI);
		
		// Loop through each element, call Lambda, store result in new array
		code.add(Label, loopLabel);
		code.add(PushD, RunTime.INDEX_TEMP_2);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);
		code.add(PushD, RunTime.INDEX_TEMP_3);
		code.add(LoadI);
		code.add(Subtract);
		code.add(JumpFalse, joinLabel);
		
		// Push argument onto Frame Stack						
			// Move Stack Pointer			
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, baseType.getSize());
			code.add(Subtract);
			code.add(StoreI);
			
			// Put argument value
			code.add(PushD, RunTime.STACK_POINTER, "%% store arg 1");
			code.add(LoadI);
			// FIXME: SOMETHING
			code.addChunk(loadBaseSCG.generate());
			code.addChunk(storeBaseSCG.generate());
			
			// Move Stack Pointer
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, arrType.getSize());
			code.add(Subtract);
			code.add(StoreI);
			
			// Put argument value
			code.add(PushD, RunTime.STACK_POINTER, "%% store arg 2");
			code.add(LoadI);
			// FIXME: SOMETHING
			code.addChunk(loadArrSCG.generate());
			code.addChunk(storeArrSCG.generate());
			
			// Push lambda and call
			code.append(lambda);
			code.add(CallV);
			
			// Get and store return value
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
			
		code.add(PushD, RunTime.INDEX_TEMP_3);
		code.add(PushD, RunTime.INDEX_TEMP_3);
		code.add(LoadI);
		code.add(PushI, 1);
		code.add(Add);
		code.add(StoreI);
		
		code.add(Jump, loopLabel);
		code.add(Label, joinLabel);
		
		code.add(PushD, RunTime.INDEX_TEMP_1);
		code.add(LoadI);

		return code;
	}

}
