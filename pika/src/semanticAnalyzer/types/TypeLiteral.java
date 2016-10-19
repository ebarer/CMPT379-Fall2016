package semanticAnalyzer.types;

import tokens.Token;

public enum TypeLiteral implements Type {
	BOOLEAN(1, PrimitiveType.BOOLEAN),
	CHARACTER(1, PrimitiveType.CHARACTER),
	STRING(4, PrimitiveType.STRING),
	INTEGER(4, PrimitiveType.INTEGER),
	FLOATING(8, PrimitiveType.FLOATING),
	RATIONAL(8, PrimitiveType.RATIONAL),
	
	ERROR(0, PrimitiveType.ERROR),				// use as a value when a syntax error has occurred
	NO_TYPE(0, PrimitiveType.NO_TYPE, "");		// use as a value when no type has been assigned.
	
	private int sizeInBytes;
	private Type type;
	private String infoString;
	
	private TypeLiteral(int size, Type type) {
		this.sizeInBytes = size;
		this.type = type;
		this.infoString = toString();
	}
	private TypeLiteral(int size, Type type, String infoString) {
		this.sizeInBytes = size;
		this.type = type;
		this.infoString = infoString;
	}
	public int getSize() {
		return sizeInBytes;
	}
	public Type getType() {
		return type;
	}
	public String infoString() {
		return infoString;
	}
	
	
	////////////////////////////////////////////////////////////
	// convenience factory
	
	public static TypeLiteral withToken(Token token) {
		switch(token.getLexeme()) {
		case "bool":
			return TypeLiteral.BOOLEAN;
		case "char":
			return TypeLiteral.CHARACTER;
		case "string":
			return TypeLiteral.STRING;
		case "int":
			return TypeLiteral.INTEGER;
		case "float":
			return TypeLiteral.FLOATING;
		default:
			return TypeLiteral.ERROR;
		}
	}
}