package tokens;

import inputHandler.TextLocation;

public class IdentifierToken extends TokenImp {
	protected IdentifierToken(TextLocation location, String lexeme) {
		super(location, lexeme.intern());
	}
	
	public static IdentifierToken make(TextLocation location, String lexeme) {
		IdentifierToken result = new IdentifierToken(location, lexeme);
		return result;
	}


	@Override
	protected String rawString() {
		return "identifier, " + getLexeme();
	}
}
