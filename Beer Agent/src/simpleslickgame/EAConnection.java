package simpleslickgame;

import java.util.List;
import java.util.ArrayList;

public class EAConnection {
	private BeerAgent ba;
	private ANN an;
	private Board board;
	
	private int numObjects = 0;
	private int iter = 0;
	
	private int contacts = 0;
	private int captures = 0;
	private int bigCaptures = 0;
	
	public EAConnection(){
		int[] hid = {2};
		an = new ANN(hid,0.5,5);
		an.test();
		board = new Board(30, 15, 0, 5, 0);
		ba = new BeerAgent(an,board);
	}
	
	
	public void run(double[] weights){
		an.setWeight(weights);
		System.out.println(an);
		restart();
		while(numObjects<40)
			iter();
	}
	
	public void iter(){
		iter++;
		board.iter();
		if(numObjects<40 && (iter>=7 && Math.random()>0.5)){
			board.addNewObject();
			numObjects++;
			board.updateBoard();
			iter = 0;
		}
		ba.update();
		
		for (FallingObject f : board.getFallingObjects()) {
			if(f.contact(ba.getRenderPosition())){
				contacts++;
			}
			if(f.caught(ba.getRenderPosition(), 0.8)){
				captures++;
				if(f.getWidth()>5)
					bigCaptures++;
			}
		}
	}
	
	public void restart(){
		iter = 0;
		numObjects = 0;
		contacts = 0;
		captures = 0;
		bigCaptures  =0;
		board.clearAll();
		ba.reset();
	}
	
	public List<int[]> getResults(){
		List<int[]> ret = new ArrayList<int[]>();
		int[] temp = {
			contacts,
			captures,
			numObjects,
			bigCaptures,
			board.getNumberOfLargeObjects()
		};
		ret.add(temp);
		return ret;
	}
	
	public BeerAgent getAgent(){
		return ba;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public int getNumberOfWeightsNeeded(){
//		return 3;
		return an.getNumberOfWeightsNeeded();
	}
	
	public int getNumberOfNodesNeeded(){
//		return 3;
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
	public int getCaptures(){
		return captures;
	}
	
	public int getContacts(){
		return contacts;
	}
	
	public int getBigCaptures(){
		return bigCaptures;
	}
	
	public int getNumOfObjectCreated(){
		return numObjects;
	}
	
	public int getTotalLargeObjects(){
		return board.getNumberOfLargeObjects();
	}
}
