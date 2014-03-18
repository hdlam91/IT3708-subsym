package simpleslickgame;

import java.util.Arrays;

public class Scenario {
	private int[][] board;
	private int numberOfPoison;
	private int numberOfFood;
	private int startX, startY;
	
	private int remainingFood, remainingPoison;
	
	private int sizeX, sizeY;
	
	public Scenario(double foodratio, double poisonratio, int sizeX, int sizeY, boolean randomInitPos){
		this.board = new int[sizeY][sizeX];
		this.numberOfFood = 0;
		this.setSizeX(sizeX);
		this.setSizeY(sizeY);
		if(!randomInitPos){
			this.startX = 0;
			this.startY = 0;
		}
		else{
			this.startX = (int)(Math.random()*sizeX);
			this.startY = (int)(Math.random()*sizeY);
		}
		
		board[startY][startX] = 3;
		
		for (int i = 0; i < sizeY; i++) {
			for (int j = 0; j < sizeX; j++) {
				if(i !=startY || j!=startX){
					if(Math.random()<foodratio && (numberOfFood<=(0.55*sizeX*sizeY))){
						board[i][j] = 2;
						numberOfFood++;
					}
					if(board[i][j] != 2 && Math.random()<poisonratio && (numberOfPoison<=(0.55*(sizeX*sizeY-numberOfFood)))){
						board[i][j] = 1;
						numberOfPoison++;
					}
				}
			}
		}
		
		this.remainingFood = numberOfFood;
		this.remainingPoison = numberOfPoison;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public void removeObjectAt(int y, int x){
		if(board[y][x] == 2)
			remainingFood --;
		else if(board[y][x] == 1)
			remainingPoison --;
		this.board[y][x] = 0;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	
	public int getNumberOfPoison() {
		return numberOfPoison;
	}

	public int getNumberOfFood() {
		return numberOfFood;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getRemainingFood(){
		return remainingFood;
	}
	
	public int getRemainingPoison(){
		return remainingPoison;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < sizeY; i++) {
			buf.append(Arrays.toString(board[i])+"\n");
		}
		buf.append("remaining food: " + remainingFood + " remaining poison: " + remainingPoison+"\n");
		buf.append("Original food: " +numberOfFood + " original poison: " + numberOfPoison+"\n");
		buf.append("startX: " + startX + " startY: " + startY+"\n");
		return buf.toString();
	}
	
	public static void main(String[] args) {
		Scenario sc = new Scenario(0.5,0.5,8,8,false);
		System.out.println(sc);
		sc.removeObjectAt(1, 1);
		System.out.println(sc);
	}
}
