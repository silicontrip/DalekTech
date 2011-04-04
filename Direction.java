
public class Direction {


	int dir;
	
	public static final int NONE = -1;
	public static final int NORTH = 0;
	public static final int NORTHEAST = 1;
	public static final int SOUTHEAST = 2;
	public static final int SOUTH = 3;
	public static final int SOUTHWEST = 4;
	public static final int NORTHWEST = 5;
	
	public static final double PI6 = 0.523598775598298873077107230546583813;
	public static final double PI3 = 1.04719755119659774615421446109316762;
	
	static final String[] directionShort = {"N","NE","SE","S","SW","NW"};

	
	public Direction() { this.dir = NORTH; }	
	public Direction (int d) { this.setDirection(d); }
	
	public void setDirection(int dir) { 
		dir = dir %6;
		while (dir<0) {dir += 6;}
		this.dir = dir; 
	}
	
	public boolean isNone() { return dir == NONE; }
	public boolean isNorth() { return dir == NORTH; }
	public boolean isNorthEast() { return dir == NORTHEAST; }
	public boolean isSouthEast() { return dir == SOUTHEAST; }
	public boolean isSouth() { return dir == SOUTH; }
	public boolean isSouthWest() { return dir == SOUTHWEST; }
	public boolean isNorthWest() { return dir == NORTHWEST; }

	public int getTurnTo(Direction d) {
	
		int turn = this.getDirection()  - d.getDirection();

		// condition for wrap around
		if (java.lang.Math.abs(turn) > 1) {
			if (turn >1 ) { turn = -1;} 
			if (turn <-1 ) { turn = 1;} 
		}
		return turn;
	}
	
	void setDirectionFromString (String dir) {
		
		// lot's of conditional logic
		this.setDirection(NONE);

		if (dir.equalsIgnoreCase("n") ) { this.setDirection(NORTH); }
		if (dir.equalsIgnoreCase("ne") ) { this.setDirection(NORTHEAST); }
		if (dir.equalsIgnoreCase("se") ) { this.setDirection(SOUTHEAST); }
		if (dir.equalsIgnoreCase("s") ) { this.setDirection(SOUTH); }
		if (dir.equalsIgnoreCase("sw") ) { this.setDirection(SOUTHWEST); }
		if (dir.equalsIgnoreCase("nw") ) { this.setDirection(NORTHWEST); }
	}
	
	
	public Direction reverseDirection() { return new Direction(this.dir + 3); }
	public String toString() { return directionShort[dir]; }
	public int getDirection() { return dir; }
	public double getReverseAngle() { return this.reverseDirection().getAngle(); }
	public double getAngle() { return dir * PI3; }
	public int getDegrees() { return dir * 60; }
	public boolean isDirection() { return dir != NONE; }
	
	public void setDirectionFromAngle(int degrees) { this.setDirection((degrees + 30) / 60); }
	public void setDirectionFromAngle (double angle) { this.setDirection((int)((angle + PI6) / PI3)); }
	public void turnLeft () { setDirection((this.getDirection() - 1)  % 6); }
	public void turnRight () { setDirection((this.getDirection() + 1)  % 6); }

	

}
