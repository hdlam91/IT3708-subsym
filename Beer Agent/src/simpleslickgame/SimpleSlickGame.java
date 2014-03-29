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
	int squareSize;
	int width, height;
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
		counter = 0;
		
		squareSize = 64;
		width = gc.getWidth();
		height = gc.getHeight();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(200);
		gc.setForceExit(true);
		ba.test();
		counter++;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		int[] renderPos = ba.getRenderPosition();
		if(ba.getSensors()[0])
			leftS.draw(boardPosX(renderPos[0]),50,squareSize,squareSize);
		else
			left.draw(boardPosX(renderPos[0]),50,squareSize,squareSize);
		for (int i = 1; i < renderPos.length-1; i++) {
			if(ba.getSensors()[i])
				mid.draw(boardPosX(renderPos[i]),50,squareSize,squareSize);
			else
				midS.draw(boardPosX(renderPos[i]),50,squareSize,squareSize);
			
		}
		if(ba.getSensors()[4])
			right.draw(boardPosX(renderPos[4]),50,squareSize,squareSize);
		else
			rightS.draw(boardPosX(renderPos[4]),50,squareSize,squareSize);
	}		
	
	
//	private int boardPosY(int y){
//		return y*squareSize+(height/2-squareSize*br.getSizeY()/2);
//	}
	private int boardPosX(int x){
		return x*squareSize;
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