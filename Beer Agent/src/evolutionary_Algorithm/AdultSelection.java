package evolutionary_Algorithm;

import java.util.List;

public abstract class AdultSelection <T>{
	
	public abstract Population<T> findAdultsFromPopulations(Population<T> pop1, Population<T> pop2, List<Double> pheno1Values, List<Double> pheno2Values, int sizeOfOutComePopulation);
}
