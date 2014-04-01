package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

import simpleslickgame.EAConnection;

public class Population <T>{
	private List<Individual<T>> individuals;
	private int numOfIndividuals;
	
	public Population(int numberOfIndividuals, int requiredGenotypeSize, int requiredNumberOfBits, boolean initializeRandomly, int individualType, EAConnection con){
		this.individuals = new ArrayList<Individual<T>>();
		for (int i = 0; i < numberOfIndividuals; i++) {
			this.individuals.add(getIndividualOfType(requiredGenotypeSize, requiredNumberOfBits, initializeRandomly, individualType,con));
		}
		this.numOfIndividuals = numberOfIndividuals;
	}
	
	public Population(int numberOfIndividuals, List<Individual<T>> individuals){
		this.numOfIndividuals = numberOfIndividuals;
		this.individuals = new ArrayList<Individual<T>>();
		for (Individual<T> individual : individuals) {
			this.individuals.add(individual);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Individual<T> getIndividualOfType(int requiredGenoTypeSize, int requiredNumberOfBits, boolean initializeRandomly, int IndividualType, EAConnection con){
		switch(IndividualType){
		case 0:
		case 1:
			return (Individual<T>) new BinaryIndividual(requiredGenoTypeSize, requiredNumberOfBits, initializeRandomly);
		case 2:
			return (Individual<T>) new DoubleIndividual(requiredGenoTypeSize, requiredNumberOfBits, initializeRandomly, con);
		default:
			return null;
		}
	}
	
	
	public List<Individual<T>> getIndividuals(){
		return individuals;
	}
	
	public void setIndividuals(List<Individual<T>> individuals){
		this.individuals = individuals;
	}
	
	public void removeIndividualAtPosition(int i){
		this.individuals.remove(i);
	}
	
	public void addIndividual(Individual<T> individual){
		this.individuals.add(individual);
	}
	
	public void updateIndividualAtPosition(int index, Individual<T> individual){
		this.individuals.set(index, individual);
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("Number of Individuals: " + numOfIndividuals + "\n");
		buf.append(individuals);
		buf.append("\n");
		return buf.toString();
	}
}
