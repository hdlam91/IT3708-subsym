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
		sizeX = 30;
	}
	
	public void test(){
//		double turn = Math.random()*200;
//		if(Math.random()>0.5)
//			posX = safeX(posX-(int)turn);
//		else
//			posX = safeX(posX+(int)turn);
		posX = safeX(posX+1);
		updateSensor();
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
