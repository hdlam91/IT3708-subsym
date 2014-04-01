package evolutionary_Algorithm;

public abstract class PhenoType <T>{
	protected int[] representation;
	private boolean fulfillsGoal;
	
	public PhenoType(int numberOfFields){
		representation = new int[numberOfFields];
		this.fulfillsGoal = false;
	}
	
	public abstract String toString();
	public abstract void convertFromGenoTypeToPhenoType(T[] genotype, int lengthOfGenoType);
	
	public int[] getPhenoTypeValues(){
		return this.representation;
	}
	
	public int getLengthOfPhenotype(){
		return this.representation.length;
	}
	
	public boolean getGoalFulfilled(){
		return this.fulfillsGoal;
	}
	
	public void setGoalFulfilled(boolean val){
		this.fulfillsGoal = val;
	}
}
