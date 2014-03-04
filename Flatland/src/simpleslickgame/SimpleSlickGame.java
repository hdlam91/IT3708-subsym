package simpleslickgame;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	int height;
	int width;
	int squareSize;
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
		height = gc.getHeight();
		width = gc.getWidth();
		squareSize = 64;
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		counter++;
		robotImage.setRotation(counter);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//places the field in the middle somehwere
				g.drawRect(i*squareSize+(width/2-squareSize*4),j*squareSize+(height/2-squareSize*4),squareSize,squareSize);
			}
			
		}
		robotImage.draw(5*squareSize+(width/2-squareSize*4),5*squareSize+(height/2-squareSize*4));
		//g.drawString("Howdy!" + counter, 100, 100);
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