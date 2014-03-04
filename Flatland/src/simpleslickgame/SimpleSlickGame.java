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
	Image myImage;
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		counter = 0;
		myImage = new Image("res/myimage.png");
		myImage.setCenterOfRotation(myImage.getWidth()/2, myImage.getHeight()/2);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		myImage.rotate(1);
		counter++;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		myImage.drawCentered(320,240);
		g.drawString("Howdy!" + counter, 100, 100);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}