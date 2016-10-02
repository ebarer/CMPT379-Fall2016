package lexicalAnalyzer;

import java.util.Arrays;
import java.util.List;
import tokens.LextantToken;
import tokens.Token;


public enum Punctuator implements Lextant {
	SPACE(";"),
	SEPARATOR(","),
	TERMINATOR("."),
	
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	DIVISION("/"),
	
	LESS_OR_EQUAL("<="),
	LESS("<"),
	EQUAL("=="),
	NOT_EQUAL("!="),
	GREATER(">"),
	GREATER_OR_EQUAL(">="),
	
	OPEN_BRACE("{"),
	CLOSE_BRACE("}"),
	OPEN_PARENTHESIS("("),
	CLOSE_PARENTHESIS(")"),
	OPEN_BRACKET("["),
	CLOSE_BRACKET("]"),
	
	PIPE("|"),
	HASH("#"),
	ASSIGN(":="),
	
	NULL_PUNCTUATOR("");

	private String lexeme;
	private Token prototype;
	
	private Punctuator(String lexeme) {
		this.lexeme = lexeme;
		this.prototype = LextantToken.make(null, lexeme, this);
	}
	public String getLexeme() {
		return lexeme;
	}
	public Token prototype() {
		return prototype;
	}
	
	//   the following hashtable lookup can replace the implementation of forLexeme above. It is faster but less clear. 
	private static LexemeMap<Punctuator> lexemeToPunctuator = new LexemeMap<Punctuator>(values(), NULL_PUNCTUATOR);
	public static Punctuator forLexeme(String lexeme) {
		   return lexemeToPunctuator.forLexeme(lexeme);
	}
	
	public static Lextant[] getComparators() {
		Lextant[] comparators = { LESS_OR_EQUAL, LESS, EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL };
		return comparators;
	}
	
//	public static Punctuator forLexeme(String lexeme) {
//	for(Punctuator punctuator: values()) {
//		if(punctuator.lexeme.equals(lexeme)) {
//			return punctuator;
//		}
//	}
//	return NULL_PUNCTUATOR;
//}
	
}


