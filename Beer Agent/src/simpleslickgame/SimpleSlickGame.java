package simpleslickgame;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
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
	private EAConnection ea;
//	BeerAgent ba;
//	ANN network;
	private Image left, mid, right;
	private Image leftS, midS, rightS;
	private Image block;
	private int squareSize;
	private int width, height;
	private int yPosition;
	private int[] temp;
	private boolean pause, init;
	private int sleepTimer;
	
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		ea = new EAConnection();
		
		
		int K = 20;
		double P = 0.8;
		
		int numberOfIndividuals = 200;
//		int numberOfFieldsPerGenoType = 40;
		int requiredNumberOfBitsForGenoType = 1;
		
		int typeOfProblem = 2;
		int adultType = 2;
		int parentType = 1;
		
		double crossOverRate = 0.0;
		double mutationRate = 0.2;
		
		boolean mutationPerComponent = true;
		boolean initializeRandomly = true;
		
//		GeneralEA s = new GeneralEA(numberOfIndividuals, numberOfFieldsPerGenoType, requiredNumberOfBitsForGenoType, 
//									typeOfProblem, adultType, parentType, crossOverRate, mutationRate, mutationPerComponent, K, P, initializeRandomly,null);
		
		
		int numberofWeights = ea.getNumberOfWeightsNeeded();
		int numberofNodes = ea.getNumberOfNodesNeeded()*3;
		
		int numberOfFields = numberofWeights+numberofNodes;
		
		GeneralEA s = new GeneralEA(numberOfIndividuals,numberOfFields,requiredNumberOfBitsForGenoType,
				typeOfProblem,adultType,parentType,crossOverRate,mutationRate,mutationPerComponent,K,P,initializeRandomly,ea);
		System.out.println(Arrays.toString(s.getWeightsOfBestIndividual()));
		
		
		ea.setWeight(s.getWeightsOfBestIndividual());
		
		
		
		left = new Image("res/left.png");
		mid = new Image("res/mid.png");
		right = new Image("res/right.png");
		leftS = new Image("res/leftOn.png");
		midS = new Image("res/midOn.png");
		rightS = new Image("res/rightOn.png");
		block = new Image("res/block.png");
		
		pause = false;
		init = true;
		
		squareSize = 32;
		width = gc.getWidth();
		height = gc.getHeight();
		yPosition = boardPosY(14);
		temp = new int[2];
		sleepTimer = 1000;
		
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(sleepTimer);
		temp[0] = (int) (Math.random()*30);
		temp[1] = (int) (Math.random()*15);
		if(!pause&&!init){
			ea.iter();
		}
		else 
			init = false;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		int [][] board = ea.getBoard().getBoard();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j]==1)
					block.draw(boardPosX(j), boardPosY(i), squareSize, squareSize);
				g.drawRect(boardPosX(j),boardPosY(i),squareSize,squareSize);
			}
		}
		
		int[] renderPos = ea.getAgent().getRenderPosition();
		if(ea.getAgent().getSensors()[0])
			leftS.draw(boardPosX(renderPos[0]),yPosition,squareSize,squareSize);
		else
			left.draw(boardPosX(renderPos[0]),yPosition,squareSize,squareSize);
		for (int i = 1; i < renderPos.length-1; i++) {
			if(ea.getAgent().getSensors()[i])
				midS.draw(boardPosX(renderPos[i]),yPosition,squareSize,squareSize);
			else
				mid.draw(boardPosX(renderPos[i]),yPosition,squareSize,squareSize);
			
		}
		if(ea.getAgent().getSensors()[4])
			rightS.draw(boardPosX(renderPos[4]),yPosition,squareSize,squareSize);
		else
			right.draw(boardPosX(renderPos[4]),yPosition,squareSize,squareSize);
		
		g.drawString("Number of timesteps:" + ea.getAgent().getTimeStep() + " number of bricks spawned: " + ea.getNumOfObjectCreated(), boardPosX(0),boardPosY(0)-50);
		g.drawString("Number of contacts:" + (ea.getContacts()) + "/" + ea.getNumOfObjectCreated(), boardPosX(20)+squareSize+50,boardPosY(0)-50);
		g.drawString("Number of captures:" + (ea.getCaptures()) + "/" + ea.getNumOfObjectCreated(), boardPosX(20)+squareSize+50,boardPosY(0)-38);
		g.drawString("Number of big captures:" + (ea.getBigCaptures()) + "/" + ea.getTotalLargeObjects(), boardPosX(20)+squareSize+50,boardPosY(0)-26);
		g.drawString("press r to reset, space to pause, 123 for speed, g for graph, q to quit", boardPosX(0),boardPosY(14)+squareSize);
	}		
	
	
	private int boardPosY(int y){
		return y*squareSize+(height/2-squareSize*15/2);
	}
	private int boardPosX(int x){
		return x*squareSize+(width/2-squareSize*30/2);
	}
	
	
	
	public void keyPressed(int key, char c) {
		if(key == Input.KEY_Q){
			System.exit(0);
			return;
		}
		if(key== Input.KEY_UP){
			//ea.addSceneIndex();
		}
		else if(key==Input.KEY_DOWN){
//			ea.subSceneIndex();
		}
		
		if (key == Input.KEY_SPACE) {
			pause = !pause;
			
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
	
	
	private void graph(){
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
			appgc = new AppGameContainer(new SimpleSlickGame("Beer-agent"));
			appgc.setDisplayMode(w, h, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}