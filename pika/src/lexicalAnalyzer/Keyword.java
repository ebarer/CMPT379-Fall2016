package lexicalAnalyzer;

import tokens.LextantToken;
import tokens.Token;


public enum Keyword implements Lextant {
	EXEC("exec"),
	
	// PRIMITIVE TYPES
	BOOL("bool"),
	TRUE("_true_"),
	FALSE("_false_"),
	CHAR("char"),
	STRING("string"),
	INT("int"),
	FLOAT("float"),
	RATIONAL("rat"),
	VOID("void"),
	
	// VARIABLE DECLARATION
	STATIC("static"),
	CONST("const"),
	VAR("var"),

	// ARRAY METHODS
	NEW("new"),
	CLONE("clone"),
	RELEASE("release"),
	LENGTH("length"),
	
	// STRING METHODS
	REVERSE("reverse"),
	
	// OPERATORS
	FOLD("fold"),
	MAP("map"),
	REDUCE("reduce"),
	ZIP("zip"),
	
	// FUNCTION METHODS
	FUNCTION("func"),
	CALL("call"),
	RETURN("return"),
	
	// CONTROL STRUCTURES
	IF("if"),
	ELSE("else"),
	WHILE("while"),
	FOR("for"),
	INDEX("index"),
	ELEMENT("elem"),
	OF("of"),
	CONTINUE("continue"),
	BREAK("break"),
	
	// PRINT
	PRINT("print"),
	NEWLINE("_n_"),
	TAB("_t_"),
	
	NULL_KEYWORD("");

	private String lexeme;
	private Token prototype;
	
	private Keyword(String lexeme) {
		this.lexeme = lexeme;
		this.prototype = LextantToken.make(null, lexeme, this);
	}
	public String getLexeme() {
		return lexeme;
	}
	public Token prototype() {
		return prototype;
	}
	
	public static Keyword forLexeme(String lexeme) {
		for(Keyword keyword: values()) {
			if(keyword.lexeme.equals(lexeme)) {
				return keyword;
			}
		}
		return NULL_KEYWORD;
	}
	public static boolean isAKeyword(String lexeme) {
		return forLexeme(lexeme) != NULL_KEYWORD;
	}
	
	public static Lextant[] getTypeLiteral() {
		Lextant[] literals = { BOOL, CHAR, STRING, INT, FLOAT, RATIONAL, VOID };
		return literals;
	}
	
	/*   the following hashtable lookup can replace the serial-search implementation of forLexeme() above. It is faster but less clear. 
	private static LexemeMap<Keyword> lexemeToKeyword = new LexemeMap<Keyword>(values(), NULL_KEYWORD);
	public static Keyword forLexeme(String lexeme) {
		return lexemeToKeyword.forLexeme(lexeme);
	}
	*/
}
