package evolutionary_Algorithm;

import simpleslickgame.ANN;

public class DoubleFitnessEvaluator extends FitnessEvaluator<Integer>{

	private ANN ann;
	
	public DoubleFitnessEvaluator(ANN ann) {
		this.ann = ann;
	}
	
	@Override
	public double getFitnessValue(PhenoType<Integer> phenoType) {
		return 0;
	}
}
