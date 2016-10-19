package asmCodeGenerator;

import static asmCodeGenerator.codeStorage.ASMOpcode.Jump;
import static asmCodeGenerator.codeStorage.ASMOpcode.JumpTrue;
import static asmCodeGenerator.codeStorage.ASMOpcode.Label;
import static asmCodeGenerator.codeStorage.ASMOpcode.Printf;
import static asmCodeGenerator.codeStorage.ASMOpcode.PushD;

import com.sun.xml.internal.fastinfoset.Decoder;

import parseTree.ParseNode;
import parseTree.nodeTypes.NewlineNode;
import parseTree.nodeTypes.PrintStatementNode;
import parseTree.nodeTypes.SpaceNode;
import parseTree.nodeTypes.TabNode;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.ASMCodeGenerator.CodeVisitor;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.runtime.RunTime;

public class PrintStatementGenerator {
	ASMCodeFragment code;
	ASMCodeGenerator.CodeVisitor visitor;
	
	
	public PrintStatementGenerator(ASMCodeFragment code, CodeVisitor visitor) {
		super();
		this.code = code;
		this.visitor = visitor;
	}

	public void generate(PrintStatementNode node) {
		for(ParseNode child : node.getChildren()) {
			if(child instanceof NewlineNode || child instanceof TabNode || child instanceof SpaceNode) {
				ASMCodeFragment childCode = visitor.removeVoidCode(child);
				code.append(childCode);
			}
			else {
				appendPrintCode(child);
			}
		}
	}

	private void appendPrintCode(ParseNode node) {
		String format = printFormat(node.getType());
		ASMCodeFragment value = visitor.removeValueCode(node);
		code.append(value);
		
		convertToStringIfBoolean(node);
		convertToStringIfRational(node, value);
		
		code.add(PushD, format);
		code.add(Printf);
	}
	private void convertToStringIfBoolean(ParseNode node) {
		if(node.getType() != PrimitiveType.BOOLEAN) {
			return;
		}
		
		Labeller labeller = new Labeller("print-boolean");
		String trueLabel = labeller.newLabel("true");
		String endLabel = labeller.newLabel("join");

		code.add(JumpTrue, trueLabel);
		code.add(PushD, RunTime.BOOLEAN_FALSE_STRING);
		code.add(Jump, endLabel);
		code.add(Label, trueLabel);
		code.add(PushD, RunTime.BOOLEAN_TRUE_STRING);
		code.add(Label, endLabel);
	}
	private void convertToStringIfRational(ParseNode node, ASMCodeFragment value) {
		if(node.getType() != PrimitiveType.RATIONAL) {
			return;
		}
		
		Labeller labeller = new Labeller("print-rational");
		String skipFraction = labeller.newLabel("skip-fraction");
		String skipWhole = labeller.newLabel("skip-whole");
		String joinLabel= labeller.newLabel("join");
		
		// Determine fraction
		code.add(PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		code.add(ASMOpcode.LoadI);
		
		code.add(PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		code.add(ASMOpcode.LoadI);
		code.add(PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		code.add(ASMOpcode.LoadI);
		code.add(ASMOpcode.Remainder);
		
		code.add(ASMOpcode.Duplicate);
		code.add(ASMOpcode.JumpFalse, skipFraction);
		
		
		// Determine whole number
		code.add(PushD, RunTime.RATIONAL_TEMP_NUMERATOR_1);
		code.add(ASMOpcode.LoadI);
		code.add(PushD, RunTime.RATIONAL_TEMP_DENOMINATOR_1);
		code.add(ASMOpcode.LoadI);
		code.add(ASMOpcode.Divide);
	}


	private static String printFormat(Type type) {
		assert type instanceof PrimitiveType;
		
		switch((PrimitiveType)type) {
		case INTEGER:	return RunTime.INTEGER_PRINT_FORMAT;
		case FLOATING:	return RunTime.FLOATING_PRINT_FORMAT;
		case RATIONAL:  return RunTime.RATIONAL_PRINT_FORMAT;
		case BOOLEAN:	return RunTime.BOOLEAN_PRINT_FORMAT;
		case CHARACTER:	return RunTime.CHARACTER_PRINT_FORMAT;
		case STRING:	return RunTime.STRING_PRINT_FORMAT;
		default:		
			assert false : "Type " + type + " unimplemented in PrintStatementGenerator.printFormat()";
			return "";
		}
	}
}
