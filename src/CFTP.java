import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 100;
	private static final double BETA = 0.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    private static int STEP_SIZE = 100;
    
    private static ArrayList<Pair> moves = new ArrayList<Pair>();
    
	public static int binary_search(int low_index, int high_index, IsingGrid grid1, IsingGrid grid2) {
		int mid = (low_index+high_index)/2;
		if(grid1.isCoupled(grid2, moves, mid) == 0) {
			if(grid1.isCoupled(grid2, moves, mid+1) != 0 || low_index==high_index) {
				return mid;
			}
			return binary_search(mid+1, high_index, grid1, grid2);
		}
		return binary_search(low_index, mid, grid1, grid2);
	
	}
    
	public static void main(String[] args) {
		// Initialize grids
		IsingGrid posGrid = new IsingGrid(POS, GRID_SIZE, BETA);
		IsingGrid negGrid = new IsingGrid(NEG, GRID_SIZE, BETA);
		
		int xcoord = 0, ycoord = 0, index = STEP_SIZE; 
		int num_loops = 0;
		Random random = new Random();
		
		int coupled = 1;
		
		while(coupled != 0) {
			if(num_loops>0) {
				index = (STEP_SIZE*(int)(Math.pow(2, num_loops-1)));
			}
			
			
			boolean even_flag = true;
			// DECOUPLE THIS FROM WHILE LOOP
			// STEP 1: Iterate over/Add new randomness
			for(int i=0; i<index; i++) {
				double flag = random.nextDouble();
				moves.add(0, new Pair(xcoord, ycoord, flag));
				
				// CHECKERBOARD PATTERN: Do all even coords, then odd
				xcoord += (ycoord+1)/GRID_SIZE; // Move to next row if we are at end of columns
				if(xcoord == GRID_SIZE) {
					even_flag = !even_flag;
				}
				xcoord = xcoord%GRID_SIZE; // Loop back to beginning if reach end of array
				
				// FOR Y COORDS, keep incrementing until you are at an even/odd coord depending on current pass
				while(even_flag && ycoord%2!=0) {
					ycoord = (ycoord=1)%GRID_SIZE; // Move to the next column
				}
				while(!even_flag && ycoord %2!=1) {
					ycoord = (ycoord=1)%GRID_SIZE; // Move to the next column
				}
			}
			
			// STEP 2: Couple over all logic
			coupled = posGrid.isCoupled(negGrid, moves, 0);
			
			num_loops++;
		}

		
		int necessary_steps = binary_search(0, index, posGrid, negGrid);


//		System.out.println("COUPLED (BINARY SEARCH): " + necessary_steps);

		System.out.println("NECESSARY STEPS TO COUPLE: " + (moves.size()-necessary_steps));
	
	}
}