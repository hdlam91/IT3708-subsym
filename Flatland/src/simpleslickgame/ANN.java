package simpleslickgame;

import java.util.ArrayList;
import java.util.Arrays;

public class ANN {
	
	private Node foodInputLeft, foodInputFront, foodInputRight;
	private Node poisonInputLeft, poisonInputFront, poisonInputRight;
	private Node leftOutput, frontOutput, rightOutput;
	private ArrayList<Node> outputs;
	private ArrayList<Node> hiddenNodes; 
	private ArrayList<Node> inputs;
	int numOfInput, numOfOutput;
	boolean hiddenLayerUsed;
	
	int[] hiddenLayerStructure;
	
	public ANN(double treshold) {//no hidden layers
		foodInputLeft = new Node(true);
		foodInputFront = new Node(true);
		foodInputRight = new Node(true);
		poisonInputLeft = new Node(true);
		poisonInputFront = new Node(true);
		poisonInputRight = new Node(true);
		
		inputs= new  ArrayList<Node>();
		inputs.add(foodInputLeft);
		inputs.add(foodInputFront);
		inputs.add(foodInputRight);
		inputs.add(poisonInputLeft);
		inputs.add(poisonInputFront);
		inputs.add(poisonInputRight);
		
		hiddenLayerUsed = false;
		leftOutput = new Node(treshold);
		frontOutput = new Node(treshold);
		rightOutput = new Node(treshold);
		
		outputs = new ArrayList<Node>();
		outputs.add(leftOutput);
		outputs.add(frontOutput);
		outputs.add(rightOutput);
		numOfInput = 6;
		numOfOutput = outputs.size();
		
	}
	
	
	/*first hidden layer needs 6 w
	//next needs nodeStructure[0] w
	....
	last needs nodeStructure[size-1] w
	*/
	public ANN(int[] nodeStructure, double treshold) {
//		foodInputLeft = new Node();
//		foodInputFront = new Node();
//		foodInputRight = new Node();
//		poisonInputLeft = new Node();
//		poisonInputFront = new Node();
//		poisonInputRight = new Node();
//		leftOutput = new Node();
//		frontOutput = new Node();
//		rightOutput = new Node();
		this(treshold);
		
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
		if(nodeStructure[0] == 0){
			hiddenLayerUsed = false;
		}
		else{
			hiddenLayerUsed = true;
		}
		
//		outputs = new ArrayList<Node>();
//		outputs.add(leftOutput);
//		outputs.add(frontOutput);
//		outputs.add(rightOutput);
//		
//		numOfInput = 6;
//		numOfOutput = outputs.size();		
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
//					System.out.println(counter + "/ "+ i + "..." + Arrays.toString(Arrays.copyOfRange(v, start, end)));

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
	
	public void run(){
		double firstOut[] = new double[6];
		for (int i = 0; i < inputs.size(); i++) {
			firstOut[i] = inputs.get(i).output();
		}
		if(hiddenLayerUsed){
			double[] currentOut = firstOut;
			int counter = 0;
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				double[] newLayer = new double[hiddenLayerStructure[i]];
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {				
					
					hiddenNodes.get(counter).input(currentOut);
					newLayer[j] = hiddenNodes.get(counter).output(); 
					counter++;
//					System.out.println(counter + "/ "+ i + "..." + Arrays.toString(Arrays.copyOfRange(v, start, end)));
//					System.out.println("Hidden layers output:" + Arrays.toString(currentOut));
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
	
	
	
	
	public void input(boolean fleft, boolean ffront, boolean fright,boolean pleft, boolean pfront, boolean pright){
		//DO something		
		foodInputLeft.input(fleft);
		foodInputFront.input(ffront);
		foodInputRight.input(fright);
		poisonInputLeft.input(pleft);
		poisonInputFront.input(pfront);
		poisonInputRight.input(pright);
//		System.out.println("INPUTS:");
//		System.out.println(fleft);
//		System.out.println(ffront);
//		System.out.println(fright);
//		System.out.println(pleft);
//		System.out.println(pfront);
//		System.out.println(pright);
	}
	
	
	public double getLeftMotor(){
		return leftOutput.output();
	}
	public double getFrontMotor(){
		return frontOutput.output();
	}
	public double getRightMotor(){
		return rightOutput.output();
	}
	
	
	public int output(){
		return 0;
	}
	
	
}
