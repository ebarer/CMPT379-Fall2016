package optimizer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import asmCodeGenerator.codeStorage.ASMCodeChunk;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMInstruction;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.runtime.RunTime;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;

public class Optimizer {
	boolean debug = false;
	
	private ASMCodeFragment fragment;
	private static final int HEADER = 0;
	private static final int DATA = 1;
	private static final int INSTRUCTIONS = 2;
	
	public static ASMCodeFragment optimize(ASMCodeFragment fragment) {
		Optimizer optimizer = new Optimizer(fragment);
		return optimizer.optimize();
	}
	public Optimizer(ASMCodeFragment fragment) {
		this.fragment = fragment;
	}
	
	public ASMCodeFragment optimize() {
		ASMCodeFragment[] fragments = splitDirectives(fragment);		
		ASMCodeFragment returnFragment = new ASMCodeFragment(CodeType.GENERATES_VOID);
		
		// Eliminate duplicate strings
		removeDuplicateStrings(fragments[DATA]);
		
		// Constant arithmetic calculations
		constantFolding(fragments[INSTRUCTIONS]);
		
		// Divide instructions into BasicBlocks, construct ControlFlowGraph
		fragments[INSTRUCTIONS] = flattenFragment(fragments[INSTRUCTIONS]);
		BasicBlockFragment cfg = blockDivision(fragments[INSTRUCTIONS]);
		constructControlFlowGraph(cfg);
		printCFG(cfg);
		
		// Manipulate CFG
		while(removeUnreachableCode(cfg) > 0);
		while(mergeBlocks(cfg));
		cloneBlocks(cfg);
		
		// Grab optimized instructions from BasicBlocks
		fragments[INSTRUCTIONS] = replaceInstructions(cfg);
		while(simplifyJumps(fragments[INSTRUCTIONS]));
		
		// Merge fragments
		returnFragment.append(fragments[HEADER]);
		returnFragment.append(fragments[DATA]);
		returnFragment.append(fragments[INSTRUCTIONS]);
		
		return returnFragment;
	}
	
	// Split into data and instruction fragments
	private ASMCodeFragment[] splitDirectives(ASMCodeFragment fragment) {
		ASMCodeFragment fragments[] = {
			new ASMCodeFragment(CodeType.GENERATES_VOID),
			new ASMCodeFragment(CodeType.GENERATES_VOID),
			new ASMCodeFragment(CodeType.GENERATES_VOID),
		};
		
		List<ASMCodeChunk> chunks = fragment.getChunks();
		
		// Indexes
		int currentChunk = 0;
		int currentInstr = 0;
		
		// Ensure header information stays at the top,
		// regardless of optimizer input (compilers vs. file)
		if (chunks.size() > 1) {
			fragments[HEADER].addChunk(chunks.get(0));
			fragments[HEADER].addChunk(chunks.get(1));
			currentChunk = 2;
		} else {
			ASMCodeChunk chunk = chunks.get(currentChunk);
			List<ASMInstruction> instructions = chunk.getInstructions();
			
			while (currentInstr < chunk.getInstructions().size()) {
				Object argument = instructions.get(currentInstr).getArgument();
				if (argument == null || !argument.equals("$$main")) {
					fragments[HEADER].add(instructions.get(currentInstr++));
				} else break;
			}
			
			fragments[HEADER].add(instructions.get(currentInstr++));
		}

		for (; currentChunk < chunks.size(); currentChunk++) {
			ASMCodeChunk chunk = chunks.get(currentChunk);
			List<ASMInstruction> instructions = chunk.getInstructions();
			
			if (chunks.size() > 1) {
				currentInstr = 0;
			}
			
			for (; currentInstr < chunk.getInstructions().size(); currentInstr++) {
				ASMInstruction instruction = instructions.get(currentInstr);
				ASMOpcode opcode = instruction.getOpcode();
				
				if (opcode == ASMOpcode.Nop) {
					continue;
				} else if (opcode.isDataDirective()) {
					fragments[DATA].add(instruction);				
				} else {
					fragments[INSTRUCTIONS].add(instruction);
				}
			}
		}
		
		return fragments;
	}
	private ASMCodeFragment flattenFragment(ASMCodeFragment fragment) {
		ASMCodeFragment flatFragment = new ASMCodeFragment(CodeType.GENERATES_VOID);
		
		for (ASMCodeChunk chunk : fragment.getChunks()) {
			List<ASMInstruction> instructions = chunk.getInstructions();
			for (int i = 0; i < instructions.size(); i++) {
				flatFragment.add(instructions.get(i));
			}
		}
		
		return flatFragment;
	}
	
