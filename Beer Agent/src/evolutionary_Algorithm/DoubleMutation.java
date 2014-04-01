package evolutionary_Algorithm;

public class DoubleMutation extends Mutation<Integer>{

	@Override
	public void getMutatedIndividual(Individual<Integer> person,Double mutationRate, boolean perComponent) {
		DoubleIndividual ind = (DoubleIndividual) person;
		Integer[] genoType = (Integer[]) person.getGenoType();
		
		if(perComponent){
			for (int i = 0; i < genoType.length; i++) {
				if(Math.random()<mutationRate){
					if(i<ind.getNumW())
						genoType[i] = (int)(Math.random()>0.5? (((Math.random()*(genoType[i]*1.1-genoType[i]*0.9))+genoType[i]*0.9+1)%ind.getWfactor()) : (Math.random()*ind.getWfactor())*(Math.random()>0.5?1:-1)); //10% diff or new random val
					else if(i<(ind.getNumN()+ind.getNumW()))
						genoType[i] = (int)(Math.random()>0.5? (((Math.random()*(genoType[i]*1.1-genoType[i]*0.9))+genoType[i]*0.9+1)%ind.getBfactor()) : (Math.random()*ind.getBfactor())*-1); //10% diff or new random val
					else if(i<(ind.getNumN()*2+ind.getNumW())){
						genoType[i] = (int)(Math.random()>0.5? (((Math.random()*(genoType[i]*1.1-genoType[i]*0.9))+genoType[i]*0.9+1)) : (Math.random()*(ind.getGfactor()-ind.getLowGfactor()))+ind.getLowGfactor()); //10% diff or new random val	
						genoType[i] = (genoType[i]>ind.getGfactor()?(genoType[i]%ind.getGfactor())+ind.getLowGfactor():genoType[i]);
					}
					else{
						genoType[i] = (int)(Math.random()>0.5? (((Math.random()*(genoType[i]*1.1-genoType[i]*0.9))+genoType[i]*0.9+1)) : (Math.random()*(ind.getTfactor()-ind.getLowTfactor()))+ind.getLowTfactor()); //10% diff or new random val	
						genoType[i] = (genoType[i]>ind.getTfactor()?(genoType[i]%ind.getTfactor())+ind.getLowTfactor():genoType[i]);
					}
				}
			}
		}
		else{
			int index = (int)(Math.random()*genoType.length);
			if(index<ind.getNumW())
				genoType[index] = (int)(Math.random()>0.5? (((Math.random()*(genoType[index]*1.1-genoType[index]*0.9))+genoType[index]*0.9+1)%ind.getWfactor()) : (Math.random()*ind.getWfactor())*(Math.random()>0.5?1:-1)); //10% diff or new random val
			else if(index<(ind.getNumN()+ind.getNumW()))
				genoType[index] = (int)(Math.random()>0.5? (((Math.random()*(genoType[index]*1.1-genoType[index]*0.9))+genoType[index]*0.9+1)%ind.getBfactor()) : (Math.random()*ind.getBfactor())*-1); //10% diff or new random val
			else if(index<(ind.getNumN()*2+ind.getNumW())){
				genoType[index] = (int)(Math.random()>0.5? (((Math.random()*(genoType[index]*1.1-genoType[index]*0.9))+genoType[index]*0.9+1)) : (Math.random()*(ind.getGfactor()-ind.getLowGfactor()))+ind.getLowGfactor()); //10% diff or new random val	
				genoType[index] = (genoType[index]>ind.getGfactor()?(genoType[index]%ind.getGfactor())+ind.getLowGfactor():genoType[index]);
			}
			else{
				genoType[index] = (int)(Math.random()>0.5? (((Math.random()*(genoType[index]*1.1-genoType[index]*0.9))+genoType[index]*0.9+1)) : (Math.random()*(ind.getTfactor()-ind.getLowTfactor()))+ind.getLowTfactor()); //10% diff or new random val	
				genoType[index] = (genoType[index]>ind.getTfactor()?(genoType[index]%ind.getTfactor())+ind.getLowTfactor():genoType[index]);
			}
		}
	}
}

