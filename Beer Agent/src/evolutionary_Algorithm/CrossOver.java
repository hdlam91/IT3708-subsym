package evolutionary_Algorithm;

import java.util.List;

public abstract class CrossOver <T>{
	
	public abstract List<Individual<T>> getCrossoveredChildrenFromParents(Individual<T> parent1, Individual<T> parent2);
}
