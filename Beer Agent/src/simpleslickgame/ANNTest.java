package simpleslickgame;

import java.util.ArrayList;
import java.util.Arrays;

public class ANNTest {
	
	
	private ArrayList<Node> outputs;
	private ArrayList<Node[]> hiddenNodes; 
	int numOfInput, numOfOutput;
	boolean hiddenLayerUsed;
	
	int[] hiddenLayerStructure;
	
	public ANNTest(double treshold, int inputs) {//no hidden layers
		hiddenLayerUsed = false;
		
		outputs = new ArrayList<Node>();
		for (int i = 0; i < 2; i++) {
			outputs.add(new Node(treshold));			
		}
		numOfInput = inputs;
		numOfOutput = outputs.size();
	}
	

	public ANNTest(int[] nodeStructure, double treshold, int inputs) {
		this(treshold, inputs);
		
		hiddenNodes = new ArrayList<Node[]>();
		for (int i = 0; i < nodeStructure.length; i++) {
			if(nodeStructure[i]== 0 && i != 0){
				System.out.println("ERROR, 0 nodes for the hidden layer at layer:" + i);
			}
			Node[] temp = new Node[nodeStructure[i]];
			for (int j = 0; j < temp.length; j++) {
				temp[j] = new Node(treshold);
			}
			hiddenNodes.add(temp);
		}
		hiddenLayerStructure = nodeStructure.clone();
		if(nodeStructure.length == 0 || nodeStructure[0] == 0){
			hiddenLayerUsed = false;
		}
		else{
			hiddenLayerUsed = true;
		}
	}
	
	public int getNumberOfWeightsNeeded(){
		int numOfW = 0;
		if(hiddenLayerUsed){
			numOfW += numOfInput*hiddenLayerStructure[0];
			for (int i = 1; i < hiddenLayerStructure.length; i++) {
				numOfW += hiddenLayerStructure[i-1]*hiddenLayerStructure[i];
			}
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				numOfW += hiddenLayerStructure[i]*hiddenLayerStructure[i]; //self weight
			}
			numOfW += numOfOutput*hiddenLayerStructure[hiddenLayerStructure.length-1];
		}
		else{
			numOfW = numOfInput*numOfOutput;
		}
		numOfW+= numOfOutput*numOfOutput;
		
