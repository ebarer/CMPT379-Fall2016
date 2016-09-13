package tokens;

import inputHandler.TextLocation;

public class NullToken extends TokenImp {

	protected NullToken(TextLocation location, String lexeme) {
		super(location, lexeme);
	}

	@Override
	protected String rawString() {
		return "END OF INPUT";
	}
	
	public static NullToken make(TextLocation location) {
		NullToken result = new NullToken(location, "");
		return result;
	}
}
