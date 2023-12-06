import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 5;
	private static final double BETA = 0.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    
    private static ArrayList<Pair> moves = new ArrayList<Pair>();
    
	public static int binary_search(int low_index, int high_index, IsingGrid grid1, IsingGrid grid2) {
		int mid = (low_index+high_index)/2;
		if(grid1.isCoupled(grid2, moves, mid) == 0) {
			if(grid1.isCoupled(grid2, moves, mid+1) != 0) {
				return mid;
			}
			return binary_search(mid+1, high_index, grid1, grid2);
		}
		return binary_search(low_index, mid, grid1, grid2);
	
	}
    
	public static void main(String[] args) {
		// Initialize grids to all positive/negative
		IsingGrid posGrid = new IsingGrid(POS, GRID_SIZE, BETA);
		IsingGrid negGrid = new IsingGrid(NEG, GRID_SIZE, BETA);
		
		int xcoord, ycoord, index = 100, necessary_steps = -1; 
		//int num_loops = 0; -> USED FOR LATER IMPLEMENTATION
		Random random = new Random();
		
		int coupled = 1;
		
		while(coupled != 0) {
			index = 100;
			
			// STEP 1: Iterate over/Add new randomness
			for(int i=0; i<index; i++) {
				xcoord = random.nextInt(GRID_SIZE);
				ycoord = random.nextInt(GRID_SIZE);
				double flag = random.nextDouble();
				moves.add(0, new Pair(xcoord, ycoord, flag));
			}
			
			// STEP 2: Couple over all logic -> Go in reverse direction!
			coupled = posGrid.isCoupled(negGrid, moves, 0);
			
//			num_loops++; -> USED FOR LATER IMPLEMENTATIONS
		}
		
		// num_loops*index = total steps
		// ACTUAL STEPS NEEDED: num_loops*(index-1) <= steps <= num_loops*index
		for(int x=index-1; x>=0; x--) {
			if(posGrid.isCoupled(negGrid, moves, x)==0) {
				System.out.println("COUPLED AT INDEX: index: " + x);
				necessary_steps = moves.size() - x;
				x = -1;
			}
			
		}
		int res = binary_search(0, index, posGrid, negGrid);
		System.out.println("COUPLED (BINARY SEARCH): " + res);
				
		System.out.println("NECESSARY STEPS TO COUPLE: " + necessary_steps);
	}

}
