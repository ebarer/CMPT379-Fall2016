package asmCodeGenerator.runtime;
import static asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType.*;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

import asmCodeGenerator.codeGenerator.RationalInvertSCG;
import asmCodeGenerator.codeGenerator.RationalTempToStackSCG;
import asmCodeGenerator.codeGenerator.RationalStackToTempSCG;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
public class RunTime {
	public static final String EAT_LOCATION_ZERO      				= "$eat-location-zero";		// helps us distinguish null pointers from real ones.
	public static final String INTEGER_PRINT_FORMAT   				= "$print-format-integer";
	public static final String FLOATING_PRINT_FORMAT  				= "$print-format-floating";
	public static final String RATIONAL_PRINT_FORMAT  				= "$print-format-rational";
	public static final String RATIONAL_FRACTION_PRINT_FORMAT  		= "$print-format-rational-fraction";
	public static final String RATIONAL_NEG_FRACTION_PRINT_FORMAT  	= "$print-format-rational-neg-fraction";
	public static final String BOOLEAN_PRINT_FORMAT   				= "$print-format-boolean";
	public static final String CHARACTER_PRINT_FORMAT 				= "$print-format-character";
	public static final String STRING_PRINT_FORMAT 	  				= "$print-format-string";
	public static final String NEWLINE_PRINT_FORMAT   				= "$print-format-newline";
	public static final String TAB_PRINT_FORMAT   	  				= "$print-format-tab";
	public static final String SPACE_PRINT_FORMAT     				= "$print-format-space";
	public static final String BOOLEAN_TRUE_STRING    				= "$boolean-true-string";
	public static final String BOOLEAN_FALSE_STRING   				= "$boolean-false-string";
	public static final String ARRAY_ALREADY_RELEASED				= "$array-released";
	public static final String ARRAY_RECURSE_RELEASED   			= "$array-recurse-released";
	
	public static final String RATIONAL_TEMP_NUMERATOR_1	= "$rational-temp-numerator-1";
	public static final String RATIONAL_TEMP_DENOMINATOR_1 	= "$rational-temp-denominator-1";
	public static final String RATIONAL_TEMP_NUMERATOR_2 	= "$rational-temp-numerator-2";
	public static final String RATIONAL_TEMP_DENOMINATOR_2 	= "$rational-temp-denominator-2";
	public static final String RELEASE_TEMP_1				= "$release-temp-1";
	public static final String RELEASE_TEMP_2				= "$release-temp-2";
	public static final String RELEASE_TEMP_3				= "$release-temp-3";
	public static final String RELEASE_TEMP_4				= "$release-temp-4";
	public static final String INDEX_TEMP_1					= "$index-temp-1";
	public static final String INDEX_TEMP_2					= "$index-temp-2";
	public static final String ARRAY_TEMP_1					= "$array-temp-1";
	public static final String ARRAY_TEMP_2					= "$array-temp-2";
	public static final String ARRAY_TEMP_3					= "$array-temp-3";
	public static final String ARRAY_TEMP_4					= "$array-temp-4";
	public static final String ARRAY_TEMP_5					= "$array-temp-5";
	public static final String PRINT_RATIONAL_TEMP_1		= "$print-rational-temp-1";
	public static final String PRINT_RATIONAL_TEMP_2 		= "$print-rational-temp-2";
	public static final String PRINT_RATIONAL_TEMP_3 		= "$print-rational-temp-3";
	public static final String PRINT_TEMP_1					= "$print-temp-1";
	public static final String PRINT_TEMP_2 				= "$print-temp-2";
	public static final String PRINT_TEMP_3 				= "$print-temp-3";
	
	public static final String SUB_RATIONAL_FIND_GCD		= "$sub-rational-find-gcd";
	public static final String SUB_PRINT_ARRAY				= "$sub-print-array";
	
	public static final String GLOBAL_MEMORY_BLOCK    			= "$global-memory-block";
	public static final String USABLE_MEMORY_START    			= "$usable-memory-start";
	public static final String STACK_POINTER    				= "$stack-pointer";
	public static final String FRAME_POINTER    				= "$frame-pointer";
	
	public static final String MAIN_PROGRAM_LABEL     			= "$$main";
	
	public static final String GENERAL_RUNTIME_ERROR 			= "$$general-runtime-error";
	public static final String DIVIDE_BY_ZERO_RUNTIME_ERROR 	= "$$divide-by-zero";
	public static final String BAD_INDEX_RUNTIME_ERROR 			= "$$bad-index";
	public static final String FUNCTION_RUNOFF_RUNTIME_ERROR	= "$$function-runoff";

	private ASMCodeFragment environmentASM() {
		ASMCodeFragment result = new ASMCodeFragment(GENERATES_VOID);
		result.append(jumpToMain());
		result.append(stringsForPrintf());
		result.append(runtimeErrors());
		result.append(temporaryStorage());
		result.append(subRationalFindGCD());
		result.add(DLabel, USABLE_MEMORY_START);
		return result;
	}
	
