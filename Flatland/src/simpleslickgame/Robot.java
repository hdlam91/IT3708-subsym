package simpleslickgame;

public class Robot {

	private int direction; // 0 = up, 1 = right, 2 = left, 3 down
	private int posX, posY, sizeX, sizeY;
	private ANN ann;
	private Scenario scene;
	private boolean poiLeft, poiForward, poiRight;
	private boolean foodLeft, foodForward, foodRight;
	private int timeStep;
	
	public Robot(int x, int y, int sizeX, int sizeY, Scenario scene) {
		posX = x;
		posY = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		direction = 0;
		timeStep = 0;
		this.scene = scene;	
	}
	
	private int safePosx(int pos){
		if(pos == sizeX){
			return 0;
		}
		if(pos < 0){
			return sizeX-1;
		}
		return pos;
	}
	private int safePosy(int pos){
		if(pos == sizeY){
			return 0;
		}
		if(pos < 0){
			return sizeY-1;
		}
		return pos;
	}
	
	
	public void getSensorInput(){
		switch (direction) {
		case 0:
			getLeft(posX-1,posY);
			getFront(posX, posY-1);
			getRight(posX+1,posY);
			break;
		case 1:
			getLeft(posX,posY-1);
			getFront(posX+1, posY);
			getRight(posX,posY+1);
			break;
		case 2:
			getRight(posX-1,posY);
			getFront(posX, posY+1);
			getLeft(posX+1,posY);
			break;
		case 3:
			getLeft(posX,posY+1);
			getFront(posX-1, posY);
			getRight(posX,posY-1);
			break;
		default:
			System.out.println("direction is not correct!");
			break;
		}
	}
	
	public void getLeft(int x, int y){
		poiLeft = (scene.getObjectAt(safePosx(x),safePosy(y)) == 1);
		foodLeft = (scene.getObjectAt(safePosx(x),safePosy(y)) == 2);
	}
	
	public void getRight(int x, int y){
		poiRight = (scene.getObjectAt(safePosx(x),safePosy(y)) == 1);
		foodRight = (scene.getObjectAt(safePosx(x),safePosy(y)) == 2);
	}
	
	public void getFront(int x, int y){
		poiForward = (scene.getObjectAt(safePosx(x),safePosy(y)) == 1);
		foodForward = (scene.getObjectAt(safePosx(x),safePosy(y)) == 2);
	}
	
	public int getTimeStep(){
		return timeStep;
	}
	
	public void update() {
		// TODO update position
		// or update direction
		
		getSensorInput();
		System.out.println("posions:" + poiLeft + poiForward + poiRight);
		System.out.println("food:" + foodLeft + foodForward + foodRight);
		
		if(timeStep <50){
			boolean left = false, right = false;
			//turning
			if(Math.random() <.5)
				right = true;
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
				posY++;
				if (posY == sizeY)
					posY = 0;
				break;
			case 3:
				posX--;
				if (posX == -1)
					posX = sizeX -1;
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
