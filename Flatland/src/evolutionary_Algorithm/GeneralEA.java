package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.List;

import simpleslickgame.EAConnection;

public class GeneralEA <T>{
	private Population<T> currentPopulation;
	private Population<T> previousPopulation;
	private Population<T> thirdPopulation;
	
	private List<PhenoType<T>> currentPhenoTypes;
	private List<PhenoType<T>> previousPhenoTypes;
	private List<PhenoType<T>> thirdPhenoTypes;
	
	private List<Double> currentPhenoTypeFitnessValues;
	private List<Double> previousPhenoTypeFitnessValues;
	private List<Double> thirdPhenoTypeFitnessValues;
	
	private FitnessEvaluator<T> currentFitnessEvaluator;
	private AdultSelection<T> currentAdultSelection;
	private ParentSelection<T> currentParentSelection;
	
	private CrossOver<T> currentCrossOver;
	private Mutation<T> currentMutation;
	
	private boolean goalReached;
	private int sizeOfPopulation;
	private int requiredSizeOfGenoType;
	private int requiredBitsOfGenoType;
	private int typeOfProblem;
	private int factor;
	
	private double crossoverRate;
	private double mutationRate;
	private boolean componentMutation;
	
	private EAConnection connection;
	
	public GeneralEA(int sizeOfPopulation, int requiredSizeOfGenotype, int requiredBitsOfGenoType, int typeOfProblem, int typeOfAdultSelection, 
						int typeOfParentSelection, double crossOverRate, double mutationRate, boolean componentMutation, int K, double P, boolean initializeRandomly, EAConnection connection){
		
		this.currentPopulation = new Population<T>(sizeOfPopulation, requiredSizeOfGenotype, requiredBitsOfGenoType, initializeRandomly, typeOfProblem);
		this.currentPhenoTypes = new ArrayList<PhenoType<T>>();
		
		this.currentFitnessEvaluator = getFitnessEvaluatorForProblemType(typeOfProblem,connection);
		this.currentAdultSelection = getAdultSelectionMethod(typeOfAdultSelection);
		
		this.currentParentSelection = getParentSelectionMethod(typeOfParentSelection,K,P);
		
		this.currentCrossOver = getTypeOfCrossOver(typeOfProblem);
		this.currentMutation = getTypeOfMutation(typeOfProblem);
		
		this.sizeOfPopulation = sizeOfPopulation;
		this.requiredSizeOfGenoType = requiredSizeOfGenotype;
		this.requiredBitsOfGenoType = requiredBitsOfGenoType;
		this.typeOfProblem = typeOfProblem;
		
		this.crossoverRate = crossOverRate;
		this.mutationRate = mutationRate;
		this.componentMutation = componentMutation;
		
		this.connection = connection;
		
		if(typeOfAdultSelection==0)
			this.factor = 1;
		else
			this.factor = 2;
		
		updateCurrentPhenoTypeFitnessValues();

		
		this.previousPopulation = currentPopulation;
		this.previousPhenoTypes = currentPhenoTypes;
		this.previousPhenoTypeFitnessValues = currentPhenoTypeFitnessValues;
		 
		this.thirdPhenoTypes = new ArrayList<PhenoType<T>>();
		this.thirdPhenoTypeFitnessValues = new ArrayList<Double>();
		
		this.goalReached = false;
		
		geneticLoop();
	}
	
