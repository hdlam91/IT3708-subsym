package simpleslickgame;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

//graphing





import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SimpleSlickGame extends BasicGame
{
	Image foodImage, robotImage, poisonImage;
	int height, width, squareSize;
	Robot robot;
	Scenario scene;
	Scenario sceneToUse;
	Scenario scenes[];
	boolean pause, init;
	int speed;
	
	
	
	
	
	
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		pause = false;
		init =  true;
		speed = 1000;
		//initiliaze images:
		foodImage = new Image("res/food.png");
		robotImage = new Image("res/robot.png");
		poisonImage = new Image("res/poison.png");
		
		robotImage.setCenterOfRotation(robotImage.getWidth()/2, robotImage.getHeight()/2);
		
		//sets up board and robot
		scene = new Scenario(0.5,0.5,8,8,false);
		sceneToUse = new Scenario(scene);
		robot = new Robot(sceneToUse.getStartX(), sceneToUse.getStartY(), sceneToUse.getSizeX(), sceneToUse.getSizeY(), sceneToUse);
		
		//parameters used
		height = gc.getHeight();
		width = gc.getWidth();
		squareSize = 64;
	}
	

	
	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(speed);
		if(!init){
		if(!pause)
			robot.update();
		}
		robotImage.setRotation(robot.getDirection());
		init = false;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		for (int i = 0; i < sceneToUse.getSizeY(); i++) {
			for (int j = 0; j < sceneToUse.getSizeX(); j++) {
				//places the field in the middle somehwere
				g.drawRect(boardPosX(j),boardPosY(i),squareSize,squareSize);
				/*0 = nothing
				1 = poison
				2 = food 
				3 = robot
				*/
				if(sceneToUse.getBoard()[i][j]==1){
					poisonImage.draw(boardPosX(j),boardPosY(i),squareSize,squareSize);
				}
				else if(sceneToUse.getBoard()[i][j]==2){
					foodImage.draw(boardPosX(j),boardPosY(i),squareSize,squareSize);					
				}
				
			}
			
		}
		robotImage.draw(boardPosX(robot.getPosX()),boardPosY(robot.getPosY()),squareSize,squareSize);
		
		g.drawString("Number of steps:" + robot.getTimeStep() + "/" + 50, boardPosX(0)-50,boardPosY(0)-50);
		g.drawString("Number of cayke eaten:" + (sceneToUse.getNumberOfFood()-sceneToUse.getRemainingFood()) + "/" + sceneToUse.getNumberOfFood(), boardPosX(7)+squareSize+50,boardPosY(0)-50);
		g.drawString("Number of poision eaten:" + (sceneToUse.getNumberOfPoison()-sceneToUse.getRemainingPoison()) + "/" + sceneToUse.getNumberOfPoison(), boardPosX(7)+squareSize+50,boardPosY(1)-50);
		g.drawString("press r to reset, space to pause, 123 for speed", boardPosX(0)+50,boardPosY(7)+squareSize);
	}
	
	
	private int boardPosY(int y){
		return y*squareSize+(height/2-squareSize*sceneToUse.getSizeY()/2);
	}
	private int boardPosX(int x){
		return x*squareSize+(width/2-squareSize*sceneToUse.getSizeX()/2);
	}
	
	
	public void restart(){
		
		//sets up board and robot
		init = true;
		sceneToUse = new Scenario(scene);
		robot = new Robot(sceneToUse.getStartX(), sceneToUse.getStartY(), sceneToUse.getSizeX(), sceneToUse.getSizeY(), sceneToUse);
		//parameters used
	}
	
	public void keyPressed(int key, char c) {
		
		if (key == Input.KEY_SPACE) {
			if(!pause){
				pause = true;
			}
			else{
				pause = false;
			}
			
		}
		if(key == Input.KEY_1)
			speed = 0;
		else if(key == Input.KEY_2)
			speed = 200;
		else if(key == Input.KEY_3)
			speed = 1000;
		
		if(key == Input.KEY_R){
			restart();
		}
		if(key == Input.KEY_G)
			graph();
		
	}
	
	
	public void graph(){
	int numberOfIterations = 50;
	double[] x = new double[numberOfIterations];
	double[] bestFitness = new double[numberOfIterations];
	
	for (int i = 0; i < numberOfIterations; i++) {
		x[i] = i;
		bestFitness[i] = Math.random()*50;
	}
	
	Plot2DPanel plot = new Plot2DPanel();
	
	// add a line plot to the PlotPanel
	plot.addLinePlot("Best fitness", x, bestFitness);
	
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