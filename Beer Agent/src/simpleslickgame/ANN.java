package simpleslickgame;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;

public class ANN {
	
	
	private ArrayList<Node> outputs;
	private ArrayList<Node> hiddenNodes; 
	int numOfInput, numOfOutput;
	boolean hiddenLayerUsed;
	
	int[] hiddenLayerStructure;
	
	public ANN(double treshold, int inputs) {//no hidden layers
		hiddenLayerUsed = false;
		
		outputs = new ArrayList<Node>();
		for (int i = 0; i < 2; i++) {
			outputs.add(new Node(treshold));			
		}
		numOfInput = inputs;
		numOfOutput = outputs.size();
	}
	

	public ANN(int[] nodeStructure, double treshold, int inputs) {
		this(treshold, inputs);
		
		hiddenNodes = new ArrayList<Node>();
		for (int i = 0; i < nodeStructure.length; i++) {
			if(nodeStructure[i]== 0 && i != 0){
				System.out.println("ERROR, 0 nodes for the hidden layer at layer:" + i);
			}
			for (int j = 0; j < nodeStructure[i]; j++) {
				this.hiddenNodes.add(new Node(treshold));
			}
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
 		double v[] = {-4.703, -2.752, -2.22, 1.419, -4.645, -2.254, -2.189, -0.011, -4.129, 1.308, -2.519, 4.61, 0.546, 1.423, 0.076, -3.912, 0.962, 2.793, 3.128, 2.61, -3.285, 0.139, -7.799, -6.092, -2.942, -5.16, 3.572, 4.536, 4.052, 2.648, 1.272, 1.434, 1.626, 1.422};
// 		for (int i = 0; i < v.length; i++) {
// 			v[i] = Math.random()*5;
// 		}   
 		setWeight(v);
 		if(hiddenLayerUsed)
 			for (int i = 0; i < hiddenNodes.size(); i++) {
 				System.out.println(hiddenNodes.get(i));
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

// public static void main(String[] args) {
//        int [] li = {2};
//        ANN a = new ANN(li,0.5,5);
//        a.test();
        
//        boolean[] b = {false,true,true,false,false};
//        a.input(b);
//        double left = a.getLeftMotor();
//        double right = a.getRightMotor();
//        for (int i = 0; i < 10; i++) {
//            a.input(b);
//
//        	if(left == right){
//        		
//        	}
//        	else if(left > right){
//        		System.out.println("left");
//        	}
//        	else
//        		System.out.println("right");
//        	System.out.println(left);
//        	System.out.println(right);
//		}
        
//    }   
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
	 		for (int i = 0; i < hiddenLayerStructure.length; i++) {
	 			for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					hiddenNodes.get(counter).setBiasWeight(b[counter]);
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
	 		for (int i = 0; i < hiddenLayerStructure.length; i++) {
	 			for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					hiddenNodes.get(counter).setGain(g[counter]);
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
	 		for (int i = 0; i < hiddenLayerStructure.length; i++) {
	 			for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					hiddenNodes.get(counter).setTime(t[counter]);
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
		int counter = 0;
		int start = 0;
		int end = 0;
		if(hiddenLayerUsed){
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					start = end;
					if(i == 0){
						end = start+numOfInput;
					}
					else
						end = start+hiddenLayerStructure[i-1]+hiddenLayerStructure[i];
					if(i == 0){
						hiddenNodes.get(counter).setInWeight(Arrays.copyOfRange(v, start, end));
						start = end;
						end = end+hiddenLayerStructure[i]; //self and neighbour weight
						hiddenNodes.get(counter).setWeight(Arrays.copyOfRange(v, start, end));
//						System.out.println("end:" + end);
					}
					else{
						hiddenNodes.get(counter).setWeight(Arrays.copyOfRange(v, start, end));
					}
					counter++;
					
				}
			
			
			}
			for (int i = hiddenNodes.size(); i < hiddenNodes.size()+numOfOutput; i++) {
				outputs.get(i-hiddenNodes.size()).setWeight(Arrays.copyOfRange(v, end, end+hiddenLayerStructure[hiddenLayerStructure.length-1]+outputs.size()));
				end+=hiddenLayerStructure[hiddenLayerStructure.length-1]+outputs.size();
//				System.out.println("end:" + end);
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
//		System.out.println(numOfNodes);
//		System.out.println("end:" + end + "/" + v.length);
		
		
		
	}
	
	public void run(double[] input){
		if(hiddenLayerUsed){
			double[] currentOut = input.clone();
			double[] thisOut = null;
			int counter = 0;
			for (int i = 0; i < hiddenLayerStructure.length; i++) {
				double[] lastOut = new double[hiddenLayerStructure[i]];
				if(i > 0)
					thisOut = new double[hiddenLayerStructure[i-1]]; //gets the output from the above layers
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					if(i > 0)
						lastOut[j] = hiddenNodes.get(hiddenLayerStructure[i-1]+j).lastOutput(); //gets the output from last above layer
					else
						lastOut[j] = hiddenNodes.get(j).lastOutput(); //gets the last output from this layer
				}
				
				
				for (int j = 0; j < hiddenLayerStructure[i]; j++) {
					if(i == 0){
						hiddenNodes.get(counter).directIn(input.clone()); // direct input
						hiddenNodes.get(counter).input(lastOut); //inputs from neighbours and self from last round
					}
					else{
						hiddenNodes.get(counter).input(currentOut);
						thisOut[j] = hiddenNodes.get(counter).output(); //hentet alle outputene for neste iterasjon 
						
					}
					
					
					counter++;
					
				}
				if(i > 0)
				{
					currentOut = new double[thisOut.length+lastOut.length];
					for (int j = 0; j < thisOut.length+lastOut.length; j++) {
						if(j < thisOut.length)
							currentOut[j] = thisOut[j];
						else
							currentOut[j] = lastOut[j-thisOut.length];
					}
				}
			}
			//last hidden and output node
			double[] lastOut = new double[outputs.size()];
			for (int i = 0; i < outputs.size(); i++) {
				lastOut[i] = outputs.get(i).lastOutput();
			}
			if(thisOut == null)
				thisOut = new double[hiddenLayerStructure[hiddenLayerStructure.length-1]];
			currentOut = new double[thisOut.length+lastOut.length];
			for (int j = 0; j < thisOut.length+lastOut.length; j++) {
				if(j < thisOut.length)
					currentOut[j] = thisOut[j];
				else
					currentOut[j] = lastOut[j-thisOut.length];
			}
			for (int i = 0; i < outputs.size(); i++) {
				outputs.get(i).input(currentOut);
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


// 	public int getNumberOfWeightsNeeded(double v[]){
//		int numOfW = 0;
//		if(hiddenLayerUsed){
//			numOfW += numOfInput*hiddenLayerStructure[0];
//			for (int i = 1; i < hiddenLayerStructure.length; i++) {
//				numOfW += hiddenLayerStructure[i-1]*hiddenLayerStructure[i];
//			}
//			for (int i = 0; i < hiddenLayerStructure.length; i++) {
//				numOfW += hiddenLayerStructure[i]; //bias weight
//				numOfW += hiddenLayerStructure[i]*hiddenLayerStructure[i]; //self weight
//			}
//			numOfW += numOfOutput*hiddenLayerStructure[hiddenLayerStructure.length-1]+numOfOutput;
//		}
//		else{
//			numOfW = numOfInput*numOfOutput+numOfOutput;
//		}
//		numOfW+= numOfOutput*numOfOutput;
//		
//		return numOfW;
//	}
	
//	public void runBACKUP(double[] input){
//		double firstOut[] = new double[numOfInput];
//		if(hiddenLayerUsed){
//			double[] currentOut = input.clone();
//			int counter = 0;
//			for (int i = 0; i < hiddenLayerStructure.length; i++) {
//				double[] newLayer = new double[hiddenLayerStructure[i]];
//				for (int j = 0; j < hiddenLayerStructure[i]; j++) {				
//					
//					hiddenNodes.get(counter).input(currentOut);
//					newLayer[j] = hiddenNodes.get(counter).output(); 
//					counter++;
//				}
//				currentOut = newLayer;
//			}
//			for (int i = 0; i < outputs.size(); i++) {
//				outputs.get(i).input(currentOut);
//			}
//		}
//		else{ //no hidden layer, direct mapping.
////			System.out.println("inputNode's output:" + Arrays.toString(firstOut));
//			for (int i = 0; i < outputs.size(); i++) {
//				outputs.get(i).input(firstOut);
//			}
////			System.out.println("output:l" + outputs.get(0).output() + "f"+ outputs.get(1).output() + "r" + outputs.get(2).output());
//		}
//	}
	
	
}