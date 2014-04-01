package evolutionary_Algorithm;

public class test {

	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			int genoType = (int)(Math.random()*1001*(Math.random()>0.5?1:-1));
			System.out.println(genoType);
			int number = (int)(Math.random()>0.5? (((Math.random()*(genoType*1.1-genoType*0.9))+genoType*0.9+1)%1001) : (Math.random()*1001*(Math.random()>0.5?1:-1)));
			System.out.println(number+"\n");
//			genoType = (((int)((Math.random()*(genoType)*1.1-genoType*0.9)+genoType*0.9+1))%1000);
		}
	}
}
