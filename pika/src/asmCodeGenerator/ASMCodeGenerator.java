package asmCodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import asmCodeGenerator.codeGenerator.*;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;
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
	List<IdentifierNode> functionDefinitions;

	public static ASMCodeFragment generate(ParseNode syntaxTree) {
		ASMCodeGenerator codeGenerator = new ASMCodeGenerator(syntaxTree);
		return codeGenerator.makeASM();
	}
	public ASMCodeGenerator(ParseNode root) {
		super();
		this.root = root;
		this.functionDefinitions = new ArrayList<IdentifierNode>();
	}
	
	public ASMCodeFragment makeASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);

		code.append(MemoryManager.codeForInitialization());
		code.append(callStackBlockASM());
		code.append(RunTime.getEnvironment());
		code.append(globalVariableBlockASM());
		code.append(programASM());
		code.append(MemoryManager.codeForAfterApplication());
		
		return code;
	}
	private ASMCodeFragment callStackBlockASM() {		
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);		
		
		code.add(DLabel, RunTime.STACK_POINTER);
		code.add(DataI, 0);
		code.add(DLabel, RunTime.FRAME_POINTER);
		code.add(DataI, 0);
		
		code.add(PushD, RunTime.STACK_POINTER);
		code.add(Memtop);
		code.add(StoreI);
		
		code.add(PushD, RunTime.FRAME_POINTER);
		code.add(Memtop);
		code.add(StoreI);
		
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

		code.append(programCode());
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
			else if(node.getType() instanceof LambdaType) {
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
		
		public void visitLeave(ProgramNode node) {
			newVoidCode(node);
			
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		public void visitLeave(MainBlockNode node) {
			newVoidCode(node);
			
			code.add(Label, RunTime.MAIN_PROGRAM_LABEL);
			
			code.append(storeFunctionDefinitions());
			
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		private ASMCodeFragment storeFunctionDefinitions() {
			ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);

			for (IdentifierNode identifier : functionDefinitions) {
//				String label = ((LambdaNode)identifier.getParent().child(1)).getStartLabel();
				String label = identifier.getBinding().getLabel();
				ASMCodeFragment address = removeAddressCode(identifier);
				code.append(address);
				code.add(PushD, label);
				code.add(StoreI);
			}
			
			return code;
		}
		public void visitLeave(BlockNode node) {
			newVoidCode(node);
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}

		///////////////////////////////////////////////////////////////////////////
		// functions and lambdas

		public void visitLeave(FunctionDefinitionNode node) {
			newVoidCode(node);
			
			if (node.child(0) instanceof IdentifierNode) {
				functionDefinitions.add((IdentifierNode) node.child(0));
			}
			
			ASMCodeFragment lambda = removeValueCode(node.child(1));
			code.append(lambda);
		}
		
		public void visitLeave(LambdaNode node) {
			newValueCode(node);
			
			// Jump over lambda if it is inline
			if (!(node.getParent() instanceof FunctionDefinitionNode)) {
				code.add(Jump, node.getJumpLabel());
			}
			
			code.add(Label, node.getStartLabel());
			
			// Put return address on Frame Stack below Dynamic Link
			code.add(PushD, RunTime.STACK_POINTER, "%% store return addr.");
			code.add(LoadI);
			code.add(PushI, -8);
			code.add(Add);
			code.add(Exchange);
			code.add(StoreI);
			
			// Store Dynamic Link (current value of Frame Pointer) below the Stack Pointer
			code.add(PushD, RunTime.STACK_POINTER, "%% store dyn. link");
			code.add(LoadI);
			code.add(PushI, -4);
			code.add(Add);
			code.add(PushD, RunTime.FRAME_POINTER);
			code.add(LoadI);
			code.add(StoreI);
			
			// Move Frame Pointer to Stack Pointer
			code.add(PushD, RunTime.FRAME_POINTER, "%% move frame pointer");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(StoreI);
			
			// Move Stack Pointer to end of frame
			code.add(PushD, RunTime.STACK_POINTER, "%% move stack pointer");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, node.getFrameSize());
			code.add(Subtract);
			code.add(StoreI);

			
			// Lambda execution code
			ASMCodeFragment childCode = removeVoidCode(node.child(1));
			code.append(childCode);

			
			// Runoff error handling
			code.add(Label, node.getExitErrorLabel());
			code.add(Jump, RunTime.FUNCTION_RUNOFF_RUNTIME_ERROR);
			
			// Exit handshake
			code.add(Label, node.getExitHandshakeLabel());
		
			// Push the return address onto the accumulator stack
			code.add(PushD, RunTime.FRAME_POINTER, "%% get return addr.");
			code.add(LoadI);
			code.add(PushI, -8);
			code.add(Add);
			code.add(LoadI);
			
			// Replace the Frame Pointer with the dynamic link
			code.add(PushD, RunTime.FRAME_POINTER, "%% restore frame pointer");
			code.add(PushD, RunTime.FRAME_POINTER);
			code.add(LoadI);
			code.add(PushI, -4);
			code.add(Add);
			code.add(LoadI);
			code.add(StoreI);
			
			// Move Stack Pointer above current Parameter Scope
			code.add(PushD, RunTime.STACK_POINTER, "%% pop frame stack");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, node.getFrameSize());
			code.add(Add);
			code.add(PushI, node.getArgSize());
			code.add(Add);
			
			// Decrease the stack pointer by the return value size
			Type returnType = node.getReturnType();
			code.add(PushI, returnType.getSize(), "%% store return val.");
			code.add(Subtract);
			code.add(StoreI);
			
			// Store return address in temp until value is stored
			code.add(ASMOpcode.PushD, RunTime.FUNC_RETURN_ADDR_TEMP);
			code.add(ASMOpcode.Exchange);
			code.add(ASMOpcode.StoreI);
			
			// Store return value
			OpcodeForStoreFunctionSCG scg = new OpcodeForStoreFunctionSCG(returnType);
			code.addChunk(scg.generate());
			
			// Load return address from temp
			code.add(ASMOpcode.PushD, RunTime.FUNC_RETURN_ADDR_TEMP);
			code.add(ASMOpcode.LoadI);
			
			code.add(Return);
			
			// Push lambda jump and address onto accumulator if inline
			if (!(node.getParent() instanceof FunctionDefinitionNode)) {
				code.add(Label, node.getJumpLabel());
				code.add(PushD, node.getStartLabel());
			}
		}
		public void visitLeave(ReturnNode node) {
			newVoidCode(node);
			
			// Get the return value
			if (node.nChildren() > 0) {
				ASMCodeFragment returnValue = removeValueCode(node.child(0));
				code.append(returnValue);
			}
			
			// Jump to exit handshake
			LambdaNode lambda = (LambdaNode) node.getLambda();
			code.add(Jump, lambda.getExitHandshakeLabel());
		}
		public void visitLeave(LambdaParamTypeNode node) {
		}
		public void visitLeave(LambdaParamNode node) {
		}

		public void visitLeave(CallNode node) {
			newVoidCode(node);
			
			ASMCodeFragment lambdaCode = removeValueCode(node.child(0));
			code.append(lambdaCode);
			
			// Remove value if function return isn't VOID
			if (node.getType() != PrimitiveType.VOID) {
				code.add(Pop);
			}
		}
		public void visitLeave(FunctionInvocationNode node) {
			newValueCode(node);
			
			if (!(node.child(0) instanceof IdentifierNode)) {
				ASMCodeFragment subroutineCode = removeValueCode(node.child(0));
				code.append(subroutineCode);
			}
			
			// Push arguments onto Frame Stack
			for (int i = 1; i < node.nChildren(); i++) {
				Type argType = node.child(i).getType();
				int argSize = argType.getSize();
				
				// Move Stack Pointer
				ASMCodeFragment argFrag = new ASMCodeFragment(CodeType.GENERATES_VALUE);
				argFrag.add(PushD, RunTime.STACK_POINTER);
				argFrag.add(PushD, RunTime.STACK_POINTER);
				argFrag.add(LoadI);
				argFrag.add(PushI, argSize);
				argFrag.add(Subtract);
				argFrag.add(StoreI);
				code.append(argFrag);
				
				// Put argument value
				code.add(PushD, RunTime.STACK_POINTER, "%% store arg " + i);
				code.add(LoadI);
				ASMCodeFragment argValue = removeValueCode(node.child(i));
				code.append(argValue);
				
				OpcodeForStoreSCG scg1 = new OpcodeForStoreSCG(argType);
				code.addChunk(scg1.generate());
			}

			if (node.child(0) instanceof IdentifierNode) {				
				ASMCodeFragment identifier = removeAddressCode(node.child(0));
				code.append(identifier);
				code.add(LoadI);
			}
			
			// Call function
			code.add(CallV);
			
			// Get return value
			Type returnType = node.getType();
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			OpcodeForLoadSCG scg2 = new OpcodeForLoadSCG(returnType);
			code.addChunk(scg2.generate());
			
			// Move the Stack Pointer up by the size of the return value
			code.add(PushD, RunTime.STACK_POINTER, "%% restore stack pointer");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, returnType.getSize());
			code.add(Add);
			code.add(StoreI);
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
			
			OpcodeForStoreSCG scg = new OpcodeForStoreSCG(type);
			code.addChunk(scg.generate());
		}

		///////////////////////////////////////////////////////////////////////////
		// release statement
		public void visitLeave(ReleaseNode node) {
			newVoidCode(node);
			
			Type type = node.child(0).getType();
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			code.append(arg1);
			
			if (type instanceof ArrayType) {
				ArrayReleaseSCG arrayRelease = new ArrayReleaseSCG();
				code.addChunk(arrayRelease.generate(type));
			}
			
			if (type == PrimitiveType.STRING) {
				StringReleaseSCG stringRelease = new StringReleaseSCG();
				code.addChunk(stringRelease.generate(type));
			}
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
		public void visitEnter(WhileNode node){
			Labeller labeller = new Labeller("while-stmt");
			String loopLabel  = labeller.newLabel("loop");
			String joinLabel  = labeller.newLabel("join");
			
			node.setLabels(loopLabel, joinLabel);
		}
		public void visitLeave(WhileNode node) {
			newVoidCode(node);
			
			code.add(Label, node.getLoopLabel());
			
			ASMCodeFragment conditionCode = removeValueCode(node.child(0));
			code.append(conditionCode);
			code.add(JumpFalse, node.getJoinLabel());

			ASMCodeFragment blockCode = removeVoidCode(node.child(1));
			code.append(blockCode);

			code.add(Jump, node.getLoopLabel());

			code.add(Label, node.getJoinLabel());
		}
		
		///////////////////////////////////////////////////////////////////////////
		// control statements
		public void visitLeave(ControlNode node) {
			newVoidCode(node);
			
			ParseNode pNode = node.getParent();
			while (!(pNode instanceof WhileNode)) {
				pNode = pNode.getParent();
			}
			
			WhileNode parent = (WhileNode) pNode;
			
			if (node.getToken().isLextant(Keyword.CONTINUE)) {
				code.add(Jump, parent.getLoopLabel());
			}
			
			if (node.getToken().isLextant(Keyword.BREAK)) {
				code.add(Jump, parent.getJoinLabel());
			}
		}

		///////////////////////////////////////////////////////////////////////////
		// expressions
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
			Object variant = node.getSignature().getVariant();
			if (variant instanceof ASMOpcode) {
				ASMOpcode opcode = (ASMOpcode) variant;
			
				Labeller labeller = new Labeller("boolean");
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

				code.add(Label, joinLabel);
			}
		}
		private void visitComparisonOperatorNode(BinaryOperatorNode node, Lextant operator) {
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			Type type = node.getSignature().paramType();
			
			Labeller labeller = new Labeller("compare");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");
			
			code.append(arg1);
			code.append(arg2);

			Object variant = node.getSignature().getVariant();
			
			if (variant instanceof SimpleCodeGenerator) {
				SimpleCodeGenerator scg1 = (SimpleCodeGenerator) variant;
				code.addChunk(scg1.generate());
			}
			
			if (variant instanceof Integer) {
				code.add((type == PrimitiveType.FLOATING) ? FSubtract : Subtract);
			}
			
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
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			code.append(arg1);
			code.append(arg2);
			
			// Ensure numerator has negative sign
			RationalNegateSCG scg = new RationalNegateSCG();
			code.addChunk(scg.generate());
			
			// Find GCD
			RationalStackToTempSCG scg1 = new RationalStackToTempSCG();
			code.addChunk(scg1.generate());

			code.add(ASMOpcode.Call, RunTime.SUB_RATIONAL_FIND_GCD);
			
			RationalTempToStackSCG scg2 = new RationalTempToStackSCG();
			code.addChunk(scg2.generate());
		}
		private void visitExpressOverOperatorNode(RationalOperatorNode node) {
			assert node.nChildren() == 2;
			
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			
			code.append(arg1);
			
			Type type = node.child(0).getType();
			if (type == PrimitiveType.FLOATING) {
				code.append(arg2);
				DivisionByZeroSCG divZero = new DivisionByZeroSCG(PrimitiveType.INTEGER);
				code.addChunk(divZero.generate());
				code.add(ConvertF);
				code.add(FMultiply);
				code.add(ConvertI);
			} else if (type == PrimitiveType.RATIONAL) {
				code.add(Exchange);
				code.append(arg2);
				DivisionByZeroSCG divZero = new DivisionByZeroSCG(PrimitiveType.INTEGER);
				code.addChunk(divZero.generate());
				code.add(Multiply);
				code.add(Exchange);
				code.add(Divide);
			}
		}
		private void visitRationalizeOperatorNode(RationalOperatorNode node) {
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			ASMCodeFragment frag = new ASMCodeFragment(GENERATES_VALUE);
			
			code.append(arg1);
			
			Type type = node.child(0).getType();
			if (type == PrimitiveType.FLOATING) {
				code.append(arg2);
				frag.add(ConvertF);
				DivisionByZeroSCG divZero = new DivisionByZeroSCG(PrimitiveType.INTEGER);
				code.addChunk(divZero.generate());
				frag.add(FMultiply);
				frag.add(ConvertI);
				code.append(frag);
			} else if (type == PrimitiveType.RATIONAL) {
				code.add(Exchange);
				code.append(arg2);
				DivisionByZeroSCG divZero = new DivisionByZeroSCG(PrimitiveType.INTEGER);
				code.addChunk(divZero.generate());
				frag.add(Multiply);
				frag.add(Exchange);
				frag.add(Divide);
				code.append(frag);
			}
			
			code.append(arg2);
			
			// Ensure numerator has negative sign
			RationalNegateSCG scg = new RationalNegateSCG();
			code.addChunk(scg.generate());
			
			// Find GCD
			RationalStackToTempSCG scg1 = new RationalStackToTempSCG();
			code.addChunk(scg1.generate());
			
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
						
						OpcodeForStoreSCG scg = new OpcodeForStoreSCG(node.getSubtype());
						code.addChunk(scg.generate());
					}
				} else {
					ArrayPopulateSCG scg4 = new ArrayPopulateSCG();
					code.addChunk(scg4.generate(node.getSubtype()));
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
		public void visit(LambdaTypeNode node) {
			newValueCode(node);
			
		}
	}

}
