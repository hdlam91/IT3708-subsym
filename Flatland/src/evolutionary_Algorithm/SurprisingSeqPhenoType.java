package evolutionary_Algorithm;

import java.util.Arrays;

public class SurprisingSeqPhenoType extends PhenoType<Integer>{

//	public SurprisingSeqPhenoType(int numberOfFields) {
//		super(numberOfFields);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void convertFromGenoTypeToPhenoType(Integer[] genotype,
//			int lengthOfGenoType) {
//		// TODO Auto-generated method stub
//		
//	}

	public SurprisingSeqPhenoType(int numberOfFields) {
		super(numberOfFields);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		buf.append(Arrays.toString(representation)+"\n");
		for (int i = 0; i < representation.length; i++) {
			buf.append(/*(char)*/ representation[i] + " ");
		}
		buf.append("\n\n");
		return buf.toString();
	}

	@Override
	public void convertFromGenoTypeToPhenoType(Integer[] genotype,int lengthOfGenoType) {
//		Integer[]temp;
//		for (int i = 0; i < lengthOfGenoType/numberOfBitsPerLetter; i++) {
//			temp = Arrays.copyOfRange(genotype, i*numberOfBitsPerLetter, i*numberOfBitsPerLetter+numberOfBitsPerLetter);
//			this.representation[i] = booleansToInt(temp);
//		}
		
		//shouldn't need to change anything if they are all integers anyway.
	}
	
//	public int booleansToInt(boolean[] array){
//		int ret = 0;
//		for (boolean b : array) {
//			ret = (ret << 1 ) | (b?1:0);
//		}
//		return ret;
//	}

}
