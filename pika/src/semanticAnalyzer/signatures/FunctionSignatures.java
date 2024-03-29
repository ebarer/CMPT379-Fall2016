package semanticAnalyzer.signatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import asmCodeGenerator.codeGenerator.cast.CastIntToCharSCG;
import asmCodeGenerator.codeGenerator.cast.CastRatToFloatSCG;
import asmCodeGenerator.codeGenerator.cast.CastToBoolSCG;
import asmCodeGenerator.codeGenerator.cast.CastToRatSCG;
import asmCodeGenerator.codeGenerator.rational.RationalBinaryOperatorSCG;
import asmCodeGenerator.codeGenerator.rational.RationalComparisonOperatorSCG;
import asmCodeGenerator.codeGenerator.string.CharStringConcatSCG;
import asmCodeGenerator.codeGenerator.string.StringCharConcatSCG;
import asmCodeGenerator.codeGenerator.string.StringConcatSCG;
import asmCodeGenerator.codeGenerator.string.StringLengthSCG;
import asmCodeGenerator.codeGenerator.string.StringOffsetSCG;
import asmCodeGenerator.codeGenerator.string.StringRangeOffsetSCG;
import asmCodeGenerator.codeStorage.ASMOpcode;
import semanticAnalyzer.types.ArrayType;
import semanticAnalyzer.types.Type;
import semanticAnalyzer.types.TypeLiteral;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Punctuator;
import static semanticAnalyzer.types.PrimitiveType.*;

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
	
	public int numAcceptingSignature(List<Type> types) {
		int count = 0;
		for(FunctionSignature functionSignature: this) {
			if(functionSignature.accepts(types)) {
				count++;
			}
		}
		return count;
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
	public static int numSignature(Object key, List<Type> types) {
		FunctionSignatures signatures = FunctionSignatures.signaturesOf(key);
		return signatures.numAcceptingSignature(types);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	// Put the signatures for operators in the following static block.
	
	static {
		// here's one example to get you started with FunctionSignatures: the signatures for addition.		
		// for this to work, you should statically import PrimitiveType.*

		// Arithmetic Operators
		new FunctionSignatures(Punctuator.ADD,
		    new FunctionSignature(ASMOpcode.Add, 				INTEGER, 	INTEGER, 	INTEGER),
		    new FunctionSignature(ASMOpcode.FAdd, 				FLOATING, 	FLOATING, 	FLOATING),
		    new FunctionSignature(new RationalBinaryOperatorSCG(Punctuator.ADD),
		    													RATIONAL,	RATIONAL, 	RATIONAL),
		    
		    new FunctionSignature(new StringConcatSCG(),		STRING, 	STRING, 	STRING),
		    new FunctionSignature(new StringCharConcatSCG(),	STRING, 	CHARACTER, 	STRING),
		    new FunctionSignature(new CharStringConcatSCG(),	CHARACTER, 	STRING, 	STRING)
		);
		
		new FunctionSignatures(Punctuator.SUBTRACT,
		    new FunctionSignature(ASMOpcode.Subtract, 	INTEGER, 	INTEGER, 	INTEGER),
		    new FunctionSignature(ASMOpcode.FSubtract, 	FLOATING, 	FLOATING, 	FLOATING),
		    new FunctionSignature(new RationalBinaryOperatorSCG(Punctuator.SUBTRACT), 
		    											RATIONAL, 	RATIONAL,	RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.MULTIPLY,
		    new FunctionSignature(ASMOpcode.Multiply, 	INTEGER, 	INTEGER, 	INTEGER),
		    new FunctionSignature(ASMOpcode.FMultiply, 	FLOATING, 	FLOATING, 	FLOATING),
		    new FunctionSignature(new RationalBinaryOperatorSCG(Punctuator.MULTIPLY), 
		    											RATIONAL, 	RATIONAL, 	RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.DIVISION,
		    new FunctionSignature(ASMOpcode.Divide, 	INTEGER, 	INTEGER, 	INTEGER),
		    new FunctionSignature(ASMOpcode.FDivide, 	FLOATING, 	FLOATING, 	FLOATING),
		    new FunctionSignature(new RationalBinaryOperatorSCG(Punctuator.DIVISION),
		    											RATIONAL, 	RATIONAL, 	RATIONAL)
		);
		
		
		// Rational Operators
		new FunctionSignatures(Punctuator.OVER,
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	RATIONAL)
		);
		
		new FunctionSignatures(Punctuator.EXPRESS_OVER,
		    new FunctionSignature(1, 	RATIONAL, 	INTEGER, 	INTEGER),
		    new FunctionSignature(1, 	FLOATING, 	INTEGER, 	INTEGER)
		);
		
		new FunctionSignatures(Punctuator.RATIONALIZE,
			new FunctionSignature(1, 	RATIONAL, 	INTEGER, 	RATIONAL),
			new FunctionSignature(1, 	FLOATING, 	INTEGER, 	RATIONAL)
		);
		
		
		// Casting Operators
		new FunctionSignatures(Punctuator.PIPE,
			new FunctionSignature(1, 						new ArrayType(), 	TypeLiteral.ARRAY, 		new ArrayType()),	// Variable type not working
			
			new FunctionSignature(1, 								CHARACTER, 	TypeLiteral.CHARACTER, 	CHARACTER),
			new FunctionSignature(new CastToBoolSCG(CHARACTER), 	CHARACTER, 	TypeLiteral.BOOLEAN, 	BOOLEAN),
			new FunctionSignature(1, 								CHARACTER, 	TypeLiteral.INTEGER, 	INTEGER),
			new FunctionSignature(new CastToRatSCG(CHARACTER), 		CHARACTER, 	TypeLiteral.RATIONAL, 	RATIONAL),
			
			new FunctionSignature(1, 								INTEGER, 	TypeLiteral.INTEGER, 	INTEGER),
			new FunctionSignature(new CastToBoolSCG(INTEGER), 		INTEGER, 	TypeLiteral.BOOLEAN, 	BOOLEAN),
		    new FunctionSignature(new CastIntToCharSCG(INTEGER), 	INTEGER, 	TypeLiteral.CHARACTER, 	CHARACTER),
		    new FunctionSignature(new CastToRatSCG(INTEGER), 		INTEGER, 	TypeLiteral.RATIONAL, 	RATIONAL),
		    new FunctionSignature(ASMOpcode.ConvertF, 				INTEGER, 	TypeLiteral.FLOATING, 	FLOATING),
		    
		    new FunctionSignature(1, 								FLOATING, 	TypeLiteral.FLOATING, 	FLOATING),
		    new FunctionSignature(ASMOpcode.ConvertI, 				FLOATING, 	TypeLiteral.INTEGER, 	INTEGER),
		    new FunctionSignature(new CastToRatSCG(FLOATING), 		FLOATING, 	TypeLiteral.RATIONAL, 	RATIONAL),
		    
		    new FunctionSignature(1, 								RATIONAL, 	TypeLiteral.RATIONAL, 	RATIONAL),
		    new FunctionSignature(new CastRatToFloatSCG(RATIONAL), 	RATIONAL, 	TypeLiteral.FLOATING, 	FLOATING),
		    new FunctionSignature(ASMOpcode.Divide, 				RATIONAL, 	TypeLiteral.INTEGER, 	INTEGER),
		    
		    new FunctionSignature(1, 								BOOLEAN, 	TypeLiteral.BOOLEAN, 	BOOLEAN),
		    
		    new FunctionSignature(1, 								STRING, 	TypeLiteral.STRING, 	STRING)
		);
		
		
		// Comparison Operators
		new FunctionSignatures(Punctuator.LESS_OR_EQUAL,
			new FunctionSignature(1, 	CHARACTER, 	CHARACTER, 	BOOLEAN),
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	BOOLEAN),
		    new FunctionSignature(1, 	FLOATING, 	FLOATING, 	BOOLEAN),
		    new FunctionSignature(new RationalComparisonOperatorSCG(), 
										RATIONAL, 	RATIONAL,	BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.LESS,
			new FunctionSignature(1, 	CHARACTER, 	CHARACTER, 	BOOLEAN),
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	BOOLEAN),
		    new FunctionSignature(1, 	FLOATING, 	FLOATING, 	BOOLEAN),
		    new FunctionSignature(new RationalComparisonOperatorSCG(), 
					RATIONAL, 	RATIONAL,	BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.GREATER,
			new FunctionSignature(1, 	CHARACTER, 	CHARACTER, 	BOOLEAN),
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	BOOLEAN),
		    new FunctionSignature(1, 	FLOATING, 	FLOATING, 	BOOLEAN),
		    new FunctionSignature(new RationalComparisonOperatorSCG(), 
					RATIONAL, 	RATIONAL,	BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.GREATER_OR_EQUAL,
			new FunctionSignature(1, 	CHARACTER, 	CHARACTER, 	BOOLEAN),
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	BOOLEAN),
		    new FunctionSignature(1, 	FLOATING, 	FLOATING, 	BOOLEAN),
		    new FunctionSignature(new RationalComparisonOperatorSCG(), 
					RATIONAL, 	RATIONAL,	BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.EQUAL,
			new FunctionSignature(1,	new ArrayType(), 	new ArrayType(), 	BOOLEAN), 	// Variable type not working
			new FunctionSignature(1, 	BOOLEAN, 	BOOLEAN, 	BOOLEAN),
			new FunctionSignature(1, 	CHARACTER, 	CHARACTER, 	BOOLEAN),
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	BOOLEAN),
		    new FunctionSignature(1,	FLOATING, 	FLOATING, 	BOOLEAN),
		    new FunctionSignature(new RationalComparisonOperatorSCG(), 
					RATIONAL, 	RATIONAL,	BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.NOT_EQUAL,
			new FunctionSignature(1,	new ArrayType(), 	new ArrayType(), 	BOOLEAN), 	// Variable type not working
			new FunctionSignature(1,	BOOLEAN, 	BOOLEAN, 	BOOLEAN),
			new FunctionSignature(1, 	CHARACTER, 	CHARACTER, 	BOOLEAN),
		    new FunctionSignature(1, 	INTEGER, 	INTEGER, 	BOOLEAN),
		    new FunctionSignature(1, 	FLOATING, 	FLOATING, 	BOOLEAN),
		    new FunctionSignature(new RationalComparisonOperatorSCG(), 
					RATIONAL, 	RATIONAL,	BOOLEAN)
		);
		
		
		// Boolean and Unary Operators
		new FunctionSignatures(Punctuator.AND,
		    new FunctionSignature(ASMOpcode.And, 	 BOOLEAN, BOOLEAN, BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.OR,
			new FunctionSignature(ASMOpcode.Or,  	 BOOLEAN, BOOLEAN, BOOLEAN)
		);
		
		new FunctionSignatures(Punctuator.NOT,
			new FunctionSignature(ASMOpcode.BNegate, BOOLEAN, BOOLEAN)
		);

		new FunctionSignatures(Keyword.LENGTH,
			new FunctionSignature(new StringLengthSCG(), 		STRING, 	INTEGER)
		);
		
		new FunctionSignatures(Punctuator.OPEN_BRACKET,
			new FunctionSignature(new StringOffsetSCG(), 		STRING,		INTEGER,				CHARACTER),
			new FunctionSignature(new StringRangeOffsetSCG(), 	STRING,		INTEGER, 	INTEGER, 	STRING)
		);
		
		
		// Assignment Operators
		new FunctionSignatures(Punctuator.ASSIGN,
			new FunctionSignature(1,	BOOLEAN, 	BOOLEAN,	BOOLEAN),
			new FunctionSignature(1,	CHARACTER, 	CHARACTER,	CHARACTER),
			new FunctionSignature(1,	STRING, 	STRING,		STRING),
			new FunctionSignature(1,	INTEGER, 	INTEGER,	INTEGER),
			new FunctionSignature(1,	FLOATING, 	FLOATING,	FLOATING),
			new FunctionSignature(1,	RATIONAL, 	RATIONAL,	RATIONAL),
			new FunctionSignature(1,	ANY, 		ANY,		ANY)
		);

	}

}
