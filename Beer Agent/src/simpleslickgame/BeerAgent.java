package simpleslickgame;

import java.util.Arrays;

public class BeerAgent {
	
	int posX;
	int sizeX;
	ANN network;
	boolean[] sensors;
	Board board;
	int time;
	public BeerAgent(ANN ann) {
		sensors = new boolean[5];
		network = ann;
		sizeX = 30;
		board = new Board(30, 15, 0, 5, 0);
		board.addNewObject();
		board.addNewObject();
		board.addNewObject();
		board.addNewObject();
		board.addNewObject();
		board.addNewObject();
		time = 0;
	}
	
	public Board getBoard(){
		return board;
	}
	
	
	public int getTimeStep(){
		return time;
	}
	
	public void test(){
		
		board.iter();
		posX = safeX(posX+1);
		updateSensor();
		System.out.println(posX);
		System.out.println(board);
		System.out.println(Arrays.toString(sensors));
	}
	public void update(){
		//using ann
		board.updateBoard();
		
		updateSensor();
		network.input(sensors);
		double left = network.getLeftMotor();
		double right = network.getRightMotor();
		
		if(left == right){
		}
		else if(left>right){
			posX = safeX(posX-(int)(right*4+1));
		}
		else{
			
			posX = safeX(posX+(int)(right*4+1));
		}
		time++;
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
