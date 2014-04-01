package evolutionary_Algorithm;

public class test {

	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			int genoType = 2001;
			System.out.println(genoType);
			int number = (int)(Math.random()>0.5? (((Math.random()*(genoType*1.1-genoType*0.9))+genoType*0.9+1)) : (Math.random()*(2001-1000))+1000); //10% diff or new random val	
			number = (number>2001?(number%2001)+1000:number);
			System.out.println(number+"\n");
//			genoType = (((int)((Math.random()*(genoType)*1.1-genoType*0.9)+genoType*0.9+1))%1000);
		}
	}
}
