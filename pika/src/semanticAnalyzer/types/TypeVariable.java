package semanticAnalyzer.types;

public class TypeVariable implements Type {
	private int sizeInBytes;
	private Type subtype;
	private String infoString;

	public TypeVariable(Type type) {
		this.subtype = type;
		this.sizeInBytes = type.getSize();
		this.infoString = toString();
	}
	public TypeVariable(Type type, String infoString) {
		this.subtype = type;
		this.sizeInBytes = type.getSize();
		this.infoString = infoString;
	}
	public int getSize() {
		return sizeInBytes;
	}
	public String infoString() {
		return infoString;
	}
	
	// ACCESSOR METHODS
	public void setType(Type type) {
		this.subtype = type;
		this.sizeInBytes = type.getSize();
	}
	
	public Type getType() {
		return this.subtype;
	}
	
	public void reset() {
		this.subtype = PrimitiveType.ANY;
		this.sizeInBytes = 0;
	}
}