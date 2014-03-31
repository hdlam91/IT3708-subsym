package simpleslickgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	
	private int width, height, startX, startHeight, objectVelocity;
	private List<FallingObject> objectList;
	
	private int board[][];
	private boolean objectInCol[];
	
	public Board(int width, int height, int startX, int agentLength, int startHeightFallingObjects){
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startHeight = startHeightFallingObjects;
		this.objectVelocity = (Math.random()>0.5 ? 0:1)*(Math.random()>0.5?-1:1);
		
		this.board = new int[height][width];
		
		this.objectList = new ArrayList<FallingObject>();
		this.objectInCol = new boolean[width];
	}
	
	public void test(){
		addNewObject();
		iter();
		addNewObject();
		iter();
		addNewObject();
		iter();
		addNewObject();
		iter();
		addNewObject();
		updateBoard();
	}
	
	public static void main(String[] args) {
		Board b = new Board(30,5,29,5,0);
		
		//Testarrrrooney
		b.test();
		List<FallingObject> fL = b.getFallingObjects();
		for (FallingObject fallingObject : fL) {
			System.out.println(Arrays.toString(fallingObject.renderPosition()) + " y: " + fallingObject.getPosY());
		}
		System.out.println(b);
		System.out.println(Arrays.toString(b.objectInCol));
		b.iter();
		fL = b.getFallingObjects();
		for (FallingObject fallingObject : fL) {
			System.out.println(Arrays.toString(fallingObject.renderPosition()) + " y: " + fallingObject.getPosY());
		}
		System.out.println(b);
		System.out.println(Arrays.toString(b.objectInCol));
	}
	
	public void addNewObject(){
		objectList.add(new FallingObject(width, startHeight, height, objectVelocity));
	}
	
	public List<FallingObject> getFallingObjects(){
		return objectList;
	} 
	
	public void iter(){
		int iter = 0;
		while(iter!=objectList.size()) {
			FallingObject f = objectList.get(iter);
			f.iter();
			if(f.getDoneOnScreen()){
				objectList.remove(f);
				iter--;
			}
			iter++;
		}
		updateBoard();
	}
	
	public void clearBoard(){
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = 0;
				if(i==0)
					objectInCol[j] = false;
			}
		}
	}
	
	public void updateBoard(){
		clearBoard();
		for (FallingObject f : objectList) {
			int[] pos = f.renderPosition();
			for (int i = 0; i < pos.length; i++) {
				board[f.getPosY()][pos[i]] = 1;
				objectInCol[pos[i]] = true;
			}
		}
	}
	
	public boolean objectAtColumn(int x){
		return objectInCol[x];
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < height; i++) {
			buf.append(Arrays.toString(board[i])+"\n");
		}
		return buf.toString();
	}
}
