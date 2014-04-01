package evolutionary_Algorithm;

import java.util.Arrays;

import simpleslickgame.EAConnection;

public class Main {
	public static void main(String[] args) {
		int K = 20;
		double P = 0.8;
		
		int numberOfIndividuals = 200;
//		int numberOfFieldsPerGenoType = 40;
		int requiredNumberOfBitsForGenoType = 1;
		
		int typeOfProblem = 2;
		int adultType = 2;
		int parentType = 1;
		
		double crossOverRate = 0.1;
		double mutationRate = 0.2;
		
		boolean mutationPerComponent = true;
		boolean initializeRandomly = true;
		
//		GeneralEA s = new GeneralEA(numberOfIndividuals, numberOfFieldsPerGenoType, requiredNumberOfBitsForGenoType, 
//									typeOfProblem, adultType, parentType, crossOverRate, mutationRate, mutationPerComponent, K, P, initializeRandomly,null);
		
		EAConnection con = new EAConnection();
		int numberofWeights = con.getNumberOfWeightsNeeded();
		int numberofNodes = con.getNumberOfNodesNeeded()*3;
		
		int numberOfFields = numberofWeights+numberofNodes;
		
		GeneralEA s = new GeneralEA(numberOfIndividuals,numberOfFields,requiredNumberOfBitsForGenoType,
				typeOfProblem,adultType,parentType,crossOverRate,mutationRate,mutationPerComponent,K,P,initializeRandomly,con,true);
		System.out.println(Arrays.toString(s.getWeightsOfBestIndividual()));
	}
}