	public void geneticLoop(){
		int iter = 0;
		double bestFitness = 0;
		System.out.println("Init:");
		System.out.println(currentPhenoTypeFitnessValues);
		while(!goalReached && iter<100){
			double bestFintessForThisIter = 0;
//			System.out.println("Iteration: " + iter);
//			System.out.println(currentPopulation==previousPopulation);
			
//			System.out.println(currentPopulation);
//			System.out.println(currentPhenoTypes);
//			System.out.println(currentPhenoTypeFitnessValues);
			if(iter == 0){
				this.thirdPopulation = new FullGenerationReplacement<T>().findAdultsFromPopulations(previousPopulation, currentPopulation, previousPhenoTypeFitnessValues, currentPhenoTypeFitnessValues, sizeOfPopulation);
			}
			else{
				this.thirdPopulation = currentAdultSelection.findAdultsFromPopulations(previousPopulation, currentPopulation, previousPhenoTypeFitnessValues, currentPhenoTypeFitnessValues, sizeOfPopulation);
			}
			updateThirdPhenoTypeFitnessValues();
			
//			System.out.println(thirdPhenoTypes);
//			System.out.println("adults:");
//			System.out.println(thirdPhenoTypeFitnessValues);
			
			List<Individual<T>> children = new ArrayList<Individual<T>>();
			List<Individual<T>> parents = thirdPopulation.getIndividuals();
			
			while(children.size()<sizeOfPopulation*factor){
				List<Individual<T>> tempParents = currentParentSelection.getSelectedParents(parents, thirdPhenoTypeFitnessValues,typeOfProblem,iter);
				if(Math.random()<crossoverRate){
					List<Individual<T>> tempChildren = currentCrossOver.getCrossoveredChildrenFromParents(tempParents.get(0), tempParents.get(1));
					for (Individual<T> individual : tempChildren) {
						children.add(individual);
					}
				}
				else{
					for (Individual<T> individual : tempParents) {
						children.add(individual);
					}
				}
			}
			
			//Mutation
			for (Individual<T> individual : children) {
				if(componentMutation)
					currentMutation.getMutatedIndividual(individual, mutationRate, componentMutation);
				else{
					if(Math.random()<mutationRate)
						currentMutation.getMutatedIndividual(individual, mutationRate, componentMutation);
				}
			}
			
			previousPopulation = thirdPopulation;
			previousPhenoTypes = thirdPhenoTypes;
			previousPhenoTypeFitnessValues = thirdPhenoTypeFitnessValues;
			
			currentPopulation = new Population<>(children.size(), children);
			updateCurrentPhenoTypeFitnessValues();
			for (PhenoType<T> p : currentPhenoTypes) {
				if(p.getGoalFulfilled())
					goalReached = true;
			}
			double sumOfFitnessValues = 0;
			for (Double val : currentPhenoTypeFitnessValues) {
				sumOfFitnessValues+=val;
				if(val>bestFintessForThisIter)
					bestFintessForThisIter = val;
				if(val>bestFitness)
					bestFitness = val;
			}
			double mean = sumOfFitnessValues/currentPhenoTypeFitnessValues.size();
			mean = round(mean,5);
			
			double sum2 = 0;
			for (Double val : currentPhenoTypeFitnessValues) {
				sum2 += Math.pow((mean-val), 2);
			}
			
			double std = Math.sqrt(sum2/currentPhenoTypeFitnessValues.size());
			
//			System.out.println(currentPhenoTypes);
//			System.out.println("children:");
//			System.out.println(currentPhenoTypes);
//			System.out.println(currentPhenoTypeFitnessValues);
//			System.out.println("Average fitness: " + mean);
//			System.out.println("Standard deviation :" + std);
//			System.out.println("best fitness: " + bestFintessForThisIter);
//			System.out.println("best fitness overall: " + bestFitness);
//			System.out.println();
			
			//Plot 
			System.out.println(iter + "\t" + mean + "\t" + std + "\t" + bestFintessForThisIter);
			
			
			iter++;
		}
		System.out.println();
		System.out.println("Final:");
		System.out.println(currentPhenoTypes);
		System.out.println(currentPhenoTypeFitnessValues);
		System.out.println("Best overall fitness: "+ bestFitness);
		System.out.println("Total number of Iterations: " + iter);
		
	}
	