	private ASMCodeFragment jumpToMain() {
		ASMCodeFragment frag = new ASMCodeFragment(GENERATES_VOID);
		frag.add(Jump, MAIN_PROGRAM_LABEL);
		return frag;
	}

	private ASMCodeFragment stringsForPrintf() {
		ASMCodeFragment frag = new ASMCodeFragment(GENERATES_VOID);
		frag.add(DLabel, EAT_LOCATION_ZERO);
		frag.add(DataZ, 8);
		frag.add(DLabel, INTEGER_PRINT_FORMAT);
		frag.add(DataS, "%d");
		frag.add(DLabel, FLOATING_PRINT_FORMAT);
		frag.add(DataS, "%g");
		frag.add(DLabel, RATIONAL_PRINT_FORMAT);
		frag.add(DataS, "%d_%d/%d");
		frag.add(DLabel, RATIONAL_FRACTION_PRINT_FORMAT);
		frag.add(DataS, "_%d/%d");
		frag.add(DLabel, RATIONAL_NEG_FRACTION_PRINT_FORMAT);
		frag.add(DataS, "-_%d/%d");
		frag.add(DLabel, BOOLEAN_PRINT_FORMAT);
		frag.add(DataS, "%s");
		frag.add(DLabel, CHARACTER_PRINT_FORMAT);
		frag.add(DataS, "%c");
		frag.add(DLabel, STRING_PRINT_FORMAT);
		frag.add(DataS, "%s");
		frag.add(DLabel, NEWLINE_PRINT_FORMAT);
		frag.add(DataS, "\n");
		frag.add(DLabel, TAB_PRINT_FORMAT);
		frag.add(DataS, "\t");
		frag.add(DLabel, SPACE_PRINT_FORMAT);
		frag.add(DataS, " ");
		frag.add(DLabel, BOOLEAN_TRUE_STRING);
		frag.add(DataS, "true");
		frag.add(DLabel, BOOLEAN_FALSE_STRING);
		frag.add(DataS, "false");
		
		frag.add(DLabel, ARRAY_RECURSE_RELEASED);
		frag.add(DataS, "Array recursively released\n");
		frag.add(DLabel, ARRAY_ALREADY_RELEASED);
		frag.add(DataS, "Array already released\n");
		
		return frag;
	}
	
	private ASMCodeFragment runtimeErrors() {
		ASMCodeFragment frag = new ASMCodeFragment(GENERATES_VOID);
		
		generalRuntimeError(frag);
		divideByZeroError(frag);
		arrayBadIndexError(frag);
		functionRunoffError(frag);
		
		return frag;
	}
	private ASMCodeFragment generalRuntimeError(ASMCodeFragment frag) {
		String generalErrorMessage = "$errors-general-message";

		frag.add(DLabel, generalErrorMessage);
		frag.add(DataS, "Runtime error: %s\n");
		
		frag.add(Label, GENERAL_RUNTIME_ERROR);
		frag.add(PushD, generalErrorMessage);
		frag.add(Printf);
		frag.add(Halt);
		return frag;
	}
	private void divideByZeroError(ASMCodeFragment frag) {
		String divideByZeroMessage = "$errors-divide-by-zero";
		
		frag.add(DLabel, divideByZeroMessage);
		frag.add(DataS, "division by zero");
		
		frag.add(Label, DIVIDE_BY_ZERO_RUNTIME_ERROR);
		frag.add(PushD, divideByZeroMessage);
		frag.add(Jump, GENERAL_RUNTIME_ERROR);
	}
	private void arrayBadIndexError(ASMCodeFragment frag) {
		String badIndexValueMessage = "$errors-bad-index";
		
		frag.add(DLabel, badIndexValueMessage);
		frag.add(DataS, "bad index used for array");
		
		frag.add(Label, BAD_INDEX_RUNTIME_ERROR);
		frag.add(PushD, badIndexValueMessage);
		frag.add(Jump, GENERAL_RUNTIME_ERROR);
	}
	private void functionRunoffError(ASMCodeFragment frag) {
		String functionRunoffMessage = "$errors-function-runoff";
		
		frag.add(DLabel, functionRunoffMessage);
		frag.add(DataS, "code ran off the end of the function");
		
		frag.add(Label, FUNCTION_RUNOFF_RUNTIME_ERROR);
		frag.add(PushD, functionRunoffMessage);
		frag.add(Jump, GENERAL_RUNTIME_ERROR);
	}
	
