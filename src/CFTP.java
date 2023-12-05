import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 5;
	private static final double BETA = 0.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    
    private static int[][] gridPOS = new int[GRID_SIZE][GRID_SIZE];
    private static int[][] gridNEG = new int[GRID_SIZE][GRID_SIZE];
    private static int diff = GRID_SIZE*GRID_SIZE;
    private static ArrayList<Pair> moves = new ArrayList<Pair>();
    
    
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
    
    public static void coupling(double flag, double flipPosPOS, double flipPosNEG, int xcoord, int ycoord) {
    	// If flag < flipPosPOS/NEG, set both grids to positive
		if(flag<=flipPosPOS && flag<=flipPosNEG) {
			// check if the grids agree
			if (gridPOS[xcoord][ycoord] == gridNEG[xcoord][ycoord]) {
				gridPOS[xcoord][ycoord] = POS;
				gridNEG[xcoord][ycoord] = POS;
			}
			else {
				gridPOS[xcoord][ycoord] = POS;
				gridNEG[xcoord][ycoord] = POS;
				diff--;
			}
		}
		// If flag > flipPosPOS/NEG, set both grids to negative
		else if(flag>flipPosPOS && flag>flipPosNEG) {
			// check if the grids agree
			if (gridPOS[xcoord][ycoord] == gridNEG[xcoord][ycoord]) {
				gridPOS[xcoord][ycoord] = NEG;
				gridNEG[xcoord][ycoord] = NEG;
			}
			else {
				gridPOS[xcoord][ycoord] = NEG;
				gridNEG[xcoord][ycoord] = NEG;
				diff--;
			}
		}
		// If they disagree, set opposite
		else if(flag<=flipPosPOS && flag>flipPosNEG) {
			// check if the grids agree
			if (gridPOS[xcoord][ycoord] == gridNEG[xcoord][ycoord]) {
				gridPOS[xcoord][ycoord] = POS;
				gridNEG[xcoord][ycoord] = NEG;
				diff++;
			}
			else {
				gridPOS[xcoord][ycoord] = POS;
				gridNEG[xcoord][ycoord] = NEG;
			}
		}
		else {
			// check if the grids agree
			if (gridPOS[xcoord][ycoord] == gridNEG[xcoord][ycoord]) {
				gridPOS[xcoord][ycoord] = NEG;
				gridNEG[xcoord][ycoord] = POS;
				diff++;
			}
			else {
				gridPOS[xcoord][ycoord] = NEG;
				gridNEG[xcoord][ycoord] = POS;
			}
		}
    }
    
    private static int couple_helper(int index) {
    	// RESET ENTIRE GRID
    	diff = GRID_SIZE * GRID_SIZE; // Reset diff calculation
		for(int i=0; i<GRID_SIZE; i++) {
			for(int j=0; j<GRID_SIZE; j++) {
				gridPOS[i][j] = POS;
				gridNEG[i][j] = NEG;
				
			}
		}
		
		// DETERMINE IF WE COUPLE
		for(int j=index; j<moves.size(); j++) {
			double flipPosPOS = isingCalculationPos(moves.get(j).x, moves.get(j).y);
			double flipPosNEG = isingCalculationNeg(moves.get(j).x, moves.get(j).y);
			coupling(moves.get(j).randNum, flipPosPOS, flipPosNEG, moves.get(j).x, moves.get(j).y);
		}
		return diff;
    }
    
    // CONDUCT BINARY SEARCH THROUGH "moves" to find exact coupling point
    // OPTIMIZE THIS LATER
    private static int binarySearch(int low_index, int high_index) {
    	System.out.println(low_index + " ... " + high_index);
    	if(low_index>high_index) {
    		System.out.println("ERROR");
    		return -1;
    	}
    	int mid = (low_index + high_index)/2;
    	
    	// RESET ENTIRE GRID
		diff = GRID_SIZE * GRID_SIZE; // Reset diff calculation
		for(int i=0; i<GRID_SIZE; i++) {
			for(int j=0; j<GRID_SIZE; j++) {
				gridPOS[i][j] = POS;
				gridNEG[i][j] = NEG;
				
			}
		}
		
		// DETERMINE IF WE COUPLE
//		for(int j=mid; j<moves.size(); j++) {
//			double flipPosPOS = isingCalculationPos(moves.get(j).x, moves.get(j).y);
//			double flipPosNEG = isingCalculationNeg(moves.get(j).x, moves.get(j).y);
//			coupling(moves.get(j).randNum, flipPosPOS, flipPosNEG, moves.get(j).x, moves.get(j).y);
//		}
		couple_helper(mid);
		
		if(diff != 0) { // If diff != 0, then we need more steps to couple
			if(low_index+1 == high_index) {
				if(couple_helper(high_index) == 0) {
					return low_index;
				}
				return low_index;
			}
			return binarySearch(low_index, mid-1);
		}
		else {
			if(low_index == high_index) {
				if(couple_helper(low_index) != 0) {
					System.out.println("NO SOLUTION");
					return -2;
				}
				return low_index;
			}
			if(couple_helper(mid+1) != 0) {
				return mid;
			}
			return binarySearch(mid+1, high_index);	
		}
    }
	
    
	public static void main(String[] args) {
		// Initialize grids to all positive/negative
		for(int i=0; i<GRID_SIZE; i++) {
			for(int j=0; j<GRID_SIZE; j++) {
				gridPOS[i][j] = POS;
				gridNEG[i][j] = NEG;
				
			}
		}
		
		int xcoord, ycoord, necessary_steps = 0;
		Random random = new Random();
		
		System.out.println(moves.size() + "..." + necessary_steps + "\nNECESSARY STEPS: " + (moves.size()-necessary_steps));
	}

}
