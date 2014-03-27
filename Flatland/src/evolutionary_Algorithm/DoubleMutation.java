package evolutionary_Algorithm;

public class DoubleMutation extends Mutation<Integer>{

	@Override
	public void getMutatedIndividual(Individual<Integer> person,Double mutationRate, boolean perComponent) {
		Integer[] genoType = (Integer[]) person.getGenoType();
		
		if(perComponent){
			for (int i = 0; i < genoType.length; i++) {
				if(Math.random()<mutationRate)
					genoType[i] = (int)(Math.random()>0.5? (((Math.random()*(genoType[i]*1.1-genoType[i]*0.9))+genoType[i]*0.9+1)%1001) : (Math.random()*1001)); //10% diff or new random val
			}
		}
		else{
			int index = (int)(Math.random()*genoType.length);
			genoType[index] = (int)(Math.random()>0.5? (((Math.random()*(genoType[index]*1.1-genoType[index]*0.9))+genoType[index]*0.9+1)%1001) : (Math.random()*1001)); //10% diff or new random val
		}
	}
}
