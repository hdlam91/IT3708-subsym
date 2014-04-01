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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SimpleSlickGame extends BasicGame
{
	BeerAgent ba;
	ANN network;
	Image left, mid, right;
	Image leftS, midS, rightS;
	Image block;
	int squareSize;
	int width, height;
	int yPosition;
	int[] temp;
	boolean pause;
	int sleepTimer;
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		network = new ANN(0.5,5);
		ba = new  BeerAgent(network);
		left = new Image("res/left.png");
		mid = new Image("res/mid.png");
		right = new Image("res/right.png");
		leftS = new Image("res/leftOn.png");
		midS = new Image("res/midOn.png");
		rightS = new Image("res/rightOn.png");
		block = new Image("res/block.png");
		
		pause = false;
		
		
		squareSize = 32;
		width = gc.getWidth();
		height = gc.getHeight();
		yPosition = boardPosY(15);
		temp = new int[2];
		sleepTimer = 1000;
		
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(sleepTimer);
		temp[0] = (int) (Math.random()*30);
		temp[1] = (int) (Math.random()*15);
		if(!pause)
			ba.test();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		int[] renderPos = ba.getRenderPosition();
		if(ba.getSensors()[0])
			leftS.draw(boardPosX(renderPos[0]),yPosition,squareSize,squareSize);
		else
			left.draw(boardPosX(renderPos[0]),yPosition,squareSize,squareSize);
		for (int i = 1; i < renderPos.length-1; i++) {
			if(ba.getSensors()[i])
				mid.draw(boardPosX(renderPos[i]),yPosition,squareSize,squareSize);
			else
				midS.draw(boardPosX(renderPos[i]),yPosition,squareSize,squareSize);
			
		}
		if(ba.getSensors()[4])
			right.draw(boardPosX(renderPos[4]),yPosition,squareSize,squareSize);
		else
			rightS.draw(boardPosX(renderPos[4]),yPosition,squareSize,squareSize);
		block.draw(boardPosX(temp[0]),boardPosY(temp[1]),squareSize,squareSize);
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
//			ea.restart();
		}
		if(key == Input.KEY_G)
			graph();
		
	}
	
	
	private void graph(){
		
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