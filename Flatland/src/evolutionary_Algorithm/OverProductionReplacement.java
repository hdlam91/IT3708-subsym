package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class OverProductionReplacement<T> extends AdultSelection<T> {

	@Override
	public Population<T> findAdultsFromPopulations(Population<T> pop1, Population<T> pop2, List<Double> pheno1Values, List<Double> pheno2Values, int sizeOfOutComePopolation) {
		List<Individual<T>> individuals = pop2.getIndividuals();
		List<Individual<T>> newIndividuals = new ArrayList<Individual<T>>();
		
		Map<Double,List<Individual<T>>> hm = new TreeMap<Double,List<Individual<T>>>();
		for (int i = 0; i < individuals.size(); i++) {
			if(!hm.containsKey(pheno2Values.get(i)))
				hm.put(pheno2Values.get(i), new ArrayList<Individual<T>>());
			hm.get(pheno2Values.get(i)).add(individuals.get(i));
		}
		
		Set<Double> keySet = hm.keySet();
		Double[] l = keySet.toArray(new Double[keySet.size()]);
		Double key;
		
		for (int i = l.length-1; i >= 0; i--) {
			key = l[i];
			List<Individual<T>> tempIndi = hm.get(key);
			if(newIndividuals.size() == sizeOfOutComePopolation)
				break;
			else if(newIndividuals.size()+tempIndi.size()<=sizeOfOutComePopolation){
				for (int j = tempIndi.size()-1; j >= 0; j--) {
					newIndividuals.add(tempIndi.get(j));
				}
			}
			else if(newIndividuals.size() + tempIndi.size() > sizeOfOutComePopolation){
				boolean[] takenIndices = new boolean[tempIndi.size()];
				while(newIndividuals.size()<sizeOfOutComePopolation){
					int randomindex = (int) (Math.random()*tempIndi.size());
					if(!takenIndices[randomindex]){
						newIndividuals.add(tempIndi.get(randomindex));
						takenIndices[randomindex] = true;
					}
				}
			}
		}
		return new Population<T>(newIndividuals.size(), newIndividuals);
	}
}
