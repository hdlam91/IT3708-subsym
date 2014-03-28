package simpleslickgame;

public class BeerAgent {
	
	int posX;
	int sizeX;
	ANN network;
	boolean[] sensors;
	public BeerAgent(ANN ann) {
		// TODO Auto-generated constructor stub
		sensors = new boolean[5];
		network = ann;
	}
	
	
	public void update(){
		//using ann
		updateSensor();
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
			sensors[i] = false;
			//sesnsors[i] = getLineAt(posX-(i+2));
		}
	}
	
	
	public int getPosition(){
		return posX;
	}
	
	private int safeX(int x){
		return (x+sizeX)%sizeX;
	}
	
	
	
}
