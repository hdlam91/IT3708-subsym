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
	
	
	
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		counter = 0;
		
		
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gc.sleep(1000);
		gc.setForceExit(true);
		counter++;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		
		g.drawString("Howdy!" + counter, 100, 100);
	}
	
	
//	private int boardPosY(int y){
//		return y*squareSize+(height/2-squareSize*br.getSizeY()/2);
//	}
//	private int boardPosX(int x){
//		return x*squareSize+(width/2-squareSize*br.getSizeX()/2);
//	}
	
	
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