	public void updateThirdPhenoTypeFitnessValues(){
		List<Individual<T>> individuals = thirdPopulation.getIndividuals();
		
		thirdPhenoTypes = new ArrayList<PhenoType<T>>();
		for (int i = 0; i < sizeOfPopulation; i++) {
			thirdPhenoTypes.add(getNewPhenotypeOfProblemType(typeOfProblem, requiredSizeOfGenoType, requiredBitsOfGenoType));
			thirdPhenoTypes.get(i).convertFromGenoTypeToPhenoType(individuals.get(i).getGenoType(), individuals.get(i).getLengthOfGenoType());
		}
		
		thirdPhenoTypeFitnessValues = new ArrayList<Double>();
		for (PhenoType<T> p : thirdPhenoTypes) {
			thirdPhenoTypeFitnessValues.add(currentFitnessEvaluator.getFitnessValue(p));
		}
	}
	
	public void updateCurrentPhenoTypeFitnessValues(){
		List<Individual<T>> individuals = currentPopulation.getIndividuals();
		
		currentPhenoTypes = new ArrayList<PhenoType<T>>();
		for (int i = 0; i < individuals.size(); i++) {
			currentPhenoTypes.add(getNewPhenotypeOfProblemType(typeOfProblem, requiredSizeOfGenoType, requiredBitsOfGenoType));
			currentPhenoTypes.get(i).convertFromGenoTypeToPhenoType(individuals.get(i).getGenoType(), individuals.get(i).getLengthOfGenoType());
		}
		currentPhenoTypeFitnessValues = new ArrayList<Double>();
		for (PhenoType<T> p : currentPhenoTypes) {
			currentPhenoTypeFitnessValues.add(currentFitnessEvaluator.getFitnessValue(p));
		}
	}
	
	@SuppressWarnings("unchecked")
	public PhenoType<T> getNewPhenotypeOfProblemType(int type, int sizeOfGenoType, int requiredBits){
		switch(type){
			case 0:
			case 1:
				return (PhenoType<T>) new BinaryPhenoType(sizeOfGenoType);
			case 2:
				return (PhenoType<T>) new DoublePhenoType(sizeOfGenoType);
			default:
				return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public FitnessEvaluator<T> getFitnessEvaluatorForProblemType(int type, EAConnection con){
		switch(type){
		case 0:
			return (FitnessEvaluator<T>) new BinaryFitnessEvaluator();
		case 1:
			return (FitnessEvaluator<T>) new SpecificBitStringFitnessEvaluator();
		case 2:
			return (FitnessEvaluator<T>) new DoubleFitnessEvaluator(con);
		default:
			return null;
		}
	}
	
	public AdultSelection<T> getAdultSelectionMethod(int type){
		switch(type){
		case 0:
			return new FullGenerationReplacement<T>();
		case 1:
			return new OverProductionReplacement<T>();
		case 2:
			return new GenerationalMixing<T>();
		default:
			return null;	
		}
	}
	
	public ParentSelection<T> getParentSelectionMethod(int type, int K, double P){
		switch(type){
		case 0:
			return new FitnessProportionate<T>();
		case 1:
			return new TournamentSelection<>(K, P);
		case 2:
			return new SigmaScaling<>();
		case 3:
			return new EliteSelection<>();
		default:
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public CrossOver<T> getTypeOfCrossOver(int type){
		switch(type){
		case 0:
		case 1:
			return (CrossOver<T>) new BinaryCrossOver();
		case 2:
			return (CrossOver<T>) new DoubleCrossOver();
		default:
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Mutation<T> getTypeOfMutation(int type){
		switch(type){
		case 0:
		case 1:
			return (Mutation<T>) new BinaryMutation();
		case 2:
			return (Mutation<T>) new DoubleMutation();
		default:
			return null;
		}
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append(currentPopulation);
		buf.append("\n");
		for (PhenoType<T> p : currentPhenoTypes) {
			buf.append(p);
		}
		buf.append("FitnessValues:\n");
		buf.append(currentPhenoTypeFitnessValues);
		return buf.toString();
	}
	
	public static double round(double value, int places){
		if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
