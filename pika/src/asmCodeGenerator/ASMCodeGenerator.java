package asmCodeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import asmCodeGenerator.codeGenerator.*;
import asmCodeGenerator.codeGenerator.array.ArrayAllocateSCG;
import asmCodeGenerator.codeGenerator.array.ArrayCloneSCG;
import asmCodeGenerator.codeGenerator.array.ArrayGenerateRecordSCG;
import asmCodeGenerator.codeGenerator.array.ArrayNegativeIndexSCG;
import asmCodeGenerator.codeGenerator.array.ArrayPopulateSCG;
import asmCodeGenerator.codeGenerator.array.ArrayReleaseSCG;
import asmCodeGenerator.codeGenerator.array.ArrayTempToStackSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreFunctionSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreSCG;
import asmCodeGenerator.codeGenerator.operators.FoldOperatorBaseSCG;
import asmCodeGenerator.codeGenerator.rational.RationalMemToStackSCG;
import asmCodeGenerator.codeGenerator.rational.RationalNegateSCG;
import asmCodeGenerator.codeGenerator.rational.RationalStackToTempSCG;
import asmCodeGenerator.codeGenerator.rational.RationalTempToStackSCG;
import asmCodeGenerator.codeGenerator.string.StringReleaseSCG;
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
	List<DeclarationNode> globalDeclarations;
	ASMCodeFragment functions;

	public static ASMCodeFragment generate(ParseNode syntaxTree) {
		ASMCodeGenerator codeGenerator = new ASMCodeGenerator(syntaxTree);
		return codeGenerator.makeASM();
	}
	public ASMCodeGenerator(ParseNode root) {
		super();
		this.root = root;
		this.functionDefinitions = new ArrayList<IdentifierNode>();
		this.globalDeclarations = new ArrayList<DeclarationNode>();
		this.functions = new ASMCodeFragment(GENERATES_VOID);
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
		code.append(functions);
		
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
			
			code.append(storeGlobalDefinitions());
			
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		private ASMCodeFragment storeGlobalDefinitions() {
			ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);

			for (DeclarationNode node : globalDeclarations) {
				Type type = node.getType();
				ASMCodeFragment lvalue = removeAddressCode(node.child(0));	
				ASMCodeFragment rvalue = removeValueCode(node.child(1));
				
				code.append(lvalue);
				code.append(rvalue);
				
				OpcodeForStoreSCG scg = new OpcodeForStoreSCG(type);
				code.addChunk(scg.generate());
			}
			
			for (IdentifierNode identifier : functionDefinitions) {
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
		}
		
		public void visitLeave(LambdaNode node) {
			newValueCode(node);
			
			// Jump over lambda if it is inline
			if (!(node.getParent() instanceof FunctionDefinitionNode)) {
				//code.add(Jump, node.getJumpLabel());
			}
			
			functions.add(Label, node.getStartLabel());
			
			// Put return address on Frame Stack below Dynamic Link
			functions.add(PushD, RunTime.STACK_POINTER, "%% store return addr.");
			functions.add(LoadI);
			functions.add(PushI, -8);
			functions.add(Add);
			functions.add(Exchange);
			functions.add(StoreI);
			
			// Store Dynamic Link (current value of Frame Pointer) below the Stack Pointer
			functions.add(PushD, RunTime.STACK_POINTER, "%% store dyn. link");
			functions.add(LoadI);
			functions.add(PushI, -4);
			functions.add(Add);
			functions.add(PushD, RunTime.FRAME_POINTER);
			functions.add(LoadI);
			functions.add(StoreI);
			
			// Move Frame Pointer to Stack Pointer
			functions.add(PushD, RunTime.FRAME_POINTER, "%% move frame pointer");
			functions.add(PushD, RunTime.STACK_POINTER);
			functions.add(LoadI);
			functions.add(StoreI);
			
			// Move Stack Pointer to end of frame
			functions.add(PushD, RunTime.STACK_POINTER, "%% move stack pointer");
			functions.add(PushD, RunTime.STACK_POINTER);
			functions.add(LoadI);
			functions.add(PushI, node.getFrameSize());
			functions.add(Subtract);
			functions.add(StoreI);

			
			// Lambda execution code
			ASMCodeFragment childCode = removeVoidCode(node.child(1));
			functions.append(childCode);

			
			// Runoff error handling
			functions.add(Label, node.getExitErrorLabel());
			functions.add(Jump, RunTime.FUNCTION_RUNOFF_RUNTIME_ERROR);
			
			// Exit handshake
			functions.add(Label, node.getExitHandshakeLabel());
		
			// Push the return address onto the accumulator stack
			functions.add(PushD, RunTime.FRAME_POINTER, "%% get return addr.");
			functions.add(LoadI);
			functions.add(PushI, -8);
			functions.add(Add);
			functions.add(LoadI);
			
			// Replace the Frame Pointer with the dynamic link
			functions.add(PushD, RunTime.FRAME_POINTER, "%% restore frame pointer");
			functions.add(PushD, RunTime.FRAME_POINTER);
			functions.add(LoadI);
			functions.add(PushI, -4);
			functions.add(Add);
			functions.add(LoadI);
			functions.add(StoreI);
			
			// Move Stack Pointer above current Parameter Scope
			functions.add(PushD, RunTime.STACK_POINTER, "%% pop frame stack");
			functions.add(PushD, RunTime.STACK_POINTER);
			functions.add(LoadI);
			functions.add(PushI, node.getFrameSize());
			functions.add(Add);
			functions.add(PushI, node.getArgSize());
			functions.add(Add);
			
			// Decrease the stack pointer by the return value size
			Type returnType = node.getReturnType();
			functions.add(PushI, returnType.getSize(), "%% store return val.");
			functions.add(Subtract);
			functions.add(StoreI);
			
			// Store return address in temp until value is stored
			functions.add(PushD, RunTime.FUNC_RETURN_ADDR_TEMP);
			functions.add(Exchange);
			functions.add(StoreI);
			
			// Store return value
			OpcodeForStoreFunctionSCG scg = new OpcodeForStoreFunctionSCG(returnType);
			functions.addChunk(scg.generate());
			
			// Load return address from temp
			functions.add(PushD, RunTime.FUNC_RETURN_ADDR_TEMP);
			functions.add(LoadI);
			
			functions.add(Return);
			
			// Push lambda jump and address onto accumulator if inline
			if (!(node.getParent() instanceof FunctionDefinitionNode)) {
				//code.add(Label, node.getJumpLabel());
				code.add(PushD, node.getStartLabel());
			}
		}
		public void visitLeave(ReturnNode node) {
			newVoidCode(node);
			
			// Get the return value
			if (node.nChildren() > 0 && node.getType() != PrimitiveType.VOID) {
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
				
				// If RATIONAL, add a second pop
				if (node.getType() == PrimitiveType.RATIONAL) {
					code.add(Pop);
				}
			}
		}
		public void visitLeave(FunctionInvocationNode node) {
			newValueCode(node);
						
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
			
			// Push lambda location
			if (node.child(0) instanceof IdentifierNode) {				
				ASMCodeFragment identifier = removeAddressCode(node.child(0));
				code.append(identifier);
				code.add(LoadI);
			} else {
				ASMCodeFragment subroutineCode = removeValueCode(node.child(0));
				code.append(subroutineCode);
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
		// index and cast
		public void visitLeave(IndexNode node) {
			newAddressCode(node);
			
			ASMCodeFragment indexedItem = removeValueCode(node.child(0));
			ASMCodeFragment index = removeValueCode(node.child(1));
			
			code.append(indexedItem);
			code.append(index);
			
			if (node.nChildren() > 2) {
				index = removeValueCode(node.child(2));
				code.append(index);
			}
			
			Object variant = node.getSignature().getVariant();
			
			if (variant instanceof SimpleCodeGenerator) {
				SimpleCodeGenerator scg1 = (SimpleCodeGenerator) variant;
				code.addChunk(scg1.generate());
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
			// Handle global declaration
			if (node.getParent() instanceof ProgramNode) {
				newVoidCode(node);
				globalDeclarations.add((DeclarationNode) node);
			} else if (node.isStatic()) {
				newVoidCode(node);
				globalDeclarations.add((DeclarationNode) node);
			} else {
				storeNode(node);
			}
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
			String startLabel = labeller.newLabel();
			String ifLabel = labeller.newLabel("if");
			String elseLabel  = labeller.newLabel("else");
			String joinLabel  = labeller.newLabel("join");
			
			code.add(Label, startLabel);			
			
			ASMCodeFragment conditionCode = removeValueCode(node.child(0));
			code.append(conditionCode);
			code.add(JumpFalse, elseLabel);
			code.add(Jump, ifLabel);
			
			code.add(Label, ifLabel);
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
			String loopLabel  = labeller.newLabel("condition");
			String bodyLabel  = labeller.newLabel("body");
			String joinLabel  = labeller.newLabel("end");
			
			node.setLabels(loopLabel, bodyLabel, joinLabel);
		}
		public void visitLeave(WhileNode node) {
			newVoidCode(node);
			
			code.add(Label, node.getLoopLabel());
			
			ASMCodeFragment conditionCode = removeValueCode(node.child(0));
			code.append(conditionCode);
			code.add(JumpFalse, node.getJoinLabel());

			code.add(Label, node.getBodyLabel());
			ASMCodeFragment blockCode = removeVoidCode(node.child(1));
			code.append(blockCode);

			code.add(Jump, node.getLoopLabel());

			code.add(Label, node.getJoinLabel());
		}
		
		///////////////////////////////////////////////////////////////////////////
		// for statements
		public void visitEnter(ForNode node){
			Labeller labeller = new Labeller("for-stmt");
			String loopLabel  = labeller.newLabel("condition");
			String bodyLabel  = labeller.newLabel("body");
			String incLabel  = labeller.newLabel("increment");
			String joinLabel  = labeller.newLabel("end");
			
			node.setLabels(loopLabel, bodyLabel, incLabel, joinLabel);
		}
		public void visitLeave(ForNode node) {
			newVoidCode(node);
			
			if (node.isForElement()) {
				visitForElementNode(node);
			} else {
				visitForIndexNode(node);
			}
		}
		private void visitForIndexNode(ForNode node) {
			Type type = node.child(0).getType();
			int recordOffset = 8;
			if (type instanceof ArrayType) { recordOffset = 12; }
			
			ASMCodeFragment sequenceCode = removeValueCode(node.child(0));
			ASMCodeFragment identifier = removeAddressCode(node.child(1));
			ASMCodeFragment bodyCode = removeVoidCode(node.child(2));
			
			//////////////////////////////////////////////////
			// Push temp values on stack
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER, "!! dump temp values");
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX);
			code.add(LoadI);

			//////////////////////////////////////////////////
			// Configure for loop temp values
			// Store identifier address
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.append(identifier);
			code.add(StoreI);
			// Store sequence address
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.append(sequenceCode);
			code.add(StoreI);
			
			//////////////////////////////////////////////////
			// Initialize indices
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(PushI, 0);
			code.add(StoreI);		// Start at index 0
							
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(LoadI);
			code.add(PushI, recordOffset);
			code.add(Add);
			code.add(LoadI);
			code.add(StoreI);		// Finish at index (length-1)
			
			//////////////////////////////////////////////////
			// Initialize identifier
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(StoreI);

			//////////////////////////////////////////////////
			// Loop
			code.add(Label, node.getLoopLabel());
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(Subtract);
			code.add(JumpFalse, node.getJoinLabel());
			
			//////////////////////////////////////////////////
			// Body
			code.add(Label, node.getBodyLabel());
			code.append(bodyCode);
			
			//////////////////////////////////////////////////
			// Update loop index and identifier
			// Update index
			code.add(Label, node.getIncrementLabel());
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(PushI, 1);
			code.add(Add);
			code.add(StoreI);
			// Update identifier (INDEX)
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(StoreI);
			
			code.add(Jump, node.getLoopLabel());
			code.add(Label, node.getJoinLabel());

			//////////////////////////////////////////////////
			// Return temp values from stack
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX, "!! restore temp values");
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.add(Exchange);
			code.add(StoreI);
		}
		private void visitForElementNode(ForNode node) {
			Type type = node.child(0).getType();
			Type subtype = PrimitiveType.CHARACTER;
			int recordOffset = 8;
			int dataOffset = 12;
			
			if (type instanceof ArrayType) {
				recordOffset = 12;
				dataOffset = 16;
				subtype = ((ArrayType) type).getSubtype();
			}
			
			ASMCodeFragment sequenceCode = removeValueCode(node.child(0));
			ASMCodeFragment identifier = removeAddressCode(node.child(1));
			ASMCodeFragment bodyCode = removeVoidCode(node.child(2));
			SimpleCodeGenerator loadSCG = new OpcodeForLoadSCG(subtype);
			SimpleCodeGenerator storeSCG = new OpcodeForStoreSCG(subtype);
			
			//////////////////////////////////////////////////
			// Push temp values on stack
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER, "!! dump temp values");
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX);
			code.add(LoadI);

			//////////////////////////////////////////////////
			// Configure for loop temp values
			// Store identifier address
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.append(identifier);
			code.add(StoreI);
			// Store sequence address
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.append(sequenceCode);
			code.add(StoreI);
			
			//////////////////////////////////////////////////
			// Initialize indices
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(PushI, 0);
			code.add(StoreI);		// Start at index 0
							
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(LoadI);
			code.add(PushI, dataOffset);
			code.add(Add);
			code.add(StoreI);
			
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(LoadI);
			code.add(PushI, recordOffset);
			code.add(Add);
			code.add(LoadI);
			code.add(StoreI);		// Finish at index (length-1)
			
			//////////////////////////////////////////////////
			// Initialize identifier
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(LoadI);
			code.addChunk(loadSCG.generate());
			code.addChunk(storeSCG.generate());

			//////////////////////////////////////////////////
			// Loop
			code.add(Label, node.getLoopLabel());
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(Subtract);
			code.add(JumpFalse, node.getJoinLabel());
			
			//////////////////////////////////////////////////
			// Body
			code.add(Label, node.getBodyLabel());
			code.append(bodyCode);
			
			//////////////////////////////////////////////////
			// Update loop index, offset, and identifier
			// Update index
			code.add(Label, node.getIncrementLabel());
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(LoadI);
			code.add(PushI, 1);
			code.add(Add);
			code.add(StoreI);
			// Update offset
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(LoadI);
			code.add(PushI, subtype.getSize());
			code.add(Add);
			code.add(StoreI);
			// Update identifier (ELEMENT)
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.add(LoadI);
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(LoadI);
			code.addChunk(loadSCG.generate());
			code.addChunk(storeSCG.generate());
			
			code.add(Jump, node.getLoopLabel());
			code.add(Label, node.getJoinLabel());

			//////////////////////////////////////////////////
			// Return temp values from stack
			code.add(PushD, RunTime.FOR_LOOP_END_INDEX, "!! restore temp values");
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_OFFSET);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_INDEX);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_SEQUENCE);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.FOR_LOOP_IDENTIFIER);
			code.add(Exchange);
			code.add(StoreI);
		}
		
		
		///////////////////////////////////////////////////////////////////////////
		// control statements
		public void visitLeave(ControlNode node) {
			newVoidCode(node);
			
			ParseNode pNode = node.getParent();
			while (!(pNode instanceof WhileNode || pNode instanceof ForNode)) {
				pNode = pNode.getParent();
			}
			
			if (pNode instanceof WhileNode) {
				WhileNode parent = (WhileNode)pNode;
				
				if (node.getToken().isLextant(Keyword.CONTINUE)) {
					code.add(Jump, parent.getLoopLabel());
				}
				
				if (node.getToken().isLextant(Keyword.BREAK)) {
					code.add(Jump, parent.getJoinLabel());
				}
			}
			
			if (pNode instanceof ForNode) {
				ForNode parent = (ForNode)pNode;
				
				if (node.getToken().isLextant(Keyword.CONTINUE)) {
					code.add(Jump, parent.getIncrementLabel());
				}
				
				if (node.getToken().isLextant(Keyword.BREAK)) {
					code.add(Jump, parent.getJoinLabel());
				}
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
			code.add(Jump, joinLabel);
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
		
		///////////////////////////////////////////////////////////////////////////
		// high level operators
		public void visitLeave(FoldOperatorNode node) {
			newValueCode(node);
			
			ASMCodeFragment arrayCode = removeValueCode(node.child(0));			
			ASMCodeFragment lambdaCode = removeValueCode(node.child(node.nChildren() - 1));
			
			node.getSCG().setArray(arrayCode);
			node.getSCG().setLambda(lambdaCode);
			
			if (node.nChildren() == 3 && node.getSCG() instanceof FoldOperatorBaseSCG) {
				ASMCodeFragment baseCode = removeValueCode(node.child(1));
				((FoldOperatorBaseSCG) node.getSCG()).setBase(baseCode);
			}
			
			code.append(node.getSCG().generate());
		}
		public void visitLeave(MapOperatorNode node) {
			newValueCode(node);
			
			ASMCodeFragment arrayCode = removeValueCode(node.child(0));			
			ASMCodeFragment lambdaCode = removeValueCode(node.child(1));
			
			node.getSCG().setArray(arrayCode);
			node.getSCG().setLambda(lambdaCode);
			code.append(node.getSCG().generate());
		}
		public void visitLeave(ReduceOperatorNode node) {
			newValueCode(node);
			
			ASMCodeFragment arrayCode = removeValueCode(node.child(0));			
			ASMCodeFragment lambdaCode = removeValueCode(node.child(1));
			
			node.getSCG().setArray(arrayCode);
			node.getSCG().setLambda(lambdaCode);
			code.append(node.getSCG().generate());
		}
		public void visitLeave(ZipOperatorNode node) {
			newValueCode(node);
			
			ASMCodeFragment array1Code = removeValueCode(node.child(0));
			ASMCodeFragment array2Code = removeValueCode(node.child(1));
			ASMCodeFragment lambdaCode = removeValueCode(node.child(2));
			
			node.getSCG().setArray1(array1Code);
			node.getSCG().setArray2(array2Code);
			node.getSCG().setLambda(lambdaCode);
			code.append(node.getSCG().generate());
		}
		public void visitLeave(ReverseOperatorNode node) {
			newValueCode(node);

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			code.append(arg1);
			
			code.addChunk(node.getSCG().generate());
		}

		///////////////////////////////////////////////////////////////////////////
		// array node
		public void visitLeave(ArrayNode node) {
			newValueCode(node);
			
			Labeller labeller = new Labeller("array");
			String startLabel  = labeller.newLabel();
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
				ArrayGenerateRecordSCG scg2 = new ArrayGenerateRecordSCG(node.getSubtype());
				code.addChunk(scg2.generate());
				
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
		// type nodes (ErrorNode not necessary)
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
