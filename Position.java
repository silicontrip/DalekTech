import java.util.*;

public class Position {
	
	int x;
	int y;
	static final double sin60 = 0.866025403784439;
	
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int i) { x=i; }
	public void setY(int i) { y=i; }
	public void setPosition(Position p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public double getSpatialX() { return x * sin60; }
	public double getSpatialY() { return y + (x % 2) / 2; }
	public double distanceTo(Position p) { 
		double v1 = java.lang.Math.abs(this.getSpatialX() - p.getSpatialX());
		double v2 = java.lang.Math.abs(this.getSpatialY() - p.getSpatialY());
		
		return java.lang.Math.sqrt(v1*v1 + v2 * v2);
	}

	public String toString() {
		return this.x + "," + this.y;
	}
	
	public boolean isIn (Collection<Position> p) {
	
		Iterator<Position> it = p.iterator();
		
		while (it.hasNext()) {
			if (this.equals(it.next())) {
				return true;
			}
		}
		return false;
	}
	public boolean equals (Position p) {
		return p.getX() == this.getX() && p.getY() == this.getY();
	}
	
	public Position () {;}
	public Position (int x, int y) {this.setX(x); this.setY(y);}
	public Position (double x, double y) { 
		// finds the nearest point
		this.x = (int)java.lang.Math.round(x/sin60);
		this.y = (int)java.lang.Math.round(y - (this.x % 2) / 2);
	}
	public Position(Position p) { this.setPosition(p); }
}