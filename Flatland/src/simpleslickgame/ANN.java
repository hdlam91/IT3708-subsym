//package simpleslickgame;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class ANN {
//	
//	private ArrayList<Node> outputs;
//	private ArrayList<Node> hiddenNodes; 
//	private ArrayList<Node> inputs;
//	int numOfInput, numOfOutput;
//	boolean hiddenLayerUsed;
//	
//	int[] hiddenLayerStructure;
//	
//	public ANN(double treshold) {//no hidden layers
//		inputs= new  ArrayList<Node>();
//		
//		numOfInput = 6;
//		for (int i = 0; i < numOfInput; i++) {
//			inputs.add(new Node(true));
//		}
//			
//		
//		
//		hiddenLayerUsed = false;
//		
//		outputs = new ArrayList<Node>();
//		numOfOutput = 3;
//		for (int i = 0; i < numOfOutput; i++) {
//			outputs.add(new Node(treshold));
//			
//		}
//		
//	}
//	
//	
//	/*first hidden layer needs 6 w
//	//next needs nodeStructure[0] w
//	....
//	last needs nodeStructure[size-1] w
//	*/
//	public ANN(int[] nodeStructure, double treshold) {
//		this(treshold);
//		
//		hiddenNodes = new ArrayList<Node>();
//		for (int i = 0; i < nodeStructure.length; i++) {
//			if(nodeStructure[i]== 0 && i != 0){
//				System.out.println("ERROR, 0 nodes for the hidden layer at layer:" + i);
//			}
//			for (int j = 0; j < nodeStructure[i]; j++) {
//				this.hiddenNodes.add(new Node(treshold));
//			}
//		}
//		hiddenLayerStructure = nodeStructure.clone();
//		if(nodeStructure[0] == 0){
//			hiddenLayerUsed = false;
//		}
//		else{
//			hiddenLayerUsed = true;
//		}
//		
//	}
//	
//	public int getNumberOfWeightsNeeded(){
//		int numOfW = 0;
//		if(hiddenLayerUsed){
//		numOfW += numOfInput*hiddenLayerStructure[0];
//		for (int i = 1; i < hiddenLayerStructure.length; i++) {
//			numOfW += hiddenLayerStructure[i-1]*hiddenLayerStructure[i];
//		}
//		numOfW += numOfOutput*hiddenLayerStructure[hiddenLayerStructure.length-1];}
//		else{
//			numOfW = numOfInput*numOfOutput;
//		}
//		return numOfW;
//	}
//	
//
//	
//	
//
//	
//	
//	public void setWeight(double v[]){
//		int counter = 0;
//		int start = 0;
//		int end = 0;
//		if(hiddenLayerUsed){
//			for (int i = 0; i < hiddenLayerStructure.length; i++) {
//
//				for (int j = 0; j < hiddenLayerStructure[i]; j++) {
//					start = end;
//					if(i == 0){
//						end = start+numOfInput;
//					}
//					else
//						end = start+hiddenLayerStructure[i-1];
//					hiddenNodes.get(counter).setWeight(Arrays.copyOfRange(v, start, end));
//					counter++;
//
//				}
//			
//			
//			}
//			for (int i = hiddenNodes.size(); i < hiddenNodes.size()+numOfOutput; i++) {
//				outputs.get(i-hiddenNodes.size()).setWeight(Arrays.copyOfRange(v, end, end+hiddenLayerStructure[hiddenLayerStructure.length-1]));
//				end+=hiddenLayerStructure[hiddenLayerStructure.length-1];
//			}
//		}
//			
//		else{
//			for (int i = 0; i < outputs.size(); i++) {
//				outputs.get(i).setWeight(Arrays.copyOfRange(v, end, end+numOfInput));
//				end+=numOfInput;
//			}
//		}
//	}
//	
//	public void run(){
//		double firstOut[] = new double[6];
//		for (int i = 0; i < inputs.size(); i++) {
//			firstOut[i] = inputs.get(i).output();
//		}
//		if(hiddenLayerUsed){
//			double[] currentOut = firstOut;
//			int counter = 0;
//			for (int i = 0; i < hiddenLayerStructure.length; i++) {
//				double[] newLayer = new double[hiddenLayerStructure[i]];
//				for (int j = 0; j < hiddenLayerStructure[i]; j++) {				
//					
//					hiddenNodes.get(counter).input(currentOut);
//					newLayer[j] = hiddenNodes.get(counter).output(); 
//					counter++;
//				}
//				currentOut = newLayer;
//			}
//			for (int i = 0; i < outputs.size(); i++) {
//				outputs.get(i).input(currentOut);
//			}
//		}
//		else{ //no hidden layer, direct mapping.
////			System.out.println("inputNode's output:" + Arrays.toString(firstOut));
//			for (int i = 0; i < outputs.size(); i++) {
//				outputs.get(i).input(firstOut);
//			}
//		}
//	}
//	
//	
//	public void input(boolean[] input){
//		for (int i = 0; i < input.length; i++) {
//			inputs.get(i).input(input[i]);
//		}
//	}
//	
//	
//	public double getLeftMotor(){
//		return outputs.get(0).output();
//	}
//	public double getFrontMotor(){
//		return outputs.get(1).output();
//	}
//	public double getRightMotor(){
//		return outputs.get(2).output();
//	}
//	
//	
//	public int output(){
//		return 0;
//	}
//	
//	
//}
package simpleslickgame;