	// Eliminate duplicate strings
	private LinkedHashMap<Integer, Object> locateStrings(ASMCodeFragment fragment) {
		LinkedHashMap<Integer, Object> strings = new LinkedHashMap<Integer, Object>();
		
		ASMCodeChunk chunk = fragment.getChunks().get(0);
		List<ASMInstruction> instructions = chunk.getInstructions();
		
		for (ASMInstruction instruction : instructions) {
			if (instruction.isString()) {
				Object operand = instruction.getArgument();
				Integer loc = instructions.indexOf(instruction);
				strings.put(loc, operand);
			}
		}
		
		return strings;
	}
	private void removeDuplicateStrings(ASMCodeFragment fragment) {
		int stringRecordOffset = 4;
		if (fragment.getChunks().size() > 0) {
			ASMCodeChunk chunk = fragment.getChunks().get(0);
			List<ASMInstruction> instructions = chunk.getInstructions();
			HashMap<Object, Integer> stringLegend = new HashMap<>();
			
			int removedInstrCount = 0;
			LinkedHashMap<Integer, Object> strings = locateStrings(fragment);
			for (Map.Entry<Integer, Object> stringEntry : strings.entrySet()) {
				int loc = stringEntry.getKey();
				loc -= removedInstrCount; // Compensate for deleted instructions				
				int stringStart = loc + stringRecordOffset;
				
				ASMInstruction dLabel = instructions.get(loc);
				ASMOpcode opcode = instructions.get(stringStart).getOpcode();
				String stringData = getString(instructions, stringStart);
				
				if (stringLegend.containsKey(stringData)) {					
					// Remove string label and record
					for (int l = loc; l < stringStart; l++) {
						instructions.remove(loc);
						removedInstrCount++;
					}
					
					// Remove DataS or DataI directives
					if (opcode == ASMOpcode.DataS) {
						instructions.remove(loc);
						removedInstrCount++;
					} else if (opcode == ASMOpcode.DataC) {
						while (loc < instructions.size() - 1 && opcode == ASMOpcode.DataC) {
							instructions.remove(loc);
							removedInstrCount++;
							opcode = instructions.get(loc).getOpcode();
						}
					}
					
					// Insert DLabel at location of first instance of string
					int addLoc = stringLegend.get(stringData) + 1;
					instructions.add(addLoc, dLabel);
					removedInstrCount--;
				} else {
					stringLegend.put(stringData, loc);				
				}
			}
		}
	}
	private String getString(List<ASMInstruction> instructions, int loc){
		ASMInstruction instruction = instructions.get(loc);
		
		if (instruction.getOpcode() == ASMOpcode.DataS) {
			return (String)instruction.getArgument();
		} else if (instruction.getOpcode() == ASMOpcode.DataC) {
			StringBuffer stringBuffer = new StringBuffer();
			while (instruction.getOpcode() == ASMOpcode.DataC) {
				int value = (int)instruction.getArgument();
				stringBuffer.append(value);
				instruction = instructions.get(++loc);
			}
			return stringBuffer.toString();
		} else {
			return "";
		}
	}

