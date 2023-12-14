import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 100;
	private static final double BETA = 0.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    private static int STEP_SIZE = 15000;
    
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
		
		int xcoord, ycoord, index = STEP_SIZE; 
		int num_loops = 0;
		Random random = new Random();
		
		int coupled = 1;
		
		while(coupled != 0) {
			if(num_loops>0) {
				index = (STEP_SIZE*(int)(Math.pow(2, num_loops-1)));
			}
			
			// STEP 1: Iterate over/Add new randomness
			for(int i=0; i<index; i++) {
				xcoord = random.nextInt(GRID_SIZE);
				ycoord = random.nextInt(GRID_SIZE);
				double flag = random.nextDouble();
				moves.add(0, new Pair(xcoord, ycoord, flag));
			}
			
			// STEP 2: Couple over all logic
			coupled = posGrid.isCoupled(negGrid, moves, 0);
			
			num_loops++;
//			System.out.println("INDEX AT NUMLOOP {"+num_loops+"}: "+index);
		}
		
		
		int necessary_steps = binary_search(0, index, posGrid, negGrid);
		System.out.println("MOVES SIZE: " + moves.size());
		
		System.out.println("COUPLED (BINARY SEARCH): " + necessary_steps);
				
		System.out.println("NECESSARY STEPS TO COUPLE: " + (moves.size()-necessary_steps));
//		System.out.println((moves.size()-necessary_steps));
	}
}
