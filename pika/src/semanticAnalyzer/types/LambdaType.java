package semanticAnalyzer.types;

import java.util.ArrayList;

import asmCodeGenerator.codeStorage.ASMOpcode;
import semanticAnalyzer.signatures.FunctionSignature;

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
	
	public FunctionSignature getSignature() {
		return new FunctionSignature(ASMOpcode.Nop, typeList, returnType);
	}
	
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
	
	@Override
	public boolean equals(Object type2) {
		if (!(type2 instanceof LambdaType)) {
			return false;
		}
		
		LambdaType compareType = (LambdaType) type2;
		
		// Check that number of arguments is the same
		if (this.typeList.size() != compareType.typeList.size()) {
			return false;
		}

		// Check that each argument is the same
		for (int i = 0; i < this.typeList.size(); i++) {
			Type t1 = this.typeList.get(i);
			Type t2 = compareType.typeList.get(i);
			
			if (!compare(t1, t2)) {
				return false;
			}
		}

		// Check that return type is the same
		return compare(this.returnType, compareType.returnType);
	}
	private boolean compare(Type t1, Type t2) {
		if (t1 instanceof ArrayType) {				
			if (!((ArrayType) t1).equals(t2)) {
				return false;
			}
		} else if (t1 instanceof LambdaType) {
			if (!((LambdaType) t1).equals(t2)) {
				return false;
			}
		} else {
			if (t1 != t2) {
				return false;
			}
		}
		
		return true;
	}

}
