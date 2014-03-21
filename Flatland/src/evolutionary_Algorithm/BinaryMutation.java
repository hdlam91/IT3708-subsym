package evolutionary_Algorithm;

public class BinaryMutation extends Mutation<Boolean>{
	
	public void getMutatedIndividual(Individual<Boolean> person, Double mutationRate, boolean perComponent){
		Boolean[] genoType = (Boolean[]) person.getGenoType();
		
		if(perComponent){
			for (int i = 0; i < genoType.length; i++) {
				if(Math.random()<mutationRate)
					genoType[i] = !genoType[i];
			}
		}
		else{
			int index = (int)(Math.random()*genoType.length);
			genoType[index] = !genoType[index];
		}
	}
}
