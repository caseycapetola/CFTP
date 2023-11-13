import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 9;
	private static final double BETA = 1.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    
    private static int[][] gridPOS = new int[GRID_SIZE][GRID_SIZE];
    private static int[][] gridNEG = new int[GRID_SIZE][GRID_SIZE];
    private static int diff = GRID_SIZE*GRID_SIZE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Initialize grids to all positive/negative
				for(int i=0; i<GRID_SIZE; i++) {
					for(int j=0; j<GRID_SIZE; j++) {
						gridPOS[i][j] = POS;
						gridNEG[i][j] = NEG;
						
					}
				}
				
				int xcoord, ycoord;
				Random random = new Random();
	}

}
