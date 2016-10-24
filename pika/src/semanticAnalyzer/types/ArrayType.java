package semanticAnalyzer.types;

public class ArrayType implements Type {	
	private Type subtype;
	private String infoString;
	private int sizeInBytes = 8;
	
	public ArrayType() {
		this.infoString = "ARRAY[NULL]";
	}
	public ArrayType(String infoString) {
		this.infoString = infoString;
	}
	public int getSize() {
		return sizeInBytes;
	}
	
// ACCESSORS
	public void setSubtype(Type type) {
		this.subtype = type;
		this.infoString = "ARRAY[" + subtype.infoString() + "]";
	}
	public Type getSubtype() {
		return subtype;
	}
	
	public String infoString() {
		return infoString;
	}
}
