package simpleslickgame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BoardReader {
	
	private BufferedReader reader;
	private int[][] board;
	private int numberOfPoison;
	private int numberOfFood;
	private int startX, startY;
	private int sizeX,sizeY;
	
	public BoardReader(String fileName){
		try {
			reader = new BufferedReader(new FileReader(fileName));
			buildFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("something wrong with reading the file");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("something wrong with reading lines");
			e.printStackTrace();
		}
	}
	
	private void buildFromFile() throws IOException{
		String line = null;
		String[] content = null;
		
		reader.readLine(); //
		
		line = reader.readLine();
		content = line.split(",");
		sizeY = Integer.parseInt(content[0]);
		sizeX = Integer.parseInt(content[1]);
		
		board = new int[sizeX][sizeY];
		
		reader.readLine(); //
		
		line = reader.readLine();
		content = line.split(",");
		
		startY = Integer.parseInt(content[0]);
		startX = Integer.parseInt(content[1]);
		
		reader.readLine(); //
		
		line = reader.readLine();
		
		numberOfPoison = Integer.parseInt(line);
		
		reader.readLine(); //
		
		line = reader.readLine();
		
		numberOfFood = Integer.parseInt(line);
		
		reader.readLine(); //
		
		for (int i = 0; i < sizeY; i++) {
			line = reader.readLine();
			for (int j = 0; j < sizeX; j++) {
				board[i][j] = Character.getNumericValue(line.charAt(j));
			}
		}
		reader.close();
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getNumberOfPoison(){
		return numberOfPoison;
	}
	
	public int getNumberOfFood(){
		return numberOfFood;
	}
	
	public int getStartX(){
		return startX;
	}
	
	public int getStartY(){
		return startY;
	}
	
	
	public static void main(String[] args) {
		BoardReader br = new BoardReader("res/test2.txt");
		int[][] b = br.getBoard();
		System.out.println("X: " +br.getStartX() + ", Y: " + br.startY);
		System.out.println("p: "+ br.getNumberOfPoison() + ", f:" + br.getNumberOfFood());
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				System.out.print(b[i][j]);
			}
			System.out.print("\n");
		}
	}
}