	// Constant arithmetic calculations
	static int numChanges = -1;
	private void constantFolding(ASMCodeFragment fragment) {
		if (fragment.getChunks().size() > 0) {
			ASMCodeChunk chunk = fragment.getChunks().get(0);
			List<ASMInstruction> instructions = chunk.getInstructions();
			
			// (ASMInstruction instruction : instructions) {
			while (numChanges != 0) {
				numChanges = 0;
				for (int i = 0; i < instructions.size(); i++) {
					if (i >= instructions.size() - 2) break;
					
					ASMOpcode op1 = instructions.get(i).getOpcode();
					ASMOpcode op2 = instructions.get(i+1).getOpcode();
					ASMOpcode op3 = instructions.get(i+2).getOpcode();
					
					if (op1 == ASMOpcode.PushI) {
						switch (op2) {
						case PushI:
							if (op3.isArithmetic()) {
								foldArithmetic(i, instructions, op1);
							}
							break;
						case Negate:
							foldNegation(i, instructions, op1);
							break;
						case ConvertF:
							foldConversion(i, instructions, op1);
							break;
						default:
							break;
						}
					} else if (op1 == ASMOpcode.PushF) {
						switch (op2) {
						case PushF:
							if (op3.isArithmetic()) {
								foldArithmetic(i, instructions, op1);
							}
							break;
						case FNegate:
							foldNegation(i, instructions, op1);
							break;
						case ConvertI:
							foldConversion(i, instructions, op1);
							break;
						default:
							break;
						}
					}
				}
			}
		}
	}
	private void foldArithmetic(int loc, List<ASMInstruction> instructions, ASMOpcode opcode) {
		ASMOpcode op = instructions.get(loc+2).getOpcode();
		Object left = instructions.get(loc).getArgument();
		Object right = instructions.get(loc+1).getArgument();
		Object argument = null;
		
		switch (op) {
		case Add:
			argument = (int)left + (int)right;
			break;
		case FAdd:
			argument = (double)left + (double)right;
			break;
		case Subtract:
			argument = (int)left - (int)right;
			break;
		case FSubtract:
			argument = (double)left - (double)right;
			break;
		case Multiply:
			argument = (int)left * (int)right;
			break;
		case FMultiply:
			argument = (double)left * (double)right;
			break;
		case Divide:
		case FDivide:
		case Duplicate:
			foldDivision(left, right, loc, instructions, opcode);
			return;
		default:
			break;
		}
		
		if (argument != null) {
			// Remove old instructions
			instructions.remove(loc);
			instructions.remove(loc);
			instructions.remove(loc);
			
			// Insert folded instruction
			ASMInstruction instr = new ASMInstruction(opcode, argument);
			instructions.add(loc, instr);
			
			numChanges++;
		}
	}
	private void foldDivision(Object left, Object right, int loc, List<ASMInstruction> instructions, ASMOpcode opcode) {
		if (right instanceof Integer) { right = ((Integer)right).doubleValue(); }
		if (left instanceof Integer) { left = ((Integer)left).doubleValue(); }
		
		// Remove old instructions
		instructions.remove(loc);
		instructions.remove(loc);
		instructions.remove(loc);
		instructions.remove(loc);
		instructions.remove(loc);
		
		if ((Double)right == 0) {
			// Issue division by 0 error
			instructions.remove(loc);
			ASMInstruction instr = new ASMInstruction(ASMOpcode.Jump, RunTime.DIVIDE_BY_ZERO_RUNTIME_ERROR);
			instructions.add(loc, instr);
		} else {			
			// Insert folded instruction
			Double argument = (Double)left / (Double)right;
			
			if (opcode == ASMOpcode.PushF) {
				// TODO: make sure doubleValue() not floatValue()
				ASMInstruction instr = new ASMInstruction(opcode, argument.doubleValue());
				instructions.add(loc, instr);				
			} else if (opcode == ASMOpcode.PushI) {
				ASMInstruction instr = new ASMInstruction(opcode, argument.intValue());
				instructions.add(loc, instr);
			}
		}
		
		numChanges++;
	}
	private void foldNegation(int loc, List<ASMInstruction> instructions, ASMOpcode opcode) {
		ASMOpcode op = instructions.get(loc+1).getOpcode();
		Object val = instructions.get(loc).getArgument();
		
		if (op == ASMOpcode.Negate || op == ASMOpcode.FNegate) {
			val = -1 * (double)val;		
		
			// Remove old instructions
			instructions.remove(loc);
			instructions.remove(loc);
			
			// Insert folded instruction
			ASMInstruction instr = new ASMInstruction(opcode, val);
			instructions.add(loc, instr);
			
			numChanges++;
		}
	}
	private void foldConversion(int loc, List<ASMInstruction> instructions, ASMOpcode opcode) {
		ASMOpcode op = instructions.get(loc+1).getOpcode();
		Object val = instructions.get(loc).getArgument();
		Object argument = null;
		
		if (op == ASMOpcode.ConvertF) {
			argument = ((Integer)val).doubleValue();
		} else if (op == ASMOpcode.ConvertI) {
			argument = ((Double)val).intValue();
		}
		
		if (argument != null) {
			// Remove old instructions
			instructions.remove(loc);
			instructions.remove(loc);
			
			// Insert folded instruction
			if (opcode == ASMOpcode.PushF) {
				ASMInstruction instr = new ASMInstruction(ASMOpcode.PushI, argument);
				instructions.add(loc, instr);
			} else {
				ASMInstruction instr = new ASMInstruction(ASMOpcode.PushF, argument);
				instructions.add(loc, instr);
			}
			
			numChanges++;
		}
	}

