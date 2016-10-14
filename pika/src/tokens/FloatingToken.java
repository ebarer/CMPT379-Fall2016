package tokens;

import inputHandler.TextLocation;

public class FloatingToken extends TokenImp {
	protected float value;
	
	protected FloatingToken(TextLocation location, String lexeme) {
		super(location, lexeme);
	}
	protected void setValue(float value) {
		this.value = value;
	}
	public float getValue() {
		return value;
	}
	
	public static FloatingToken make(TextLocation location, String lexeme) {
		FloatingToken result = new FloatingToken(location, lexeme);
		result.setValue(Float.parseFloat(lexeme));
		return result;
	}
	
	@Override
	protected String rawString() {
		return "float, " + value;
	}
}
