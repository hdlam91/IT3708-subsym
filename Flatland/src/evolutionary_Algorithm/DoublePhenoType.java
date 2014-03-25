package evolutionary_Algorithm;

import java.util.Arrays;

public class DoublePhenoType extends PhenoType<Integer>{

	protected double[] representation;
	
	public DoublePhenoType(int numberOfFields) {
		super(numberOfFields);
		this.representation = new double[numberOfFields];
	}

	@Override
	public String toString() {
		return (Arrays.toString(representation)+"\n");
	}

	@Override
	public void convertFromGenoTypeToPhenoType(Integer[] genotype, int lengthOfGenoType) {
		for (int i = 0; i < getLengthOfPhenotype(); i++) {
			this.representation[i] = genotype[i]/1000.0;
		}
	}
}
