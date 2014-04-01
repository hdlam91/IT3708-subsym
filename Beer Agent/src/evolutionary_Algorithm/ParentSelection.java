package evolutionary_Algorithm;

import java.util.List;

public abstract class ParentSelection <T>{
	
	public abstract List<Individual<T>> getSelectedParents(List<Individual<T>> individuals, List<Double> phenotypeFitnessValues, int type, int generation);
	
	@SuppressWarnings("unchecked")
	public Individual<T> getNewIndividualInstance(Individual<T> ind, int type){
		switch(type){
		case 0: 
		case 1:
			return (Individual<T>) new BinaryIndividual((Boolean[]) ind.getGenoType());
		case 2:
			DoubleIndividual ind2 = (DoubleIndividual) ind;
			return (Individual<T>) new DoubleIndividual((Integer[]) ind.getGenoType(),ind2.getNumW(),ind2.getNumN());
		default:
			return null;
		}
	}
	
	public static double round(double value, int places){
		if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
