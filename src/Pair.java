
public class Pair {
	int x;
	int y;
	double randNum;
	double posCalc;
	double negCalc;
	
	public Pair(int xcoord, int ycoord, double val, double posCalc, double negCalc) {
		x = xcoord;
		y = ycoord;
		randNum = val;
		this.posCalc = posCalc;
		this.negCalc = negCalc;
	}
	
	// Set x and y coordinates for move
	public void setCoords(int xcoord, int ycoord) {
		x = xcoord;
		y = ycoord;
	}
}


