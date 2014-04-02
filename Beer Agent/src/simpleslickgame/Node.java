package simpleslickgame;

import java.util.ArrayList;

public class Node {
	private int functionType;
	private double threshold;
	private double xIn[];
	private ArrayList<Double> w,x;
	private double biasW;
	private boolean inputNode;
	private double y, DY, s;
	private double gain, time;
	private boolean hasIn;
	
	private double out, lastOut;
	
	private double inWeight[]; //only used for I_j
	
	public Node(double t){
		biasW = 0;
		functionType = 0;
		inputNode = false;
		setThreshold(t);
		hasIn = false;
		out = 0;
		lastOut = 0;
		w = new ArrayList<Double>();
		x = new ArrayList<Double>();
	}
	
	public void setThreshold(double t){
		threshold = t;
	}
	
	
	
	
	public void calculateS(){
		s = 0;
		for (int i = 0; i < x.size(); i++) {
			s+= x.get(i)*w.get(i);
		}
		
		if(hasIn){
			for (int j = 0; j < xIn.length; j++) {
				s+= xIn[j]*inWeight[j];
				
			}
		}
			
	}
	
	
	public void calculateDY(){
		DY = (1/time) * (-y+s+biasW);
	}
	
	public void calculateY(){
		y = y +DY;
	}
	
	public void calculateOut(){
		lastOut = out;
		out = 1.0/(1+Math.exp(-gain*y));
	}
	
	
	public void input(double in[]){
		x.clear();;
		for (int i = 0; i < in.length; i++) {
			x.add(in[i]);
		}
	}
	
	public void input(double in){
		x.clear();
		x.add(in);
	}
	
	public void directIn(double in[]){
		xIn = new double[in.length];
		for (int i = 0; i < in.length; i++) {
			xIn[i]= in[i];
		}
	}
	
	public void biasInput(double biasIn){
//		this.biasIn = biasIn;
	}
	
	public void setBiasWeight(double biasW){
		this.biasW = biasW;
	}
	
	public void setWeight(double v[]){
		w.clear();
		for (int i = 0; i < v.length; i++) {
			w.add(v[i]);
		}
	}
	
	public void setInWeight(double[] inV) {
		hasIn = true;
		inWeight = new double[inV.length];
		for (int i = 0; i < inV.length; i++) {
			inWeight[i] = inV[i];
		}
	}
	
	public double output(){
		calculateS();
		calculateDY();
		calculateY();
		calculateOut();
		return out;	
	}
	public double lastOutput(){
		return lastOut;
	}
	
//	public double function(double sum){
//		if(functionType == 0){//step
//			if(sum >= threshold)
//				return 1;
//			else if(sum <= -threshold)
//				return -1;
//		}
//		else if(functionType == 1){//sigmoid
//			double sigmoid = 1.0/(1+Math.exp(-sum));
//			return sigmoid;
//		}
//		else if(functionType == 2){//hyporbolicus something tanh stuff
//			double htan = (Math.exp(2*sum)-1)/(Math.exp(2*sum)-1);
//			return htan;
//		}
//		
//		return 0;
//		
//		
//	}
	
	
//	public double sum(){
//		double sum = 0;
//		
//		if(w != null)
//			for (int i = 0; i < x.length; i++) {
//				sum += x[i]*w.get(i);
//			}
//		else
//			for (int i = 0; i < x.length; i++) {
//				sum += x[i];
//			}
//		//sum+= biasIn*biasW;
////		System.out.println("SUM IS"+ sum);
//		return sum;
//	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(hasIn){
			sb.append("Direct weights:\n");
			for (int i = 0; i < inWeight.length; i++) {
				
				sb.append(inWeight[i] + "\t");
			}
			
		}
		sb.append("\nneighbour weights: \n");
		for (int i = 0; i < w.size(); i++) {
			sb.append(w.get(i) + "\t");
		}
		//sb.append(biasW);
		sb.append("\n");
		sb.append("bias:" +biasW);
		sb.append("\tgain:" + gain);
		sb.append("\ttime:" +time+"\n");
		return sb.toString();
	}

	public void setGain(double g) {
		gain = g;
	}
	
	public void setTime(double t){
		time = t;
	}

	public void addinput(double[] last) {
		for (int i = 0; i < last.length; i++) {
			x.add(last[i]);
		}
		
	}

}
