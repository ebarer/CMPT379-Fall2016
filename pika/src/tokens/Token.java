package tokens;

import lexicalAnalyzer.Lextant;
import inputHandler.TextLocation;

public interface Token {
	public String getLexeme();
	public TextLocation getLocation();
	public String fullString();
	public boolean isLextant(Lextant ...lextants);
}

