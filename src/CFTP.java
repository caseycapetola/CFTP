import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 10;
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
		//If flag > flipPosPOS/NEG, set both grids to negative
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
		
		// Each iteration we'll try 100 moves (times 2 for each iteration)
		int steps = 100;
		
		boolean isCoupled = false;
		
		while(!isCoupled) {
			// STEP 1: Iterate over new randomness
			for(int i=0; i<steps; i++) {
				xcoord = random.nextInt(GRID_SIZE);
				ycoord = random.nextInt(GRID_SIZE);
				double flipPosPOS = isingCalculationPos(xcoord, ycoord);
				double flipPosNEG = isingCalculationNeg(xcoord, ycoord);
				double flag = random.nextDouble();
				moves.add(0, new Pair(xcoord, ycoord, flag, flipPosPOS, flipPosNEG));
				coupling(flag, flipPosPOS, flipPosNEG, xcoord, ycoord);
			}
			
			// STEP 2: Iterate over old logic
			int index = moves.size();
			for(int j=steps; j<moves.size(); j++) {
				coupling(moves.get(j).randNum, moves.get(j).posCalc, moves.get(j).negCalc, moves.get(j).x, moves.get(j).y);
			}
			
			if(diff==0) {
				isCoupled = true;
				
				// ADD BINARY SEARCH LOGIC TO FIND EXACTLY WHERE WE COUPLE
			}
		}
		System.out.println(moves.size());
	}

}
