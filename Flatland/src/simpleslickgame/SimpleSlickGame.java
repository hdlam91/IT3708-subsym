package simpleslickgame;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

//graphing

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleSlickGame extends BasicGame
{
	int counter;
	Image foodImage, robotImage, poisonImage;
	int height, width, squareSize;
	Robot robot;
	BoardReader br;
	
	
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		counter = 0;
		
		
		
		//initiliaze images:
		foodImage = new Image("res/food.png");
		robotImage = new Image("res/robot.png");
		poisonImage = new Image("res/poison.png");
		
		robotImage.setCenterOfRotation(robotImage.getWidth()/2, robotImage.getHeight()/2);
		
		//sets up board and robot
		br = new BoardReader("res/test.txt");
		robot = new Robot(br.getStartX(), br.getStartY());
		
		//parameters used
		height = gc.getHeight();
		width = gc.getWidth();
		squareSize = 64;
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(1000);
		gc.setForceExit(true);
		counter++;
		robot.update();
		robotImage.setRotation(robot.getDirection());
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		for (int i = 0; i < br.getSizeY(); i++) {
			for (int j = 0; j < br.getSizeX(); j++) {
				//places the field in the middle somehwere
				g.drawRect(boardPosX(j),boardPosY(i),squareSize,squareSize);
				/*0 = nothing
				1 = poison
				2 = food 
				3 = robot
				*/
				if(br.getBoard()[i][j]==1){
					poisonImage.draw(boardPosX(j),boardPosY(i));
				}
				else if(br.getBoard()[i][j]==2){
					foodImage.draw(boardPosX(j),boardPosY(i));					
				}
				
			}
			
		}
		robotImage.draw(boardPosX(robot.getPosX()),boardPosY(robot.getPosY()));
		
		//g.drawString("Howdy!" + counter, 100, 100);
	}
	
	
	private int boardPosY(int y){
		return y*squareSize+(height/2-squareSize*br.getSizeY()/2);
	}
	private int boardPosX(int x){
		return x*squareSize+(width/2-squareSize*br.getSizeX()/2);
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
			
			System.out.println("hello");
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
		
//		int numberOfIterations = 50;
//		double[] x = new double[numberOfIterations];
//		double[] bestFitness = new double[numberOfIterations];
//		
//		for (int i = 0; i < numberOfIterations; i++) {
//			x[i] = i;
//			bestFitness[i] = Math.random()*50;
//		}
//		
//		Plot2DPanel plot = new Plot2DPanel();
//		
//		// add a line plot to the PlotPanel
//		plot.addLinePlot("Best fitness", x, bestFitness);
//		
//		// put the PlotPanel in a JFrame, as a JPanel
//		JFrame frame = new JFrame("EA plot");
//		frame.setContentPane(plot);
//		frame.setVisible(true);
//		
//		frame.setLocation(100,100);
//		Dimension minSize = new Dimension(1200,800);
//		frame.setMinimumSize(minSize);
		
		
		
		
	}
}