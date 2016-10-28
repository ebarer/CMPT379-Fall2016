package optimizer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import asmCodeGenerator.codeStorage.ASMInstruction;
import asmCodeGenerator.codeStorage.ASMOpcode;

public class BasicBlock {
	private List<ASMInstruction> instructions;
	private HashMap<ASMOpcode, BasicBlock> incomingEdges;
	private HashMap<ASMOpcode, BasicBlock> outgoingEdges;
	private boolean visited = false;
	
	public BasicBlock() {
		this.instructions = new LinkedList<ASMInstruction>();
		this.incomingEdges = new HashMap<ASMOpcode, BasicBlock>();
		this.outgoingEdges = new HashMap<ASMOpcode, BasicBlock>();
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
	public void addIncomingEdge(ASMOpcode opcode, BasicBlock node) {
		incomingEdges.put(opcode, node);
	}
	public HashMap<ASMOpcode, BasicBlock> getIncomingEdges() {
		return incomingEdges;
	}
	
	public void addOutgoingEdge(ASMOpcode opcode, BasicBlock node) {
		outgoingEdges.put(opcode, node);
	}
	public HashMap<ASMOpcode, BasicBlock> getOutgoingEdges() {
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
	
	public void mergeWith(BasicBlock target) {
		// Copy across instructions
		for (ASMInstruction instruction : target.getInstructions()) {
			this.instructions.add(instruction);
		}
		
		// Set outgoing edges
		this.outgoingEdges = target.outgoingEdges;
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
