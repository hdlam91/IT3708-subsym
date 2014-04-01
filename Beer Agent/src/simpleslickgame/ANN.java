package simpleslickgame;

import java.util.ArrayList;
import java.util.Arrays;

public class ANN {
	
	
	private ArrayList<Node> outputs;
	private ArrayList<Node> hiddenNodes; 
	int numOfInput, numOfOutput;
	boolean hiddenLayerUsed;
	
	int[] hiddenLayerStructure;
	
	public ANN(double treshold, int inputs) {//no hidden layers
		hiddenLayerUsed = false;
		
		outputs = new ArrayList<Node>();
		for (int i = 0; i < 2; i++) {
			outputs.add(new Node(treshold));			
		}
		numOfInput = inputs;
		numOfOutput = outputs.size();
	}
	

	public ANN(int[] nodeStructure, double treshold, int inputs) {
		this(treshold, inputs);
		
		hiddenNodes = new ArrayList<Node>();
		for (int i = 0; i < nodeStructure.length; i++) {
			if(nodeStructure[i]== 0 && i != 0){
				System.out.println("ERROR, 0 nodes for the hidden layer at layer:" + i);
			}
			for (int j = 0; j < nodeStructure[i]; j++) {
				this.hiddenNodes.add(new Node(treshold));
			}
		}
		hiddenLayerStructure = nodeStructure.clone();
		if(nodeStructure.length == 0 || nodeStructure[0] == 0){
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
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				numOfW += hiddenLayerStructure[i]*hiddenLayerStructure[i]; //self weight
			}
			numOfW += numOfOutput*hiddenLayerStructure[hiddenLayerStructure.length-1];
		}
		else{
			numOfW = numOfInput*numOfOutput;
		}
		numOfW+= numOfOutput*numOfOutput;
		
		return numOfW;
	}
	
	public void test(){
  		if(hiddenLayerUsed)
  			for (int i = 0; i < hiddenNodes.size(); i++) {
  				System.out.println(hiddenNodes.get(i));
  			}
 		for (int i = 0; i < outputs.size(); i++) {
 			System.out.println(outputs.get(i));
  		}
 		System.out.println("\n\n\n" + getNumberOfWeightsNeeded());
  	}
	


 public static void main(String[] args) {
        int [] li = {3};
        ANN a = new ANN(li,0.5,5);
        double v[] = new double[30];
        for (int i = 0; i < v.length; i++) {
            v[i] = i;
        }   
        a.setWeight(v);
        a.test();
    }   
 	public int getNumberOfNodes(){
 		int numOfNodes = 0;
 		if(hiddenLayerUsed){
	 		for (int i = 0; i < hiddenLayerStructure.length; i++) {
	 			numOfNodes+= hiddenLayerStructure[i];
	 		}
 		}
 		numOfNodes += numOfOutput;
 		return numOfNodes;
			
 	}
	
 
 public int getNumberOfWeightsNeeded(double v[]){
		int numOfW = 0;
		if(hiddenLayerUsed){
			numOfW += numOfInput*hiddenLayerStructure[0];
			for (int i = 1; i < hiddenLayerStructure.length; i++) {
				numOfW += hiddenLayerStructure[i-1]*hiddenLayerStructure[i];
			}
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				numOfW += hiddenLayerStructure[i]; //bias weight
				numOfW += hiddenLayerStructure[i]*hiddenLayerStructure[i]; //self weight
			}
			numOfW += numOfOutput*hiddenLayerStructure[hiddenLayerStructure.length-1]+numOfOutput;
		}
		else{
			numOfW = numOfInput*numOfOutput+numOfOutput;
		}
		numOfW+= numOfOutput*numOfOutput;
		
		System.out.println(hiddenLayerStructure[0]);
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
//					if(i == 0)
//						hiddenNodes.get(counter).setInWeight(Arrays.copyOfRange(v, start, end));
//					else
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
		
		//setting neighbour weights and selfweight:
		counter = 0;
		if(hiddenLayerUsed){
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {
//					hiddenNodes.get(counter).;
					counter++;
					end+=hiddenLayerStructure[i]*hiddenLayerStructure[i];
				}
			}
		}
		
		
	}
	
	public void run(double[] input){
		double firstOut[] = new double[numOfInput];
		if(hiddenLayerUsed){
			double[] currentOut = input.clone();
			int counter = 0;
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				double[] newLayer = new double[hiddenLayerStructure[i]];
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {				
					
					hiddenNodes.get(counter).input(currentOut);
					newLayer[j] = hiddenNodes.get(counter).output(); 
					counter++;
//					System.out.println(counter + "/ "+ i + "..." + Arrays.toString(Arrays.copyOfRange(v, start, end)));
					System.out.println("Hidden layers output:" + Arrays.toString(currentOut));
				}
				currentOut = newLayer;
			}
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).input(currentOut);
			}
		}
		else{ //no hidden layer, direct mapping.
//			System.out.println("inputNode's output:" + Arrays.toString(firstOut));
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).input(firstOut);
			}
//			System.out.println("output:l" + outputs.get(0).output() + "f"+ outputs.get(1).output() + "r" + outputs.get(2).output());
		}
	}
	
	
	
	
	public void input(boolean [] b){
		double[] converted= new double[b.length];
		for (int i = 0; i < converted.length; i++) {
			converted[i] = b[i]? 1: 0;
		}
		run(converted);
	}
	
	
	public double getLeftMotor(){
		return outputs.get(0).output();
	}
	
	public double getRightMotor(){
		return outputs.get(1).output();
	}
	
	
	public int output(){
		return 0;
	}


	
	
}