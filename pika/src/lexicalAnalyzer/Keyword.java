package lexicalAnalyzer;

import tokens.LextantToken;
import tokens.Token;


public enum Keyword implements Lextant {
	EXEC("exec"),

	CONST("const"),
	VAR("var"),

	NEW("new"),
	CLONE("clone"),
	RELEASE("release"),
	LENGTH("length"),
	
	IF("if"),
	ELSE("else"),
	WHILE("while"),
	
	BOOL("bool"),
	TRUE("_true_"),
	FALSE("_false_"),
	CHAR("char"),
	STRING("string"),
	INT("int"),
	FLOAT("float"),
	RATIONAL("rat"),
	
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
	
	/*   the following hashtable lookup can replace the serial-search implementation of forLexeme() above. It is faster but less clear. 
	private static LexemeMap<Keyword> lexemeToKeyword = new LexemeMap<Keyword>(values(), NULL_KEYWORD);
	public static Keyword forLexeme(String lexeme) {
		return lexemeToKeyword.forLexeme(lexeme);
	}
	*/
}
