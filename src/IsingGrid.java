
public class IsingGrid {
	private static final int POS = 1;
    private static final int NEG = -1;
	int[][] layout;
	int grid_size;
	double beta;
	
	public IsingGrid(int type, int size, double b) {
		grid_size = size;
		beta = b;
		layout = new int[size][size];
//		if(type==POS) {
//			for(int i=0; i<size; i++) {
//				for(int j=0; j<size; j++) {
//					layout[i][j] = POS;
//				}
//			}
//		} else {
//			for(int i=0; i<size; i++) {
//				for(int j=0; j<size; j++) {
//					layout[i][j] = NEG;
//				}
//			}
//		}
		resetGrid(size);
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

	public void resetGrid(int type) {
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
}
