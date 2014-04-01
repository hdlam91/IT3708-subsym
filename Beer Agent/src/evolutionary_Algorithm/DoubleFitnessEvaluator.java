package evolutionary_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import simpleslickgame.EAConnection;

public class DoubleFitnessEvaluator extends FitnessEvaluator<Integer>{

	private EAConnection connection;
	
	public DoubleFitnessEvaluator(EAConnection con) {
		this.connection = con;
	}
	
	@Override
	public double getFitnessValue(PhenoType<Integer> phenoType) {
		DoublePhenoType temp = (DoublePhenoType) phenoType;
		connection.run(temp.getDoublePhenoTypeValues());
		List<int[]> results = connection.getResults();
		
		List<Double> sums = new ArrayList<Double>();
		double sum = 0;
		for (int[] r : results) {
			int foodEaten = r[0];
			int totalFood = r[1];
			int poisonEaten = r[2];
			int totalPoison = r[3];
			sum = foodEaten/(totalFood*0.85) - poisonEaten/(totalPoison*1.0);
			sums.add(sum);
			sum = 0;
		}
		for (Double s : sums) {
			sum+=s;
		}
		sum=sum/sums.size();
		return sum;
	}
}
