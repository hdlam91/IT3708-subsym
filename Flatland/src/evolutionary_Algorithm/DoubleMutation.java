package evolutionary_Algorithm;

public class DoubleMutation extends Mutation<Integer>{

	@Override
	public void getMutatedIndividual(Individual<Integer> person,Double mutationRate, boolean perComponent) {
		Integer[] genoType = (Integer[]) person.getGenoType();
		
		if(perComponent){
			for (int i = 0; i < genoType.length; i++) {
				if(Math.random()<mutationRate)
					genoType[i] = 0; //FIX
			}
		}
		else{
			int index = (int)(Math.random()*genoType.length);
			genoType[index] = 0; //FIX
		}
	}
}