import java.util.ArrayList;
import java.util.Arrays;

public class ANN {
	
	private ArrayList<Node> outputs;
	private ArrayList<Node> hiddenNodes; 
	int numOfInput, numOfOutput;
	boolean hiddenLayerUsed;
	
	int[] hiddenLayerStructure;
	
	public ANN(double treshold, int inputs, int type) {//no hidden layers
		numOfInput = inputs;
		hiddenLayerUsed = false;
		
		outputs = new ArrayList<Node>();
		numOfOutput = 3;
		for (int i = 0; i < numOfOutput; i++) {
			outputs.add(new Node(treshold,type));
			
		}
		
	}
	
	
	/*first hidden layer needs 6 w
	//next needs nodeStructure[0] w
	....
	last needs nodeStructure[size-1] w
	*/
	public ANN(int[] nodeStructure, double treshold, int inputs, int type) {
		this(treshold, inputs, type);
		
		hiddenNodes = new ArrayList<Node>();
		for (int i = 0; i < nodeStructure.length; i++) {
			if(nodeStructure[i]== 0 && i != 0){
				System.out.println("ERROR, 0 nodes for the hidden layer at layer:" + i);
			}
			for (int j = 0; j < nodeStructure[i]; j++) {
				this.hiddenNodes.add(new Node(treshold,type));
			}
		}
		hiddenLayerStructure = nodeStructure.clone();
		if(nodeStructure[0] == 0){
			hiddenLayerUsed = false;
		}
		else{
			hiddenLayerUsed = true;
		}
		
	}
	
	public int getNumberOfWeightsNeeded(){
		int numOfW = 0;
		if(hiddenLayerUsed){
		numOfW += numOfInput*hiddenLayerStructure[0];
		for (int i = 1; i < hiddenLayerStructure.length; i++) {
			numOfW += hiddenLayerStructure[i-1]*hiddenLayerStructure[i];
		}
		numOfW += numOfOutput*hiddenLayerStructure[hiddenLayerStructure.length-1];}
		else{
			numOfW = numOfInput*numOfOutput;
		}
		return numOfW;
	}
	

	
	

	
	
	public void setWeight(double v[]){
		int counter = 0;
		int start = 0;
		int end = 0;
		if(hiddenLayerUsed){
			for (int i = 0; i < hiddenLayerStructure.length; i++) {

				for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					start = end;
					if(i == 0){
						end = start+numOfInput;
					}
					else
						end = start+hiddenLayerStructure[i-1];
					hiddenNodes.get(counter).setWeight(Arrays.copyOfRange(v, start, end));
					counter++;

				}
			
			
			}
			for (int i = hiddenNodes.size(); i < hiddenNodes.size()+numOfOutput; i++) {
				outputs.get(i-hiddenNodes.size()).setWeight(Arrays.copyOfRange(v, end, end+hiddenLayerStructure[hiddenLayerStructure.length-1]));
				end+=hiddenLayerStructure[hiddenLayerStructure.length-1];
			}
		}
			
		else{
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).setWeight(Arrays.copyOfRange(v, end, end+numOfInput));
				end+=numOfInput;
			}
		}
	}
	
	public void run(double [] inputs){
		if(hiddenLayerUsed){
			double[] currentOut = inputs.clone();
			int counter = 0;
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				double[] newLayer = new double[hiddenLayerStructure[i]];
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {				
					
					hiddenNodes.get(counter).input(currentOut);
					newLayer[j] = hiddenNodes.get(counter).output(); 
					counter++;
				}
				currentOut = newLayer;
			}
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).input(currentOut);
			}
		}
		else{ //no hidden layer, direct mapping.
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).input(inputs.clone());
			}
		}
	}
	
	
	
	
	
	public void input(boolean[] input){
		double[] converted = new double[input.length];
		for (int i = 0; i < converted.length; i++) {
			converted[i] = input[i]? 1 : 0;
		}
		run(converted);
	}
	
	
	public double getLeftMotor(){
		return outputs.get(0).output();
	}
	public double getFrontMotor(){
		return outputs.get(1).output();
	}
	public double getRightMotor(){
		return outputs.get(2).output();
	}
	
	
	public int output(){
		return 0;
	}
	
	
}