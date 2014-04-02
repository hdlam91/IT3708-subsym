package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

public class DoubleCrossOver extends CrossOver<Integer>{

	@Override
	public List<Individual<Integer>> getCrossoveredChildrenFromParents(Individual<Integer> parent1, Individual<Integer> parent2) {
		Integer[] genoTypeFromParent1 = parent1.getGenoType();
		Integer[] genoTypeFromParent2 = parent2.getGenoType();
		
		Integer[] genoTypeChild1 = new Integer[genoTypeFromParent1.length];
		Integer[] genoTypeChild2 = new Integer[genoTypeFromParent2.length];
		
		int indexToCrossOverAt = (int) (Math.random()*genoTypeFromParent1.length);
		
		for (int i = 0; i < indexToCrossOverAt; i++) {
			genoTypeChild1[i] = genoTypeFromParent1[i];
			genoTypeChild2[i] = genoTypeFromParent2[i];
		}
		for (int i = indexToCrossOverAt; i < genoTypeChild2.length; i++) {
			genoTypeChild1[i] = genoTypeFromParent2[i];
			genoTypeChild2[i] = genoTypeFromParent1[i];
		}
		
		DoubleIndividual ind = (DoubleIndividual) parent1;
		
		int NumW = ind.getNumW();
		int NumN = ind.getNumN();
		
		List<Individual<Integer>> ret = new ArrayList<Individual<Integer>>();
		ret.add(new DoubleIndividual(genoTypeChild1,NumW,NumN));
		ret.add(new DoubleIndividual(genoTypeChild2,NumW,NumN));
		return ret;
	}
}
