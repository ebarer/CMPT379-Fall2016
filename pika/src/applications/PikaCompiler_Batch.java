package applications;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import optimizer.Optimizer;
import asmCodeGenerator.ASMCodeGenerator;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.Scanner;
import logging.PikaLogger;
import parseTree.ParseNode;
import parser.Parser;
import semanticAnalyzer.SemanticAnalyzer;
import tokens.Tokens;

public class PikaCompiler_Batch extends PikaApplication {
	/** Compiles a Pika file.
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		
		for (File file : files) {
			//checkArguments(args, className());
			String path = file.getPath();
			if (path.endsWith(".pika")) {
				PikaLogger.resetCounter();
				System.out.println("Compiling file: " + path);
				Tokens.setPrintLevel(Tokens.Level.FULL);
				compile(path);
			}
		}
	}
	
	/** analyzes a file specified by filename.
	 * @param filename the name of the file to be analyzed.
	 * @throws FileNotFoundException 
	 */
	public static void compile(String filename) throws FileNotFoundException {
		Scanner scanner         = LexicalAnalyzer.make(filename);
		ParseNode syntaxTree    = Parser.parse(scanner);
		ParseNode decoratedTree = SemanticAnalyzer.analyze(syntaxTree);
		generateCodeIfNoErrors(filename, decoratedTree);
	}

	private static void generateCodeIfNoErrors(String filename, ParseNode decoratedTree)
			throws FileNotFoundException {
		String outfile = outputFilename(filename);
		
		if(thereAreErrors()) {
			stopProcessing(outfile);
			//System.exit(1);
		} 
		else {
			generateOptimizeAndPrintCode(outfile, decoratedTree);
		}
	}

	// stopProcessing -- inform user and clean up.
	private static void stopProcessing(String outfile) {
		informUserNoCodeGenerated();
		removeOldASMFile(outfile);
	}
	private static void informUserNoCodeGenerated() {
		System.err.println("Program has errors. No executable created.");
	}
	private static void removeOldASMFile(String filename) {
		File file = new File(filename);
		if(file.exists()) {
			file.delete();
		}
	}
	
	// normal code generation and optimization.
	private static void generateOptimizeAndPrintCode(String outfile, ParseNode decoratedTree) 
			throws FileNotFoundException {
		ASMCodeFragment code = ASMCodeGenerator.generate(decoratedTree);
		ASMCodeFragment optimized = Optimizer.optimize(code);
		printCodeToFile(outfile, optimized);
	}
	private static void printCodeToFile(String filename, ASMCodeFragment code)
			throws FileNotFoundException {
		File file = new File(filename);
		PrintStream out = new PrintStream(file);
		out.print(code);
		out.close();
		System.out.println("Program compiled successfully: " + filename);
	}

	private static boolean thereAreErrors() {
		return logging.PikaLogger.hasErrors();
	}
}
