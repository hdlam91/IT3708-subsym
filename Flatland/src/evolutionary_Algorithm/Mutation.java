package evolutionary_Algorithm;

public abstract class Mutation <T>{
	
	public abstract void getMutatedIndividual(Individual<T> person, Double mutationRate, boolean perComponent);
	
}
