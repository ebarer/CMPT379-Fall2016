package optimizer;

import asmCodeGenerator.codeStorage.ASMCodeFragment;

public class Optimizer {
	private ASMCodeFragment fragment;
	
	public static ASMCodeFragment optimize(ASMCodeFragment fragment) {
		Optimizer optimizer = new Optimizer(fragment);
		return optimizer.optimize();
	}
	public Optimizer(ASMCodeFragment fragment) {
		this.fragment = fragment;
	}
	
	public ASMCodeFragment optimize() {
		return fragment;
	}
}
