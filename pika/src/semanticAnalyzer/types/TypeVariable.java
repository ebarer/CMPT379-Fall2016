package semanticAnalyzer.types;

public enum TypeVariable implements Type {
	VARIABLE(8);
	
	private int sizeInBytes;
	private String infoString;
	
	private TypeVariable(int size) {
		this.sizeInBytes = size;
		this.infoString = toString();
	}
	private TypeVariable(int size, String infoString) {
		this.sizeInBytes = size;
		this.infoString = infoString;
	}
	public int getSize() {
		return sizeInBytes;
	}
	public String infoString() {
		return infoString;
	}
}