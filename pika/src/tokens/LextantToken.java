package tokens;

import lexicalAnalyzer.Lextant;
import inputHandler.TextLocation;

public final class LextantToken extends TokenImp {

	private Lextant lextant;
	
	private LextantToken(TextLocation location, String lexeme, Lextant lextant) {
		super(location, lexeme);
		this.lextant = lextant;
	}
	
	public Lextant getLextant() {
		return lextant;
	}
	public boolean isLextant(Lextant ...lextants) {
		for(Lextant lextant: lextants) {
			if(this.lextant == lextant)
				return true;
		}
		return false;
	}
	protected String rawString() {
		return lextant.toString();
	}
	
	
	public static LextantToken make(TextLocation location, String lexeme, Lextant lextant) {
		return new LextantToken(location, lexeme, lextant);
	}
}
