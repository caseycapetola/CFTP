
public class Pair {
	int x;
	int y;
	double randNum;
	double posCalc;
	double negCalc;
	
	public Pair(int xcoord, int ycoord, double val) {
		x = xcoord;
		y = ycoord;
		randNum = val;
	}
	
	// Set x and y coordinates for move
	public void setCoords(int xcoord, int ycoord) {
		x = xcoord;
		y = ycoord;
	}
}


