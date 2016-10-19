package semanticAnalyzer.signatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import asmCodeGenerator.codeStorage.ASMOpcode;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import semanticAnalyzer.types.TypeLiteral;
import lexicalAnalyzer.Punctuator;


public class FunctionSignatures extends ArrayList<FunctionSignature> {
	private static final long serialVersionUID = -4907792488209670697L;
	private static Map<Object, FunctionSignatures> signaturesForKey = new HashMap<Object, FunctionSignatures>();
	
	Object key;
	
	public FunctionSignatures(Object key, FunctionSignature ...functionSignatures) {
		this.key = key;
		for(FunctionSignature functionSignature: functionSignatures) {
			add(functionSignature);
		}
		signaturesForKey.put(key, this);
	}
	
	public Object getKey() {
		return key;
	}
	public boolean hasKey(Object key) {
		return this.key.equals(key);
	}
	
	public FunctionSignature acceptingSignature(List<Type> types) {
		for(FunctionSignature functionSignature: this) {
			if(functionSignature.accepts(types)) {
				return functionSignature;
			}
		}
		return FunctionSignature.nullInstance();
	}
	public boolean accepts(List<Type> types) {
		return !acceptingSignature(types).isNull();
	}

	
	/////////////////////////////////////////////////////////////////////////////////
	// access to FunctionSignatures by key object.
	
	public static FunctionSignatures nullSignatures = new FunctionSignatures(0, FunctionSignature.nullInstance());

	public static FunctionSignatures signaturesOf(Object key) {
		if(signaturesForKey.containsKey(key)) {
			return signaturesForKey.get(key);
		}
		return nullSignatures;
	}
	public static FunctionSignature signature(Object key, List<Type> types) {
		FunctionSignatures signatures = FunctionSignatures.signaturesOf(key);
		return signatures.acceptingSignature(types);
	}

	
	///////////////////////////////////////////////////////////////////
	// Signatures for pika-0 operators
	// this section will probably disappear in pika-1 (in favor of FunctionSignatures)
	//private static FunctionSignature addSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	//private static FunctionSignature multiplySignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER);
	//private static FunctionSignature greaterSignature = new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN);
	
	
	/////////////////////////////////////////////////////////////////////////////////
	// Put the signatures for operators in the following static block.
	
