import java.util.*;
import java.io.*;
import java.lang.Math;
import java.awt.geom.Point2D.Double;


public class Position implements Serializable {
	
	int x;
	int y;
	Direction dir;
	static final double sin60 = 0.866025403784439;
	
	public int getX() { return x; }
	public int getY() { return y; }
	public Direction getDirection() { return dir; }
	public void setX(int i) { x=i; }
	public void setY(int i) { y=i; }
	public void setDirection(Direction dir) { this.dir = dir; }
	
	public void setPosition(int x, int y, Direction d) {
		this.x =x;
		this.y =y;
		this.dir = d;
	}
	
	public void setPosition(int x, int y) {
		this.x =x;
		this.y =y;
	}
	
	public void setPosition(double x, double y, Direction dir) {
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
		// this.dir = null;
	}
	
	public void moveForward () {
		;
	}
	
	public void moveBackward () {
		;
	}
	
	public void facePosition (Position p) {
		if (getDirection() == null) { setDirection(new Direction()); }
		this.getDirection().setDirectionFromAngle(this.getAngleTo(p));
	}
	
	public void turnLeft () { dir.turnLeft(); }
	public void turnRight () { dir.turnRight(); }
	
	public double getAngleTo(Position p) {
		
		Double diff = this.getSpatialDiff(p);
		
		double angle=0;
		
		//	System.out.println("tsx: " + this.getSpatialX() + ", tsy: " + this.getSpatialY() + ", psx: " + p.getSpatialX() + ", psy: " +  p.getSpatialY() );
		
		if (java.lang.Math.abs (diff.getX()) < 0.001) {
			
			if (p.getSpatialY() > this.getSpatialY() ) { angle =  3 * java.lang.Math.PI / 2; }
			if (p.getSpatialY() < this.getSpatialY() ) { angle =  java.lang.Math.PI/2; }
			
		} else {
		
			// cartesian coordinates have the origin at the bottom left.
			// hence the -diff.getY
			angle = java.lang.Math.atan( (-diff.getY()) / diff.getX() );
			if (diff.getX() < 0 ) {	angle += java.lang.Math.PI; }
		
			// convert from trig angle to mapping angles
			// rotate 90 degrees, 
			// anticlockwise to clockwise.
		
		}
		angle -= java.lang.Math.PI/2;
		angle = 2*java.lang.Math.PI - angle;
		
		return angle;
		
	}
	
	
	public double getReverseAngle() { return dir.reverseDirection().getAngle(); }
	public double getAngle() { return dir.getAngle(); }
	public int getDegrees() { return dir.getDegrees(); }
	
	public Double getSpatial() { return new Double(getSpatialX(),getSpatialY()); }
	public Double getSpatialDiff(Position p) {
		return new Double(p.getSpatialX() - this.getSpatialX(),
						  p.getSpatialY() - this.getSpatialY());
	}
	
	public int getXMod2() {  		
		
		if (this.x<0) { return (-x) % 2; }
		return x % 2;
	}
	
	public double getSpatialX() { return x * sin60; }
	public double getSpatialY() { return y + getXMod2() / 2.0; }
	public double distanceTo(Position p) { return this.getSpatial().distance(p.getSpatial()); }

	public String toString() {
		
		if (dir != null) {
			return this.x + "," + this.y + "/" + dir;
		}
		return this.x + "," + this.y;

	}
	
	public boolean isIn (Collection<Position> p) {
		Iterator<Position> it = p.iterator();
		while (it.hasNext()) {
			if (this.equalsIgnoreDirection(it.next())) { return true; }
		}
		return false;
	}
	
	public boolean equalsIgnoreDirection (Position p) { return p.getX() == this.getX() && p.getY() == this.getY(); }
	
	public Position newForwardsPosition() { return this.newPosition(this.getDirection()); }
	public Position newBackwardsPosition() { return this.newPosition(this.getDirection().reverseDirection()); }
	
	public Position newForwardLeftPosition() { 
		this.turnLeft();
		Position left = this.newForwardsPosition();
		this.turnRight();
		return left;
	}
	
	public Position newForwardRightPosition() { 
		this.turnRight();
		Position right = this.newForwardsPosition();
		this.turnLeft();
		return right;
	}
	
	

	public Position newPosition(Direction direction) {
		int col;
		
		if (direction.isNorth()) {
			return new Position(this.getX(),this.getY()-1,this.getDirection());
		}
		if (direction.isSouth()) {
			return new Position(this.getX(),this.getY()+1,this.getDirection());
		}
		
		col=getXMod2();
		
		if (direction.isNorthWest()) {
			return new Position(this.getX()-1,this.getY()+col-1,this.getDirection());
		}
		if (direction.isNorthEast()) {
			return new Position(this.getX()+1,this.getY()+col-1,this.getDirection());
		}
		if (direction.isSouthWest()) {
			return new Position(this.getX()-1,this.getY()+col,this.getDirection());
		}
		if (direction.isSouthEast()) {
			return new Position(this.getX()+1,this.getY()+col,this.getDirection());
		}
		return this;
	}
/*
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException  {
		stream.writeInt(x);
		stream.writeInt(y);
		stream.writeInt(dir.getDirection());
	}

private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException { 
	x = stream.readInt();
	y = stream.readInt();
	dir = new Direction(stream.readInt()); 
}
*/	
	
	public Position () { this.x = -1 ; this.y = -1 ; setDirection(new Direction()); }
	public Position (int x, int y) {this(x,y,new Direction());}
	public Position (int x, int y, Direction dir) {this.setX(x); this.setY(y); this.setDirection(dir);}
	public Position (Double p) { this(p.getX(),p.getY()); }
	public Position (double x, double y) { this(x,y,new Direction()); }
	public Position (double x, double y, Direction dir) { this.setPosition(x,y,dir);}
	public Position (Position p) { this(p.getX(),p.getY(),p.getDirection()); }
}
