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
	private ASMCodeFragment fragment;
	private static final int DATA = 0;
	private static final int INSTRUCTIONS = 1;
	
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
		
		// Merge fragments
		returnFragment.append(fragments[DATA]);
		returnFragment.append(fragments[INSTRUCTIONS]);
		
		return returnFragment;
	}
	
	// Split into data and instruction fragments
	private ASMCodeFragment[] splitDirectives(ASMCodeFragment fragment) {
		ASMCodeFragment fragments[] = {
			new ASMCodeFragment(CodeType.GENERATES_VOID),
			new ASMCodeFragment(CodeType.GENERATES_VOID),
		};
		
		List<ASMCodeChunk> chunks = fragment.getChunks();

		for (ASMCodeChunk chunk : chunks) {			
			for (ASMInstruction instruction : chunk.getInstructions()) {
				ASMOpcode opcode = instruction.getOpcode();
				Object operand = instruction.getArgument();
				String comment = instruction.getComment();
				
				if (opcode == ASMOpcode.Nop) {
					continue;
				} else if (opcode == ASMOpcode.Jump && operand == "$$main") {
					fragments[DATA].add(opcode, operand, comment);
				} else if (opcode.isDataDirective()) {
					fragments[DATA].add(opcode, operand, comment);				
				} else {
					fragments[INSTRUCTIONS].add(opcode, operand, comment);
				}
			}
		}
		
		return fragments;
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
		if (fragment.getChunks().size() > 0) {
			ASMCodeChunk chunk = fragment.getChunks().get(0);
			List<ASMInstruction> instructions = chunk.getInstructions();
			HashMap<Object, Integer> stringLegend = new HashMap<>();
			
			LinkedHashMap<Integer, Object> strings = locateStrings(fragment);
			for (Map.Entry<Integer, Object> stringEntry : strings.entrySet()) {
				int loc = stringEntry.getKey();
				ASMInstruction dLabel = instructions.get(loc);
				ASMInstruction dataS = instructions.get(loc + 1);
				
				Object argument = dataS.getArgument();
				if (stringLegend.containsKey(argument)) {
					// Insert DLabel at location of first instance of string
					int addLoc = stringLegend.get(argument) + 1;
					instructions.add(addLoc, dLabel);
					
					// Remove DLabel and DataS instructions
					instructions.remove(++loc);
					ASMOpcode opcode = dataS.getOpcode();
					if (opcode == ASMOpcode.DataS) {
						instructions.remove(loc);
					} else if (opcode == ASMOpcode.DataC) {
						while (loc < instructions.size() - 1 && opcode == ASMOpcode.DataC) {
							instructions.remove(loc++);
							opcode = instructions.get(loc).getOpcode();
						}
					}
				} else {
					stringLegend.put(argument, loc);				
				}
			}
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
				ASMInstruction instr = new ASMInstruction(opcode, argument.floatValue());
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
				ASMInstruction instr = new ASMInstruction(ASMOpcode.PushI, ((Double)val).intValue());
				instructions.add(loc, instr);
			} else {
				ASMInstruction instr = new ASMInstruction(ASMOpcode.PushF, ((Integer)val).doubleValue());
				instructions.add(loc, instr);
			}
			
			numChanges++;
		}
	}
}