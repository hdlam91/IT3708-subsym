package simpleslickgame;

public class Robot {

	private int direction; // 0 = up, 1 = right, 2 = left, 3 down
	private int posX, posY, sizeX, sizeY;
	private ANN ann;
	private BoardReader br;
	private boolean poiLeft, poiForward, poiRight;
	private boolean foodLeft, foodForward, foodRight;
	private int timeStep;
	
	public Robot(int x, int y, int sizeX, int sizeY) {
		posX = x;
		posY = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		direction = 0;
		timeStep = 0;
		
	}
	
	public void setBoard(){
		
		
	}
	
	public void getSensorInput(){
		switch (direction) {
		case 0:
			
			if (posY == -1)
			break;
		case 1:
			if (posX == sizeX || posX == 0)
			break;
		case 2:
			if (posX == -1 || posX == sizeX-1)
			break;
		case 3:
			if (posY == sizeY || posY == 0)
			break;
		default:
			System.out.println("direction is not correct!");
			break;
		}
	}
	
	public void getLeft(int x, int y){
		poiLeft = (br.getBoard()[x][y]== 1);
		foodLeft = (br.getBoard()[x][y]== 2);
	}
	
	public void getRight(int x, int y){
		poiRight = (br.getBoard()[x][y]== 1);
		foodRight = (br.getBoard()[x][y]== 2);
	}
	
	public void getFront(int x, int y){
		poiForward  = (br.getBoard()[x][y]== 1);
		foodRight = (br.getBoard()[x][y]== 2);
	}
	
	public int getTimeStep(){
		return timeStep;
	}
	
	public void update() {
		// TODO update position
		// or update direction
		if(timeStep <50){
			boolean left = false, right = false;
			//turning
			if (left){
				timeStep++;
				direction--;
			}
			else if (right){
				direction++;
				timeStep++;
			}
			
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
				if (posY == sizeY)
					posY = 0;
				break;
			default:
				System.out.println("direction is not correct!");
				break;
			}
			timeStep++;
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
