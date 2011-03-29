import java.util.*;
import java.io.*;
import java.lang.Math;
import java.awt.geom.Point2D.Double;


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
	public void setDirection(int dir) { 
		dir = dir %6;
		while (dir<0) {dir += 6;}
		this.dir = dir; 
	}
	
	public void setPosition(int x, int y, int d) {
		this.x =x;
		this.y =y;
		this.dir = d;
	}
	
	public void setPosition(int x, int y) {
		this.x =x;
		this.y =y;
	}
	
	public void setPosition(double x, double y,int dir) {
		setPosition(x,y);
		setDirection(dir);
	}
	
	
	public void setPosition(double x, double y) {
		// finds the nearest point
		this.setX((int)java.lang.Math.round(x/sin60));
		this.setY((int)java.lang.Math.round(y - (this.x % 2) / 2.0));
	}
	
	public void setPosition(Position p) {
		this.x = p.getX();
		this.y = p.getY();
		this.dir = p.getDirection();
	}

	public void setPosition(Double p) {
		this.setPosition(p.getX(),p.getY());
		this.dir = Tables.NONE;
	}
	
	public void moveForward () {
		;
	}
	
	public void moveBackward () {
		;
	}
	
	public void facePosition (Position p) {
		this.setDirection(this.directionTo(p));
	}
	
	public void turnLeft () { setDirection((this.getDirection() - 1)  % 6); }
	public void turnRight () { setDirection((this.getDirection() + 1)  % 6); }
	
	public int directionTo(Position p) {
	
		Double diff = this.getSpatialDiff(p);
		
	//	System.out.println("tsx: " + this.getSpatialX() + ", tsy: " + this.getSpatialY() + ", psx: " + p.getSpatialX() + ", psy: " +  p.getSpatialY() );
		
		if (java.lang.Math.abs (diff.getX()) < 0.001) {
		
			if (p.getSpatialY() > this.getSpatialY() ) { return Tables.SOUTH; }
			if (p.getSpatialY() < this.getSpatialY() ) { return Tables.NORTH; }

			return Tables.NONE;
		}
		
		double angle = java.lang.Math.atan( diff.getY() / diff.getX() );
		
	//	System.out.println("angle: " + angle);
		
		if (diff.getX() >= 0 ) {
			if (angle >=0 && angle < 1.04719755109187799103455468647774618) {return Tables.SOUTHEAST;}
			if (angle >= 1.04719755109187799103455468647774618) { return Tables.SOUTH; }
			if (angle <0 && angle > - 1.04719755109187799103455468647774618) { return Tables.NORTHEAST; }
			if (angle <= -1.04719755109187799103455468647774618) { return Tables.NORTH; }
		}
		if (diff.getX() < 0) {
			if (angle >=0 && angle < 1.04719755109187799103455468647774618) {return Tables.NORTHWEST;}
			if (angle >= 1.04719755109187799103455468647774618) { return Tables.NORTH; }
			if (angle <0 && angle > - 1.04719755109187799103455468647774618) { return Tables.SOUTHWEST; }
			if (angle <= -1.04719755109187799103455468647774618) { return Tables.SOUTH; }
		}
			
		return Tables.NONE;

		
	}

	public Double getSpatial() { return new Double(getSpatialX(),getSpatialY()); }
	public Double getSpatialDiff(Position p) {
		return new Double(p.getSpatialX() - this.getSpatialX(),
						  p.getSpatialY() - this.getSpatialY());
	}
	
	public double getSpatialX() { return x * sin60; }
	public double getSpatialY() { return y + (x % 2) / 2.0; }
	public double distanceTo(Position p) { 
		return this.getSpatial().distance(p.getSpatial());		
//		Double diff = this.getSpatialDiff(p);
//		return java.lang.Math.sqrt(diff.getX() * diff.getX()  + diff.getY() * diff.getY());
	}

	public String toString() {
		
		if (this.getDirection() == Tables.NONE) {
			return this.x + "," + this.y;
		}
		return this.x + "," + this.y + "/" + this.directionShort[dir];
	}
	
	public boolean isIn (Collection<Position> p) {
	
		Iterator<Position> it = p.iterator();
		
		while (it.hasNext()) {
			if (this.equalsIgnoreDirection(it.next())) {
				return true;
			}
		}
		return false;
	}
	public boolean equalsIgnoreDirection (Position p) {
		return p.getX() == this.getX() && p.getY() == this.getY();
	}
	
	public Position () {;}
	public Position (int x, int y) {this(x,y,0);}
	public Position (int x, int y, int dir) {this.setX(x); this.setY(y); this.setDirection(dir);}
	public Position (Double p) { this(p.getX(),p.getY()); }
	public Position (double x, double y) { this(x,y,0); }
	public Position (double x, double y, int dir) { this.setPosition(x,y,dir);}
	public Position (Position p) { this(p.getX(),p.getY(),p.getDirection()); }
}
