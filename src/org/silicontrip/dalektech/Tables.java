package org.silicontrip.dalektech;

// This class contains table information.
// NO CALCULATION IS DONE HERE
// All return values are constants.
public class Tables {
	
	public static final int DONE = -2;
	public static final int NONE = -1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int FORWARD =2;
	public static final int BACKWARD =3;
	public static final int SELECT = 4;

	
	public static final int NORTH = 0;
	public static final int NORTHEAST = 1;
	public static final int SOUTHEAST = 2;
	public static final int SOUTH = 3;
	public static final int SOUTHWEST = 4;
	public static final int NORTHWEST = 5;
	
	public static final int CLEAR = 0;
	public static final int WOODS = 1;
	public static final int ROUGH = 2;
	public static final int HEAVYWOODS = 3;
	public static final int WATER = 4;
	public static final int PAVEMENT = 5;	

		
	public static int targetHitCost(double di) { 
		if (di<=2) { return 0;}
		if (di<=4) { return 1;}
		if (di<=6) { return 2;}
		if (di<=9) { return 3;}
		return 4;
	}
	
	public static int terrainHeight (int type) {
		if (type == WOODS || type == HEAVYWOODS) { return 2; }
		return 0;
	}

	public static int woodDensity (int type) {
		if (type == WOODS) { return 1; }
		if (type == HEAVYWOODS) { return 2; }
		return 0;
	}
	
	
	public static int movementHitCost(Dalek d) {
		if (d.isStationary()) { return 0; }
		if (d.isWalking()) { return 1; }
		if (d.isRunning()) { return 2; }
		return 0;
	}
	
	
	public static int terrainMovementCost (Hex h) {
		if (h.getType() == CLEAR || (h.getType() == WATER && h.getElevation() == 0)) {
			return 1;
		}
		if (h.getType() == WOODS || h.getType() == ROUGH || (h.getType() == WATER && h.getElevation() == -1)) {
			return 2;
		}
		if (h.getType() == HEAVYWOODS) {
			return 3;
		}
		if (h.getType() == WATER) {
			return 4;
		}
		
		// invalid hex.
		return 0;
	}
	
	public static int oppositeDirection(int d) {
		if (d == NORTH) {
			return SOUTH;
		}
		if (d == NORTHEAST) {
			return SOUTHWEST;
		}
		if (d == SOUTHEAST) {
			return NORTHWEST;
		}
		if (d == SOUTH) {
			return NORTH;
		}
		if (d == SOUTHWEST) {
			return NORTHEAST;
		}
		if (d == NORTHWEST) {
			return SOUTHEAST;
		}
		// invalid direction
		return d;
	}
		
	public static Position newPosition(Position p, int d) {
		int col;
		
		if (d == NORTH) {
			return new Position(p.getX(),p.getY()-1,p.getDirection());
		}
		if (d == SOUTH) {
			return new Position(p.getX(),p.getY()+1,p.getDirection());
		}
		
		col=p.getX() % 2;
		
		if (d == NORTHWEST) {
			return new Position(p.getX()-1,p.getY()+col-1,p.getDirection());
		}
		if (d == NORTHEAST) {
			return new Position(p.getX()+1,p.getY()+col-1,p.getDirection());
		}
		if (d == SOUTHWEST) {
			return new Position(p.getX()-1,p.getY()+col,p.getDirection());
		}
		if (d == SOUTHEAST) {
			return new Position(p.getX()+1,p.getY()+col,p.getDirection());
		}
		return p;
	}
	
}
