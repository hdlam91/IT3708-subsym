package simpleslickgame;

import java.util.ArrayList;
import java.util.Arrays;

public class ANN {
	
	private Node foodInputLeft, foodInputFront, foodInputRight;
	private Node poisonInputLeft, poisonInputFront, poisonInputRight;
	private Node leftOutput, frontOutput, rightOutput;
	private Node[] weights;
	int numOfInput, numOfOutput;
	boolean hiddenLayerUsed;
	
	int[] hiddenLayerStructure;
	private ArrayList<Node> hiddenNodes; 
	
	public ANN() {//no hidden layers
		foodInputLeft = new Node();
		foodInputFront = new Node();
		foodInputRight = new Node();
		poisonInputLeft = new Node();
		poisonInputFront = new Node();
		poisonInputRight = new Node();
		
		hiddenLayerUsed = false;
		leftOutput = new Node();
		frontOutput = new Node();
		rightOutput = new Node();
		
		weights = new Node[3];
		weights[0] = leftOutput;
		weights[1] = frontOutput;
		weights[2] = rightOutput;
		hiddenNodes = new ArrayList<Node>();
		hiddenNodes.add(leftOutput);
		hiddenNodes.add(frontOutput);
		hiddenNodes.add(rightOutput);
		numOfInput = 6;
		numOfOutput = 3;
		
	}

	public ANN(int[] nodeStructure) {
		foodInputLeft = new Node();
		foodInputFront = new Node();
		foodInputRight = new Node();
		poisonInputLeft = new Node();
		poisonInputFront = new Node();
		poisonInputRight = new Node();
		leftOutput = new Node();
		frontOutput = new Node();
		rightOutput = new Node();
		
		hiddenNodes = new ArrayList<Node>();
		for (int i = 0; i < nodeStructure.length; i++) {
			if(nodeStructure[i]== 0 && i != 0){
				System.out.println("ERROR, 0 nodes for the hidden layer at layer:" + i);
			}
			for (int j = 0; j < nodeStructure[i]; j++) {
				this.hiddenNodes.add(new Node());
			}
		}
		hiddenLayerStructure = nodeStructure.clone();
		if(nodeStructure.length == 1 && nodeStructure[0] == 0){
			hiddenLayerUsed = false;
		}
		else{
			hiddenLayerUsed = true;
		}
		/*first hidden layer needs 6 w
		//next needs nodeStructure[0] w
		....
		last needs nodeStructure[size-1] w
		*/
		hiddenNodes.add(leftOutput);
		hiddenNodes.add(frontOutput);
		hiddenNodes.add(rightOutput);
		weights = new Node[3];
		weights[0] = leftOutput;
		weights[1] = frontOutput;
		weights[2] = rightOutput;
		
		numOfInput = 6;
		numOfOutput = 3;
		
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
	
	
	public void test(){
		if(hiddenLayerUsed)
			for (int i = 0; i < hiddenNodes.size(); i++) {
				System.out.println(hiddenNodes.get(i));
			}
		for (int i = 0; i < weights.length; i++) {
			System.out.println(weights[i]);
		}
		System.out.println("\n\n\n" + getNumberOfWeightsNeeded());
	}
	
	public static void main(String[] args) {
		int [] li = {1,2,3};
		ANN a = new ANN(li);
		double v[] = new double[30];
		for (int i = 0; i < v.length; i++) {
			v[i] = i;
		}
		a.setWeight(v);
		a.test();
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
			for (int i = hiddenNodes.size()-numOfOutput; i < hiddenNodes.size(); i++) {
				hiddenNodes.get(i).setWeight(Arrays.copyOfRange(v, end, end+hiddenLayerStructure[hiddenLayerStructure.length-1]));
				end+=hiddenLayerStructure[hiddenLayerStructure.length-1];
			}
		}
			
		else{
			for (int i = hiddenNodes.size()-numOfOutput; i < hiddenNodes.size(); i++) {
				hiddenNodes.get(i).setWeight(Arrays.copyOfRange(v, end, end+numOfInput));
				end+=numOfInput;
			}
		}
	}
	
	public void run(){
		
	}
	
	
	
	
	public void input(boolean fleft, boolean ffront, boolean fright,boolean pleft, boolean pfront, boolean pright){
		//DO something		
		foodInputLeft.input(fleft);
		foodInputFront.input(ffront);
		foodInputRight.input(fright);
		
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
