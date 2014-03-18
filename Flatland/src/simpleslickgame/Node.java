package simpleslickgame;

public class Node {
	private int functionType;
	private double threshold;
	private double w[], x[];
	public Node(){
		
	}
	
	public void setThreshold(double t){
		threshold = t;
	}
	
	
	
	public int output(){
		return function(sum());
	}
	
	public int function(double sum){
		if(functionType == 0){//step
			if(sum >= threshold)
				return 1;
		}
		else if(functionType == 1){//sigmoid
			double sigmoid = 1.0/(1+Math.exp(-sum));
			if(sigmoid >= threshold)
				return 1;
		}
		else if(functionType == 2){//hyporbolicus something tanh stuff
			double htan = (Math.exp(2*sum)-1)/(Math.exp(2*sum)-1);
			if(htan>= threshold)
				return 1;
		}
		
		return 0;
		
		
	}
	
	
	public double sum(){
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			sum += x[i]*w[i];
		}
		return sum;
	}
	
}
