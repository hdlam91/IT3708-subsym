package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

public class FitnessProportionate<T> extends ParentSelection<T>{

	public List<Individual<T>> getSelectedParents(List<Individual<T>> individuals, List<Double> phenotypeFitnessValues, int type, int generation) {
		double sum = 0;
		double[] values = new double[phenotypeFitnessValues.size()];
		for (int i = 0; i < phenotypeFitnessValues.size(); i++) {
			sum += phenotypeFitnessValues.get(i);
			values[i] = phenotypeFitnessValues.get(i);
		}
		sum = round(sum,3);
		for (int i = 0; i < values.length; i++) {
			if(i>0) values[i] = values[i]/sum + values[i-1];
			else values[i] = values[i]/sum;
		}
		int selectedIndex = 0;
		List<Individual<T>> ret = new ArrayList<Individual<T>>();
		
		while(ret.size()<2){
			double randomValue = Math.random();
			for (int i = 0; i < values.length; i++) {
				if(values[i]>randomValue){
					selectedIndex = i;
					break;
				}
			}
			ret.add(getNewIndividualInstance(individuals.get(selectedIndex),type));
		}
		return ret;
	}

}
