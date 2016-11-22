package optimizer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import asmCodeGenerator.codeStorage.ASMInstruction;
import asmCodeGenerator.codeStorage.ASMOpcode;

public class BasicBlock {
	private static int blockCount = 1;
	private int blockNum;
	private List<ASMInstruction> instructions;
	private HashMap<Integer, BasicBlock> incomingEdges;
	private HashMap<Integer, BasicBlock> outgoingEdges;
	private boolean visited = false;
	
	public BasicBlock() {
		this.blockNum = blockCount++;
		this.instructions = new LinkedList<ASMInstruction>();
		this.incomingEdges = new HashMap<Integer, BasicBlock>();
		this.outgoingEdges = new HashMap<Integer, BasicBlock>();
	}
	
	public List<ASMInstruction> getInstructions() {
		return instructions;
	}
	
	public void add(ASMInstruction instruction){
		instructions.add(instruction);
	}
	
	public boolean isEmpty() {
		return (instructions.size() > 0) ? false : true;
	}
	
/////////////////////////////////////////////////////////////////////////
// CFD Edge helper functions
	public void addIncomingEdge(Integer blockNum, BasicBlock node) {
		incomingEdges.put(blockNum, node);
	}
	public HashMap<Integer, BasicBlock> getIncomingEdges() {
		return incomingEdges;
	}
	
	public void addOutgoingEdge(Integer blockNum, BasicBlock node) {
		outgoingEdges.put(blockNum, node);
	}
	public HashMap<Integer, BasicBlock> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	public void visit() {
		this.visited = true;
	}
	public void unvisit() {
		this.visited = false;
	}
	public boolean wasVisited() {
		return visited;
	}
	
	public void setNum(int num) {
		this.blockNum = num;
	}
	public int getNum() {
		return this.blockNum;
	}
	public String getLabel() {
		return "basicBlock-" + blockNum;
	}
	
	public void mergeWith(BasicBlock target) {
		// Eliminate the end JUMP from the current block
		ASMInstruction lastInstr = this.instructions.get(this.instructions.size() - 1);
		if (lastInstr.getOpcode().isJump()) {
			this.instructions.remove(this.instructions.size() - 1);
		}
				
		// Eliminate the starting labels from the target block
		ASMInstruction targetInstr = target.instructions.get(0);
		while (targetInstr.getOpcode() == ASMOpcode.Label) {
			target.instructions.remove(0);
			if (target.instructions.size() > 0) {
				targetInstr = target.instructions.get(0);
			} else {
				break;
			}
		}
		
		// Copy instructions from target to current block
		for (ASMInstruction instruction : target.getInstructions()) {
			this.instructions.add(instruction);
		}
		
		// Set current block outgoing edges
		this.outgoingEdges = target.outgoingEdges;
		
		// Update outgoing targets' incoming edges
		for (BasicBlock outBlock : outgoingEdges.values()) {
			outBlock.incomingEdges.remove(target.getNum());
			outBlock.incomingEdges.put(this.getNum(), this);
		}

	}
	
/////////////////////////////////////////////////////////////////////////
// Print helper functions
	@Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (ASMInstruction instruction : instructions) {
            stringBuilder.append(instruction.toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
