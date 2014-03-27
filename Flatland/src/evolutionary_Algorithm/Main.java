package evolutionary_Algorithm;

public class Main {
	public static void main(String[] args) {
		int K = 20;
		double P = 0.8;
		
		int numberOfIndividuals = 200;
		int numberOfFieldsPerGenoType = 40;
		int requiredNumberOfBitsForGenoType = 1;
		
		int typeOfProblem = 0;
		int adultType = 1;
		int parentType = 0;
		
		double crossOverRate = 0.7;
		double mutationRate = 0.01;
		
		boolean mutationPerComponent = true;
		boolean initializeRandomly = true;
		
		GeneralEA s = new GeneralEA(numberOfIndividuals, numberOfFieldsPerGenoType, requiredNumberOfBitsForGenoType, 
									typeOfProblem, adultType, parentType, crossOverRate, mutationRate, mutationPerComponent, K, P, initializeRandomly,null);
	}
}
