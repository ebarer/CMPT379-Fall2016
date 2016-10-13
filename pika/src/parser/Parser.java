package parser;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import logging.PikaLogger;
import parseTree.*;
import parseTree.nodeTypes.AssignmentNode;
import parseTree.nodeTypes.BinaryOperatorNode;
import parseTree.nodeTypes.BlockNode;
import parseTree.nodeTypes.BooleanConstantNode;
import parseTree.nodeTypes.CastNode;
import parseTree.nodeTypes.MainBlockNode;
import parseTree.nodeTypes.DeclarationNode;
import parseTree.nodeTypes.ErrorNode;
import parseTree.nodeTypes.IdentifierNode;
import parseTree.nodeTypes.IfNode;
import parseTree.nodeTypes.CharacterNode;
import parseTree.nodeTypes.StringNode;
import parseTree.nodeTypes.IntegerConstantNode;
import parseTree.nodeTypes.FloatingConstantNode;
import parseTree.nodeTypes.NewlineNode;
import parseTree.nodeTypes.PrintStatementNode;
import parseTree.nodeTypes.ProgramNode;
import parseTree.nodeTypes.SpaceNode;
import parseTree.nodeTypes.TabNode;
import parseTree.nodeTypes.UnaryOperatorNode;
import semanticAnalyzer.types.PrimitiveType;
import tokens.*;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import lexicalAnalyzer.Scanner;


public class Parser {
	private Scanner scanner;
	private Token nowReading;
	private Token previouslyRead;
	
	public static ParseNode parse(Scanner scanner) {
		Parser parser = new Parser(scanner);
		return parser.parse();
	}
	public Parser(Scanner scanner) {
		super();
		this.scanner = scanner;
	}
	
	public ParseNode parse() {
		readToken();
		return parseProgram();
	}

	////////////////////////////////////////////////////////////
	// "program" is the start symbol S
	// S -> EXEC mainBlock
	
	private ParseNode parseProgram() {
		if(!startsProgram(nowReading)) {
			return syntaxErrorNode("program");
		}
		ParseNode program = new ProgramNode(nowReading);
		
		expect(Keyword.EXEC);
		ParseNode mainBlock = parseMainBlock();
		program.appendChild(mainBlock);
		
		if(!(nowReading instanceof NullToken)) {
			return syntaxErrorNode("end of program");
		}
		
		return program;
	}
	private boolean startsProgram(Token token) {
		return token.isLextant(Keyword.EXEC);
	}
	
	
	///////////////////////////////////////////////////////////
	// mainBlock
	
