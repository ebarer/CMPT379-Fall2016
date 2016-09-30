package parseTree.nodeTypes;

import parseTree.ParseNodeVisitor;

public interface NumberConstantNode {
	public int getValue();	
	public void accept(ParseNodeVisitor visitor);
}
