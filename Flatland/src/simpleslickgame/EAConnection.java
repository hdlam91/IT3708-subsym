package simpleslickgame;

import java.util.ArrayList;

public class EAConnection {
	Robot robot;
	ANN network;
	Scenario[] scenes;
	Scenario sceneToUse;
	int sceneIndex;
	double treshold;
	Robot bestRobot;
	ArrayList <int[]> results; 
	
	double [] bestFitnessG, avgFitnessG, sdFitnessG;
	
	public EAConnection() {
		treshold = 0.5;
		results = new ArrayList<int[]>();
		sceneIndex = 0;
		scenes = new Scenario[5];
		for (int i = 0; i < scenes.length; i++) {
			scenes[i] =  new Scenario(0.5,0.5,8,8,false);
		}
		sceneToUse = new Scenario(scenes[sceneIndex]);
//		int hidden[] = {3};
		network = new ANN(treshold ,6);
	}
	
	public int getNumberOfWeightsNeeded(){
		return network.getNumberOfWeightsNeeded();
	}
	
	public void setANNWeight(double[] v){
		network.setWeight(v);
	}
	
	public void initRobot(){
		robot = new Robot(sceneToUse.getStartX(), sceneToUse.getStartY(), sceneToUse.getSizeX(), sceneToUse.getSizeY(), sceneToUse, network);
		
	}
	
	public void createNewScenes(){
		scenes = new Scenario[5];
		for (int i = 0; i < scenes.length; i++) {
			scenes[i] =  new Scenario(0.5,0.5,8,8,false);
		}
	}
	
	
	public Robot bestRobot(){
		return bestRobot;
	}
	
	public void run(double[] v){
		results.clear();
		setANNWeight(v);
		for (int i = 0; i < scenes.length; i++) {
			sceneIndex = i;
			restart();
			robot.run();
			int[] e = new int[4];
			e[0] = getNumberOfFoodEaten(); e[1] = getNumberOfFoodTotal();
			e[2] = getNumberOfPoisonEaten(); e[3] = getNumberOfPoisonTotal();
			results.add(e);
		}
	}
	
	
	public void setGraph(double[] b,double[] avg,double[] sd){
		bestFitnessG = b;
		avgFitnessG = avg;
		sdFitnessG = sd;
	}
	
	public double[] getBestFitness(){
		return bestFitnessG;
	}
	public double[] getAvgFitness(){
		return avgFitnessG;
	}
	
	public double[] getSDFitness(){
		return sdFitnessG;
	}
	
	
	public void restart(){
		sceneToUse = new Scenario(scenes[sceneIndex]);
		initRobot();
	}
	
	public ArrayList<int[]> getResults(){
		return results;
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

}
