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
			int contacts = r[0];
			int captures = r[1];
			int total = r[2];
			int bigcaptures = r[3];
			int bigtotal = r[4];
			if(captures==0)
				sum = -1;
			else if(bigtotal==0)
				sum = (captures/(total*1.0))-((contacts-captures)/(total*1.0));
			else sum = ((captures)/(total*1.0))-((contacts-captures)/(total*1.0)); //-bigcaptures
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
