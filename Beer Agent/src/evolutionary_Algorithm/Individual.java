package evolutionary_Algorithm;

public abstract class Individual<T> {
	
	protected T[] genotype;
	
	public abstract T[] getGenoType();
	public abstract void setGenoType(T[] genotype);
	public abstract String toString();
	public abstract int getLengthOfGenoType();
}