	// Divide instructions into BasicBlocks, construct Control Flow Graph (CFG)
	private BasicBlockFragment blockDivision(ASMCodeFragment fragment) {
		BasicBlockFragment blocks = new BasicBlockFragment();
		List<ASMInstruction> instructions = fragment.getChunk(0).getInstructions();
		
		for (int i = 0; i < instructions.size(); i++) {
			ASMInstruction instruction = instructions.get(i);
			
			if (instruction.getOpcode() == ASMOpcode.Label) {
				if (!blocks.lastBlock().isEmpty()) {
					blocks.newBlock();
				}
				
				blocks.add(instructions.get(i));
				continue;
			}
			
			if (instruction.getOpcode() == ASMOpcode.Return ||
				instruction.getOpcode() == ASMOpcode.Halt ||
				instruction.getOpcode() == ASMOpcode.Call) {
				
				blocks.add(instructions.get(i));
				blocks.newBlock();
				continue;
			}
			
			if (instruction.getOpcode().isJump()) {
				while (instruction.getOpcode().isJump()) {
					blocks.add(instruction);
					instruction = instructions.get(++i);
				}
				
				i--;
				blocks.newBlock();
				continue;
			}
			
			blocks.add(instructions.get(i));
		}
		
		return blocks;
	}
	private void constructControlFlowGraph(BasicBlockFragment fragment) {
		HashMap<String, BasicBlock> labelLookup = fragment.getLabelLookup();
		
		for (int j = 0; j < fragment.getBlocks().size(); j++) {
			BasicBlock block = fragment.getBlock(j);
			List<ASMInstruction> instructions = block.getInstructions();
			
			for (int i = 0; i < instructions.size(); i++) {
				ASMInstruction instruction = instructions.get(i);
				
				// If first instruction is not a label, connect to previous block
				if (i == 0 && instruction.getOpcode() != ASMOpcode.Label) {
					BasicBlock incomingBlock = fragment.getBlock(j-1);
					
					// Define incoming edge
					block.addIncomingEdge(ASMOpcode.Nop, incomingBlock);
					
					// Define outgoing edge
					incomingBlock.addOutgoingEdge(ASMOpcode.Nop, block);
				}
				
				// If instruction is a jump, define edges
				if (instruction.getOpcode().isJump()) {	
					String label = (String)instruction.getArgument();
					ASMOpcode opcode = instruction.getOpcode();
					BasicBlock outgoingBlock = labelLookup.get(label);
					
					// Define outgoing edge
					block.addOutgoingEdge(opcode, outgoingBlock);
					
					// Define incoming edge
					outgoingBlock.addIncomingEdge(opcode, block);
				}
				
				// If last instruction is not a Jump type or Call, connect to next block
				if (i == (instructions.size()-1) && !instruction.getOpcode().isJump() && !instruction.getOpcode().isLeave()) {
					BasicBlock outgoingBlock = fragment.getBlock(j+1);
					
					// Define outgoing edge
					block.addOutgoingEdge(ASMOpcode.Nop, outgoingBlock);
					
					// Define incoming edge
					outgoingBlock.addIncomingEdge(ASMOpcode.Nop, block);
				}
			}
		}
	}
	
