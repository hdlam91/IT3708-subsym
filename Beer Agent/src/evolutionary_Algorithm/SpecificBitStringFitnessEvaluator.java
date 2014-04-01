package evolutionary_Algorithm;

public class SpecificBitStringFitnessEvaluator extends FitnessEvaluator<Boolean>{

	int[] goalMatrix = {0,0,1,0,1,1,1,0,0,0};
	
	@Override
	public double getFitnessValue(PhenoType<Boolean> phenoType) {
		BinaryPhenoType temp = (BinaryPhenoType)phenoType;
		
		int totalTimesNeeded = temp.getLengthOfPhenotype()/goalMatrix.length;
		
		int numberToCheckAgainst = 0;
		int sum = 0;

		for (int i = 0; i < totalTimesNeeded; i++) {
			for (int j = 0; j < goalMatrix.length; j++) {
				numberToCheckAgainst = goalMatrix[j];
				if(temp.getPhenoTypeValues()[i*goalMatrix.length+j]==numberToCheckAgainst)
					sum++;
			}			
		}
		
		if(sum==temp.getLengthOfPhenotype())
			temp.setGoalFulfilled(true);
		return (double) sum/temp.getLengthOfPhenotype();
	}
}
