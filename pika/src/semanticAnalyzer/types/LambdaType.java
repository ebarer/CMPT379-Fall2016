package semanticAnalyzer.types;

import java.util.ArrayList;

public class LambdaType implements Type {	
	private String infoString;
	private ArrayList<Type> typeList;
	private Type returnType;
	private int sizeInBytes = 8;
	
	public LambdaType() {
		returnType = PrimitiveType.VOID;
		typeList = new ArrayList<Type>();
	}
	public LambdaType(String infoString) {
		this.infoString = infoString;
	}
	public int getSize() {
		return sizeInBytes;
	}
	
// ACCESSORS
	public void setReturnType(Type type) {
		this.returnType = type;
		updateInfoString();
	}
	public Type getReturnType() {
		return returnType;
	}
	
	public ArrayList<Type> getTypeList() {
		return typeList;
	}
	public void addType(Type type) {
		typeList.add(type);
		updateInfoString();
	}
	
	public String infoString() {
		return infoString;
	}
	private void updateInfoString() {
		ArrayList<String> types = new ArrayList<String>();
		typeList.forEach((type) -> types.add(type.infoString()));
		
		String typeString = types.toString();
		typeString = typeString.replace('[', '<');
		typeString = typeString.replace(']', '>');
		
		this.infoString = typeString + " -> " + returnType.infoString();
	}
	
// HELPER FUNCTIONS
	
	public boolean equals(Type type2) {
		if (!(type2 instanceof LambdaType)) {
			return false;
		}
		
		return true;
	}
	
//	public boolean equals(Type type2) {
//		if (!(type2 instanceof ArrayType)) {
//			return false;
//		}
//		
//		Type subtype1 = this.getSubtype();
//		Type subtype2 = ((ArrayType)type2).getSubtype();
//		
//		while ((subtype1 instanceof ArrayType) && (subtype2 instanceof ArrayType)) {
//			subtype1 = ((ArrayType)subtype1).getSubtype();
//			subtype2 = ((ArrayType)subtype2).getSubtype();
//		}
//		
//		if (subtype1 instanceof TypeLiteral) {
//			subtype1 = ((TypeLiteral) subtype1).getType();
//		}
//		
//		if (subtype2 instanceof TypeLiteral) {
//			subtype2 = ((TypeLiteral) subtype2).getType();
//		}
//		
//		if (subtype1 == subtype2) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
