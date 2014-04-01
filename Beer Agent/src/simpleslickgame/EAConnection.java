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
		test();
	}
	
	/**
	 * TODO
	 * 
	 */
	
	public void iter(){
		board.iter();
		ba.update();
	}
	
	public void test(){
		board.addNewObject();
		board.addNewObject();
		board.iter();
	}
	
	public void run(double[] weights){}
	
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

	public double[] getBestFitness() {
		double temp[] = {1,2,3,4,45,5,6,7,8,1.6,90,9};
		return temp;
	}

	public double[] getAvgFitness() {
		double temp[] = {1,2,3,4,45,5,6,7,8,1.6,90,9};
		return temp;
	}

	public double[] getSDFitness() {
		double temp[] = {1,2,3,4,45,5,6,7,8,1.6,90,9};
		return temp;
	}
}
