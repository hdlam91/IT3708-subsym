package simpleslickgame;

public class Robot {

	private int direction; // 0 = up, 1 = right, 2 = left, 3 down
	private int posX, posY, sizeX, sizeY;
	private ANN ann;
	private Scenario scene;
	private boolean poiLeft, poiForward, poiRight;
	private boolean foodLeft, foodForward, foodRight;
	private int timeStep;
	private int totalTimeStepAllowed;
	public Robot(int x, int y, int sizeX, int sizeY, Scenario scene, ANN ann) {
		posX = x;
		posY = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		direction = 0;
		timeStep = 0;
		totalTimeStepAllowed = 50;
		this.scene = scene;
		
		this.ann = ann;
//		int [] li = {0};
//		ann = new ANN(li);
//		double[] w = new double[ann.getNumberOfWeightsNeeded()];
//		System.out.println(w.length);
//		w[0] = 1;
//		w[1] = 0;
//		w[2] = 0;
//		w[3] = -1;
//		w[4] = 0;
//		w[5] = 0;
//		w[6] = 0;
//		w[7] = 1;
//		w[8] = 0;
//		w[9] = 0;
//		w[10] = -1;
//		w[11] = 0;
//		w[12] = 0;
//		w[13] = 0;
//		w[14] = 0.8;
//		w[15] = 0;
//		w[16] = 0;
//		w[17] = -1;
//		
//		
//		ann.setWeight(w.clone());

	}
	
	public ANN getANN(){
		return ann;
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
	
	public void run(){
		while(timeStep<totalTimeStepAllowed){
			update();
		}
	}
	
	public void update() {
		// TODO update position
		// or update direction
		
		if(timeStep <totalTimeStepAllowed){
			getSensorInput();
			ann.input(foodLeft, foodForward, foodRight, poiLeft, poiForward, poiRight);
			ann.run();
			double front = ann.getFrontMotor();
			double left = ann.getLeftMotor();
			double right = ann.getRightMotor();
			
			boolean l = false, r = false;
			if(left>right && left> front){
				l = true;
			}
			else if(left<right && right> front){
				r = true;
			}
			else if(left > front && right > front){
				if(Math.random() >0.5){
					l = true;
				}
				else
					r = true;
				
			
			}
		
			//turning
			
			if (l){
				timeStep++;
				direction--;
			}
			else if (r){
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
			if(scene.getObjectAt(posX, posY) == 1 || scene.getObjectAt(posX, posY) == 2){
				scene.removeObjectAt(posX, posY);
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
