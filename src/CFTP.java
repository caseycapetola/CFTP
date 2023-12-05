import java.util.ArrayList;
import java.util.Random;

public class CFTP {

	private static final int GRID_SIZE = 5;
	private static final double BETA = 0.5;
    private static final int POS = 1;
    private static final int NEG = -1;
    
    private static ArrayList<Pair> moves = new ArrayList<Pair>();
    
	
    
	public static void main(String[] args) {
		// Initialize grids to all positive/negative
		IsingGrid posGrid = new IsingGrid(POS, GRID_SIZE, BETA);
		IsingGrid negGrid = new IsingGrid(NEG, GRID_SIZE, BETA);
		
		int xcoord, ycoord, necessary_steps = 0;
		Random random = new Random();
		
		xcoord = random.nextInt(GRID_SIZE);
		ycoord = random.nextInt(GRID_SIZE);
		double flag = random.nextDouble();
		
		System.out.println(moves.size() + "..." + necessary_steps + "\nNECESSARY STEPS: " + (moves.size()-necessary_steps));
	}

}
