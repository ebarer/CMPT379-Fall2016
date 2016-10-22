package asmCodeGenerator.codeGenerator;

import asmCodeGenerator.codeStorage.*;

public interface SimpleCodeGenerator {
	
	public ASMCodeChunk generate();
	public ASMCodeChunk generate(Object... var);
	
}
