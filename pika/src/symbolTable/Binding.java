package symbolTable;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import inputHandler.TextLocation;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;

public class Binding {
	private Type type;
	private Boolean isStatic = false;
	private Boolean mutable = null;
	private FunctionSignature signature = null;
	private String label = null;
	private TextLocation textLocation;
	private MemoryLocation memoryLocation;
	private String lexeme;
	
	public Binding(Type type, TextLocation location, MemoryLocation memoryLocation, String lexeme) {
		super();
		this.type = type;
		this.textLocation = location;
		this.memoryLocation = memoryLocation;
		this.lexeme = lexeme;
	}
	

	public String toString() {
		return "[" + lexeme +
				" " + type +  // " " + textLocation +	
				" " + memoryLocation +
				"]";
	}	
	public String getLexeme() {
		return lexeme;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Type getType() {
		return type;
	}
	public TextLocation getLocation() {
		return textLocation;
	}
	public MemoryLocation getMemoryLocation() {
		return memoryLocation;
	}
	public void generateAddress(ASMCodeFragment code) {
		memoryLocation.generateAddress(code, "%% " + lexeme);
	}
	
	public void setStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}
	public Boolean isStatic() {
		return this.isStatic;
	}
	
	public void setMutability(Boolean mutable) {
		this.mutable = mutable;
	}
	public Boolean isMutable() {
		return this.mutable;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	
	public void setSignature(FunctionSignature signature) {
		this.signature = signature;
	}
	public FunctionSignature getSignature() {
		return signature;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////
//Null Binding object
////////////////////////////////////////////////////////////////////////////////////

	public static Binding nullInstance() {
		return NullBinding.getInstance();
	}
	private static class NullBinding extends Binding {
		private static NullBinding instance=null;
		private NullBinding() {
			super(PrimitiveType.ERROR,
					TextLocation.nullInstance(),
					MemoryLocation.nullInstance(),
					"the-null-binding");
		}
		public static NullBinding getInstance() {
			if(instance==null)
				instance = new NullBinding();
			return instance;
		}
	}
}
