package asmCodeGenerator;

import parseTree.ParseNode;
import parseTree.nodeTypes.NewlineNode;
import parseTree.nodeTypes.PrintStatementNode;
import parseTree.nodeTypes.SpaceNode;
import parseTree.nodeTypes.TabNode;
import semanticAnalyzer.types.ArrayType;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.ASMCodeGenerator.CodeVisitor;
import asmCodeGenerator.codeGenerator.PrintArraySCG;
import asmCodeGenerator.codeGenerator.PrintBooleanSCG;
import asmCodeGenerator.codeGenerator.PrintRationalSCG;
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
		Type type = node.getType();
		ASMCodeFragment value = visitor.removeValueCode(node);
		
		code.append(value);
		
		if (type == PrimitiveType.RATIONAL) {
			PrintRationalSCG scg = new PrintRationalSCG();
			code.addChunk(scg.generate());
		} else if (type instanceof ArrayType) {			
			PrintArraySCG scg = new PrintArraySCG();
			Type subType = ((ArrayType) type).getSubtype();
			code.addChunk(scg.generate(subType));
		} else {
			if (type == PrimitiveType.BOOLEAN) {
				PrintBooleanSCG scg = new PrintBooleanSCG();
				code.addChunk(scg.generate());
			}
			
			if (type == PrimitiveType.STRING) {
				code.add(ASMOpcode.PushI, 12);
				code.add(ASMOpcode.Add);
			}
		
			String format = printFormat(type);
			code.add(ASMOpcode.PushD, format);
			code.add(ASMOpcode.Printf);
		}
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
