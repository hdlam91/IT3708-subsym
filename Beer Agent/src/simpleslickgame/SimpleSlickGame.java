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
	BeerAgent ba;
	ANN network;
	Image left, mid, right;
	Image leftS, midS, rightS;
	Image block;
	int squareSize;
	int width, height;
	int yPosition;
	int[] temp;
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		network = new ANN(0.5);
		ba = new  BeerAgent(network);
		left = new Image("res/left.png");
		mid = new Image("res/mid.png");
		right = new Image("res/right.png");
		leftS = new Image("res/leftOn.png");
		midS = new Image("res/midOn.png");
		rightS = new Image("res/rightOn.png");
		block = new Image("res/block.png");
		counter = 0;
		
		squareSize = 32;
		width = gc.getWidth();
		height = gc.getHeight();
		yPosition = boardPosY(15);
		temp = new int[2];
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(200);
		gc.setForceExit(true);
		temp[0] = (int) (Math.random()*30);
		temp[1] = (int) (Math.random()*15);
		ba.test();
		counter++;
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