	// Manipulate CFG
	private int removeUnreachableCode(BasicBlockFragment fragment) {
		int nodesRemoved = fragment.getBlocks().size();
		
		fragment.traverseTree();
		fragment.traverseSubroutines();
		
		// Remove unvisited nodes
		fragment.getBlocks().removeIf(block -> !block.wasVisited());
		nodesRemoved = nodesRemoved - fragment.getBlocks().size();
		
		fragment.unvisitAllBlocks();
		fragment.locateSubroutines();
		
		return nodesRemoved;
	}
	private boolean mergeBlocks(BasicBlockFragment fragment) {		
		for (int j = 0; j < fragment.getBlocks().size(); j++) {
			BasicBlock block = fragment.getBlock(j);
			
			if (block.getOutgoingEdges().size() == 1) {
				for (BasicBlock target : block.getOutgoingEdges().values()) {
					if (target.getIncomingEdges().size() == 1) {
						if (target.getIncomingEdges().containsValue(block)) {
							block.mergeWith(target);
							fragment.getBlocks().remove(target);							
							return true;
						}
					}					
				}
			}
		}
		
		return false;
	}
	private void cloneBlocks(BasicBlockFragment fragment){
		
	}
	
	// Convert CFD into ASMCodeFragment
	private ASMCodeFragment replaceInstructions(BasicBlockFragment fragment) {
		ASMCodeFragment newInstructions = new ASMCodeFragment(CodeType.GENERATES_VOID);

		for (int i = 0; i < fragment.getBlocks().size(); i++) {
			BasicBlock block = fragment.getBlock(i);
			String labelString = "basicBlock-" + (i+1);
			newInstructions.add(ASMOpcode.Label, labelString);
			for (ASMInstruction instruction : block.getInstructions()) {
				newInstructions.add(instruction);
			}
		}
		
		return newInstructions;
	}
	private boolean simplifyJumps(ASMCodeFragment fragment) {
		List<ASMInstruction> instructions = fragment.getChunk(0).getInstructions();
		for (int i = 0; i < instructions.size(); i++) {
			ASMInstruction instruction = instructions.get(i);
			Object pushValue = instruction.getArgument();
			
			if (instruction.getOpcode() == ASMOpcode.PushI) {
				ASMInstruction jumpInstruction = instructions.get(i+1);
				ASMOpcode jumpCode = jumpInstruction.getOpcode();
				Object jumpArgument = jumpInstruction.getArgument();
				if (jumpCode.isJump()) {
					ASMInstruction newInstruction = new ASMInstruction(ASMOpcode.Jump, jumpArgument);
					
					switch (jumpCode) {
					case JumpTrue:
						if ((int)pushValue != 0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break;
					case JumpFalse:
						if ((int)pushValue == 0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break;	
					case JumpPos:
						if ((int)pushValue > 0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break; 
					case JumpNeg:
						if ((int)pushValue < 0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break;
					default:
							break;
					}
				}
			}
			
			if (instruction.getOpcode() == ASMOpcode.PushF) {
				ASMInstruction jumpInstruction = instructions.get(i+1);
				ASMOpcode jumpCode = jumpInstruction.getOpcode();
				Object jumpArgument = jumpInstruction.getArgument();
				if (jumpCode.isJump()) {
					ASMInstruction newInstruction = new ASMInstruction(ASMOpcode.Jump, jumpArgument);
					
					switch (jumpCode) {
					case JumpFZero:
						if ((double)pushValue == 0.0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break;	
					case JumpFPos:
						if ((double)pushValue > 0.0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break; 
					case JumpFNeg:
						if ((double)pushValue < 0.0) {
							instructions.remove(i);
							instructions.remove(i);
							instructions.add(i, newInstruction);
							return true;
						}
						break; 
					default:
						break;
					}
				}
			}
			
		}
		
		return false;
	}

	// CFG print helper function
	private void printCFG(BasicBlockFragment fragment) {
		if (debug) {
			StringBuilder stringBuilder = new StringBuilder();
			int i = 1;
			for (BasicBlock block : fragment.getBlocks()) {
				stringBuilder.append("Block #" + (i++) + ":\n");
				stringBuilder.append("  Incoming Number of Edges: " + block.getIncomingEdges().size() + "\n");
				stringBuilder.append("  Outgoing Number of Edges: " + block.getOutgoingEdges().size() + "\n\n");
			    stringBuilder.append(block.toString());
			    stringBuilder.append("===============================================================\n");
			}
			System.out.println(stringBuilder.toString());
		}
	}

}