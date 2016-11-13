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
	
// HELPER FUNCTIONS
	public boolean equals(Type type2) {
		if (!(type2 instanceof ArrayType)) {
			return false;
		}
		
		Type subtype1 = this.getSubtype();
		Type subtype2 = ((ArrayType)type2).getSubtype();
		
		while ((subtype1 instanceof ArrayType) && (subtype2 instanceof ArrayType)) {
			subtype1 = ((ArrayType)subtype1).getSubtype();
			subtype2 = ((ArrayType)subtype2).getSubtype();
		}
		
		if (subtype1 instanceof TypeLiteral) {
			subtype1 = ((TypeLiteral) subtype1).getType();
		}
		
		if (subtype2 instanceof TypeLiteral) {
			subtype2 = ((TypeLiteral) subtype2).getType();
		}
		
		if (subtype1 == subtype2) {
			return true;
		} else {
			return false;
		}
	}
}
