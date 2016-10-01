package lexicalAnalyzer;


import logging.PikaLogger;

import inputHandler.InputHandler;
import inputHandler.LocatedChar;
import inputHandler.LocatedCharStream;
import inputHandler.PushbackCharStream;
import tokens.IdentifierToken;
import tokens.LextantToken;
import tokens.NullToken;
import tokens.CharacterToken;
import tokens.StringToken;
import tokens.IntegerToken;
import tokens.FloatingToken;
import tokens.Token;

import static lexicalAnalyzer.PunctuatorScanningAids.*;

import java.util.logging.Level;

public class LexicalAnalyzer extends ScannerImp implements Scanner {
	public static LexicalAnalyzer make(String filename) {
		InputHandler handler = InputHandler.fromFilename(filename);
		PushbackCharStream charStream = PushbackCharStream.make(handler);
		return new LexicalAnalyzer(charStream);
	}

	public LexicalAnalyzer(PushbackCharStream input) {
		super(input);
	}

	
	//////////////////////////////////////////////////////////////////////////////
	// Token-finding main dispatch	

	@Override
	protected Token findNextToken() {
		LocatedChar ch = nextNonWhitespaceChar();

		if(ch.getCharacter() == '#') {
			return scanComment(ch);
		}
		else if(ch.getCharacter() == '^') {
			return scanCharacter(ch);
		}
		else if(ch.getCharacter() == '"') {
			return scanString(ch);
		}
		else if(ch.isDigit()) {
			return scanNumber(ch);
		}
		else if(ch.isSign()) {
			return scanSignedNumber(ch);
		}
		else if(ch.isLowerCase() || ch.isUpperCase()) {
			return scanIdentifier(ch);
		}
		else if(isPunctuatorStart(ch)) {
			return PunctuatorScanner.scan(ch, input);
		}
		else if(isEndOfInput(ch)) {
			return NullToken.make(ch.getLocation());
		}
		else {
			lexicalError(ch);
			return findNextToken();
		}
	}


	private LocatedChar nextNonWhitespaceChar() {
		LocatedChar ch = input.next();
		while(ch.isWhitespace()) {
			ch = input.next();
		}
		return ch;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Comment lexical analysis
	private Token scanComment(LocatedChar ch) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(ch.getCharacter());
		
		LocatedChar c = input.next();
		while (c.getCharacter() != '#' && c.getCharacter() != '\n') {
			buffer.append(c.getCharacter());
			c = input.next();
		}
		
		return findNextToken();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Character and String lexical analysis
	// TASK: Finish character and string lexical analysis
	private Token scanCharacter(LocatedChar ch) {
		LocatedChar aChar = input.next();
		LocatedChar c = input.next();
		if (!aChar.isASCII() || c.getCharacter() != '^') {
			lexicalError(c);
			return findNextToken();
		}

		return CharacterToken.make(ch.getLocation(), aChar.getCharacter().toString());
	}
	
	private Token scanString(LocatedChar ch) {
		StringBuffer buffer = new StringBuffer();	
		
		LocatedChar c = input.next();
		while (c.getCharacter() != '"') {
			if (c.getCharacter() == '\n') {
				lexicalError(c);
				return findNextToken();
			}
			
			buffer.append(c.getCharacter());
			c = input.next();
		}
		
		return StringToken.make(ch.getLocation(), buffer.toString());
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Integer lexical analysis	
	
	private Token scanSignedNumber(LocatedChar ch) {
		LocatedChar next = input.peek();
		if(next.isDigit() || next.getCharacter() == '.') {
			return scanNumber(ch);	
		} else {
			return PunctuatorScanner.scan(ch, input);
		}
	}

	private Token scanNumber(LocatedChar ch) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(ch.getCharacter());
		
		appendSubsequentDigits(buffer);
		
		// If next character is not a period, or if next character is a period
		// but is not followed by a digit, return INTEGER
		LocatedChar c = input.next();
		if (c.getCharacter() != '.' || !(input.peek().isDigit())) {
			input.pushback(c);
			return IntegerToken.make(ch.getLocation(), buffer.toString());
		}
		buffer.append(c.getCharacter());
		
		// If next character is not a digit, potentially invalid lexeme
		c = input.next();
		if (c.isDigit()) {
			buffer.append(c.getCharacter());
			appendSubsequentDigits(buffer);
			c = input.next();
		}
		
		if (c.getCharacter() == 'E') {
			LocatedChar c2 = input.next();
			if (c2.isSign() || c2.isDigit()) {
				buffer.append(c.getCharacter());
				buffer.append(c2.getCharacter());
				appendSubsequentDigits(buffer);
			} else {
				input.pushback(c2);
				input.pushback(c);
			}
		} else {
			input.pushback(c);
		}
		
		return FloatingToken.make(ch.getLocation(), buffer.toString());
	}
	private void appendSubsequentDigits(StringBuffer buffer) {
		LocatedChar c = input.next();
		while(c.isDigit()) {
			buffer.append(c.getCharacter());
			c = input.next();
		}
		input.pushback(c);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Identifier and keyword lexical analysis	

	private Token scanIdentifier(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		if (firstChar.isLowerCase() || firstChar.isUpperCase() || firstChar.getCharacter() == '_') {
			buffer.append(firstChar.getCharacter());
			appendSubsequentCharacters(buffer);
	
			String lexeme = buffer.toString();
			// If identifier exceeds 32 characters,  
			if(lexeme.length() > 32) {
				// Custom lexical error for more-useful message
				PikaLogger log = PikaLogger.getLogger("compiler.lexicalAnalyzer");
				log.log(Level.WARNING, "Lexical error: variable name greater than 32 characters " + firstChar);
				return findNextToken();
			}
			
			if(Keyword.isAKeyword(lexeme)) {
				return LextantToken.make(firstChar.getLocation(), lexeme, Keyword.forLexeme(lexeme));
			}
			else {
				return IdentifierToken.make(firstChar.getLocation(), lexeme);
			}
		} else {
			lexicalError(firstChar);
			return findNextToken();
		}
	}
	private void appendSubsequentCharacters(StringBuffer buffer) {
		LocatedChar c = input.next();
		while(c.isLowerCase() || c.isUpperCase() || c.getCharacter() == '_' || c.getCharacter() == '$' || c.isDigit()) {
			buffer.append(c.getCharacter());
			c = input.next();
		}
		input.pushback(c);
	}

	

	//////////////////////////////////////////////////////////////////////////////
	// Character-classification routines specific to Pika scanning.	

	private boolean isPunctuatorStart(LocatedChar lc) {
		char c = lc.getCharacter();
		return isPunctuatorStartingCharacter(c);
	}

	private boolean isEndOfInput(LocatedChar lc) {
		return lc == LocatedCharStream.FLAG_END_OF_INPUT;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Error-reporting	

	private void lexicalError(LocatedChar ch) {
		PikaLogger log = PikaLogger.getLogger("compiler.lexicalAnalyzer");
		log.severe("Lexical error: invalid character " + ch);
	}

	
}
