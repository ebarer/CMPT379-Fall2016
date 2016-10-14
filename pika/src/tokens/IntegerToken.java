package tokens;

import inputHandler.TextLocation;

public class IntegerToken extends TokenImp {
	protected int value;
	
	protected IntegerToken(TextLocation location, String lexeme) {
		super(location, lexeme);
	}
	protected void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	
	public static IntegerToken make(TextLocation location, String lexeme) {
		IntegerToken result = new IntegerToken(location, lexeme);
		result.setValue(Integer.parseInt(lexeme));
		return result;
	}
	
	@Override
	protected String rawString() {
		return "integer, " + value;
	}
}
