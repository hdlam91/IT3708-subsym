package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

public class BinaryCrossOver extends CrossOver<Boolean>{
	
	public List<Individual<Boolean>> getCrossoveredChildrenFromParents(Individual<Boolean> parent1, Individual<Boolean> parent2){
		Boolean[] genoTypeFromParent1 = parent1.getGenoType();
		Boolean[] genoTypeFromParent2 = parent2.getGenoType();
		
		Boolean[] genoTypeChild1 = new Boolean[genoTypeFromParent1.length];
		Boolean[] genoTypeChild2 = new Boolean[genoTypeFromParent2.length];
		
		int indexToCrossOverAt = (int) (Math.random()*genoTypeFromParent1.length);
		
		for (int i = 0; i < indexToCrossOverAt; i++) {
			genoTypeChild1[i] = genoTypeFromParent1[i];
			genoTypeChild2[i] = genoTypeFromParent2[i];
		}
		for (int i = indexToCrossOverAt; i < genoTypeChild2.length; i++) {
			genoTypeChild1[i] = genoTypeFromParent2[i];
			genoTypeChild2[i] = genoTypeFromParent1[i];
		}
		
		List<Individual<Boolean>> ret = new ArrayList<Individual<Boolean>>();
		ret.add(new BinaryIndividual(genoTypeChild1));
		ret.add(new BinaryIndividual(genoTypeChild2));
		return ret;
	}
	
}