	// mainBlock -> { statement* }
	private ParseNode parseMainBlock() {
		if(!startsMainBlock(nowReading)) {
			return syntaxErrorNode("mainBlock");
		}
		ParseNode mainBlock = new MainBlockNode(nowReading);
		expect(Punctuator.OPEN_BRACE);
		
		while(startsStatement(nowReading)) {
			if (startsBlockStatement(nowReading)) {
				ParseNode blockStatement = parseBlockStatement();
				mainBlock.appendChild(blockStatement);
			} else {
				ParseNode statement = parseStatement();
				mainBlock.appendChild(statement);
			}
		}
		
		expect(Punctuator.CLOSE_BRACE);
		return mainBlock;
	}
	private boolean startsMainBlock(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACE);
	}
	
	///////////////////////////////////////////////////////////
	// blockStatement
	
	// blockStatement -> { statement* }
	private ParseNode parseBlockStatement() {
		if(!startsBlockStatement(nowReading)) {
			return syntaxErrorNode("blockStatement");
		}
		ParseNode blockStatement = new BlockNode(nowReading);
		expect(Punctuator.OPEN_BRACE);
		
		while(startsStatement(nowReading)) {
			ParseNode statement = parseStatement();
			blockStatement.appendChild(statement);
		}
		
		expect(Punctuator.CLOSE_BRACE);
		return blockStatement;
	}
	private boolean startsBlockStatement(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACE);
	}
	
	
	
	
	///////////////////////////////////////////////////////////
	// statements
	
	// statement-> declaration | printStmt
	private ParseNode parseStatement() {
		if(!startsStatement(nowReading)) {
			return syntaxErrorNode("statement");
		}
		if(startsDeclaration(nowReading)) {
			return parseDeclaration();
		}
		if(startsIfStatement(nowReading)) {
			return parseIfStatement();
		}
		if(startsAssignment(nowReading)) {
			return parseAssignment();
		}
		if(startsPrintStatement(nowReading)) {
			return parsePrintStatement();
		}
		return syntaxErrorNode("statement");
	}
	private boolean startsStatement(Token token) {
		return startsDeclaration(token) ||
			   startsAssignment(token) ||
			   startsIfStatement(token) ||
			   //startsWhileStatement(token) ||
			   startsPrintStatement(token) ||
			   startsBlockStatement(token);
	}
	
	// declaration -> CONST identifier := expression .
	private ParseNode parseDeclaration() {
		if(!startsDeclaration(nowReading)) {
			return syntaxErrorNode("declaration");
		}
		Token declarationToken = nowReading;
		readToken();
		
		// If declared as VAR, define identifier as mutable
		ParseNode identifier = parseIdentifier();
		
		expect(Punctuator.ASSIGN);
		
		ParseNode initializer = parseExpression();
		
		expect(Punctuator.TERMINATOR);
		
		return DeclarationNode.withChildren(declarationToken, identifier, initializer);
	}
	private boolean startsDeclaration(Token token) {		
		return token.isLextant(Keyword.CONST, Keyword.VAR);
	}
	

	// assignment -> target := expression .
	private ParseNode parseAssignment() {
		if(!startsAssignment(nowReading)) {
			return syntaxErrorNode("assignment");
		}
		
		ParseNode target = parseIdentifier();		
		expect(Punctuator.ASSIGN);
		Token assignmentToken = Punctuator.ASSIGN.prototype();
		ParseNode expression = parseExpression();
		
		expect(Punctuator.TERMINATOR);
		
		return AssignmentNode.withChildren(assignmentToken, target, expression);
	}
	private boolean startsAssignment(Token token) {
		return token instanceof IdentifierToken;
	}
	
	
	// if (expression) blockStatement (else blockStatement)?
	private ParseNode parseIfStatement() {
		if(!startsIfStatement(nowReading)) {
			return syntaxErrorNode("if-statement");
		}
		
		expect(Keyword.IF);

		Token ifToken = previouslyRead;
		ParseNode condition = parseExpression();
		ParseNode blockStatement = parseBlockStatement();
		
		// Check for ELSE clause
		if (startsElseStatement(nowReading)) {
			expect(Keyword.ELSE);
			ParseNode elseStatement = parseBlockStatement();
			return IfNode.withElse(ifToken, condition, blockStatement, elseStatement);
		} else {
			ParseNode ifNode = new IfNode(ifToken, condition, blockStatement);	
			return ifNode;
		}
	}
	private boolean startsIfStatement(Token token) {
		return token.isLextant(Keyword.IF);
	}
	private boolean startsElseStatement(Token token) {
		return token.isLextant(Keyword.ELSE);
	}
	
	
	// printStmt -> PRINT printExpressionList .
	private ParseNode parsePrintStatement() {
		if(!startsPrintStatement(nowReading)) {
			return syntaxErrorNode("print statement");
		}
		PrintStatementNode result = new PrintStatementNode(nowReading);
		
		readToken();
		result = parsePrintExpressionList(result);
		
		expect(Punctuator.TERMINATOR);
		return result;
	}
	private boolean startsPrintStatement(Token token) {
		return token.isLextant(Keyword.PRINT);
	}	

	// This adds the printExpressions it parses to the children of the given parent
	// printExpressionList -> printExpression* bowtie (,|;)  (note that this is nullable)
	private PrintStatementNode parsePrintExpressionList(PrintStatementNode parent) {
		while(startsPrintExpression(nowReading) || startsPrintSeparator(nowReading)) {
			parsePrintExpression(parent);
			parsePrintSeparator(parent);
		}
		return parent;
	}
	

	// This adds the printExpression it parses to the children of the given parent
	// printExpression -> (expr | nl)?     (nullable)
	
	private void parsePrintExpression(PrintStatementNode parent) {
		if(startsExpression(nowReading)) {
			ParseNode child = parseExpression();
			parent.appendChild(child);
		}
		else if (nowReading.isLextant(Keyword.TAB)) {
			readToken();
			ParseNode child = new TabNode(previouslyRead);
			parent.appendChild(child);
		}
		else if(nowReading.isLextant(Keyword.NEWLINE)) {
			readToken();
			ParseNode child = new NewlineNode(previouslyRead);
			parent.appendChild(child);
		}
		// else we interpret the printExpression as epsilon, and do nothing
	}
	private boolean startsPrintExpression(Token token) {
		return startsExpression(token) || token.isLextant(Keyword.TAB) || token.isLextant(Keyword.NEWLINE) ;
	}
	
	
	// This adds the printExpression it parses to the children of the given parent
	// printExpression -> expr? ,? nl? 
	
	private void parsePrintSeparator(PrintStatementNode parent) {
		if(!startsPrintSeparator(nowReading) && !nowReading.isLextant(Punctuator.TERMINATOR)) {
			ParseNode child = syntaxErrorNode("print separator");
			parent.appendChild(child);
			return;
		}
		
		if(nowReading.isLextant(Punctuator.SPACE)) {
			readToken();
			ParseNode child = new SpaceNode(previouslyRead);
			parent.appendChild(child);
		}
		else if(nowReading.isLextant(Punctuator.SEPARATOR)) {
			readToken();
		}		
		else if(nowReading.isLextant(Punctuator.TERMINATOR)) {
			// we're at the end of the bowtie and this printSeparator is not required.
			// do nothing.  Terminator is handled in a higher-level nonterminal.
		}
	}
	private boolean startsPrintSeparator(Token token) {
		return token.isLextant(Punctuator.SEPARATOR, Punctuator.SPACE);
	}


	
	///////////////////////////////////////////////////////////
	// expressions
	// expr                     -> booleanExpression
	// booleanExpression        -> comparisonExpression
	// comparisonExpression     -> additiveExpression [> additiveExpression]?
	// additiveExpression       -> multiplicativeExpression [+ multiplicativeExpression]*  (left-assoc)
	// multiplicativeExpression -> atomicExpression [MULT atomicExpression]*  (left-assoc)
	// atomicExpression         -> unaryExpression
	// unaryExpression			-> literal
	// literal                  -> intNumber | floatNumber | booleanConstant | identifier 

	// expr  -> comparisonExpression
	private ParseNode parseExpression() {		
		if(!startsExpression(nowReading)) {
			return syntaxErrorNode("expression");
		}
		return parseBooleanExpression();
	}
	private boolean startsExpression(Token token) {
		return startsBooleanExpression(token);
	}
	
	// booleanExpression -> comparisonExpression
	private ParseNode parseBooleanExpression() {
		if(!startsBooleanExpression(nowReading)) {
			return syntaxErrorNode("boolean expression");
		}
		
		// ned to be left recursive??
		ParseNode left = parseComparisonExpression();
		while(nowReading.isLextant(Punctuator.getBooleanOperators())) {
			Token booleanToken = nowReading;
			readToken();
			ParseNode right = parseComparisonExpression();
			
			left = BinaryOperatorNode.withChildren(booleanToken, left, right);
		}
		return left;
	}
	private boolean startsBooleanExpression(Token token) {
		return startsComparisonExpression(token);
	}

	// comparisonExpression -> additiveExpression [> additiveExpression]?
	private ParseNode parseComparisonExpression() {
		if(!startsComparisonExpression(nowReading)) {
			return syntaxErrorNode("comparison expression");
		}
		
		ParseNode left = parseAdditiveExpression();
		if(nowReading.isLextant(Punctuator.getComparators())) {
			Token compareToken = nowReading;
			readToken();
			ParseNode right = parseAdditiveExpression();
			
			return BinaryOperatorNode.withChildren(compareToken, left, right);
		}
		return left;

	}
	private boolean startsComparisonExpression(Token token) {
		return startsAdditiveExpression(token);
	}
	
	

	// additiveExpression -> multiplicativeExpression [+ multiplicativeExpression]*  (left-assoc)
	private ParseNode parseAdditiveExpression() {
		if(!startsAdditiveExpression(nowReading)) {
			return syntaxErrorNode("additiveExpression");
		}
		
		ParseNode left = parseMultiplicativeExpression();
		while(nowReading.isLextant(Punctuator.ADD, Punctuator.SUBTRACT)) {
			Token additiveToken = nowReading;
			readToken();
			ParseNode right = parseMultiplicativeExpression();
			
			left = BinaryOperatorNode.withChildren(additiveToken, left, right);
		}
		return left;
	}
	private boolean startsAdditiveExpression(Token token) {
		return startsMultiplicativeExpression(token);
	}

	// multiplicativeExpression -> atomicExpression [MULT atomicExpression]*  (left-assoc)
	private ParseNode parseMultiplicativeExpression() {
		if(!startsMultiplicativeExpression(nowReading)) {
			return syntaxErrorNode("multiplicativeExpression");
		}
		
		ParseNode left = parseAtomicExpression();
		while(nowReading.isLextant(Punctuator.MULTIPLY, Punctuator.DIVISION)) {
			Token multiplicativeToken = nowReading;
			readToken();
			ParseNode right = parseAtomicExpression();
			
			left = BinaryOperatorNode.withChildren(multiplicativeToken, left, right);
		}
		return left;
	}
	private boolean startsMultiplicativeExpression(Token token) {
		return startsAtomicExpression(token);
	}
	
	// atomicExpression -> literal
	private ParseNode parseAtomicExpression() {
		if(!startsAtomicExpression(nowReading)) {
			return syntaxErrorNode("atomic expression");
		}
		
		if (startsCast(nowReading)){
			expect(Punctuator.OPEN_BRACKET);
			ParseNode left = parseExpression();
			expect(Punctuator.PIPE);
			Token castToken = previouslyRead;
			PrimitiveType castType = parseCastType();
			expect(Punctuator.CLOSE_BRACKET);
			return CastNode.withChildren(castToken, left, castType);
		} else if (startsParenthetical(nowReading)) {
			expect(Punctuator.OPEN_PARENTHESIS);
			ParseNode left = parseExpression();
			expect(Punctuator.CLOSE_PARENTHESIS);
			return left;
		} else if (startsNegation(nowReading)) {
			expect(Punctuator.NOT);
			Token negationToken = previouslyRead;
			ParseNode right = parseExpression();
			ParseNode left = UnaryOperatorNode.withChild(negationToken, right); 
			return left;
		} else {
			return parseLiteral();
		}
	}
	private PrimitiveType parseCastType() {
		readToken();
		switch(previouslyRead.getLexeme()) {
		case "bool":
			return PrimitiveType.BOOLEAN;
		case "char":
			return PrimitiveType.CHARACTER;
		case "string":
			return PrimitiveType.STRING;
		case "int":
			return PrimitiveType.INTEGER;
		case "float":
			return PrimitiveType.FLOATING;
		default:
			return PrimitiveType.ERROR;
		}
	}
	private boolean startsAtomicExpression(Token token) {
		if (startsParenthetical(token) || startsCast(token) || startsNegation(token)) {
			return true;
		} else {
			return startsLiteral(token);
		}
	}
	
	// literal -> number | identifier | booleanConstant
	private ParseNode parseLiteral() {
		if(startsIntNumber(nowReading)) {
			return parseIntNumber();
		}
		if(startsFloatNumber(nowReading)) {
			return parseFloatNumber();
		}
		if(startsCharacter(nowReading)) {
			return parseCharacter();
		}
		if(startsString(nowReading)) {
			return parseString();
		}
		if(startsIdentifier(nowReading)) {
			return parseIdentifier();
		}
		if(startsBooleanConstant(nowReading)) {
			return parseBooleanConstant();
		}

		return syntaxErrorNode("literal");
	}
	private boolean startsLiteral(Token token) {
		return  startsIntNumber(token) || startsFloatNumber(token) ||
				startsCharacter(token) || startsString(token) ||
				startsIdentifier(token) || startsBooleanConstant(token);
	}

	// casting and parenthetical
	private boolean startsCast(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACKET);
	}
	private boolean startsParenthetical(Token token) {
		return token.isLextant(Punctuator.OPEN_PARENTHESIS); 
	}
	private boolean startsNegation(Token token) {
		return token.isLextant(Punctuator.NOT); 
	}
	
	// number (terminal)
	private ParseNode parseIntNumber() {
		if(!startsIntNumber(nowReading)) {
			return syntaxErrorNode("integer constant");
		}
		readToken();
		return new IntegerConstantNode(previouslyRead);
	}
	private boolean startsIntNumber(Token token) {
		return token instanceof IntegerToken;
	}
	
	private ParseNode parseFloatNumber() {
		if(!startsFloatNumber(nowReading)) {
			return syntaxErrorNode("floating constant");
		}
		readToken();
		return new FloatingConstantNode(previouslyRead);
	}
	private boolean startsFloatNumber(Token token) {
		return token instanceof FloatingToken;
	}
	
	// character/string (terminal)
	private ParseNode parseCharacter() {
		if(!startsCharacter(nowReading)) {
			return syntaxErrorNode("character");
		}
		readToken();
		return new CharacterNode(previouslyRead);
	}
	private boolean startsCharacter(Token token) {
		return token instanceof CharacterToken;
	}
	
	private ParseNode parseString() {
		if(!startsString(nowReading)) {
			return syntaxErrorNode("string");
		}
		readToken();
		return new StringNode(previouslyRead);
	}
	private boolean startsString(Token token) {
		return token instanceof StringToken;
	}

	// identifier (terminal)
	private ParseNode parseIdentifier() {
		if(!startsIdentifier(nowReading)) {
			return syntaxErrorNode("identifier");
		}
		readToken();
		return new IdentifierNode(previouslyRead);
	}
	private boolean startsIdentifier(Token token) {
		return token instanceof IdentifierToken;
	}

	// boolean constant (terminal)
	private ParseNode parseBooleanConstant() {
		if(!startsBooleanConstant(nowReading)) {
			return syntaxErrorNode("boolean constant");
		}
		readToken();
		return new BooleanConstantNode(previouslyRead);
	}
	private boolean startsBooleanConstant(Token token) {
		return token.isLextant(Keyword.TRUE, Keyword.FALSE);
	}

	private void readToken() {
		previouslyRead = nowReading;
		nowReading = scanner.next();
	}	
	
	// if the current token is one of the given lextants, read the next token.
	// otherwise, give a syntax error and read next token (to avoid endless looping).
	private void expect(Lextant ...lextants) {
		if(!nowReading.isLextant(lextants)) {
			syntaxError(nowReading, "expecting " + Arrays.toString(lextants));
		}
		readToken();
	}	
	private ErrorNode syntaxErrorNode(String expectedSymbol) {
		syntaxError(nowReading, "expecting " + expectedSymbol);
		ErrorNode errorNode = new ErrorNode(nowReading);
		readToken();
		return errorNode;
	}
	private void syntaxError(Token token, String errorDescription) {
		String message = "" + token.getLocation() + " " + errorDescription;
		error(message);
	}
	private void error(String message) {
		PikaLogger log = PikaLogger.getLogger("compiler.Parser");
		log.severe("syntax error: " + message);
	}	
}

