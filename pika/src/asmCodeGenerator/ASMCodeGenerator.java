package asmCodeGenerator;

import java.util.HashMap;
import java.util.Map;
import asmCodeGenerator.codeGenerator.*;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import lexicalAnalyzer.*;
import parseTree.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.types.*;
import symbolTable.*;
import static asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType.*;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

// do not call the code generator if any errors have occurred during analysis.
public class ASMCodeGenerator {
	ParseNode root;

	public static ASMCodeFragment generate(ParseNode syntaxTree) {
		ASMCodeGenerator codeGenerator = new ASMCodeGenerator(syntaxTree);
		return codeGenerator.makeASM();
	}
	public ASMCodeGenerator(ParseNode root) {
		super();
		this.root = root;
	}
	
	public ASMCodeFragment makeASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);

		code.append(MemoryManager.codeForInitialization());
		code.append(RunTime.getEnvironment());
		code.append(globalVariableBlockASM());
		code.append(programASM());
		code.append(MemoryManager.codeForAfterApplication());
		
		return code;
	}
	private ASMCodeFragment globalVariableBlockASM() {
		assert root.hasScope();
		Scope scope = root.getScope();
		int globalBlockSize = scope.getAllocatedSize();
		
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
		code.add(DLabel, RunTime.GLOBAL_MEMORY_BLOCK);
		code.add(DataZ, globalBlockSize);
		return code;
	}
	private ASMCodeFragment programASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
		
		code.add(Label, RunTime.MAIN_PROGRAM_LABEL);
		code.append( programCode());
		code.add(Halt, "", "%% End of Execution");
		
		return code;
	}
	private ASMCodeFragment programCode() {
		CodeVisitor visitor = new CodeVisitor();
		root.accept(visitor);
		return visitor.removeRootCode(root);
	}


	protected class CodeVisitor extends ParseNodeVisitor.Default {
		private Map<ParseNode, ASMCodeFragment> codeMap;
		ASMCodeFragment code;
		
		public CodeVisitor() {
			codeMap = new HashMap<ParseNode, ASMCodeFragment>();
		}


		////////////////////////////////////////////////////////////////////
        // Make the field "code" refer to a new fragment of different sorts.
		private void newAddressCode(ParseNode node) {
			code = new ASMCodeFragment(GENERATES_ADDRESS);
			codeMap.put(node, code);
		}
		private void newValueCode(ParseNode node) {
			code = new ASMCodeFragment(GENERATES_VALUE);
			codeMap.put(node, code);
		}
		private void newVoidCode(ParseNode node) {
			code = new ASMCodeFragment(GENERATES_VOID);
			codeMap.put(node, code);
		}

	    ////////////////////////////////////////////////////////////////////
        // Get code from the map.
		private ASMCodeFragment getAndRemoveCode(ParseNode node) {
			ASMCodeFragment result = codeMap.get(node);
			codeMap.remove(result);
			return result;
		}
	    public  ASMCodeFragment removeRootCode(ParseNode tree) {
			return getAndRemoveCode(tree);
		}		
		ASMCodeFragment removeValueCode(ParseNode node) {
			ASMCodeFragment frag = getAndRemoveCode(node);
			makeFragmentValueCode(frag, node);
			return frag;
		}		
		private ASMCodeFragment removeAddressCode(ParseNode node) {
			ASMCodeFragment frag = getAndRemoveCode(node);
			assert frag.isAddress();
			return frag;
		}		
		ASMCodeFragment removeVoidCode(ParseNode node) {
			ASMCodeFragment frag = getAndRemoveCode(node);
			assert frag.isVoid();
			return frag;
		}
		
	    ////////////////////////////////////////////////////////////////////
        // convert code to value-generating code.
		private void makeFragmentValueCode(ASMCodeFragment code, ParseNode node) {
			assert !code.isVoid();
			
			if(code.isAddress()) {
				turnAddressIntoValue(code, node);
			}	
		}
		private void turnAddressIntoValue(ASMCodeFragment code, ParseNode node) {
			if(node.getType() == PrimitiveType.INTEGER) {
				code.add(LoadI);
			}
			else if(node.getType() == PrimitiveType.FLOATING) {
				code.add(LoadF);
			}	
			else if(node.getType() == PrimitiveType.RATIONAL) {
				RationalMemToStackSCG scg = new RationalMemToStackSCG();
				code.addChunk(scg.generate());
			}	
			else if(node.getType() == PrimitiveType.BOOLEAN) {
				code.add(LoadC);
			}
			else if(node.getType() == PrimitiveType.CHARACTER) {
				code.add(LoadC);
			}
			else if(node.getType() == PrimitiveType.STRING) {
				code.add(LoadI);
			}
			else if(node.getType() instanceof ArrayType) {
				code.add(LoadI);
			}
			else {
				assert false : "node " + node;
			}
			code.markAsValue();
		}
		
	    ////////////////////////////////////////////////////////////////////
        // ensures all types of ParseNode in given AST have at least a visitLeave	
		public void visitLeave(ParseNode node) {
			assert false : "node " + node + " not handled in ASMCodeGenerator";
		}
		
		///////////////////////////////////////////////////////////////////////////
		// constructs larger than statements
		public void visitLeave(ProgramNode node) {
			newVoidCode(node);
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		public void visitLeave(MainBlockNode node) {
			newVoidCode(node);
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		public void visitLeave(BlockNode node) {
			newVoidCode(node);
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}

		///////////////////////////////////////////////////////////////////////////
		// identifiers	
		
		public void visitLeave(IdentifierNode node) {
			newAddressCode(node);
			Binding binding = node.getBinding();
			binding.generateAddress(code);
		}
		
		///////////////////////////////////////////////////////////////////////////
		// index	
		
		public void visitLeave(IndexNode node) {
			newAddressCode(node);
			
			ASMCodeFragment array = removeValueCode(node.child(0));
			ASMCodeFragment index = removeValueCode(node.child(1));
			
			code.append(array);
			code.append(index);
								
			ArrayOffsetSCG scg = new ArrayOffsetSCG();
			code.addChunk(scg.generate());
		}		
				
		///////////////////////////////////////////////////////////////////////////
		// statements and declarations

		public void visitLeave(PrintStatementNode node) {
			newVoidCode(node);
			new PrintStatementGenerator(code, this).generate(node);	
		}
		public void visit(NewlineNode node) {
			newVoidCode(node);
			code.add(PushD, RunTime.NEWLINE_PRINT_FORMAT);
			code.add(Printf);
		}
		public void visit(TabNode node) {
			newVoidCode(node);
			code.add(PushD, RunTime.TAB_PRINT_FORMAT);
			code.add(Printf);
		}
		public void visit(SpaceNode node) {
			newVoidCode(node);
			code.add(PushD, RunTime.SPACE_PRINT_FORMAT);
			code.add(Printf);
		}
		
		public void visitLeave(DeclarationNode node) {
			storeNode(node);
		}
		public void visitLeave(AssignmentNode node) {
			storeNode(node);
		}
		private void storeNode(ParseNode node) {
			newVoidCode(node);
			
			Type type = node.getType();
			ASMCodeFragment lvalue = removeAddressCode(node.child(0));	
			ASMCodeFragment rvalue = removeValueCode(node.child(1));
			
			code.append(lvalue);
			code.append(rvalue);
			
			opcodeForStore(type);
		}
		private void opcodeForStore(Type type) {
			if(type == PrimitiveType.INTEGER) {
				code.add(ASMOpcode.StoreI);
			}
			else if(type == PrimitiveType.FLOATING) {
				code.add(ASMOpcode.StoreF);
			}
			else if(type == PrimitiveType.RATIONAL) {
				RationalStackToTempSCG scg = new RationalStackToTempSCG();
				code.addChunk(scg.generate());
				code.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
				
				RationalTempToRationalSCG scg2 = new RationalTempToRationalSCG();
				code.addChunk(scg2.generate());
			}
			else if(type == PrimitiveType.BOOLEAN) {
				code.add(ASMOpcode.StoreC);
			}
			else if(type == PrimitiveType.CHARACTER) {
				code.add(ASMOpcode.StoreC);
			}
			else if(type == PrimitiveType.STRING) {
				code.add(ASMOpcode.StoreI);
			}
			else if(type instanceof ArrayType) {
				code.add(ASMOpcode.StoreI);
			}
			else {
				assert false: "Type " + type + " unimplemented in opcodeForStore()";
			}
		}
		
		///////////////////////////////////////////////////////////////////////////
		// if statements
		public void visitLeave(ReleaseNode node) {
			newVoidCode(node);
			
			Labeller labeller = new Labeller("release-stmt");
			String startLabel = labeller.newLabel("");
			String joinLabel  = labeller.newLabel("join");
			
			code.add(ASMOpcode.Label, startLabel);
			
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			code.append(arg1);
			code.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
			code.add(ASMOpcode.Exchange);
			code.add(ASMOpcode.StoreI);
			
			// Check permanent-status bit
			code.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 4);
			code.add(ASMOpcode.Add);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 1);
			code.add(ASMOpcode.BTAnd);
			code.add(ASMOpcode.JumpTrue, joinLabel);
			
			// Check is-deleted-status bit
			code.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 4);
			code.add(ASMOpcode.Add);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 2);
			code.add(ASMOpcode.BTAnd);
			code.add(ASMOpcode.JumpTrue, joinLabel);
			
			// TODO: Check the subtype-is-reference bit (recurse)
			code.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 4);
			code.add(ASMOpcode.Add);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 4);
			code.add(ASMOpcode.BTAnd);
			code.add(ASMOpcode.JumpTrue, joinLabel);

			// Set is-deleted-status bit
			code.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 4);
			code.add(ASMOpcode.Add);
			code.add(ASMOpcode.Duplicate);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.PushI, 2);
			code.add(ASMOpcode.BTOr);
			code.add(ASMOpcode.StoreI);
			
			// Deallocate memory
			code.add(ASMOpcode.PushD, RunTime.RELEASE_TEMP_1);
			code.add(ASMOpcode.LoadI);
			code.add(ASMOpcode.Call, MemoryManager.MEM_MANAGER_DEALLOCATE);
			
			code.add(ASMOpcode.Label, joinLabel);
		}
		
		///////////////////////////////////////////////////////////////////////////
		// if statements
		public void visitLeave(IfNode node) {
			newVoidCode(node);
			
			Labeller labeller = new Labeller("if-stmt");
			String startLabel = labeller.newLabel("");
			String elseLabel  = labeller.newLabel("else");
			String joinLabel  = labeller.newLabel("join");
			
			code.add(Label, startLabel);
			
			ASMCodeFragment conditionCode = removeValueCode(node.child(0));
			code.append(conditionCode);
			code.add(JumpFalse, elseLabel);
			
			ASMCodeFragment blockCode = removeVoidCode(node.child(1));
			code.append(blockCode);
			code.add(Jump, joinLabel);
			
			code.add(Label, elseLabel);
			if (node.nChildren() == 3) {
				ASMCodeFragment elseCode = removeVoidCode(node.child(2));
				code.append(elseCode);				
			}
			code.add(Label, joinLabel);
		}
		
		///////////////////////////////////////////////////////////////////////////
		// while statements
		public void visitLeave(WhileNode node) {
			newVoidCode(node);
			
			Labeller labeller = new Labeller("while-stmt");
			String loopLabel  = labeller.newLabel("loop");
			String joinLabel  = labeller.newLabel("join");
			
			code.add(Label, loopLabel);
			
			ASMCodeFragment conditionCode = removeValueCode(node.child(0));
			code.append(conditionCode);
			code.add(JumpFalse, joinLabel);

			ASMCodeFragment blockCode = removeVoidCode(node.child(1));
			code.append(blockCode);

			code.add(Jump, loopLabel);

			code.add(Label, joinLabel);
		}

		///////////////////////////////////////////////////////////////////////////
		// expressions
		public void visitEnter(BinaryOperatorNode node) {
			if (node.isBooleanOperator() && !(node.getParent() instanceof BinaryOperatorNode)) {
				new Labeller("boolean");
			}
		}
		public void visitLeave(BinaryOperatorNode node) {
			newValueCode(node);
			
			if (node.isBooleanOperator()) {
				visitBooleanOperatorNode(node);
			} else if (node.isComparator()) {
				Lextant operator = node.getOperator();
				visitComparisonOperatorNode(node, operator);
			} else {
				visitNormalBinaryOperatorNode(node);
			}
		}
		private void visitBooleanOperatorNode(BinaryOperatorNode node) {
			// TODO: Make into a code generator
			Object variant = node.getSignature().getVariant();
			if (variant instanceof ASMOpcode) {
				ASMOpcode opcode = (ASMOpcode) variant;
			
				Labeller labeller = new Labeller("boolean", false);
				String joinLabel  = labeller.newLabel("join");

				ASMCodeFragment arg1 = removeValueCode(node.child(0));
				ASMCodeFragment arg2 = removeValueCode(node.child(1));

				code.append(arg1);
				code.add(Duplicate);
				
				if (opcode == And) {
					code.add(JumpFalse, joinLabel);
				} else if (opcode == Or) {
					code.add(JumpTrue, joinLabel);
				}

				code.append(arg2);
				code.add(opcode);
				
				if (!(node.getParent() instanceof BinaryOperatorNode)) {
					code.add(Label, joinLabel);
				}
			}
		}
		private void visitComparisonOperatorNode(BinaryOperatorNode node, Lextant operator) {
			// TODO: Make into a code generator
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			Type type = node.getSignature().paramType();
			
			Labeller labeller = new Labeller("compare");
			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");
			
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add((type == PrimitiveType.FLOATING) ? FSubtract : Subtract);
			
			Punctuator comparator = (Punctuator) operator;
			switch(comparator) {
			case LESS_OR_EQUAL:
				code.add((type == PrimitiveType.FLOATING) ? JumpFPos : JumpPos, falseLabel);
				code.add(Jump, trueLabel);	
				break;
			case LESS:
				code.add((type == PrimitiveType.FLOATING) ? JumpFNeg : JumpNeg, trueLabel);
				code.add(Jump, falseLabel);	
				break;
			case EQUAL:
				code.add((type == PrimitiveType.FLOATING) ? JumpFZero : JumpFalse, trueLabel);
				code.add(Jump, falseLabel);	
				break;
			case NOT_EQUAL:
				code.add((type == PrimitiveType.FLOATING) ? JumpFZero : JumpFalse, falseLabel);
				code.add(Jump, trueLabel);	
				break;
			case GREATER:
				code.add((type == PrimitiveType.FLOATING) ? JumpFPos : JumpPos, trueLabel);
				code.add(Jump, falseLabel);	
				break;
			case GREATER_OR_EQUAL:
				code.add((type == PrimitiveType.FLOATING) ? JumpFNeg : JumpNeg, falseLabel);
				code.add(Jump, trueLabel);	
				break;
			default:
				break;
			}

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Label, joinLabel);
		}
		private void visitNormalBinaryOperatorNode(BinaryOperatorNode node) {
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			Type type = node.getSignature().paramType();
			
			code.append(arg1);
			code.append(arg2);
			
			Object variant = node.getSignature().getVariant();
			
			if (variant instanceof SimpleCodeGenerator) {
				SimpleCodeGenerator scg1 = (SimpleCodeGenerator) variant;
				code.addChunk(scg1.generate());
			}
			
			if (variant instanceof ASMOpcode) {
				ASMOpcode opcode = (ASMOpcode) variant;
				
				// Check for division by 0, issue Runtime error
				if (opcode == ASMOpcode.Divide || opcode == ASMOpcode.FDivide) {
					DivisionByZeroSCG scg = new DivisionByZeroSCG(type);
					code.addChunk(scg.generate());
				}
				
				code.add(opcode);
			}
		}
		
		public void visitLeave(RationalOperatorNode node) {
			newValueCode(node);
			
			if (node.getOperator() == Punctuator.OVER) {
				visitOverOperatorNode(node);	
			}
			
			if (node.getOperator() == Punctuator.EXPRESS_OVER) {
				visitExpressOverOperatorNode(node);
			}
			
			if (node.getOperator() == Punctuator.RATIONALIZE) {
				visitRationalizeOperatorNode(node);
			}
		}
		private void visitOverOperatorNode(RationalOperatorNode node){
			// TODO: Make into a code generator
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			code.append(arg1);
			code.append(arg2);
		}
		private void visitExpressOverOperatorNode(RationalOperatorNode node) {
			// TODO: Make into a code generator
			assert node.nChildren() == 2;
			
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			
			code.append(arg1);
			
			Type type = node.child(0).getType();
			if (type == PrimitiveType.FLOATING) {
				code.append(arg2);
				code.add(ConvertF);
				code.add(FMultiply);
				code.add(ConvertI);
			} else if (type == PrimitiveType.RATIONAL) {
				// TODO: Double check functionality
				code.add(Exchange);
				code.append(arg2);
				code.add(Multiply);
				code.add(Exchange);
				code.add(Divide);
//				code.add(Divide);
//				code.append(arg2);
//				code.add(Multiply);
			}
		}
		private void visitRationalizeOperatorNode(RationalOperatorNode node) {
			// TODO: Make into a code generator
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			ASMCodeFragment frag = new ASMCodeFragment(GENERATES_VALUE);
			
			code.append(arg1);
			
			Type type = node.child(0).getType();
			if (type == PrimitiveType.FLOATING) {
				code.append(arg2);
				frag.add(ConvertF);
				frag.add(FMultiply);
				frag.add(ConvertI);
				code.append(frag);
			} else if (type == PrimitiveType.RATIONAL) {
				code.add(Exchange);
				code.append(arg2);
				frag.add(Multiply);
				frag.add(Exchange);
				frag.add(Divide);
				code.append(frag);
			}
			
			code.append(arg2);
			
			RationalStackToTempSCG scg = new RationalStackToTempSCG();
			code.addChunk(scg.generate());
			
			code.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
			
			RationalTempToStackSCG scg2 = new RationalTempToStackSCG();
			code.addChunk(scg2.generate());
		}
		
		public void visitLeave(UnaryOperatorNode node) {
			newValueCode(node);

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			code.append(arg1);
			
			Object variant = node.getSignature().getVariant();
			
			if (variant instanceof SimpleCodeGenerator) {
				SimpleCodeGenerator scg1 = (SimpleCodeGenerator) variant;
				code.addChunk(scg1.generate());
			}
			
			if (variant instanceof ASMOpcode) {
				ASMOpcode opcode = (ASMOpcode) variant;				
				code.add(opcode);
			}
		}
		
		public void visitLeave(CastNode node) {
			newValueCode(node);

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			code.append(arg1);
			
			Object variant = node.getSignature().getVariant();
			
			if (variant instanceof SimpleCodeGenerator) {
				SimpleCodeGenerator scg = (SimpleCodeGenerator) variant;
				code.addChunk(scg.generate());
			}
			
			if (variant instanceof ASMOpcode) {
				ASMOpcode opcode = (ASMOpcode) variant;
				code.add(opcode);
			}
		}

		public void visitLeave(ArrayNode node) {
			newValueCode(node);
			
			Labeller labeller = new Labeller("array");
			String startLabel  = labeller.newLabel("");
			String recordLabel  = labeller.newLabel("create-record");
			String startChildrenLabel  = labeller.newLabel("start-store-children");
			String endChildrenLabel  = labeller.newLabel("end-store-children");
			code.add(ASMOpcode.Label, startLabel);
			
			Lextant operator = node.getOperator();
			
			if (operator == Keyword.NEW) {
				ASMCodeChunk subtypeChunk = new ASMCodeChunk();
				
				// Push length onto stack
				if (node.isEmpty()) {	// Empty
					ASMCodeFragment length = removeValueCode(node.child(0));
					code.append(length);
					code.append(length);
					ArrayNegativeIndexSCG negativeIndexSCG = new ArrayNegativeIndexSCG();
					code.addChunk(negativeIndexSCG.generate());
				} else {	// Populatd
					code.add(ASMOpcode.PushI, node.nChildren());
					code.add(ASMOpcode.PushI, node.nChildren());
				}
				
				// Push subtype size onto stack
				code.addChunk(subtypeChunk);
				code.add(ASMOpcode.PushI, node.getSubtype().getSize());
				code.add(ASMOpcode.Multiply);
				
				// Allocate memory for array
				ArrayAllocateSCG scg1 = new ArrayAllocateSCG();
				code.addChunk(scg1.generate(operator));
				
				// Store header
				code.add(ASMOpcode.Label, recordLabel);
				ArrayGenerateRecordSCG scg2 = new ArrayGenerateRecordSCG();
				boolean isReference = false;
				if (node.getSubtype() instanceof ArrayType) {
					isReference = true;
				}
				code.addChunk(scg2.generate(node, isReference));
				
				// Push address onto stack
				ArrayTempToStackSCG scg3 = new ArrayTempToStackSCG();
				code.addChunk(scg3.generate(operator));
				
				code.add(ASMOpcode.Label, startChildrenLabel);
				if (!node.isEmpty()) {
					for (int i = 0; i < node.nChildren(); i++) {
						code.add(ASMOpcode.Duplicate);
						code.add(ASMOpcode.PushI, node.getOffset(i));
						code.add(ASMOpcode.Add);
						
						ASMCodeFragment child = removeValueCode(node.child(i));
						code.append(child);
						
						opcodeForStore(node.getSubtype());
					}
				}
				code.add(ASMOpcode.Label, endChildrenLabel);
			}
			
			if (operator == Keyword.CLONE) {				
				// Store CloneNode address
				ASMCodeFragment arg1 = removeValueCode(node.child(0));
				code.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
				code.append(arg1);
				code.add(ASMOpcode.StoreI);
				
				ArrayAllocateSCG scg1 = new ArrayAllocateSCG();
				code.addChunk(scg1.generate(operator));
				
				// Push address onto stack
				ArrayTempToStackSCG scg3 = new ArrayTempToStackSCG();
				code.addChunk(scg3.generate(operator));
				
				ArrayCloneSCG scg2 = new ArrayCloneSCG();
				code.addChunk(scg2.generate(node));				
			}
		}
		
		///////////////////////////////////////////////////////////////////////////
		// leaf nodes (ErrorNode not necessary)
		public void visit(BooleanConstantNode node) {
			newValueCode(node);
			code.add(PushI, node.getValue() ? 1 : 0);
		}
		public void visit(IntegerConstantNode node) {
			newValueCode(node);
			code.add(PushI, node.getValue());
		}
		public void visit(FloatingConstantNode node) {
			newValueCode(node);
			code.add(PushF, node.getValue());
		}
		public void visit(CharacterNode node) {
			newValueCode(node);
			code.add(PushI, node.getValue());
		}
		public void visit(StringNode node) {
			newValueCode(node);
			
			Labeller labeller = new Labeller("stringConstant");
			
			IdentifierNode identifier = node.getIdentifier();
			String varName = "";
			if (identifier != null) {
				varName = identifier.getToken().getLexeme();
			}
			String stringLabel = labeller.newLabel(varName);

			code.add(DLabel, stringLabel);
			
			// Configure string header
			code.add(DataI, 6); 				// String type
			code.add(DataI, 9); 				// Immutable and permanent
			code.add(DataI, node.getLength()); 	// Length
			
			// Push string characters
			code.add(DataS, node.getValue());
			code.add(PushD, stringLabel);
		}
	}

}
