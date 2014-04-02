package simpleslickgame;
import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;





//graphing
import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import evolutionary_Algorithm.GeneralEA;

public class SimpleSlickGame extends BasicGame
{
	Image foodImage, robotImage, poisonImage;
	int height, width, squareSize;
	EAConnection ea;
	boolean pause, init;
	int sleepTimer;
	
	
	
	
	
	
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	public void init(GameContainer gc) throws SlickException {
		
		pause = false;
		init =  true;
		sleepTimer = 2000;
		//initiliaze images:
		foodImage = new Image("res/food.png");
		robotImage = new Image("res/robot.png");
		poisonImage = new Image("res/poison.png");
		
		robotImage.setCenterOfRotation(robotImage.getWidth()/2, robotImage.getHeight()/2);
		
		//sets up board and robot
		ea = new EAConnection();
		
		//parameters used
		height = gc.getHeight();
		width = gc.getWidth();
		squareSize = 64;
		
		
		
		
		
		int K = 20;
		double P = 0.8;
		
		int numberOfIndividuals = 30;
		int requiredNumberOfBitsForGenoType = 1;
		
		int typeOfProblem = 2;
		int adultType = 2;
		int parentType = 1;
		
		double crossOverRate = 0.3;
		double mutationRate = 0.2;
		
		boolean mutationPerComponent = true;
		boolean initializeRandomly = true;
		
		EAConnection con = new EAConnection();
		int numberofWeights = con.getNumberOfWeightsNeeded();
		System.out.println(numberofWeights);
		GeneralEA s = new GeneralEA(numberOfIndividuals,numberofWeights,requiredNumberOfBitsForGenoType,
				typeOfProblem,adultType,parentType,crossOverRate,mutationRate,mutationPerComponent,K,P,initializeRandomly,con,false);
		ea.setANNWeight(s.getWeightsOfBestIndividual());
		ea.setGraph(s.getBestList(), s.getMeanList(), s.getStdList());
		ea.restart();
	}
	

	
	public void update(GameContainer gc, int i) throws SlickException {
//		if(System.currentTimeMillis()%(sleepTimer) >= sleepTimer-10){
		gc.sleep(sleepTimer);
		
		if(!init){
				if(!pause)
				
					ea.getRobot().update();
				}
		else
			init = false;
		robotImage.setRotation(ea.getRobot().getDirection());
//		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		for (int i = 0; i < ea.getScene().getSizeY(); i++) {
			for (int j = 0; j < ea.getScene().getSizeX(); j++) {
				//places the field in the middle somehwere
				g.drawRect(boardPosX(j),boardPosY(i),squareSize,squareSize);
				/*0 = nothing
				1 = poison
				2 = food 
				3 = robot
				*/
				if(ea.getScene().getBoard()[i][j]==1){
					poisonImage.draw(boardPosX(j),boardPosY(i),squareSize,squareSize);
				}
				else if(ea.getScene().getBoard()[i][j]==2){
					foodImage.draw(boardPosX(j),boardPosY(i),squareSize,squareSize);					
				}
				
			}
			
		}
		robotImage.draw(boardPosX(ea.getRobot().getPosX()),boardPosY(ea.getRobot().getPosY()),squareSize,squareSize);
		
		g.drawString("Number of steps:" + ea.getRobot().getTimeStep() + "/" + 50, boardPosX(0)-50,boardPosY(0)-50);
		
		g.drawString("Number of cayke eaten:" + (ea.getScene().getNumberOfFood()-ea.getScene().getRemainingFood()) + "/" + ea.getScene().getNumberOfFood(), boardPosX(7)+squareSize+50,boardPosY(0)-50);
		g.drawString("Number of poision eaten:" + (ea.getScene().getNumberOfPoison()-ea.getScene().getRemainingPoison()) + "/" + ea.getScene().getNumberOfPoison(), boardPosX(7)+squareSize+50,boardPosY(0)-38);
		g.drawString("press r to reset, space to pause, 123 for speed, g for graph", boardPosX(0),boardPosY(7)+squareSize);
		g.drawString("Current scene: "+ ea.getSceneIndex() + " press up/down to change scene, q for quit", boardPosX(0),boardPosY(7)+squareSize+12);
	}
	
	
	private int boardPosY(int y){
		return y*squareSize+(height/2-squareSize*ea.getScene().getSizeY()/2);
	}
	private int boardPosX(int x){
		return x*squareSize+(width/2-squareSize*ea.getScene().getSizeX()/2);
	}
	
	
	
	
	
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_Q){
			System.exit(0);
			return;
		}
		if(key== Input.KEY_UP){
			ea.addSceneIndex();
		}
		else if(key==Input.KEY_DOWN){
			ea.subSceneIndex();
		}
		
		if (key == Input.KEY_SPACE) {
			if(!pause){
				pause = true;
			}
			else{
				pause = false;
			}
			
		}
		if(key == Input.KEY_1)
			sleepTimer = 1;
		else if(key == Input.KEY_2)
			sleepTimer = 200;
		else if(key == Input.KEY_3)
			sleepTimer = 1000;
		
		if(key == Input.KEY_R){
			ea.restart();
			init = true;
		}
		if(key == Input.KEY_G)
			graph();
		
	}
	
	
	public void graph(){
		Plot2DPanel plot = new Plot2DPanel();
		
		// add a line plot to the PlotPanel
		plot.addLinePlot("Best fitness",Color.GREEN, ea.getBestFitness());
		plot.addLinePlot("Avg fitness",Color.BLUE, ea.getAvgFitness());
		plot.addLinePlot("SD fitness",Color.MAGENTA, ea.getSDFitness());
		plot.addLegend("EAST"); //wow such legendary
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Fitness plot");
		frame.setContentPane(plot);
		frame.setVisible(true);
		
		frame.setLocation(100,100);
		Dimension minSize = new Dimension(1200,800);
		frame.setMinimumSize(minSize);
	}
	
	public static void main(String[] args)
	{
		int w=1280,h=720;
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Flatland"));
			appgc.setDisplayMode(w, h, false);
			appgc.start();
			
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}