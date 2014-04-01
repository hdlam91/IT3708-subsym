package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SigmaScaling<T> extends ParentSelection<T>{

	@Override
	public List<Individual<T>> getSelectedParents(List<Individual<T>> individuals, List<Double> phenotypeFitnessValues, int type, int generation) {
		int size = phenotypeFitnessValues.size();
		
		double sum = 0;
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			sum += phenotypeFitnessValues.get(i);
			values[i] = phenotypeFitnessValues.get(i);
		}
		sum = round(sum,3);
		double mean = sum/size;
		mean = round(mean,5);
		double sum2 = 0;
		
//		System.out.println(mean);
		
		for (int i = 0; i < size; i++) {
			sum2 += Math.pow((mean-phenotypeFitnessValues.get(i)), 2);
		}
		
		double std = Math.sqrt(sum2/size);
//		System.out.println(std);
		
//		System.out.println();
		for (int i = 0; i < size; i++) {
			if(i>0) values[i] = (1+((phenotypeFitnessValues.get(i)-mean)/(2*std)))/size + values[i-1];
			else values[i] = (1+((phenotypeFitnessValues.get(i)-mean)/(2*std)))/size;
//			System.out.println(1+((phenotypeFitnessValues.get(i)-mean)/(2*std)));
		}
		
//		System.out.println(Arrays.toString(values));
		
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
		
//		return null;
	}
}
