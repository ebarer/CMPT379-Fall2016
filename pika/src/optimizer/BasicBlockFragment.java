package optimizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


import asmCodeGenerator.codeStorage.ASMInstruction;
import asmCodeGenerator.codeStorage.ASMOpcode;

public class BasicBlockFragment {
	private List<BasicBlock> blocks;
	private HashMap<String, BasicBlock> labelLookup;
	private HashSet<String> subroutineLookup;
	private BasicBlock firstBlock;
	
	public BasicBlockFragment() {
		this.blocks = new LinkedList<BasicBlock>();
		this.labelLookup = new HashMap<String, BasicBlock>();
		this.subroutineLookup = new HashSet<String>();
	}
	
	public void add(ASMInstruction instruction){
		lastBlock().add(instruction);
		
		Object argument = instruction.getArgument();
		if (argument != null && argument.equals("$$main")) {
			firstBlock = getLastBlock();
		}
		
		if (instruction.getOpcode() == ASMOpcode.Label) {
			String label = (String)instruction.getArgument();
			labelLookup.put(label, getLastBlock());
		}
		
		if (instruction.getOpcode() == ASMOpcode.Call) {
			String label = (String)instruction.getArgument();
			subroutineLookup.add(label);
		}
	}
	
	public BasicBlock lastBlock() {
		if(blocks.size() == 0) {
			newBlock();
		}
		return blocks.get(blocks.size() - 1);
	}

/////////////////////////////////////////////////////////////////////////
// Tree functions
	public void traverseTree() {
		traverse(firstBlock);
	}
	private void traverse(BasicBlock block) {
		if (block.wasVisited()) return;
		
		block.visit();
		
		for (BasicBlock childBlock : block.getOutgoingEdges().values()) {
			traverse(childBlock);
		}
	}
	
	public void locateSubroutines() {
		subroutineLookup.clear();
		
		for (BasicBlock block : blocks) {
			for (ASMInstruction instruction : block.getInstructions()) {
				if (instruction.getOpcode() == ASMOpcode.Call) {
					String label = (String)instruction.getArgument();
					subroutineLookup.add(label);
				}
			}
		}
	}
	public void traverseSubroutines() {
		for (String label : subroutineLookup) {
			BasicBlock subroutineBlock = labelLookup.get(label);
			subroutineBlock.visit();
		}
	}
	
	public void unvisitAllBlocks() {
		this.getBlocks().forEach(block -> block.unvisit());
	}
	
/////////////////////////////////////////////////////////////////////////
// Optimizer helper functions

	public void newBlock() {
		BasicBlock block = new BasicBlock();
		addBlock(block);
	}
	public void addBlock(BasicBlock block) {
		blocks.add(block);
	}
	public List<BasicBlock> getBlocks() {
		return blocks;
	}
	public BasicBlock getBlock(int index) {
		return blocks.get(index);
	}
	public BasicBlock getLastBlock() {
		int index = blocks.size() - 1;
		return getBlock(index);
	}
	
	public BasicBlock getFirstBlock() {
		return firstBlock;
	}
	
	public HashMap<String, BasicBlock> getLabelLookup() {
		return labelLookup;
	}
}
