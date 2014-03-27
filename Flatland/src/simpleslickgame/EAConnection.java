package simpleslickgame;

public class EAConnection {
	Robot robot;
	ANN network;
	Scenario[] scenes;
	Scenario sceneToUse;
	int sceneIndex;
	double treshold;
	public EAConnection() {
		treshold = 0.5;
		
		sceneIndex = 0;
		scenes = new Scenario[5];
		for (int i = 0; i < scenes.length; i++) {
			scenes[i] =  new Scenario(0.5,0.5,8,8,false);
		}
		sceneToUse = new Scenario(scenes[sceneIndex]);
		network = new ANN(treshold);
		testRun();
	}
	
	public void setANNWeight(double[] v){
		network.setWeight(v);
	}
	
	public void initRobot(){
		robot = new Robot(sceneToUse.getStartX(), sceneToUse.getStartY(), sceneToUse.getSizeX(), sceneToUse.getSizeY(), sceneToUse, network);
		
	}
	
	public void run(){
		robot.run();
	}
	
	
	public void restart(){
		
		sceneToUse = new Scenario(scenes[sceneIndex]);
		initRobot();
	}
	
	
	public Robot getRobot(){
		return robot;
	}
	public Scenario getScene(){
		return sceneToUse;
	}
	public int getSceneIndex(){
		return sceneIndex;
	}
	public int getNumberOfFoodEaten(){
		return sceneToUse.getNumberOfFood()-sceneToUse.getRemainingFood();
	}
	public int getNumberOfPoisonEaten(){
		return sceneToUse.getNumberOfPoison()-sceneToUse.getRemainingPoison();
	}
	public int getNumberOfFoodTotal(){
		return sceneToUse.getNumberOfFood();
	}
	public int getNumberOfPoisonTotal(){
		return sceneToUse.getNumberOfPoison();
	}
	
	
	
	public void addSceneIndex(){
		sceneIndex++;
		if(sceneIndex > scenes.length-1)
			sceneIndex = 0;
		restart();
	}
	
	public void subSceneIndex(){
		sceneIndex--;
		if(sceneIndex < 0)
			sceneIndex = scenes.length-1;
		restart();
	}
	
	public void testRun(){
		double[] w = new double[network.getNumberOfWeightsNeeded()];
		System.out.println(w.length);
		w[0] = 1;
		w[1] = 0;
		w[2] = 0;
		w[3] = -1;
		w[4] = 0;
		w[5] = 0;
		w[6] = 0;
		w[7] = 1;
		w[8] = 0;
		w[9] = 0;
		w[10] = -1;
		w[11] = 0;
		w[12] = 0;
		w[13] = 0;
		w[14] = 0.8;
		w[15] = 0;
		w[16] = 0;
		w[17] = -1;
		
		
		setANNWeight(w.clone());
		initRobot();
	}
	
}
