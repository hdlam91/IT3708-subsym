package simpleslickgame;

public class Robot {

	private int direction; // 0 = up, 1 = right, 2 = left, 3 down
	private int posX, posY, sizeX, sizeY;
	private ANN ann;
	private BoardReader br;
	
	public Robot(int x, int y, int sizeX, int sizeY) {
		posX = x;
		posY = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		direction = 0;
		
	}
	
	public void setBoard(){
		
		
	}
	
	public void getSensorInput(){
		switch (direction) {
		case 0:
			
			if (posY == -1)
			break;
		case 1:
			if (posX == sizeX)
			break;
		case 2:
			if (posX == -1)
			break;
		case 3:
			if (posY == sizeX)
			break;
		default:
			System.out.println("direction is not correct!");
			break;
		}
	}
	
	public void getLeft(){
		
	}
	
	
	
	
	public void update() {
		// TODO update position
		// or update direction

		boolean left = false, right = false;
		
		//turning
		if (left)
			direction--;
		else if (right)
			direction++;
		
		//ensures the rotation is inside the given interval.
		if (direction == 4)
			direction = 0;
		else if (direction == -1)
			direction = 3;
		
		//moving
		switch (direction) {
		case 0:
			posY--;
			if (posY == -1)
				posY = sizeY - 1;
			break;
		case 1:
			posX++;
			if (posX == sizeX)
				posX = 0;
			break;
		case 2:
			posX--;
			if (posX == -1)
				posX = sizeX - 1;
			break;
		case 3:
			posY++;
			if (posY == sizeX)
				posY = 0;
			break;
		default:
			System.out.println("direction is not correct!");
			break;
		}
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getDirection() {
		return direction * 90;
	}
}
