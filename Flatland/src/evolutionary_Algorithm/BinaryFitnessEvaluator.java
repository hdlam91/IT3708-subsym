package evolutionary_Algorithm;

public class BinaryFitnessEvaluator extends FitnessEvaluator<Boolean>{

	@Override
	public double getFitnessValue(PhenoType<Boolean> phenoType) {
		// TODO Auto-generated method stub
		BinaryPhenoType temp = (BinaryPhenoType)phenoType;
		double sumOfOnes = 0;
		for (Integer i : temp.getPhenoTypeValues()) {
			sumOfOnes +=i;
		}
		double retVal = sumOfOnes/temp.getLengthOfPhenotype();
		if(retVal==1.0)
			temp.setGoalFulfilled(true);
		return retVal;
	}
}