		return numOfW;
	}
	
	public void test(){
 		int needed = getNumberOfNodes()*3+getNumberOfWeightsNeeded();
 		System.out.println(needed);
 		double v[] = new double[needed];
 		for (int i = 0; i < v.length; i++) {
			v[i] = i;
		}
 		
 		
 		setWeight(v);
 		if(hiddenLayerUsed)
 			for (int i = 0; i < hiddenNodes.size(); i++) {
 				for (int j = 0; j < hiddenNodes.get(i).length; j++) {
 					System.out.println(hiddenNodes.get(i)[j]);
 				}
					
 			}
 		for (int i = 0; i < outputs.size(); i++) {
 			System.out.println(outputs.get(i));
 		}
 		System.out.println("\n\n\n" + getNumberOfWeightsNeeded());
  	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(hiddenLayerUsed)
 			for (int i = 0; i < hiddenNodes.size(); i++) {
 				sb.append(hiddenNodes.get(i)+"\n");
 			}
 		for (int i = 0; i < outputs.size(); i++) {
 			sb.append(outputs.get(i)+"\n");
 		}
 		return sb.toString();
	}



 	public int getNumberOfNodes(){
 		int numOfNodes = 0;
 		if(hiddenLayerUsed){
	 		for (int i = 0; i < hiddenLayerStructure.length; i++) {
	 			numOfNodes+= hiddenLayerStructure[i];
	 		}
 		}
 		numOfNodes += numOfOutput;
 		return numOfNodes;
			
 	}
	
 	
 
 	public void setBias(double b[]){
 		int counter = 0;
 		if(hiddenLayerUsed){
	 		for (int i = 0; i < hiddenNodes.size(); i++) {
	 			for (int j = 0; j < hiddenNodes.get(i).length; j++) {
					hiddenNodes.get(i)[j].setBiasWeight(b[counter]);
					counter++;
				}
	 		}
 		}
 		for (int i = 0; i < numOfOutput; i++) {
 			outputs.get(i).setBiasWeight(b[counter]);
 			counter++;
 		}
 	}
 	
 	public void setGains(double g[]){
 		int counter = 0;
 		if(hiddenLayerUsed){
	 		for (int i = 0; i < hiddenNodes.size(); i++) {
	 			for (int j = 0; j < hiddenNodes.get(i).length; j++) {
					hiddenNodes.get(i)[j].setGain(g[counter]);
					counter++;
				}
	 		}
 		}
 		for (int i = 0; i < numOfOutput; i++) {
 			outputs.get(i).setGain(g[counter]);
 			counter++;
 		}
 	}
 	public void setTime(double t[]){
 		int counter = 0;
 		if(hiddenLayerUsed){
	 		for (int i = 0; i < hiddenNodes.size(); i++) {
	 			for (int j = 0; j < hiddenNodes.get(i).length; j++) {
					hiddenNodes.get(i)[j].setTime(t[counter]);
					counter++;
				}
	 		}
 		}
 		for (int i = 0; i < numOfOutput; i++) {
 			outputs.get(i).setTime(t[counter]);
 			counter++;
 		}
 	}
	
	
	public void setWeight(double v[]){
		int start = 0;
		int end = 0;
		if(hiddenLayerUsed){
			for (int i = 0; i < hiddenNodes.size(); i++) {
				for (int j = 0; j < hiddenNodes.get(i).length; j++) {
					start = end;
					if(i == 0)
						end = start+numOfInput;
					else
						end = start+hiddenLayerStructure[i-1]+hiddenLayerStructure[i];
					if(i == 0){
						hiddenNodes.get(i)[j].setInWeight(Arrays.copyOfRange(v, start, end));
						start = end;
						end = end+hiddenLayerStructure[i]; //self and neighbour weight
						hiddenNodes.get(i)[j].setWeight(Arrays.copyOfRange(v, start, end));
					}
					else{
						hiddenNodes.get(i)[j].setWeight(Arrays.copyOfRange(v, start, end));
					}
				}
			
			
			}
			for (int i = hiddenNodes.size(); i < hiddenNodes.size()+numOfOutput; i++) {
				outputs.get(i-hiddenNodes.size()).setWeight(Arrays.copyOfRange(v, end, end+hiddenLayerStructure[hiddenLayerStructure.length-1]+outputs.size()));
				end+=hiddenLayerStructure[hiddenLayerStructure.length-1]+outputs.size();
			}
		}
			
		else{
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).setInWeight(Arrays.copyOfRange(v, end, end+numOfInput));
				end+=numOfInput;
				outputs.get(i).setWeight(Arrays.copyOfRange(v, end, end+outputs.size()));
				end+=outputs.size();
			}
		}
		
		//setting time gains and bias
		int numOfNodes = getNumberOfNodes();
		
		setBias(Arrays.copyOfRange(v, end, end+numOfNodes));
		end+= numOfNodes;
		setGains(Arrays.copyOfRange(v, end, end+numOfNodes));
		end+= numOfNodes;
		setTime(Arrays.copyOfRange(v, end, end+numOfNodes));
		end+=numOfNodes;
		
		
		
	}
	
	public void run(double[] input){
		if(hiddenLayerUsed){
			double[] currentOut = null;
			for (int i = 0; i < hiddenNodes.size(); i++) {
				double[] lastForCurrentLayer = new double[hiddenNodes.get(i).length];
				
				
				for (int j = 0; j < hiddenNodes.get(i).length; j++){
					lastForCurrentLayer[j] = hiddenNodes.get(i)[j].lastOutput();//gets the output from LAST iteration
				}
				for (int j = 0; j < hiddenNodes.get(i).length; j++) {
					if(i == 0){
						hiddenNodes.get(i)[j].directIn(input.clone()); //direct input
						hiddenNodes.get(i)[j].input(lastForCurrentLayer); //last iteration's input
					}
					else{
						hiddenNodes.get(i)[j].input(currentOut);
						hiddenNodes.get(i)[j].addinput(lastForCurrentLayer); //last iteration's input
					}
						
				}
				currentOut = new double[hiddenNodes.get(i).length];
				for (int j = 0; j < hiddenNodes.get(i).length; j++) {
					currentOut[j] = hiddenNodes.get(i)[j].output(); //gets this rounds output, saving for next round
				}
				
			}
			
			//this is ensured above
//			currentOut = new double[hiddenNodes.get(hiddenNodes.size()-1).length]; 
//			for (int j = 0; j < hiddenNodes.get(hiddenNodes.size()-1).length; j++){
//				currentOut[j] =  hiddenNodes.get(hiddenNodes.size()-1)[j].output(); //gets above output
//			}
			double[] lastForCurrentLayer = new double[outputs.size()];
			for (int i = 0; i < outputs.size(); i++) {
				lastForCurrentLayer[i] = outputs.get(i).lastOutput(); //gets neighbour and self last
			}
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).input(currentOut); //adds above layer
				outputs.get(i).input(lastForCurrentLayer); //adds lasttime
			}
			
		}
		else{
			//no hidden layers.
			double[] newLayer = new double[outputs.size()];
			for (int i = 0; i < outputs.size(); i++) {
				newLayer[i] = outputs.get(i).lastOutput();
			}
			for (int j = 0; j < outputs.size(); j++) {
				outputs.get(j).directIn(input.clone());
				outputs.get(j).input(newLayer);
			}
		}
	}
	
	
	
	
	public void input(boolean [] b){
		double[] converted= new double[b.length];
		for (int i = 0; i < converted.length; i++) {
			converted[i] = b[i]? 1: 0;
		}
		run(converted);
	}
	
	
	public double getLeftMotor(){
		return outputs.get(0).output();
	}
	
	public double getRightMotor(){
		return outputs.get(1).output();
	}
	
	
	public int output(){
		return 0;
	}



}