	private ASMCodeFragment temporaryStorage() {
		ASMCodeFragment frag = new ASMCodeFragment(GENERATES_VOID);
		
		frag.add(DLabel, INDEX_TEMP_1);
		frag.add(DataI, 0);
		frag.add(DLabel, INDEX_TEMP_2);
		frag.add(DataI, 0);
		
		frag.add(DLabel, ARRAY_TEMP_1);
		frag.add(DataI, 0);
		frag.add(DLabel, ARRAY_TEMP_2);
		frag.add(DataI, 0);
		frag.add(DLabel, ARRAY_TEMP_3);
		frag.add(DataI, 0);
		frag.add(DLabel, ARRAY_TEMP_4);
		frag.add(DataI, 0);
		frag.add(DLabel, ARRAY_TEMP_5);
		frag.add(DataI, 0);
		
		frag.add(DLabel, RELEASE_TEMP_1);
		frag.add(DataI, 0);
		frag.add(DLabel, RELEASE_TEMP_2);
		frag.add(DataI, 0);
		frag.add(DLabel, RELEASE_TEMP_3);
		frag.add(DataI, 0);
		frag.add(DLabel, RELEASE_TEMP_4);
		frag.add(DataI, 0);
		
		frag.add(DLabel, RATIONAL_TEMP_NUMERATOR_1);
		frag.add(DataI, 0);
		frag.add(DLabel, RATIONAL_TEMP_DENOMINATOR_1);
		frag.add(DataI, 0);
		frag.add(DLabel, RATIONAL_TEMP_NUMERATOR_2);
		frag.add(DataI, 0);
		frag.add(DLabel, RATIONAL_TEMP_DENOMINATOR_2);
		frag.add(DataI, 0);
		
		frag.add(DLabel, PRINT_TEMP_1);
		frag.add(DataI, 0);
		frag.add(DLabel, PRINT_TEMP_2);
		frag.add(DataI, 0);
		frag.add(DLabel, PRINT_TEMP_3);
		frag.add(DataI, 0);
		
		frag.add(DLabel, PRINT_RATIONAL_TEMP_1);
		frag.add(DataI, 0);
		frag.add(DLabel, PRINT_RATIONAL_TEMP_2);
		frag.add(DataI, 0);
		frag.add(DLabel, PRINT_RATIONAL_TEMP_3);
		frag.add(DataI, 0);
		
		return frag;
	}
	
	private ASMCodeFragment subRationalFindGCD() {
		String loopLabel = "gcd-loop";
		String exitLabel = "gcd-exit-loop";
		String skipNegateLabel = "gcd-skip-negate-loop";
		
		RationalTempToStackSCG load = new RationalTempToStackSCG();
		RationalStackToTempSCG store = new RationalStackToTempSCG();
		RationalInvertSCG swap = new RationalInvertSCG();
		
		ASMCodeFragment subroutine = new ASMCodeFragment(GENERATES_VOID);
		subroutine.add(Label, SUB_RATIONAL_FIND_GCD);
		
		// Get values and store in secondary temp location
		subroutine.addChunk(load.generate(1));
		subroutine.addChunk(store.generate(2));
		
		// While loop, repeat until GCD found
		subroutine.add(Label, loopLabel);
		// If denominator is not 0, continue loop
		subroutine.add(PushD, RATIONAL_TEMP_DENOMINATOR_2);
		subroutine.add(LoadI);
		subroutine.add(JumpFalse, exitLabel);
		
		// Grab remainder of division between numerator and denominator
		subroutine.addChunk(load.generate(2));
		subroutine.add(ASMOpcode.Remainder);
		
		// Swap values and store remainder calculation
		// in the denominator
		subroutine.addChunk(swap.generate(2));
		subroutine.add(PushD, RATIONAL_TEMP_DENOMINATOR_2);
		subroutine.add(Exchange);
		subroutine.add(ASMOpcode.StoreI);
		subroutine.add(Jump, loopLabel);
		subroutine.add(Label, exitLabel);
		
		// Ensure GCD is positive
		subroutine.add(PushD, RATIONAL_TEMP_NUMERATOR_2);
		subroutine.add(LoadI);
		subroutine.add(Duplicate);
		subroutine.add(JumpPos, skipNegateLabel);
		subroutine.add(Negate);
		subroutine.add(Label, skipNegateLabel);
		subroutine.add(PushD, RATIONAL_TEMP_NUMERATOR_2);
		subroutine.add(Exchange);
		subroutine.add(StoreI);
		
		// Divide numerator by GCD, update value
		subroutine.add(PushD, RATIONAL_TEMP_NUMERATOR_1);
		subroutine.add(LoadI);
		subroutine.add(PushD, RATIONAL_TEMP_NUMERATOR_2);
		subroutine.add(LoadI);
		subroutine.add(Divide);
		subroutine.add(PushD, RATIONAL_TEMP_NUMERATOR_1);
		subroutine.add(Exchange);
		subroutine.add(StoreI);
		
		// Divide denominator by GCD, update value
		subroutine.add(PushD, RATIONAL_TEMP_DENOMINATOR_1);
		subroutine.add(LoadI);
		subroutine.add(PushD, RATIONAL_TEMP_NUMERATOR_2);
		subroutine.add(LoadI);
		subroutine.add(Divide);
		subroutine.add(PushD, RATIONAL_TEMP_DENOMINATOR_1);
		subroutine.add(Exchange);
		subroutine.add(StoreI);
		
		subroutine.add(Return);
		return subroutine;
	}
	
	public static ASMCodeFragment getEnvironment() {
		RunTime rt = new RunTime();
		return rt.environmentASM();
	}
}
