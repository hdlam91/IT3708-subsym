package evolutionary_Algorithm;

public abstract class FitnessEvaluator <T>{
	public abstract double getFitnessValue(PhenoType<T> phenoType);
}
