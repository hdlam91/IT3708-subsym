package simpleslickgame;

public class Robot {
	
	private enum Directions{UP, LEFT, RIGHT, DOWN};
	private Directions direction;
	private int posX, posY;
	
	public Robot(int x, int y) {
		posX = x;
		posY = y;
		direction = Directions.UP;
	}
	
	
	
	
	
	public void update(){
		//TODO update position
		//update direction
		direction = Directions.values()[(int)(Math.random()*4)];
		System.out.println(direction);
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public int getDirection(){
		switch (direction) {
		case UP:
			return 0;
		case LEFT:
			return -90;
		case RIGHT:
			return 90;
		case DOWN:
			return 180;
		default:
			return 0;
		}
	}
}
