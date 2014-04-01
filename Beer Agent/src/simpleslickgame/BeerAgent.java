package simpleslickgame;

import java.util.Arrays;

public class BeerAgent {
	
	int posX;
	int sizeX;
	ANN network;
	boolean[] sensors;
	Board board;
	int time;
	public BeerAgent(ANN ann, Board board) {
		sensors = new boolean[5];
		network = ann;
		sizeX = 30;
		time = 0;
		this.board = board;
	}
	
	public Board getBoard(){
		return board;
	}
	
	
	public int getTimeStep(){
		return time;
	}
	
	public void test(){
		
		posX = safeX(posX+1);
		updateSensor();
		System.out.println(posX);
		System.out.println(board);
		System.out.println(Arrays.toString(sensors));
	}
	public void update(){
		//using ann
		updateSensor();
		network.input(sensors);
		double left = network.getLeftMotor();
		double right = network.getRightMotor();
		
		int moving = (int)(right*4+1) -(int)(left*4+1); 
		posX = safeX(posX + moving);
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
