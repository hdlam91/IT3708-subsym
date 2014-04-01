package evolutionary_Algorithm;

public class test {

	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			int genoType = 0;
			System.out.println(genoType);
			int number = genoType = (int)(Math.random()>0.5? (((Math.random()*(genoType*1.1-genoType*0.9))+genoType*0.9+1)%10001) : (Math.random()*10001)*-1); //10% diff or new random val	
			number = (number>0?(number*-1):number);
			System.out.println(number+"\n");
//			genoType = (((int)((Math.random()*(genoType)*1.1-genoType*0.9)+genoType*0.9+1))%1000);
		}
	}
}
