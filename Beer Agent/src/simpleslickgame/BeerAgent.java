package simpleslickgame;

public class BeerAgent {
	
	int posX;
	int sizeX;
	ANN network;
	boolean[] sensors;
	Board board;
	public BeerAgent(ANN ann) {
		// TODO Auto-generated constructor stub
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
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void test(){
		
		updateSensor();
		board.iter();
		posX = safeX(posX+1);
		updateSensor();
	}
	public void update(){
		//using ann
		board.updateBoard();
		network.input(sensors);
		double left = network.getLeftMotor();
		double right = network.getRightMotor();
		
		if(left == right){
		}
		else if(left>right){
			posX = safeX(posX-(int)(right-1));
		}
		else{
			
			posX = safeX(posX+(int)(right+1));
		}
	}
	
	private void updateSensor(){
		for (int i = 0; i < sensors.length; i++) {
			if(Math.random()<0.5)
				sensors[i] = false;
			else
				sensors[i] = true;
			//sesnsors[i] = getLineAt(posX-2+i);
		}
	}
	
	
	public int getPosition(){
		return posX;
	}
	
	private int safeX(int x){
		return (x+sizeX)%sizeX;
	}
	
	public boolean[] getSensors(){
		return (boolean[]) sensors.clone();
	}
	
	public int[] getRenderPosition(){
		int [] pos =  new int[5];
		for (int i = 0; i < 5; i++) {
			pos[i] = safeX(posX-2+i);
		}
		return (int[]) pos.clone();
	}
}
