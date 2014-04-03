package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import simpleslickgame.EAConnection;

public class DoubleIndividual extends Individual<Integer>{

	private final int wfactor = 5001;
	private final int bfactor = 10001;
	private final int gfactor = 5001;
	private final int lowGfactor = 1000;
	private final int tfactor = 2001;
	private final int lowTfactor = 1000;
	
	private int numW,numN;
	
	public DoubleIndividual(int sizeOfGenoType, int requiredNumberOfBits, boolean initializeRandomly, EAConnection con){
		this.genotype = new Integer[sizeOfGenoType];
		//sizeOFGenoType = numW+(numN*3)

		this.numW = con.getNumberOfWeightsNeeded();
		this.numN = con.getNumberOfNodesNeeded();
		
		if(initializeRandomly){
			for (int i = 0; i < numW; i++) {
				this.genotype[i] = (int)(Math.random()*wfactor)*(Math.random()>0.5?1:-1);
			}
			for (int i = numW; i < numN+numW; i++) {
				this.genotype[i] = (int)(Math.random()*bfactor)*-1;
			}
			for (int i = numW+numN; i < numN*2+numW; i++) {
				this.genotype[i] = (int)(Math.random()*(gfactor-lowGfactor))+lowGfactor;
			}
			for (int i = numN*2+numW; i < numN*3+numW; i++) {
				this.genotype[i] = (int)(Math.random()*(tfactor-lowTfactor))+lowTfactor;
			}
		}
	}
	
	public DoubleIndividual(Integer[] genotype, int numW, int numN){
		this.genotype = genotype.clone();
		this.numW = numW;
		this.numN = numN;
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
	
	public int getNumW(){
		return numW;
	}
	
	public int getNumN(){
		return numN;
	}
	
	public int getWfactor(){
		return wfactor;
	}
	
	public int getBfactor(){
		return bfactor;
	}
	
	public int getGfactor(){
		return gfactor;
	}
	
	public int getLowGfactor(){
		return lowGfactor;
	}
	
	public int getTfactor(){
		return tfactor;
	}
	
	public int getLowTfactor(){
		return lowTfactor;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(Arrays.toString(genotype));
		buf.append("\n");
		return buf.toString();
	}
	
	public static void main(String[] args) {
		EAConnection ea = new EAConnection();
		List<Individual> r = new ArrayList<Individual>();
		
		int num = ea.getNumberOfNodesNeeded()*3+ ea.getNumberOfWeightsNeeded();
		
		while(r.size()<20){
			r.add(new DoubleIndividual(num, 1, true, ea));
		}
		System.out.println(r);
		List<PhenoType> q = new ArrayList<PhenoType>();
		for (int i = 0; i < r.size(); i++) {
			q.add(new DoublePhenoType(num));
			q.get(i).convertFromGenoTypeToPhenoType(r.get(i).getGenoType(), num);
		}
		System.out.println(q);
		DoubleMutation m = new DoubleMutation();
		for (Individual ind : r) {
			m.getMutatedIndividual(ind, 1.0, true);
		}
		q = new ArrayList<PhenoType>();
		for (int i = 0; i < r.size(); i++) {
			q.add(new DoublePhenoType(num));
			q.get(i).convertFromGenoTypeToPhenoType(r.get(i).getGenoType(), num);
		}
		System.out.println(q);
	}
}
