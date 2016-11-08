package tokens;

import inputHandler.TextLocation;

public class FloatingToken extends TokenImp {
	protected double value;
	
	protected FloatingToken(TextLocation location, String lexeme) {
		super(location, lexeme);
	}
	protected void setValue(double value) {
		this.value = value;
	}
	public double getValue() {
		return value;
	}
	
	public static FloatingToken make(TextLocation location, String lexeme) {
		FloatingToken result = new FloatingToken(location, lexeme);
		result.setValue(Double.parseDouble(lexeme));
		return result;
	}
	
	@Override
	protected String rawString() {
		return "float, " + value;
	}
}
