package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleIndividual extends Individual<Integer>{

	private final int factor = 1001;
	
	public DoubleIndividual(int sizeOfGenoType, int requiredNumberOfBits, boolean initializeRandomly){
		this.genotype = new Integer[sizeOfGenoType*requiredNumberOfBits];
		if(initializeRandomly){
			for (int i = 0; i < genotype.length; i++) {
				this.genotype[i] = (int)(Math.random()*factor);
			}
		}
	}
	
	public DoubleIndividual(Integer[] genotype){
		this.genotype = genotype.clone();
	}
	
	@Override
	public Integer[] getGenoType() {
		return genotype;
	}

	@Override
	public void setGenoType(Integer[] genotype) {
		this.genotype = genotype;
	}

	@Override
	public int getLengthOfGenoType() {
		return genotype.length;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(Arrays.toString(genotype));
		buf.append("\n");
		return buf.toString();
	}
	
//	public static void main(String[] args) {
//		List<Individual> r = new ArrayList<Individual>();
//		while(r.size()<10){
//			r.add(new DoubleIndividual(10, 1, true));
//		}
//		System.out.println(r);
//		List<PhenoType> q = new ArrayList<PhenoType>();
//		for (int i = 0; i < r.size(); i++) {
//			q.add(new DoublePhenoType(10));
//			q.get(i).convertFromGenoTypeToPhenoType(r.get(i).getGenoType(), 10);
//		}
//		System.out.println(q);
//		
//	}
}
