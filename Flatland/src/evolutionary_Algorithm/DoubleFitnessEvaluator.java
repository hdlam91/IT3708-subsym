package evolutionary_Algorithm;

import simpleslickgame.EAConnection;

public class DoubleFitnessEvaluator extends FitnessEvaluator<Integer>{

	private EAConnection connection;
	
	public DoubleFitnessEvaluator(EAConnection con) {
		this.connection = con;
	}
	
	@Override
	public double getFitnessValue(PhenoType<Integer> phenoType) {
		return 0;
	}
}
