import java.util.ArrayList;

public class IsingGrid {
	private static final int POS = 1;
    private static final int NEG = 0;
    private static int diff;
	int[][] layout;
	int grid_size;
	int type;
	double beta;
	
	public IsingGrid(int t, int size, double b) {
		grid_size = size;
		beta = b;
		layout = new int[size][size];
		type = t;
		resetGrid(type);
		diff = grid_size*grid_size;
	}
	
	// Conducts Ising Calculation for given layout
	public double isingCalculation(int x, int y) {
		int numPos = 0;
      	int numNeg = 0;
      	
  		// CHECK HORIZONTAL NEIGHBORS
  		if(y>0) {
  			if(layout[x][y-1] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		if(y<grid_size-1) {
  			if(layout[x][y+1] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		
  		// CHECK VERTICAL NEIGHBORS
  		if(x>0) {
  			if(layout[x-1][y] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		if(x<grid_size-1) {
  			if(layout[x+1][y] == POS) {
  				numPos++;
  			}
  			else {
  				numNeg++;
  			}
  		}
  		
  		return (Math.exp(beta*numPos)/(Math.exp(beta*numPos) + Math.exp(beta*numNeg)));
	}

	// Reset grid to initial state
	public void resetGrid(int type) {
		diff = grid_size*grid_size;
		if(type==POS) {
			for(int i=0; i<grid_size; i++) {
				for(int j=0; j<grid_size; j++) {
					layout[i][j] = POS;
				}
			}
		} else {
			for(int i=0; i<grid_size; i++) {
				for(int j=0; j<grid_size; j++) {
					layout[i][j] = NEG;
				}
			}
		}
	}

	// Conduct one step of coupling procedure
	private void coupleStep(IsingGrid grid2, double flag, double flip1, double flip2, int x, int y) {
		if(flag<=flip1 && flag<=flip2) {
			// check if grids agree
			if (layout[x][y] == grid2.layout[x][y]) {
				layout[x][y] = POS;
				grid2.layout[x][y] = POS;
			} else {
				layout[x][y] = POS;
				grid2.layout[x][y] = POS;
				diff--;
			}
		}
		else if(flag>flip1 && flag>flip2) {
			if (layout[x][y] == grid2.layout[x][y]) {
				layout[x][y] = NEG;
				grid2.layout[x][y] = NEG;
			} else {
				layout[x][y] = NEG;
				grid2.layout[x][y] = NEG;
				diff--;
			}
		}
		else if(flag<=flip1 && flag>flip2) {
			if (layout[x][y] == grid2.layout[x][y]) {
				layout[x][y] = POS;
				grid2.layout[x][y] = NEG;
				diff++;
			} else {
				layout[x][y] = POS;
				grid2.layout[x][y] = NEG;
			}
		}
		else {
			if (layout[x][y] == grid2.layout[x][y]) {
				layout[x][y] = NEG;
				grid2.layout[x][y] = POS;
				diff++;
			} else {
				layout[x][y] = NEG;
				grid2.layout[x][y] = POS;
			}
		}
	}
	
	// Conduct entire coupling procedure; return 0 if coupled, > 0 otherwise
	public int isCoupled(IsingGrid grid2, ArrayList<Pair> moves, int index) {
		resetGrid(type);
		grid2.resetGrid(grid2.type);
		
		for(int i=index; i<moves.size(); i++) {
			int x = moves.get(i).x, y = moves.get(i).y;
			coupleStep(grid2, moves.get(i).randNum, isingCalculation(x, y), grid2.isingCalculation(x, y), x, y);
		}
		
		return diff;
	}
}
