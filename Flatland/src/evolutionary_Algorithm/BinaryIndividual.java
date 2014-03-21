package evolutionary_Algorithm;

import java.util.Arrays;

public class BinaryIndividual extends Individual<Boolean>{
	
	
	public BinaryIndividual(int sizeOfGenoType, int requiredNumberOfBits, boolean initializeRandomly){
		this.genotype = new Boolean[sizeOfGenoType*requiredNumberOfBits];
		if(initializeRandomly){
			for (int i = 0; i < genotype.length; i++) {
				this.genotype[i] = (Math.random()>0.5)? true:false;
			}
		}
	}
	
	public BinaryIndividual(Boolean[] genotype){
		this.genotype = genotype.clone();
	}
	
	public Boolean[] getGenoType(){
		return this.genotype;
	}
	
	public void setGenoType(Boolean[] genoType){
		this.genotype = genoType;
	}
	
	public int getLengthOfGenoType(){
		return genotype.length;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append(Arrays.toString(genotype));
		buf.append("\n");
		return buf.toString();
	}
}
