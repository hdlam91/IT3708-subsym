package simpleslickgame;

import java.util.Arrays;

public class ANN {
	
	private Node foodInputLeft, foodInputFront, foodInputRight;
	private Node poisonInputLeft, poisonInputFront, poisonInputRight;
	private Node leftOutput, frontOutput, rightOutput;
	private Node[] weights;
	int numberOfNode;
	public ANN() {
		foodInputLeft = new Node();
		foodInputFront = new Node();
		foodInputRight = new Node();
		poisonInputLeft = new Node();
		poisonInputFront = new Node();
		poisonInputRight = new Node();
		
		numberOfNode = 6;
		
		leftOutput = new Node();
		frontOutput = new Node();
		rightOutput = new Node();
		
		weights = new Node[3];
		weights[0] = leftOutput;
		weights[1] = frontOutput;
		weights[2] = rightOutput;
		
	}
	
	public void test(){
		for (int i = 0; i < weights.length; i++) {
			System.out.println(weights[i]);
		}
	}
	
	public static void main(String[] args) {
		ANN a = new ANN();
		double v[] = new double[30];
		for (int i = 0; i < v.length; i++) {
			v[i] = i;
		}
		a.setWeight(v);
		a.test();
	}
	
	public void setWeight(double v[]){
		 double v1[] = Arrays.copyOfRange(v, 0, v.length/weights.length);
		 double v2[] = Arrays.copyOfRange(v, v.length/weights.length, (v.length/weights.length)*2);
		 double v3[] = Arrays.copyOfRange(v, (v.length/weights.length)*2, (v.length));
		 weights[0].setWeight(v1);
		 weights[1].setWeight(v2);
		 weights[2].setWeight(v3);
		
	}
	
	
	
	
	public void input(boolean fleft, boolean ffront, boolean fright,boolean pleft, boolean pfront, boolean pright){
		//DO something		
		foodInputLeft.input(fleft);
		foodInputFront.input(ffront);
		foodInputRight.input(fright);
		
	}
	
	
	public int getLeftMotor(){
		return leftOutput.output();
	}
	public int getFrontMotor(){
		return frontOutput.output();
	}
	public int getRightMotor(){
		return rightOutput.output();
	}
	
	
	public int output(){
		return 0;
	}
	
	
}
