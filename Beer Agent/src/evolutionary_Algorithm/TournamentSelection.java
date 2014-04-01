package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

public class TournamentSelection<T> extends ParentSelection<T>{
	
	private int K;
	private double P;
	private List<Double> probabilities;
	
	public TournamentSelection(int K, double P){
		this.K = K;
		this.P = P;
		this.probabilities = new ArrayList<Double>();
		for (int i = 0; i < K; i++) {
			if(i>0) probabilities.add(P*Math.pow((1-P), i) + probabilities.get(i-1));
			else probabilities.add(P);
		}
	}
	
	@Override
	public List<Individual<T>> getSelectedParents(List<Individual<T>> individuals, List<Double> phenotypeFitnessValues, int type, int generation) {
		List<Individual<T>> tempList = new ArrayList<Individual<T>>();
		List<Individual<T>> tempList2 = new ArrayList<Individual<T>>();
		
		List<Double> tempListValues = new ArrayList<Double>();
		List<Double> tempListValues2 = new ArrayList<Double>();
		
		List<Individual<T>> retList = new ArrayList<Individual<T>>();
		boolean[] indicesTaken = new boolean[individuals.size()];
		
		while(tempList.size()<K){
			int index = (int) (Math.random()*individuals.size());
			while(indicesTaken[index]){
				index = (int) (Math.random()*individuals.size());
			}
			tempList.add(individuals.get(index));
			tempListValues.add(phenotypeFitnessValues.get(index));
			indicesTaken[index] = true;
		}
//		System.out.println(tempListValues);
		for (int i = 0; i < tempListValues.size(); i++) {
			if(i==0){
				tempList2.add(tempList.get(i));
				tempListValues2.add(tempListValues.get(i));
				continue;
			}
			for (int j = 0; j < tempListValues2.size(); j++) {
				if(tempListValues.get(i)>tempListValues2.get(j)){
					tempListValues2.add(j,tempListValues.get(i));
					tempList2.add(j,tempList.get(i));
					break;
				}
				if(j == tempListValues2.size()-1){
					tempListValues2.add(tempListValues.get(i));
					tempList2.add(tempList.get(i));
					break;
				}
			}
		}
//		System.out.println(tempListValues2);
		
		boolean[] indices2 = new boolean[K];
		if(P==1.0){
			retList.add(tempList2.get(0));
			retList.add(tempList2.get(1));
		}
		else{
			while(retList.size()<2){
				double number = Math.random();
//				System.out.println(number + " " + retList.size());
				for (int i = 0; i < probabilities.size(); i++) {
					if(probabilities.get(i)>number){
						if(indices2[i] && number>P){
							continue;
						}
						else if(!indices2[i]){
							retList.add(getNewIndividualInstance(tempList2.get(i), type));
							indices2[i] = true;
//							System.out.println(tempListValues2.get(i));
							break;
						}
					}
				}
			}
		}
		
		
		return retList;
	}
}
