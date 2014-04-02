package simpleslickgame;

import java.util.Arrays;

public class BeerAgent {
	
	int posX;
	int sizeX;
	ANN network;
	boolean[] sensors;
	Board board;
	int time;
	boolean running;
	public BeerAgent(ANN ann, Board board) {
		sensors = new boolean[5];
		network = ann;
		sizeX = 30;
		time = 0;
		posX = 0;
		this.board = board;
		running= false;
	}
	
	public Board getBoard(){
		return board;
	}
	
	
	public int getTimeStep(){
		return time;
	}
	
	public void reset(){
		time = 0;
		posX = 0;
	}
	
	public void test(){
		
		posX = safeX(posX+1);
		updateSensor();
		System.out.println(posX);
		System.out.println(board);
		System.out.println(Arrays.toString(sensors));
	}
	
//	public void run(int n){
//		for (int i = 0; i < n; i++) {
//			update();
//			
//		}
//	}
	
	public void setRUnninng(){
		running = true;
	}
	
	public void update(){
		//using ann
		updateSensor();
		network.input(sensors);
		double left = network.getLeftMotor();
		double right = network.getRightMotor();
		if(running) System.out.println("l:" + left + " r:" + right);
		
		int moving = (int)(right*4+1) -(int)(left*4+1); 
		if(left == right){
			
		}
		else if(left > right){
			posX = safeX(posX-(int)(left*5));
		}
		else
			posX = safeX(posX+(int)(right*5));
		time++;
		updateSensor();
	}
	
	private void updateSensor(){
		for (int i = 0; i < sensors.length; i++) {
			sensors[i] = board.objectAtColumn(safeX(posX-2+i));
		}
	}
	
	
	public int getPosition(){
		return posX;
	}
	
	private int safeX(int x){
		return (x+sizeX)%sizeX;
	}
	
	public boolean[] getSensors(){
		return sensors.clone();
	}
	
	public int[] getRenderPosition(){
		int [] pos =  new int[5];
		for (int i = 0; i < 5; i++) {
			pos[i] = safeX(posX-2+i);
		}
		return pos.clone();
	}
}
