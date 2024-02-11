import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 15;
	private static final double BETA = 0.1;
    private static final int POS = 1;
    private static final int NEG = 0;
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
		
		int top = 0, bottom = GRID_SIZE-1, left = 0, right = GRID_SIZE-1;
		int temp_left = left, temp_bottom = bottom, temp_right = right, temp_top = top;
		
		// Boolean vars to determine which direction we should move in the spiral pattern
		boolean rmove = true, dmove = false, lmove = false, umove = true; 
		
		while(coupled != 0) {
			if(num_loops>0) {
				index = (STEP_SIZE*(int)(Math.pow(2, num_loops-1)));
			}
			
			
			// DECOUPLE THIS FROM WHILE LOOP
			// STEP 1: Iterate over/Add new randomness
			for(int i=0; i<index; i++) {
				// SPIRAL METHOD: Need to iterate over a spiral pattern
				
				// IF EITHER CONDITION HOLDS => Reset to the start
				if(top>bottom || left>right) {
					top = 0;
					bottom = GRID_SIZE-1;
					left = 0;
					right = GRID_SIZE-1;
					temp_left = left;
					temp_top = top;
					temp_bottom = bottom;
					temp_right = right;
					rmove = true;
					dmove = false;
					lmove = false;
					umove = false;
					i--;
				}
				else {
					double flag = random.nextDouble();
					if(rmove) { // Check if we are moving to the right
						moves.add(0, new Pair(top, temp_left, flag));
						temp_left++;
					}
					else if(dmove) {
						moves.add(0, new Pair(temp_top, right, flag));
						temp_top++;
					}
					else if(lmove) {
						if(top <= bottom) {
							moves.add(0, new Pair(bottom, temp_right, flag));
							temp_right--;
						}
					}
					else { // up move
						if(left <= right) {
							moves.add(0, new Pair(temp_bottom, left, flag));
							temp_bottom--;
						}
						
					}
					if(temp_left>right) {
						top++;
						temp_top = top;
						temp_left = left;
						rmove = false;
						dmove = true;
					}
					else if(temp_top>bottom) {
						right--;
						temp_right = right;
						temp_top = top;
						dmove = false;
						lmove = true;
					}
					else if(temp_right<left) {
						bottom--;
						temp_bottom = bottom;
						temp_right = right;
						lmove = false;
						umove = true;
					}
					else if(temp_bottom < top) {
						left++;
						temp_left = left;
						temp_bottom = bottom;
						umove = false;
						rmove = true;
					}
					
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