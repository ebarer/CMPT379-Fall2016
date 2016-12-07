package optimizer;

import java.util.ArrayList;
import java.util.Arrays;
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
	private List<List<BasicBlock>> loopCandidates;
	private BasicBlock firstBlock;
	
	public static int num = 1;
	
	public BasicBlockFragment() {
		this.blocks = new LinkedList<BasicBlock>();
		this.labelLookup = new HashMap<String, BasicBlock>();
		this.subroutineLookup = new HashSet<String>();
		this.loopCandidates = new ArrayList<List<BasicBlock>>();
	}
	
	public void add(ASMInstruction instruction){
		lastBlock().add(instruction);
		
		Object argument = instruction.getArgument();
		if (argument != null && argument.equals("$$main")) {
			if (firstBlock == null) {
				firstBlock = getLastBlock();
			}
		}
		
		if (instruction.getOpcode() == ASMOpcode.Label) {
			String label = (String)instruction.getArgument();
			labelLookup.put(label, getLastBlock());
		}
		
		if (instruction.getOpcode() == ASMOpcode.Call ||
			instruction.getOpcode() == ASMOpcode.PushD) {
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
	public void traverseGraph() {
		traverseGraph(false);
	}
	public void traverseGraph(boolean storeBackedges) {
		if (firstBlock == null) {
			firstBlock = blocks.get(0);
		}
		
		traverse(firstBlock, null, storeBackedges);
	}
	private void traverse(BasicBlock block, BasicBlock ancestor, boolean storeBackedges) {
		if (block.wasVisited()) {
			if (storeBackedges) {
				loopCandidates.add(Arrays.asList(block, ancestor));
			}
			
			return;
		}
		
		block.visit();
		
		for (BasicBlock childBlock : block.getOutgoingEdges().values()) {
			traverse(childBlock, block, storeBackedges);
		}
	}
	
	public HashMap<BasicBlock, Boolean> identifyHeaders() {
		unvisitAllBlocks();
		traverseGraph(true);
		
		HashMap<BasicBlock, Boolean> candidates = new HashMap<BasicBlock, Boolean>();
		
		for (int i = 0; i < loopCandidates.size(); i++) {
			unvisitAllBlocks();
			
			List<BasicBlock> candidate = loopCandidates.get(i);
			BasicBlock target = candidate.get(0);
			BasicBlock tail = candidate.get(1);
			
			boolean isValid = reverseTraverse(tail, target);
			
			Boolean value = candidates.putIfAbsent(target, isValid);
			if (value != null) {
				candidates.put(target, value && isValid);
			}
		}
		
		unvisitAllBlocks();
		
		return candidates;
	}
	private boolean reverseTraverse(BasicBlock block, BasicBlock target) {
		if (block.equals(target) || block.wasVisited()) return true;
		if (block.equals(firstBlock) || block.getIncomingEdges().isEmpty()) return false;
		
		block.visit();
		
		boolean isValid = true;
		for (BasicBlock childBlock : block.getIncomingEdges().values()) {
			isValid = isValid && reverseTraverse(childBlock, target);
		}
		
		return isValid;
	}
	
	public void locateLabels() {
		subroutineLookup.clear();
		
		for (BasicBlock block : blocks) {
			for (ASMInstruction instruction : block.getInstructions()) {
				if (instruction.getOpcode() == ASMOpcode.Label) {
					String label = (String)instruction.getArgument();
					labelLookup.put(label, block);
				}
			}
		}
	}
	
	public void locateSubroutines() {
		subroutineLookup.clear();
		
		for (BasicBlock block : blocks) {
			for (ASMInstruction instruction : block.getInstructions()) {
				if (instruction.getOpcode() == ASMOpcode.Call ||
					instruction.getOpcode() == ASMOpcode.PushD) {
					String label = (String)instruction.getArgument();
					subroutineLookup.add(label);
				}
			}
		}
	}
	public void traverseSubroutines() {
		for (String label : subroutineLookup) {
			BasicBlock subroutineBlock = labelLookup.get(label);
			if (subroutineBlock != null) {
				traverse(subroutineBlock, null, false);
			}
		}
	}
	
	public void unvisitAllBlocks() {
		this.getBlocks().forEach(block -> block.unvisit());
	}
	
	public void renumberBlocks() {
		int i = 1;
		for (BasicBlock block : blocks) {
			block.setNum(i++);
		}
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
	public void addBlock(int index, BasicBlock block) {
		blocks.add(index, block);
	}
	
	public void append(BasicBlockFragment fragment){
		for (BasicBlock basicBlock : fragment.getBlocks()) {
			this.addBlock(basicBlock);
		}
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
	public HashSet<String> getSubroutines() {
		return subroutineLookup;
	}
}
