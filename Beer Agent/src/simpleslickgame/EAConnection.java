package simpleslickgame;

import java.util.List;
import java.util.ArrayList;

public class EAConnection {
	private BeerAgent ba;
	private ANN an;
	private Board board;
	
	public EAConnection(){
		int[] hid = {2};
		an = new ANN(hid,0.5,5);
		an.test();
		board = new Board(30, 15, 0, 5, 0);
		ba = new BeerAgent(an,board);
		run(null);
	}
	
	/**
	 * TODO
	 * 
	 */
	
	public void run(double[] weights){
		int iter = 0;
		int numObjects = 0;
//		an.setWeight(weights);
		while(numObjects<40){
			if(iter%5==0 && Math.random()>0.5){
				board.addNewObject();
				numObjects++;
			}
			iter();
			iter++;
		}
	}
	
	public void iter(){
		board.iter();
		ba.update();
	}
	
	public List<int[]> getResults(){
		return null;
	}
	
	public BeerAgent getAgent(){
		return ba;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public int getNumberOfWeightsNeeded(){
		return an.getNumberOfWeightsNeeded();
	}
	
	public int getNumberOfNodesNeeded(){
		return an.getNumberOfNodes();
	}
}
