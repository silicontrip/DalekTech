import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collection;
import java.io.*;

public class Dalek implements Serializable  {

	String name;
	int walk;
	int run;
	int base;
	int movementDivisor;
	
	ArrayList<DalekSection> locationArray;
	HashMap<String,DalekSection> locationMap;
	
	Position pos;
	Position old;
	int direction;
	int facing;
	int movement;
	boolean moved;
	boolean twist;
	boolean fired;
	
//	Player owner;
		
	String getName() { return name; }
	void setPosition (Position p) {pos = p;}
	Position getPosition () { return pos; }
	void setDirection (int d) {direction = d;}
	int getDirection() { return direction; }
	void setMovement (int m) {movement = m;}
	int getMovement() { return movement; }
	void setWalk (int w) {walk = w;}
	void setRun (int r) {run = r;}
	void setBase (int b) {base = b;}
	int getWalk () { return walk / movementDivisor;}
	int getRun () { return run / movementDivisor;}
//	void setPlayer(Player p) { this.owner=p;}
//	Player getPlayer() {return owner;}
	Map getMap() { return Map.getInstance(); }
	
	int getBase() { return base; }
	void setMoveDiv(int m) {movementDivisor=m;}
	int getMoveDiv() { return movementDivisor;}
	
	void setMoved(boolean m) { this.moved = m; }
	boolean hasMoved() {return this.moved;}
	void setTwist(boolean t) { this.twist = t; }
	boolean hasTwist() { return this.twist; }

	public void setFired(boolean f) { this.fired = f; }
	public boolean hasFired() { return this.fired; }
	
	public String toString() { 
		String sections = new String();
		Collection<DalekSection> c = locationMap.values();
		Iterator<DalekSection> it  = c.iterator();
		while (it.hasNext()) {
			sections += it.next().toString();
		}
		return name +":(" + sections + ")";
	}
	
	
	boolean isStationary() { return movement == 0; }
	boolean isWalking() { return movement <= this.getWalk() && movement > 0; }
	boolean isRunning() { return movement > this.getWalk(); }
	boolean isDestroyed () { return this.getDamage("Neck") == 0; }

	HashMap<String,DalekSection> getSections() { return locationMap; }
	ArrayList<Weapon> getWeaponArray() {
		Weapon w;
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		Collection<DalekSection> c = getSections().values();
		Iterator<DalekSection> it  = c.iterator();
		
		while (it.hasNext()) {
			w = it.next().getWeapon();
			if (w != null) { weapons.add(w); } // some sections don't have weapons
		}
		return weapons;
	}
	
	DalekSection getLocation (int l) {
		return locationArray.get(l);
	}
	
	DalekSection getLocationFromName (String name) {
		if (this.locationMap.containsKey(name)) {
			return this.locationMap.get(name);
		}
		
		return null;
	}
	
	int getDamage (String name) { return this.getLocationFromName(name).getDamage(); }
	int getDamage (int location) { return this.getLocation(location).getDamage(); }
	
	void damageLocation (int location, int damage) { locationArray.get(location).doDamage(damage); }
	
	void reset() {
		movement = 0;
		facing = direction;
		setMoved(false);
		setTwist(false);
		setFired(false);
		old.setPosition(pos);
	}
	
	Position newForwardsPosition() { return newPosition(this.getDirection()); }
	Position newBackwardsPosition() { return newPosition(Tables.oppositeDirection(this.getDirection())); }

	Position newPosition(int d) { return Tables.newPosition(this.getPosition(),d); }

	boolean canMove(int d) { return this.getHex().getMovementCost(this.getHexAtNewPosition(d)) + movement <= this.getRun() && this.getHex().canMove(this.getHexAtNewPosition(d)); }
	
	boolean canMoveForwards() { return this.canMove(this.getDirection()); }
	boolean canMoveBackwards() { return this.canMove(Tables.oppositeDirection(this.getDirection())); }

	boolean moveForwards () { return this.moveDalekDirection(this.getDirection()); }
	boolean moveBackwards () { return this.moveDalekDirection(Tables.oppositeDirection(this.getDirection())); }

	
	boolean moveDalek (int d) {
	
		if (d == Tables.LEFT) {
			return this.turnLeft();
		}
		if (d == Tables.RIGHT) {
			return this.turnRight();
		}
		if (d == Tables.FORWARD) {
			return this.moveForwards();
		}
		if (d == Tables.BACKWARD) {
			return this.moveBackwards();
		}
		return false;
	}
	
