package simpleslickgame;

public class ANN {
	
	private Node foodInputLeft, foodInputFront, foodInputRight;
	private Node poisonInputLeft, poisonInputFront, poisonInputRight;
	private Node leftOutput, frontOutput, rightOutput;
	
	public ANN() {
		foodInputLeft = new Node();
		foodInputFront = new Node();
		foodInputRight = new Node();
		poisonInputLeft = new Node();
		poisonInputFront = new Node();
		poisonInputRight = new Node();
		
		leftOutput = new Node();
		frontOutput = new Node();
		rightOutput = new Node();
		
		
	}
	
	
	
	
	public void input(boolean fleft, boolean ffront, boolean fright,boolean pleft, boolean pfront, boolean pright){
		//DO something
		
		
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
