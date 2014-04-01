package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

public class EliteSelection<T> extends ParentSelection<T>{

	@Override
	public List<Individual<T>> getSelectedParents(List<Individual<T>> individuals, List<Double> phenotypeFitnessValues, int type, int generation) {
		List<Individual<T>> ret = new ArrayList<Individual<T>>();
		ret.add(getNewIndividualInstance(individuals.get(0), type));
		ret.add(getNewIndividualInstance(individuals.get(1), type));
		return ret;
	}
}
