import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 9;
	private static final double BETA = 1.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    
    private static int[][] gridPOS = new int[GRID_SIZE][GRID_SIZE];
    private static int[][] gridNEG = new int[GRID_SIZE][GRID_SIZE];
    private static int diff = GRID_SIZE*GRID_SIZE;
    
    
 // Ising Calculation for POS grid
  	private static double isingCalculationPos(int x, int y) {
      	int numPos = 0;
      	int numNeg = 0;
      	
  		// CHECK HORIZONTAL NEIGHBORS
  		if(y>0) {
  			if(gridPOS[x][y-1] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		if(y<GRID_SIZE-1) {
  			if(gridPOS[x][y+1] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		
  		// CHECK VERTICAL NEIGHBORS
  		if(x>0) {
  			if(gridPOS[x-1][y] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		if(x<GRID_SIZE-1) {
  			if(gridPOS[x+1][y] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		
  		return (Math.exp(BETA*numPos)/(Math.exp(BETA*numPos) + Math.exp(BETA*numNeg)));
      	
      }
  	
  	// Ising Calculation for NEG grid
  	private static double isingCalculationNeg(int x, int y) {
      	int numPos = 0;
      	int numNeg = 0;
      	
  		// CHECK HORIZONTAL NEIGHBORS
  		if(y>0) {
  			if(gridNEG[x][y-1] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		if(y<GRID_SIZE-1) {
  			if(gridNEG[x][y+1] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		
  		// CHECK VERTICAL NEIGHBORS
  		if(x>0) {
  			if(gridNEG[x-1][y] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		if(x<GRID_SIZE-1) {
  			if(gridNEG[x+1][y] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		
  		return (Math.exp(BETA*numPos)/(Math.exp(BETA*numPos) + Math.exp(BETA*numNeg)));
      	
      }
    
    public boolean coupling(int steps) {
    	return false;
    }
	
	public static void main(String[] args) {
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
