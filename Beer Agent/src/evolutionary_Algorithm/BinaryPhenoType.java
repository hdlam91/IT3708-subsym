package evolutionary_Algorithm;

import java.util.Arrays;

public class BinaryPhenoType extends PhenoType<Boolean>{

	public BinaryPhenoType(int numberOfFields) {
		super(numberOfFields);
	}

	@Override
	public String toString() {
		return Arrays.toString(representation)+ " Goal: " + getGoalFulfilled() +"\n";
	}

	@Override
	public void convertFromGenoTypeToPhenoType(Boolean[] genotype,int lengthOfPhenoType) {
		for (int i = 0; i < lengthOfPhenoType; i++) {
			this.representation[i] = (genotype[i])?1:0;
		}
	}

}