	static {
		// here's one example to get you started with FunctionSignatures: the signatures for addition.		
		// for this to work, you should statically import PrimitiveType.*

		// Arithmetic Operators
		new FunctionSignatures(Punctuator.ADD,
		    new FunctionSignature(ASMOpcode.Add, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
		    new FunctionSignature(ASMOpcode.FAdd, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.FLOATING),
		    new FunctionSignature(new ASMOpcode[] {ASMOpcode.Add, ASMOpcode.Add}, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.SUBTRACT,
		    new FunctionSignature(ASMOpcode.Subtract, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
		    new FunctionSignature(ASMOpcode.FSubtract, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.FLOATING),
		    new FunctionSignature(new ASMOpcode[] {ASMOpcode.Subtract, ASMOpcode.Subtract}, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.MULTIPLY,
		    new FunctionSignature(ASMOpcode.Multiply, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
		    new FunctionSignature(ASMOpcode.FMultiply, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.FLOATING),
		    new FunctionSignature(new ASMOpcode[] {ASMOpcode.Multiply, ASMOpcode.Multiply}, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.DIVISION,
		    new FunctionSignature(ASMOpcode.Divide, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.INTEGER),
		    new FunctionSignature(ASMOpcode.FDivide, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.FLOATING),
		    new FunctionSignature(new ASMOpcode[] {ASMOpcode.Divide, ASMOpcode.Divide}, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL, PrimitiveType.RATIONAL)
		);
		
		
		// Rational Operators
		new FunctionSignatures(Punctuator.OVER,
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.EXPRESS_OVER,
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.RATIONALIZE,
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.RATIONAL)
		);
		
		
		// Comparison Operators
		new FunctionSignatures(Punctuator.LESS_OR_EQUAL,
			new FunctionSignature(1, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.LESS,
			new FunctionSignature(1, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.GREATER,
			new FunctionSignature(1, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.GREATER_OR_EQUAL,
			new FunctionSignature(1, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.EQUAL,
			new FunctionSignature(1, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN),
			new FunctionSignature(1, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.NOT_EQUAL,
			new FunctionSignature(1, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN),
			new FunctionSignature(1, PrimitiveType.CHARACTER, PrimitiveType.CHARACTER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.INTEGER, PrimitiveType.INTEGER, PrimitiveType.BOOLEAN),
		    new FunctionSignature(1, PrimitiveType.FLOATING, PrimitiveType.FLOATING, PrimitiveType.BOOLEAN)
		);
		
		
		// Boolean Operators
		new FunctionSignatures(Punctuator.AND,
		    new FunctionSignature(ASMOpcode.And, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.OR,
				new FunctionSignature(ASMOpcode.Or, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.NOT,
			new FunctionSignature(ASMOpcode.BNegate, PrimitiveType.BOOLEAN, PrimitiveType.BOOLEAN)
		);
		
		
		// Casting Operators
		new FunctionSignatures(Punctuator.PIPE,
			new FunctionSignature(ASMOpcode.Nop, PrimitiveType.CHARACTER, TypeLiteral.CHARACTER, PrimitiveType.CHARACTER),
			new FunctionSignature("toBool", PrimitiveType.CHARACTER, TypeLiteral.BOOLEAN, PrimitiveType.BOOLEAN),
			new FunctionSignature(ASMOpcode.Nop, PrimitiveType.CHARACTER, TypeLiteral.INTEGER, PrimitiveType.INTEGER),
			new FunctionSignature(ASMOpcode.Nop, PrimitiveType.CHARACTER, TypeLiteral.RATIONAL, PrimitiveType.RATIONAL),
			
			new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, TypeLiteral.INTEGER, PrimitiveType.INTEGER),
			new FunctionSignature("toBool", PrimitiveType.INTEGER, TypeLiteral.BOOLEAN, PrimitiveType.BOOLEAN),
		    new FunctionSignature("intToChar", PrimitiveType.INTEGER, TypeLiteral.CHARACTER, PrimitiveType.CHARACTER),
		    new FunctionSignature(ASMOpcode.Nop, PrimitiveType.INTEGER, TypeLiteral.RATIONAL, PrimitiveType.RATIONAL),
		    new FunctionSignature(ASMOpcode.ConvertF, PrimitiveType.INTEGER, TypeLiteral.FLOATING, PrimitiveType.FLOATING),
		    
		    new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOATING, TypeLiteral.FLOATING, PrimitiveType.FLOATING),
		    new FunctionSignature(ASMOpcode.ConvertI, PrimitiveType.FLOATING, TypeLiteral.INTEGER, PrimitiveType.INTEGER),
		    new FunctionSignature(ASMOpcode.Nop, PrimitiveType.FLOATING, TypeLiteral.RATIONAL, PrimitiveType.RATIONAL),
		    
		    new FunctionSignature(ASMOpcode.Nop, PrimitiveType.RATIONAL, TypeLiteral.RATIONAL, PrimitiveType.RATIONAL),
		    new FunctionSignature(new ASMOpcode[] {ASMOpcode.ConvertF, ASMOpcode.ConvertF, ASMOpcode.FDivide},
		    						PrimitiveType.RATIONAL, TypeLiteral.FLOATING, PrimitiveType.FLOATING),
		    new FunctionSignature(ASMOpcode.Divide, PrimitiveType.RATIONAL, TypeLiteral.INTEGER, PrimitiveType.INTEGER),
		    
		    new FunctionSignature(ASMOpcode.Nop, PrimitiveType.BOOLEAN, TypeLiteral.BOOLEAN, PrimitiveType.BOOLEAN),
		    
		    new FunctionSignature(ASMOpcode.Nop, PrimitiveType.STRING, TypeLiteral.STRING, PrimitiveType.STRING)
		);
		
		// new FunctionSignature(::, new ArrayType(T), T, PrimitiveType.INTEGER);
		
		
		// First, we use the operator itself (in this case the Punctuator ADD) as the key.
		// Then, we give that key two signatures: one an (INT x INT -> INT) and the other
		// a (FLOAT x FLOAT -> FLOAT).  Each signature has a "whichVariant" parameter where
		// I'm placing the instruction (ASMOpcode) that needs to be executed.
		//
		// I'll follow the convention that if a signature has an ASMOpcode for its whichVariant,
		// then to generate code for the operation, one only needs to generate the code for
		// the operands (in order) and then add to that the Opcode.  For instance, the code for
		// floating addition should look like:
		//
		//		(generate argument 1)	: may be many instructions
		//		(generate argument 2)   : ditto
		//		FAdd					: just one instruction
		//
		// If the code that an operator should generate is more complicated than this, then
		// I will not use an ASMOpcode for the whichVariant.  In these cases I typically use
		// a small object with one method (the "Command" design pattern) that generates the
		// required code.

	}

}
