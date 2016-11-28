package parser;

import java.util.Arrays;
import logging.PikaLogger;
import parseTree.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.types.*;
import tokens.*;
import lexicalAnalyzer.*;


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
	// S -> globalDefinition* EXEC mainBlock
	private ParseNode parseProgram() {
		if(!startsProgram(nowReading)) {
			return syntaxErrorNode("program");
		}		
		ParseNode program = new ProgramNode(nowReading);
		
		while (startsFunctionDefinition(nowReading)) {
			ParseNode globalDefinition = parseFunctionDefinition();
			program.appendChild(globalDefinition);
		}
		
		expect(Keyword.EXEC);
		ParseNode mainBlock = parseMainBlock();
		program.appendChild(mainBlock);
		
		if(!(nowReading instanceof NullToken)) {
			return syntaxErrorNode("end of program");
		}
		
		return program;
	}
	private boolean startsProgram(Token token) {
		return token.isLextant(Keyword.FUNCTION) || token.isLextant(Keyword.EXEC);
	}
	
	///////////////////////////////////////////////////////////
	
	// functionDefinition -> FUNC identifier lambda
	private ParseNode parseFunctionDefinition() {
		if(!startsFunctionDefinition(nowReading)) {
			return syntaxErrorNode("function definition");
		}
		
		expect(Keyword.FUNCTION);
		
		Token definitionToken = previouslyRead;		
		ParseNode identifier = parseIdentifier();
		ParseNode lambda = parseLambda();
		
		ParseNode functionDefinitionNode = FunctionDefinitionNode.withChildren(definitionToken, identifier, lambda);
		return functionDefinitionNode;
	}
	private boolean startsFunctionDefinition(Token token){
		return token.isLextant(Keyword.FUNCTION);
	}
	
	///////////////////////////////////////////////////////////	
	
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
	
	// blockStatement -> { statement* }
	private ParseNode parseBlockStatement() {
		if(!startsBlockStatement(nowReading)) {
			return syntaxErrorNode("blockStatement");
		}
		ParseNode blockStatement = new BlockNode(nowReading);
		expect(Punctuator.OPEN_BRACE);
		
		while(startsStatement(nowReading)) {
			if (startsBlockStatement(nowReading)) {
				ParseNode childBlockStatement = parseBlockStatement();
				blockStatement.appendChild(childBlockStatement);
			} else {
				ParseNode statement = parseStatement();
				blockStatement.appendChild(statement);
			}
		}
		
		expect(Punctuator.CLOSE_BRACE);
		return blockStatement;
	}
	private boolean startsBlockStatement(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACE);
	}
	
	///////////////////////////////////////////////////////////
	
	// statement-> declaration | printStmt
	private ParseNode parseStatement() {
		if(!startsStatement(nowReading)) {
			return syntaxErrorNode("statement");
		}
		if(startsFunctionDefinition(nowReading)) {
			return parseFunctionDefinition();
		}
		if(startsDeclaration(nowReading)) {
			return parseDeclaration();
		}
		if(startsAssignment(nowReading)) {
			return parseAssignment();
		}
		if(startsReleaseStatement(nowReading)) {
			return parseReleaseStatement();
		}
		if(startsCallStatement(nowReading)) {
			return parseCallStatement();
		}
		if(startsReturnStatement(nowReading)) {
			return parseReturnStatement();
		}
		if(startsReleaseStatement(nowReading)) {
			return parseReleaseStatement();
		}
		if(startsIfStatement(nowReading)) {
			return parseIfStatement();
		}
		if(startsWhileStatement(nowReading)) {
			return parseWhileStatement();
		}
		if(startsForStatement(nowReading)) {
			return parseForStatement();
		}
		if(startsControlStatement(nowReading)) {
			return parseControlStatement();
		}
		if(startsPrintStatement(nowReading)) {
			return parsePrintStatement();
		}
		return syntaxErrorNode("statement");
	}
	private boolean startsStatement(Token token) {
		return startsFunctionDefinition(token) || 
			   startsDeclaration(token) ||
			   startsAssignment(token) ||
			   startsCallStatement(nowReading) ||
			   startsReturnStatement(nowReading) ||
			   startsReleaseStatement(token) || 
			   startsIfStatement(token) ||
			   startsWhileStatement(token) ||
			   startsForStatement(token) ||
			   startsControlStatement(token) ||
			   startsPrintStatement(token) ||
			   startsBlockStatement(token);
	}
	
	///////////////////////////////////////////////////////////
	
	// declarationStmt -> CONST/VAR identifier := expression .
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
		initializer = parseIndexOrInvocation(initializer);
		
		expect(Punctuator.TERMINATOR);
		
		return DeclarationNode.withChildren(declarationToken, identifier, initializer);
	}
	private boolean startsDeclaration(Token token) {		
		return token.isLextant(Keyword.CONST, Keyword.VAR);
	}
	
	///////////////////////////////////////////////////////////
	
	// assignmentStmt -> target := expression .
	private ParseNode parseAssignment() {
		if(!startsAssignment(nowReading)) {
			return syntaxErrorNode("assignment");
		}
		
		ParseNode target = null;
		
		if (nowReading instanceof IdentifierToken) {
			target = parseIdentifier();
		}
		
		if (startsParenthetical(nowReading)) {
			target = parseExpression();
		}
		
		if (startsCastOrArray(nowReading)) {
			target = parseExpression();
		}
		
		assert target != null;
		
		target = parseIndexOrInvocation(target);

		expect(Punctuator.ASSIGN);
		Token assignmentToken = Punctuator.ASSIGN.prototype();
		ParseNode expression = parseExpression();
		
		expect(Punctuator.TERMINATOR);
		
		return AssignmentNode.withChildren(assignmentToken, target, expression);
	}
	private boolean startsAssignment(Token token) {
		return (token instanceof IdentifierToken || startsParenthetical(token) || startsCastOrArray(token));
	}
	
	///////////////////////////////////////////////////////////
	
	// releaseStmt -> RELEASE expression
	private ParseNode parseReleaseStatement() {
		if(!startsReleaseStatement(nowReading)) {
			return syntaxErrorNode("release");
		}
		
		Token releaseToken = nowReading;
		readToken();
		
		ParseNode identifier = parseIdentifier();
		
		expect(Punctuator.TERMINATOR);
		
		return ReleaseNode.withChild(releaseToken, identifier);
	}
	private boolean startsReleaseStatement(Token token){
		return token.isLextant(Keyword.RELEASE);
	}
	
	///////////////////////////////////////////////////////////
		
	// returnStmt -> RETURN expression
	private ParseNode parseCallStatement() {
		if(!startsCallStatement(nowReading)) {
			return syntaxErrorNode("call");
		}
	
		Token callToken = nowReading;
		readToken();
		
		ParseNode callExpression = parseExpression();
		
		expect(Punctuator.TERMINATOR);
		return CallNode.withChild(callToken, callExpression);
	}
	private boolean startsCallStatement(Token token){
		return token.isLextant(Keyword.CALL);
	}
	
	///////////////////////////////////////////////////////////
	
	// returnStmt -> RETURN expression
	private ParseNode parseReturnStatement() {
		if(!startsReturnStatement(nowReading)) {
			return syntaxErrorNode("return");
		}
	
		Token returnToken = nowReading;
		readToken();
		
		if (nowReading.isLextant(Punctuator.TERMINATOR)) {
			readToken();
			return new ReturnNode(returnToken);
		}
		
		ParseNode returnExpression = parseExpression();
		
		expect(Punctuator.TERMINATOR);
		return ReturnNode.withChild(returnToken, returnExpression);
	}
	private boolean startsReturnStatement(Token token){
		return token.isLextant(Keyword.RETURN);
	}


	///////////////////////////////////////////////////////////
	
	// ifStmt -> if (expression) blockStatement (else blockStatement)?
	private ParseNode parseIfStatement() {
		if(!startsIfStatement(nowReading)) {
			return syntaxErrorNode("if-statement");
		}
		
		expect(Keyword.IF);

		Token ifToken = previouslyRead;
		
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode condition = parseExpression();
		expect(Punctuator.CLOSE_PARENTHESIS);
		
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
	
	///////////////////////////////////////////////////////////
	
	// whileStmt -> while (expression) blockStatement
	private ParseNode parseWhileStatement() {
		if(!startsWhileStatement(nowReading)) {
			return syntaxErrorNode("while-statement");
		}
		
		expect(Keyword.WHILE);

		Token whileToken = previouslyRead;
		
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode condition = parseExpression();
		expect(Punctuator.CLOSE_PARENTHESIS);
		
		ParseNode blockStatement = parseBlockStatement();
	
		return new WhileNode(whileToken, condition, blockStatement);
	}
	private boolean startsWhileStatement(Token token) {
		return token.isLextant(Keyword.WHILE);
	}
	
	///////////////////////////////////////////////////////////
		
	// whileStmt -> while (expression) blockStatement
	private ParseNode parseForStatement() {
		if(!startsForStatement(nowReading)) {
			return syntaxErrorNode("for-statement");
		}
	
		expect(Keyword.FOR);
		expect(Keyword.INDEX, Keyword.ELEMENT);
		Token forToken = previouslyRead;
		
		ParseNode identifier = parseIdentifier();
		
		expect(Keyword.OF);
		ParseNode sequence = parseExpression();
		
		ParseNode blockStatement = parseBlockStatement();

		return ForNode.withChildren(forToken, identifier, sequence, blockStatement);
	}
	private boolean startsForStatement(Token token) {
		return token.isLextant(Keyword.FOR);
	}
	
	///////////////////////////////////////////////////////////
	
	// controlStmt -> CONTINUE or BREAK.
	private ParseNode parseControlStatement() {
		if(!startsControlStatement(nowReading)) {
			return syntaxErrorNode("release");
		}
		
		Token controlToken = nowReading;
		readToken();
		
		expect(Punctuator.TERMINATOR);
		
		return ControlNode.withChild(controlToken);
	}
	private boolean startsControlStatement(Token token){
		return token.isLextant(Keyword.CONTINUE, Keyword.BREAK);
	}
	
	///////////////////////////////////////////////////////////
	
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
		return startsExpression(token) || token.isLextant(Keyword.TAB) || token.isLextant(Keyword.NEWLINE);
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
	// expr                     -> booleanOrExpression
	// booleanOrExpression      -> booleanAndExpression
	// booleanAndExpression     -> comparisonExpression
	// comparisonExpression     -> additiveExpression [> additiveExpression]?
	// additiveExpression       -> multiplicativeExpression [+ multiplicativeExpression]*  (left-assoc)
	// multiplicativeExpression -> unaryExpression [MULT unaryExpression]*  (left-assoc)
	// unaryExpression			-> [MULT atomicExpression]*  (right-assoc)
	// atomicExpression         -> literal
	// literal                  -> intNumber | floatNumber | booleanConstant | identifier 

	// expr  -> booleanOrExpression
	private ParseNode parseExpression() {		
		if(!startsExpression(nowReading)) {
			return syntaxErrorNode("expression");
		}
		return parseBooleanOrExpression();
	}
	private boolean startsExpression(Token token) {
		return startsBooleanOrExpression(token);
	}
	
	// booleanOrExpression -> booleanAndExpression
	private ParseNode parseBooleanOrExpression() {
		if(!startsBooleanOrExpression(nowReading)) {
			return syntaxErrorNode("booleanOrExpression");
		}
		
		// ned to be left recursive??
		ParseNode left = parseBooleanAndExpression();
		while(nowReading.isLextant(Punctuator.OR)) {
			Token booleanToken = nowReading;
			readToken();
			ParseNode right = parseBooleanAndExpression();
			
			left = BinaryOperatorNode.withChildren(booleanToken, left, right);
		}
		return left;
	}
	private boolean startsBooleanOrExpression(Token token) {
		return startsBooleanAndExpression(token);
	}
	
	// booleanAndExpression -> comparisonExpression
	private ParseNode parseBooleanAndExpression() {
		if(!startsBooleanAndExpression(nowReading)) {
			return syntaxErrorNode("booleanAndExpression");
		}
		
		// ned to be left recursive??
		ParseNode left = parseComparisonExpression();
		while(nowReading.isLextant(Punctuator.AND)) {
			Token booleanToken = nowReading;
			readToken();
			ParseNode right = parseComparisonExpression();
			
			left = BinaryOperatorNode.withChildren(booleanToken, left, right);
		}
		return left;
	}
	private boolean startsBooleanAndExpression(Token token) {
		return startsComparisonExpression(token);
	}

	// comparisonExpression -> additiveExpression [> additiveExpression]?
	private ParseNode parseComparisonExpression() {
		if(!startsComparisonExpression(nowReading)) {
			return syntaxErrorNode("comparisonExpression");
		}
		
		ParseNode left = parseAdditiveExpression();
		while(nowReading.isLextant(Punctuator.getComparators())) {
			Token compareToken = nowReading;
			readToken();
			ParseNode right = parseAdditiveExpression();
			
			left = BinaryOperatorNode.withChildren(compareToken, left, right);
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

	// multiplicativeExpression -> unaryExpression [MULT unaryExpression]*  (left-assoc)
	private ParseNode parseMultiplicativeExpression() {
		if(!startsMultiplicativeExpression(nowReading)) {
			return syntaxErrorNode("multiplicativeExpression");
		}
		
		ParseNode left = parseFoldExpression();
		while(nowReading.isLextant(Punctuator.getMultiplicatives())) {
			Token multiplicativeToken = nowReading;
			readToken();
			ParseNode right = parseFoldExpression();
			
			if (multiplicativeToken.isLextant(Punctuator.getRationals())) {
				left = RationalOperatorNode.withChildren(multiplicativeToken, left, right);
			} else {
				left = BinaryOperatorNode.withChildren(multiplicativeToken, left, right);
			}
		}
		return left;
	}
	private boolean startsMultiplicativeExpression(Token token) {
		return startsFoldExpression(token);
	}

	// foldExpression -> (map|reduce)Expression
	private ParseNode parseFoldExpression() {
		if(!startsFoldExpression(nowReading)) {
			return syntaxErrorNode("foldExpression");
		}

		ParseNode left = parseMapReduceExpression();
		while(nowReading.isLextant(Keyword.FOLD)) {
			Token foldToken = nowReading;
			readToken();
			
			left = FoldOperatorNode.withChildren(foldToken, left);
			
			if (nowReading.isLextant(Punctuator.OPEN_BRACKET)) {
				expect(Punctuator.OPEN_BRACKET);
				ParseNode base = parseMapReduceExpression();
				left.appendChild(base);
				expect(Punctuator.CLOSE_BRACKET);
			}
			
			ParseNode right = parseMapReduceExpression();
			left.appendChild(right);
			return left;
		}
		return left;
	}
	private boolean startsFoldExpression(Token token) {
		return startsMapReduceExpression(token);
	}
	
	// (map|reduce)Expression -> unaryExpression
	private ParseNode parseMapReduceExpression() {
		if(!startsFoldExpression(nowReading)) {
			return syntaxErrorNode("foldExpression");
		}
		
		ParseNode left = parseUnaryExpression();
		while(nowReading.isLextant(Keyword.MAP, Keyword.REDUCE)) {
			Token token = nowReading;
			readToken();
			ParseNode right = parseUnaryExpression();
			
			if (token.isLextant(Keyword.MAP)) {
				left = MapOperatorNode.withChildren(token, left, right);
			}
			
			if (token.isLextant(Keyword.REDUCE)) {
				left = ReduceOperatorNode.withChildren(token, left, right);
			}
		}
		return left;
	}
	private boolean startsMapReduceExpression(Token token) {
		return startsUnaryExpression(token);
	}
	
	// unaryExpression -> [MULT atomicExpression]*  (right-assoc)
	private ParseNode parseUnaryExpression() {
		if(!startsUnaryExpression(nowReading)) {
			return syntaxErrorNode("unaryExpression");
		}
		
		if (startsClone(nowReading)) {
			return parseArrayExpression();
		}
		
		if (startsReverse(nowReading)) {
			expect(Keyword.REVERSE);
			Token token = previouslyRead;
			
			ParseNode node = parseExpression();
			return ReverseOperatorNode.withChildren(token, node);
		}
		
		if (startsZip(nowReading)) {
			expect(Keyword.ZIP);
			Token token = previouslyRead;
			ParseNode zipNode = new ZipOperatorNode(token);
			
			for (int i = 0; i < 3; i++) {
				zipNode.appendChild(parseExpression());
				if (i < 2) {
					expect(Punctuator.SEPARATOR);
				}
			}
			
			return zipNode;
		}
		
		if (startsNegation(nowReading) || startsLength(nowReading)) {
			readToken();
			Token token = previouslyRead;
			
			ParseNode node = parseExpression(); 
			return UnaryOperatorNode.withChild(token, node);
		}
		
		return parseAtomicExpression();
	}
	private boolean startsUnaryExpression(Token token) {
		if (startsClone(token) 	|| startsNegation(token) ||
			startsLength(token) || startsReverse(token) || startsZip(token)) {
			return true;
		} else {
			return startsAtomicExpression(token);
		}
	}
	private boolean startsNegation(Token token) {
		return token.isLextant(Punctuator.NOT); 
	}
	private boolean startsLength(Token token) {
		return token.isLextant(Keyword.LENGTH); 
	}
	private boolean startsClone(Token token) {
		return token.isLextant(Keyword.CLONE); 
	}
	private boolean startsReverse(Token token) {
		return token.isLextant(Keyword.REVERSE); 
	}
	private boolean startsZip(Token token) {
		return token.isLextant(Keyword.ZIP); 
	}
		
	
	// atomicExpression -> literal
	private ParseNode parseAtomicExpression() {
		if(!startsAtomicExpression(nowReading)) {
			return syntaxErrorNode("atomicExpression");
		}
		
		if (startsParenthetical(nowReading)) {
			expect(Punctuator.OPEN_PARENTHESIS);
			ParseNode left = parseExpression();
			expect(Punctuator.CLOSE_PARENTHESIS);
			left = parseIndexOrInvocation(left);
			return left;
		}
		
		if (startsLambda(nowReading)) {
			ParseNode left = parseLambda();
			return left;
		}
		
		if (startsCastOrArray(nowReading)){
			if (nowReading.isLextant(Keyword.NEW)) {
				return parseArrayExpression();
			}
			
			expect(Punctuator.OPEN_BRACKET);
			
			ParseNode node = null;
			ParseNode left = parseExpression();
			
			expect(Punctuator.PIPE, Punctuator.SEPARATOR, Punctuator.CLOSE_BRACKET);
			
			// Array
			if (previouslyRead.isLextant(Punctuator.SEPARATOR, Punctuator.CLOSE_BRACKET)) {
				node = parseArrayExpression(left);
			}
			
			// Cast
			if (previouslyRead.isLextant(Punctuator.PIPE)) {
				Token castToken = previouslyRead;
				Type castType;
				
				if (nowReading.isLextant(Punctuator.OPEN_BRACKET)) {
					castType = parseArrayType();	
				} else {
					castType = parseTypeLiteral();
					readToken();
				}
				
				expect(Punctuator.CLOSE_BRACKET);
				node = CastNode.withChildren(castToken, left, castType);
				node.setType(castType);
			}
			
			if (node == null) {
				return syntaxErrorNode(nowReading.getLexeme());
			}
			
			node = parseIndexOrInvocation(node);
				
			return node;
		}
		
		return parseLiteral();
	}
	private boolean startsAtomicExpression(Token token) {
		if (startsParenthetical(token) || startsCastOrArray(token)) {
			return true;
		} else {
			return startsLiteral(token);
		}
	}
	private boolean startsCastOrArray(Token token) {
		return startsCast(token) || startsNewArray(token);
	}
	private boolean startsCast(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACKET); 
	}
	private boolean startsNewArray(Token token) {
		return token.isLextant(Keyword.NEW); 
	}
	private boolean startsParenthetical(Token token) {
		return token.isLextant(Punctuator.OPEN_PARENTHESIS); 
	}

	///////////////////////////////////////////////////////////
	// arrayExpression -> new arrayType (expression)
	//	   			   -> clone expression
	//				   -> [expressionList]
	private ParseNode parseArrayExpression() {
		expect(Keyword.NEW, Keyword.CLONE);
		
		if (previouslyRead.isLextant(Keyword.NEW)) {
			Token token = previouslyRead;
			
			Type type = parseArrayType();
			
			expect(Punctuator.OPEN_PARENTHESIS);
			ParseNode size = parseExpression();
			expect(Punctuator.CLOSE_PARENTHESIS);
			
			boolean isEmpty = true;
			return ArrayNode.withChildren(token, type, size, isEmpty);
		}
		
		if (previouslyRead.isLextant(Keyword.CLONE)) {
			Token token = previouslyRead;
			ParseNode cloneNode = parseExpression();
			return ArrayNode.withChildren(token, cloneNode, false);
		}
		
		return syntaxErrorNode(nowReading.getLexeme());
	}
	private ParseNode parseArrayExpression(ParseNode val) {
		if (previouslyRead.isLextant(Punctuator.SEPARATOR, Punctuator.CLOSE_BRACKET)) {
			Token token = Keyword.NEW.prototype();
			ArrayNode result = ArrayNode.withChildren(token, val, false);

			if (!previouslyRead.isLextant(Punctuator.CLOSE_BRACKET)) {
				result = parseArrayExpressionList(result);
				expect(Punctuator.CLOSE_BRACKET);
			}
			
			return result;
		}
		
		return syntaxErrorNode(nowReading.getLexeme());
	}
	private ArrayNode parseArrayExpressionList(ArrayNode parent) {
		while(startsExpression(nowReading) || nowReading.isLextant(Punctuator.SEPARATOR)) {
			if (startsExpression(nowReading)) {
				ParseNode child = parseExpression();
				parent.appendChild(child);
			}
			
			if (nowReading.isLextant(Punctuator.SEPARATOR)) {
				readToken();
			}
		}
		return parent;
	}	
	private Type parseArrayType() {
		expect(Punctuator.OPEN_BRACKET);
		ArrayType type = new ArrayType();
		
		Type subtype = parseType();
		if (subtype == TypeLiteral.ARRAY) {
			type.setSubtype(parseArrayType());	
		} else {			
			type.setSubtype(subtype);
			if (subtype instanceof TypeLiteral) {
				readToken();
			}
		}
		
		expect(Punctuator.CLOSE_BRACKET);
		
		return type;
	}

	///////////////////////////////////////////////////////////
	// lambda -> lambdaParamType blockStatement
	// lambdaParamType -> < parameterList > -> type
	// parameterList -> parameterSpecification bowtie ,
	private ParseNode parseLambda() {
		if(!startsLambda(nowReading)) {
			return syntaxErrorNode("lambda");
		}
		
		Token lambdaToken = nowReading;
		ParseNode lambdaParamType = parseLambdaParamType();
		ParseNode blockStatement = parseBlockStatement();
		
		ParseNode node = LambdaNode.withChildren(lambdaToken, lambdaParamType, blockStatement);
		node = parseIndexOrInvocation(node);
		
		return node;
	}
	private boolean startsLambda(Token token){
		return token.isLextant(Punctuator.LESS);
	}
	private ParseNode parseLambdaParamType() {
		LambdaParamTypeNode result = new LambdaParamTypeNode(previouslyRead);
		parseParameterList(result);

		expect(Punctuator.RETURNS);
		
		Type returnType = parseType();		
		result.setType(returnType);
		
		return result;
	}
	private void parseParameterList(LambdaParamTypeNode parent) {
		expect(Punctuator.LESS);
		
		while (!nowReading.isLextant(Punctuator.GREATER)) {
			if(nowReading.isLextant(Punctuator.SEPARATOR)) {
				readToken();
			}
			
			if(startsTypeLiteral(nowReading)) {
				Type type = parseType();

				Token token = nowReading;
				ParseNode identifier = parseIdentifier();
				
				LambdaParamNode child = LambdaParamNode.withChildren(token, type, identifier);
				parent.appendChild(child);
			} else if(startsCastOrArray(nowReading)) {
				Token token = nowReading;
				Type type = parseArrayType();
				
				ParseNode identifier = parseIdentifier();
				
				LambdaParamNode child = LambdaParamNode.withChildren(token, type, identifier);
				parent.appendChild(child);
			} else if(startsLambdaType(nowReading)) {
				ParseNode lambda = parseLambdaType();
				
				Token token = nowReading;
				ParseNode identifier = parseIdentifier();
				Type type = lambda.getType();
				
				LambdaParamNode child = LambdaParamNode.withChildren(token, type, identifier, lambda);
				parent.appendChild(child);
			} else {
				parent.appendChild(syntaxErrorNode(Punctuator.GREATER.getLexeme()));
				return;
			}
		}
		
		expect(Punctuator.GREATER);
	}
		
	// type literal
	private TypeLiteral parseTypeLiteral() {
		return TypeLiteral.withToken(nowReading);
	}
	private boolean startsTypeLiteral(Token token){
		return token.isLextant(Keyword.getTypeLiteral());
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
		if(startsLambda(nowReading)) {
			return parseLambda();
		}

		return syntaxErrorNode("literal");
	}
	private boolean startsLiteral(Token token) {
		return  startsCharacter(token) || startsString(token) ||
				startsIntNumber(token) || startsFloatNumber(token) ||
				startsIdentifier(token) || startsBooleanConstant(token) ||
				startsLambdaType(token);
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
		ParseNode node = new StringNode(previouslyRead);
		
		// Check for index
		if (nowReading.isLextant(Punctuator.OPEN_BRACKET)) {
			node = parseIndex(node);
		}
		
		return node;
	}
	private boolean startsString(Token token) {
		return token instanceof StringToken;
	}

	// identifier (terminal) [ also handles indexing and functionInvocation]
	private ParseNode parseIdentifier() {
		if(!startsIdentifier(nowReading)) {
			return syntaxErrorNode("identifier");
		}
		
		if (startsParenthetical(nowReading)) {
			parseAtomicExpression();
		}
		
		Token identifier = nowReading;
		readToken();

		ParseNode node = new IdentifierNode(identifier);
		node = parseIndexOrInvocation(node);
			
		return node;
	}
	private boolean startsIdentifier(Token token) {
		return token instanceof IdentifierToken;
	}

	// index and function arguments (terminal)
	private ParseNode parseIndexOrInvocation(ParseNode node) {
		while (nowReading.isLextant(Punctuator.OPEN_PARENTHESIS, Punctuator.OPEN_BRACKET)) {
			// Check for function invocation
			if (nowReading.isLextant(Punctuator.OPEN_PARENTHESIS)) {
				ParseNode invocation = new FunctionInvocationNode(node);
				invocation.appendChild(node);
				node = parseArguments(invocation);
			}
			
			// Check for index
			if (nowReading.isLextant(Punctuator.OPEN_BRACKET)) {
				node = parseIndex(node);
			}
		}
		
		return node;
	}
	private ParseNode parseIndex(ParseNode node) {
		expect(Punctuator.OPEN_BRACKET);
		Token token = previouslyRead;
		
		ParseNode index = parseExpression();
		ParseNode indexNode = IndexNode.withChildren(token, node, index);
		
		if (nowReading.isLextant(Punctuator.SEPARATOR)) {
			readToken();
			index = parseExpression();
			indexNode.appendChild(index);
		}
		
		expect(Punctuator.CLOSE_BRACKET);
		return indexNode;
	}
	private ParseNode parseArguments(ParseNode node){
		expect(Punctuator.OPEN_PARENTHESIS);
		
		while (!nowReading.isLextant(Punctuator.CLOSE_PARENTHESIS)) {
			if (nowReading.isLextant(Punctuator.SEPARATOR)) {
				readToken();
			}
			
			ParseNode child = parseExpression();
			node.appendChild(child);
		}
		
		expect(Punctuator.CLOSE_PARENTHESIS);
		return node;
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

	///////////////////////////////////////////////////////////
	// lambdaType (terminal) -> < typeList > -> type
	// typeList -> type bowtie ,
	private ParseNode parseLambdaType() {
		if(!startsLambdaType(nowReading)) {
			return syntaxErrorNode("lambdaType");
		}
		
		LambdaTypeNode result = new LambdaTypeNode(nowReading);
		parseTypeList(result);

		expect(Punctuator.RETURNS);
		
		Type returnType = parseType();
		if (returnType == PrimitiveType.ERROR) {
			return syntaxErrorNode("valid type");
		}
		
		result.setReturnType(returnType);
		
		return result;
	}
	private boolean startsLambdaType(Token token) {
		return token.isLextant(Punctuator.LESS);
	}
	private void parseTypeList(LambdaTypeNode parent) {
		expect(Punctuator.LESS);
		
		boolean expectSeparator = false;
		while (!nowReading.isLextant(Punctuator.GREATER)) {
			if (expectSeparator) {
				expect(Punctuator.SEPARATOR);
			} else {
				expectSeparator = true;
			}
			
			Type type = parseType();
			parent.addChildType(type);
			if (type == PrimitiveType.ERROR) {
				syntaxError(nowReading, "Bad lambda type.");
				return;
			}
		}
		
		expect(Punctuator.GREATER);
	}
	private Type parseType() {
		if(startsTypeLiteral(nowReading)) {
			TypeLiteral type = parseTypeLiteral();
			readToken();
			return type.getType();
		}

		if(startsLambdaType(nowReading)) {
			ParseNode lambda = parseLambdaType();
			return lambda.getType();
		}
		
		if(startsCastOrArray(nowReading)) {
			Type arrayType = parseArrayType();
			return arrayType;
		}
		
		return PrimitiveType.ERROR;
	}

	
	///////////////////////////////////////////////////////////
	
	private void readToken() {
		previouslyRead = nowReading;
		nowReading = scanner.next();
	}	
	
	
	///////////////////////////////////////////////////////////
	
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

