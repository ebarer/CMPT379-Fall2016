package tokens;

import inputHandler.TextLocation;

public class NumberToken extends TokenImp {
	protected int value;
	
	protected NumberToken(TextLocation location, String lexeme) {
		super(location, lexeme);
	}
	protected void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	
	public static NumberToken make(TextLocation location, String lexeme) {
		NumberToken result = new NumberToken(location, lexeme);
		result.setValue(Integer.parseInt(lexeme));
		return result;
	}
	
	@Override
	protected String rawString() {
		return "number, " + value;
	}
}
