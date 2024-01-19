
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 100000;
//		int[][] grid = new int[n][n];
		long time = System.currentTimeMillis();
		for(int i=0; i<1000000; i++) {
			n++;
		}
		
		System.out.print(System.currentTimeMillis()-time);

	}

}
