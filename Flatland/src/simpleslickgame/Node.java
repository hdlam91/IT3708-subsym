package simpleslickgame;

public class Node {
	private int functionType;
	private double threshold;
	private double w[], x[];
	private double biasW, biasIn;
	private boolean inputNode;
	public Node(boolean in){
		this();
		inputNode = in;
	}
	
	public Node(){
		biasW = 0;
		biasIn = 0;
		functionType = 0;
		threshold = 0.5;
		inputNode = false;
	}
	
	public void setThreshold(double t){
		threshold = t;
	}
	
	public void input(boolean in[]){
		x = new double[in.length];
		for (int i = 0; i < in.length; i++) {
			x[i]=(in[i]?1:0);
		}
	}
	
	public void input(boolean in){
		x = new double[1];
		x[0] = (in?1:0);
	}
	
	public void input(double in[]){
		x = new double[in.length];
		for (int i = 0; i < in.length; i++) {
			x[i]= in[i];
		}
	}
	
	public void input(double in){
		x = new double[1];
		x[0] = in;
	}
	public void biasInput(double biasIn){
		this.biasIn = biasIn;
	}
	
	public void setBiasWeight(double biasW){
		this.biasW = biasW;
	}
	
	public void setWeight(double v[]){
		w = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			w[i] = v[i];
			//System.out.println("weight set to be:" + w[i]);
		}
	}
	
	
	public double output(){
		if(inputNode){
			return x[0];
		}
		return function(sum());
	}
	
	public double function(double sum){
		if(functionType == 0){//step
			if(sum >= threshold)
				return 1;
			else if(sum <= -threshold)
				return -1;
		}
		else if(functionType == 1){//sigmoid
			double sigmoid = 1.0/(1+Math.exp(-sum));
			return sigmoid;
		}
		else if(functionType == 2){//hyporbolicus something tanh stuff
			double htan = (Math.exp(2*sum)-1)/(Math.exp(2*sum)-1);
			return htan;
		}
		
		return 0;
		
		
	}
	
	
	public double sum(){
		double sum = 0;
		
		if(w != null)
			for (int i = 0; i < x.length; i++) {
				sum += x[i]*this.w[i];
			}
		else
			for (int i = 0; i < x.length; i++) {
				sum += x[i];
			}
		sum+= biasIn*biasW;
		System.out.println("SUM IS"+ sum);
		return sum;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < w.length; i++) {
			sb.append(w[i] + "\t");
		}
		sb.append("\n");
		return sb.toString();
	}
	
}
