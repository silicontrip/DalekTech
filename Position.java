import java.util.*;
import java.io.*;

public class Position implements Serializable {
	
	int x;
	int y;
	int dir;
	static final double sin60 = 0.866025403784439;
	static final String[] directionShort = {"N","NE","SE","S","SW","NW"};
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getDirection() { return dir; }
	public void setX(int i) { x=i; }
	public void setY(int i) { y=i; }
	public void setDirection(int dir) { this.dir = dir; }
	
	public void setPosition(int x, int y) {
		this.x =x;
		this.y =y;
	}
	public void setPosition(double x, double y) {
		// finds the nearest point
		this.x = (int)java.lang.Math.round(x/sin60);
		this.y = (int)java.lang.Math.round(y - (this.x % 2) / 2.0);
	}
	
	public void setPosition(Position p) {
		this.x = p.getX();
		this.y = p.getY();
		this.dir = p.getDirection();
	}
	
	public void moveForward () {
		;
	}
	
	public void moveBackward () {
		;
	}
	
	public void turnLeft () {
		setDirection((this.getDirection() - 1)  % 6);
	}
	
	public void turnRight () {
		setDirection((this.getDirection() + 1)  % 6);
	}
	
	
	public double getSpatialX() { return x * sin60; }
	public double getSpatialY() { return y + (x % 2) / 2.0; }
	public double distanceTo(Position p) { 
		double v1 = java.lang.Math.abs(this.getSpatialX() - p.getSpatialX());
		double v2 = java.lang.Math.abs(this.getSpatialY() - p.getSpatialY());
		
		return java.lang.Math.sqrt(v1*v1 + v2 * v2);
	}

	public String toString() {
		return this.x + "," + this.y + "/" + this.directionShort[dir];
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
	public Position (int x, int y, int dir) {this.setX(x); this.setY(y); this.setDirection(dir);}

	public Position (double x, double y) { 
		// finds the nearest point
		this.x = (int)java.lang.Math.round(x/sin60);
		this.y = (int)java.lang.Math.round(y - (this.x % 2) / 2.0);
	}
	public Position(Position p) { this.setPosition(p); }
}
