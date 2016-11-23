package optimizer;

import java.util.ArrayList;
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
	boolean debugMerge = false;
	
	private ASMCodeFragment[] programFragments;
	private static final int HEADER = 0;
	private static final int DATA = 1;
	private static final int INSTRUCTIONS = 2;
	
	public static ASMCodeFragment optimize(ASMCodeFragment fragment) {
		Optimizer optimizer = new Optimizer(fragment);
		return optimizer.optimize();
	}
	public Optimizer(ASMCodeFragment fragment) {
		this.programFragments = splitDirectives(fragment);
	}
	
	public ASMCodeFragment optimize() {		
		ASMCodeFragment returnFragment = new ASMCodeFragment(CodeType.GENERATES_VOID);
		
		// Eliminate duplicate strings
		removeDuplicateStrings(programFragments[DATA]);
		
		// Constant arithmetic calculations
		constantFolding(programFragments[INSTRUCTIONS]);
		
		// Divide instructions into BasicBlocks, construct ControlFlowGraph
		BasicBlockFragment cfg = blockDivision(programFragments[INSTRUCTIONS]);
		constructControlFlowGraph(cfg);
		blockTempLabel(cfg);
		
		// Manipulate CFG
		while(removeUnreachableCode(cfg) > 0);

		boolean loop = true;
		while(loop) {
			loop = false;
			while (cloneBlocks(cfg)) { loop = true; }
			while (mergeBlocks(cfg)) { loop = true; }
			while (simplifyJumps(cfg)) { loop = true; }
		}

		// For each block that "fallsthrough", add explicit jump
		// to ensure code runs in the correct order
		addFallthroughJumps(cfg);
		replaceLabels(cfg);
		
		// Grab optimized instructions from BasicBlocks
		programFragments[INSTRUCTIONS] = extractInstructions(cfg);
		cleanupJumps(programFragments[INSTRUCTIONS]);
		
		// Merge fragments
		returnFragment.append(programFragments[HEADER]);
		returnFragment.append(programFragments[DATA]);
		returnFragment.append(programFragments[INSTRUCTIONS]);
		
		return returnFragment;
	}
	
	
	// Split into data and instruction fragments
	private ASMCodeFragment[] splitDirectives(ASMCodeFragment fragment) {
		ASMCodeFragment fragments[] = {
			new ASMCodeFragment(CodeType.GENERATES_VOID),
			new ASMCodeFragment(CodeType.GENERATES_VOID),
			new ASMCodeFragment(CodeType.GENERATES_VOID),
		};
		
		fragment = flattenFragment(fragment);

		ASMCodeChunk chunk = fragment.getChunk(0);
		List<ASMInstruction> instructions = chunk.getInstructions();

		// Ensure header information stays at the top, using HEADER
		int currentInstr = 0;
		while (currentInstr < chunk.getInstructions().size()) {
			Object argument = instructions.get(currentInstr).getArgument();
			if (argument == null || !argument.equals("$$main")) {
				fragments[HEADER].add(instructions.get(currentInstr++));
			} else break;
		}
		
		fragments[HEADER].add(instructions.get(currentInstr++));
			
		// Move all instructions into DATA or INSTRUCTIONS
		for (; currentInstr < chunk.getInstructions().size(); currentInstr++) {
			ASMInstruction instruction = instructions.get(currentInstr);
			ASMOpcode opcode = instruction.getOpcode();
			
			if (opcode == ASMOpcode.Nop) {
				continue;
			} else if (opcode.isDataDirective()) {
				if (instruction.getOpcode() == ASMOpcode.DataS) {
					String argString = ((String)instruction.getArgument()).replaceAll("^\"|\"$", "");
					ASMInstruction newInstruction = new ASMInstruction(ASMOpcode.DataS, argString, instruction.getComment());
					fragments[DATA].add(newInstruction);
				} else {
					fragments[DATA].add(instruction);
				}
			} else {
				fragments[INSTRUCTIONS].add(instruction);
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
		
		for (int i = 0; i < instructions.size(); i++) {
			ASMInstruction instruction = instructions.get(i);
			if (instruction.isString()) {
				Object operand = instruction.getArgument();
				strings.put(i, operand);
				
				while (instruction.getOpcode() == ASMOpcode.DLabel) {
					instruction = instructions.get(++i);
				}
			}
		}
		
		return strings;
	}
	private void removeDuplicateStrings(ASMCodeFragment fragment) {
		if (fragment.getChunks().size() > 0) {
			ASMCodeChunk chunk = fragment.getChunks().get(0);
			List<ASMInstruction> instructions = chunk.getInstructions();
			HashMap<Object, Integer> stringLegend = new HashMap<>();
			
			int removedInstrCount = 0;
			LinkedHashMap<Integer, Object> strings = locateStrings(fragment);
			for (Map.Entry<Integer, Object> stringEntry : strings.entrySet()) {
				int loc = stringEntry.getKey();
				loc -= removedInstrCount; // Compensate for deleted instructions
				
				// Get header start
				int headerStart = loc;
				while (instructions.get(headerStart).getOpcode() == ASMOpcode.DLabel) {
					headerStart++;
				}
				
				// Get header end
				int headerEnd = headerStart;
				while (instructions.get(headerEnd).getOpcode() == ASMOpcode.DataI) {
					headerEnd++;
				}
				
				int stringStart = headerEnd;
				while (instructions.get(stringStart).getOpcode() != ASMOpcode.DataC &&
					   instructions.get(stringStart).getOpcode() != ASMOpcode.DataS) {
					stringStart++;
				}
				
				ASMOpcode opcode = instructions.get(stringStart).getOpcode();
				String stringData = getString(instructions, stringStart);
				
				if (stringLegend.containsKey(stringData)) {
					List<ASMInstruction> labels = new ArrayList<ASMInstruction>();
					// Store and remove string labels
					for (int l = loc; l < headerStart; l++) {
						labels.add(instructions.get(loc));
						instructions.remove(loc);
						removedInstrCount++;
					}
					
					// Remove record instructions
					for (int l = headerStart; l < headerEnd; l++) {
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
					for (ASMInstruction label : labels) {
						instructions.add(addLoc++, label);
						removedInstrCount--;
					}
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
				if (value == 0) break;
				stringBuffer.append((char)value);
				instruction = instructions.get(++loc);
			}

			// stringBuffer.setLength(stringBuffer.length() - 1);
			String returnString = stringBuffer.toString();
			return returnString;
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
							if (op3.isArithmetic() || op3 == ASMOpcode.Duplicate) {
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
							if (op3.isArithmetic() || op3 == ASMOpcode.Duplicate) {
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
			if (op == ASMOpcode.Duplicate) {
				ASMInstruction jumpInstr = instructions.get(loc+3);
				ASMInstruction divideInstr = instructions.get(loc+4);
				
				if (divideInstr.getOpcode() == ASMOpcode.FDivide || divideInstr.getOpcode() == ASMOpcode.Divide) {
					if (jumpInstr.getOpcode() == ASMOpcode.JumpFalse || jumpInstr.getOpcode() == ASMOpcode.JumpFZero) {
						foldDivision(left, right, loc, instructions, opcode);	
					}
				}
			} else {
				foldDivision(left, right, loc, instructions, opcode);
			}
			
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
		ASMInstruction jumpInstr = instructions.get(loc + 3);
		
		// Remove old instructions
		instructions.remove(loc);
		instructions.remove(loc);
		instructions.remove(loc);
		
		if (jumpInstr.getOpcode().isJump()) {
			instructions.remove(loc);
			instructions.remove(loc);
			
			// Issue division by 0 error
			if ((Double)right == 0) {
				ASMInstruction instr = new ASMInstruction(ASMOpcode.Jump, jumpInstr.getArgument());
				instructions.add(loc, instr);
				numChanges++;
				return;
			}
		}	
		
		// Insert folded instruction
		Double argument = (Double)left / (Double)right;
		
		if (opcode == ASMOpcode.PushF) {
			ASMInstruction instr = new ASMInstruction(opcode, argument.doubleValue());
			instructions.add(loc, instr);				
		} else if (opcode == ASMOpcode.PushI) {
			ASMInstruction instr = new ASMInstruction(opcode, argument.intValue());
			instructions.add(loc, instr);
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
				if (instruction.getArgument() instanceof String) { 
					blocks.lastBlock().setLabel((String)instruction.getArgument());
				}
				
				blocks.add(instructions.get(i));
				continue;
			}
			
			if (instruction.getOpcode().isLeave()) {
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
	private void blockTempLabel(BasicBlockFragment fragment) {
		for (BasicBlock block : fragment.getBlocks()) {
			if (block.getInstructions().size() == 0 || block.getInstructions().get(0).getOpcode() != ASMOpcode.Label) {
				String label = "tempLabel-" + block.getNum();
				ASMInstruction labelInstr = new ASMInstruction(ASMOpcode.Label, label);
				block.setLabel(label);
				block.getInstructions().add(0, labelInstr);
				fragment.getLabelLookup().put(label, block);
			}
		}
	}
	private void constructControlFlowGraph(BasicBlockFragment fragment) {
		HashMap<String, BasicBlock> labelLookup = fragment.getLabelLookup();
		
		for (int j = 0; j < fragment.getBlocks().size(); j++) {
			BasicBlock block = fragment.getBlock(j);
			List<ASMInstruction> instructions = block.getInstructions();
			
			for (int i = 0; i < instructions.size(); i++) {
				ASMInstruction instruction = instructions.get(i);
				
				// If first instruction is not a label, connect to previous block
				if ((j > 0) && (i == 0) && (instruction.getOpcode() != ASMOpcode.Label)) {
					BasicBlock fromBlock = fragment.getBlock(j-1);
					BasicBlock.createEdge(fromBlock, block);
				}
				
				// If instruction is a jump, define edges
				if (instruction.getOpcode().isJump()) {	
					String label = (String)instruction.getArgument();
					BasicBlock toBlock = labelLookup.get(label);
					BasicBlock.createEdge(block, toBlock);
				}
				
				// If last instruction is not a Jump type or Call, connect to next block
				if ((j < fragment.getBlocks().size() - 1) && (i == instructions.size() - 1) && (instruction.getOpcode() != ASMOpcode.Jump) && (!instruction.getOpcode().isLeave())) {
					BasicBlock toBlock = fragment.getBlock(j+1);
					BasicBlock.createEdge(block, toBlock);
				}
			}
		}
	}


	// Manipulate CFG
	private int removeUnreachableCode(BasicBlockFragment fragment) {
		int nodesRemoved = fragment.getBlocks().size();
		
		fragment.unvisitAllBlocks();
		fragment.traverseSubroutines();
		fragment.traverseGraph();
		
		// Remove unvisited nodes
		fragment.getBlocks().removeIf(block -> !block.wasVisited());
		nodesRemoved = nodesRemoved - fragment.getBlocks().size();
		
		fragment.locateSubroutines();
		
		if (debugMerge) {
			printCFG(fragment);
		}
		
		return nodesRemoved;
	}
	private boolean cloneBlocks(BasicBlockFragment fragment){
		for (int j = 0; j < fragment.getBlocks().size(); j++) {
			BasicBlock blockX = fragment.getBlock(j);
			
			// Suppose that we have an empty block X with two outneighbors
			if (blockX.getOutgoingEdges().size() == 2) {
				
				// X has two or more inneighbors Y1, Y2, ... Yk,
				if (blockX.getIncomingEdges().size() >= 2) {
					
					// All of these inneighbors have one outneighbor
					for (BasicBlock inBlock : blockX.getIncomingEdges().values()) {
						if (inBlock.getOutgoingEdges().size() > 1) {
							return false;
						}
					}
					
					// If X ends in a true/false branch
					int instrSize = blockX.getInstructions().size();
					if (instrSize == 2) {
						ASMInstruction instr1 = blockX.getInstructions().get(instrSize - 1);
						if (instr1.getOpcode().isJump()) {
							
							// Remove initial block
							fragment.getBlocks().remove(j);
							
							// Clone X for each INCOMING EDGE
							for (BasicBlock fromBlock : blockX.getIncomingEdges().values()) {
								BasicBlock cloneBlock = BasicBlock.clone(blockX);

								// Redefine edges
								fromBlock.removeOutgoingEdge(blockX);
								BasicBlock.createEdge(fromBlock, cloneBlock);
								fragment.getBlocks().add(j, cloneBlock);
							}
							
							return true;
						}					
					}
				}
			}
		}
		
		return false;
	}
	private boolean mergeBlocks(BasicBlockFragment fragment) {
		for (BasicBlock block : fragment.getBlocks()) {			
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
	private boolean simplifyJumps(BasicBlockFragment fragment) {
		for (BasicBlock block : fragment.getBlocks()) {
			List<ASMInstruction> instructions = block.getInstructions();
			
			for (int i = 0; i < instructions.size() - 1; i++) {
				ASMInstruction instruction = instructions.get(i);
				Object pushValue = instruction.getArgument();
				
				if (instruction.getOpcode() == ASMOpcode.PushI) {
					ASMInstruction jumpInstruction = instructions.get(i+1);
					ASMOpcode jumpCode = jumpInstruction.getOpcode();
					Object jumpArgument = jumpInstruction.getArgument();
					ASMInstruction newInstruction = new ASMInstruction(ASMOpcode.Jump, jumpArgument);
					
					if (jumpCode.isJump()) {
						switch (jumpCode) {
						case JumpTrue:
							instructions.remove(i);
							instructions.remove(i);
							if ((int)pushValue != 0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						case JumpFalse:
							instructions.remove(i);
							instructions.remove(i);
							if ((int)pushValue == 0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						case JumpPos:
							instructions.remove(i);
							instructions.remove(i);
							if ((int)pushValue > 0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						case JumpNeg:
							instructions.remove(i);
							instructions.remove(i);
							if ((int)pushValue < 0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						default:
							break;
						}
					}
				} else if (instruction.getOpcode() == ASMOpcode.PushF) {
					ASMInstruction jumpInstruction = instructions.get(i+1);
					ASMOpcode jumpCode = jumpInstruction.getOpcode();
					Object jumpArgument = jumpInstruction.getArgument();
					ASMInstruction newInstruction = new ASMInstruction(ASMOpcode.Jump, jumpArgument);
					
					if (jumpCode.isJump()) {
						switch (jumpCode) {
						case JumpFZero:
							instructions.remove(i);
							instructions.remove(i);
							if ((double)pushValue == 0.0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						case JumpFPos:
							instructions.remove(i);
							instructions.remove(i);
							if ((double)pushValue > 0.0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						case JumpFNeg:
							instructions.remove(i);
							instructions.remove(i);
							if ((double)pushValue < 0.0) {
								instructions.add(i++, newInstruction);
								
								while (i < instructions.size()) {
									instructions.remove(i);
								}
								
								// Remove outgoing edges
								for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
									toBlock.removeIncomingEdge(block);
								}
								block.getOutgoingEdges().clear();
								
								// Redefine outgoing edges
								BasicBlock toBlock = fragment.getLabelLookup().get(jumpArgument);
								if (toBlock != null) {
									BasicBlock.createEdge(block, toBlock);
								}
							} else {								
								BasicBlock outBlock = fragment.getLabelLookup().get(jumpArgument);
								if (outBlock != null) {
									BasicBlock.removeEdge(block, outBlock);
								}
							}
							return true;
						default:
							break;
						}
					}
				}
				
			}
		}
		
		return false;
	}
	private void addFallthroughJumps(BasicBlockFragment fragment) {
		for (BasicBlock block : fragment.getBlocks()) {
			// If last instruction isn't a leave or jump, add jump for fallthrough
			ASMInstruction lastInstr = block.getInstructions().get(block.getInstructions().size() - 1);
			if (!lastInstr.getOpcode().isLeave() && lastInstr.getOpcode() != ASMOpcode.Jump) {
				for (BasicBlock toBlock : block.getOutgoingEdges().values()) {
					// Select the outgoing edge that is not represented
					// if there already exists a conditional jump
					if (toBlock.getLabel() != null && !toBlock.getLabel().equals(lastInstr.getArgument())) {
						ASMInstruction jumpInstr = new ASMInstruction(ASMOpcode.Jump, toBlock.getLabel());
						block.getInstructions().add(jumpInstr);	
					}
				}
			}
		}
	}

	
	// Convert CFD into ASMCodeFragment
	private void replaceLabels(BasicBlockFragment fragment) {
		fragment.renumberBlocks();
		
		for (String label : fragment.getLabelLookup().keySet()) {
			BasicBlock block = fragment.getLabelLookup().get(label);
			for (ASMInstruction instruction : block.getInstructions()) {
				if (instruction.getArgument() != null && instruction.getArgument().equals(label)) {
					instruction.setArgument(block.getOutputLabel());
				}
			}
			
			// Handle main program label
			if (label == RunTime.MAIN_PROGRAM_LABEL) {
				for (ASMInstruction instruction : programFragments[HEADER].getChunk(0).getInstructions()) {
					if (instruction.getArgument() != null) {
						if (instruction.getArgument() == label) {
							instruction.setArgument(block.getOutputLabel());
						}
					}
				}
			}
			
			for (BasicBlock inBlock : fragment.getBlocks()) {
				for (ASMInstruction instruction : inBlock.getInstructions()) {
					if (instruction.getArgument() != null) {
						if (instruction.getArgument() instanceof String) {
							if (((String)instruction.getArgument()).equals(label)) {
								instruction.setArgument(block.getOutputLabel());
							}
						}
					}
				}
			}
		}		
	}
	private ASMCodeFragment extractInstructions(BasicBlockFragment fragment) {
		ASMCodeFragment extractedInstructions = new ASMCodeFragment(CodeType.GENERATES_VOID);
		
		// Visit each block and extract instructions
		for (BasicBlock block : fragment.getBlocks()) {
			for (ASMInstruction instruction : block.getInstructions()) {
				extractedInstructions.add(instruction);
			}
		}

		return extractedInstructions;
	}
	private void cleanupJumps(ASMCodeFragment fragment){
		
	}
	
	// CFG print helper function
	private void printCFG(BasicBlockFragment fragment) {
		if (debug) {
			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append("<--------------------- CFG Print --------------------->\n");
			stringBuilder.append("  Num Blocks: " + fragment.getBlocks().size() + "\n");
			stringBuilder.append("<----------------------------------------------------->\n\n");
			stringBuilder.append("===============================================================\n");

			for (BasicBlock block : fragment.getBlocks()) {
				stringBuilder.append("Block #" + block.getNum() + ":\n");
				stringBuilder.append("  Visited: " + block.wasVisited() + "\n");
				
				stringBuilder.append("  Incoming Number of Edges: " + block.getIncomingEdges().size() + " - ");
				stringBuilder.append("[");
				for (BasicBlock inBlock : block.getIncomingEdges().values()) {
					stringBuilder.append(inBlock.getNum() + ", ");
				}
				stringBuilder.append("]\n");
				
				stringBuilder.append("  Outgoing Number of Edges: " + block.getOutgoingEdges().size() + " - ");
				stringBuilder.append("[");
				for (BasicBlock outBlock : block.getOutgoingEdges().values()) {
					stringBuilder.append(outBlock.getNum() + ", ");
				}
				stringBuilder.append("]\n\n");
				
			    stringBuilder.append(block.toString());
			    stringBuilder.append("===============================================================\n");
			}
			System.out.println(stringBuilder.toString());
		}
	}

}