	boolean moveDalekDirection(int d) {
		boolean r = this.canMove(d);
		if (r) {
			movement += this.getHex().getMovementCost(this.getHexAtNewPosition(d));
			pos.setPosition(this.newPosition(d));
		}
		return r;
	}
	boolean canTurn() { return movement < this.getRun(); }
	
	boolean turnLeft () { 			
		if (this.canTurn()) {
			setDirection((this.getDirection() - 1)  % 6);
			movement ++;
			return true;
		}
		return false;
	}
	
	boolean turnRight () { 			
		if (this.canTurn()) {
			setDirection((this.getDirection() + 1)  % 6);
			movement ++;
			return true;
		}
		return false;

	}
	/*
	boolean changeDirection(int d) { 
		boolean r = this.canTurn();
		if (r) {
			if (d == Tables.LEFT) {
				setDirection((this.getDirection() - 1)  % 6);
			}
			if (d == Tables.RIGHT) {
				setDirection((this.getDirection() + 1)  % 6);
			}
			movement ++;
		}
		return r;
	}
	 */
	
	double distanceMoved() { return this.getPosition().distanceTo(this.old); }
	int baseHit() { return Tables.movementHitCost(this); }
	int targetHitCost() { return Tables.targetHitCost(this.distanceMoved()); }
	int getHeight() { return this.getHex().getElevation() + 1; } // Daleks stand 1 unit heigher than the ground
	Hex getHex() { return this.getHexAt(this.getPosition()); }
	Hex getHexAt(Position p) { return getMap().getHexAt(p); }
	Hex getHexAtNewPosition(int d) { return this.getHexAt(this.newPosition(d)); }
	Hex getForwardsHex() { return this.getHexAt(this.newForwardsPosition()); }
	Hex getBackwardsHex() { return this.getHexAt(this.newBackwardsPosition()); }

	
	int terrainLineCost (Dalek d) {
	
		int cost=0;
		int dalekHeight = java.lang.Math.max(this.getHeight(),d.getHeight());
		ArrayList line = getMap().getLineOfHex(this.getPosition(),d.getPosition());
		
		if (this.getHex().isDepth1Water()) { cost ++; }
		if (d.getHex().isDepth1Water()) { cost --; }
		
		// Don't include the first hex
		for (int index=1;index < line.size();index++) {
			Hex h = (Hex)line.get(index);
			if (h.getWoodElevation() > dalekHeight) {
				// only woods blocking LOS count.
				cost += h.getTargetCost();
			}
		}
		return cost;
	}
	
	boolean hasLOS(Dalek d) {

		int maxHeight = 0;
		int wood = 0;
		int dalekHeight = java.lang.Math.max(this.getHeight(),d.getHeight());
		
		ArrayList<Hex> line = getMap().getLineOfHex(this.getPosition(),d.getPosition());
		
		// cannot shoot into or out of depth 2 water
		if (this.getHex().isDepth2Water()) { return false; }
		if (d.getHex().isDepth2Water()) { return false; }
		
		// Don't include the first or last hexes
		for (int index=1;index < line.size()-1;index++) {
			Hex h = line.get(index);
			maxHeight=java.lang.Math.max(h.getElevation(),maxHeight);
			
			if (h.getWoodElevation() > dalekHeight) {
				wood += h.woodDensity(); // only woods blocking LOS count.
			}
		}
		
		return maxHeight <= dalekHeight && wood < 3;
	}
	
	
	double distanceTo (Dalek d) { return this.getPosition().distanceTo(d.getPosition()); }
	
	void addSection(DalekSection ds) {
	
		ds.setDalek(this);
		locationArray.add(ds);
		locationMap.put(ds.getName(),ds);
		
	}
	
	public Dalek (String name, int walk, int run, int base) {
		
		
		this.name = name;
		this.walk = walk;
		this.run = run;
		this.base = base;
		
		this.movementDivisor = 1;
		this.locationArray = new ArrayList<DalekSection>();
		this.locationMap = new HashMap<String,DalekSection>();
		
		this.pos = new Position(-1,-1);
		this.old = new Position(-1,-1);
		this.direction = Tables.NORTH; 
		this.reset();
	}
	
}
