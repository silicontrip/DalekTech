package org.silicontrip.dalektech.dalek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;

import java.io.*;

import org.silicontrip.dalektech.map.*;
import org.silicontrip.dalektech.dalek.DalekSection;
import org.silicontrip.dalektech.Tables;


public class Dalek implements Serializable  {

	String name;
	int walk;
	int run;
	int base;
	
	Long id;
	
	ArrayList<DalekSection> locationArray;
	HashMap<String,DalekSection> locationMap;
	
	transient int movementDivisor;
	Position position;
	transient Position old;
	Direction facing;
	int movement;
	transient boolean moved;
	transient boolean twist;
	transient boolean fired;
	
//	Player owner;
		
	public void setDalekID(Long id) { this.id = id; }
	public Long getDalekID() { return id; }

	
	public String getName() { return name; }
	public void setName(String s) { name = s; }
	public void setWalk (int w) { walk = w; }
	public void setRun (int r) { run = r; }
	public void setBase (int b) { base = b; }
	public int getWalk () { return walk / movementDivisor; }
	public int getRun () { return run / movementDivisor; }
	public int getBase() { return base; }
	public ArrayList<DalekSection> getLocationArray() { return locationArray; }
	public void setLocationArray(ArrayList<DalekSection> ds) {  this.locationArray = ds; }
	public HashMap<String,DalekSection> getLocationMap() { return locationMap; }
	// public void getLocationMap(HashMap<String,DalekSection> ds) { this.locationMap = ds; }
	
	public void setPosition (Position p) {this.position = p; old = new Position(p); this.setFacing(p.getDirection()); }
	public Position getPosition () { return this.position; }
	
	public void setDirection (Direction d) {
		this.getPosition().setDirection(d); 
		this.setFacing(d); 
	}
	public void setFacing(Direction d) { this.facing.setDirection(d); }
	public Direction getFacing() { return facing; }
	public Direction getDirection() { return this.getPosition().getDirection(); }
	public void setMovement (int m) { movement = m; }
	public int getMovement() { return movement; }
//	void setPlayer(Player p) { this.owner=p;}
//	Player getPlayer() {return owner;}
	public Map getMap() { return Map.getInstance(); }
	
	public void setMoveDiv(int m) { movementDivisor=m; }
	public int getMoveDiv() { return movementDivisor; }
	
	public void setMoved(boolean m) { this.moved = m; }
	public boolean getMoved() {return this.moved;}
	public boolean hasMoved() {return this.moved;}
	public void setTwist(boolean t) { this.twist = t; }
	public boolean getTwist() { return this.twist; }

	public boolean hasTwist() { return this.twist; }

	public void setFired(boolean f) { this.fired = f; }
	public boolean hasFired() { return this.fired; }
	public boolean getFired() { return this.fired; }

	public String toString() { 
		String sections = new String();
		Iterator<DalekSection> it  = locationMap.values().iterator();
		while (it.hasNext()) {
			sections += it.next().toString();
		}
		return name +":(" + sections + ")";
	}
	
	
	public boolean isStationary() { return movement == 0; }
	public boolean isWalking() { return movement <= this.getWalk() && movement > 0; }
	public boolean isRunning() { return movement > this.getWalk(); }
	public boolean isDestroyed () { return this.getDamage("Neck") == 0; }

	public HashMap<String,DalekSection> getSections() { return locationMap; }
	public ArrayList<Weapon> getWeaponArray() {
		Weapon w;
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		Collection<DalekSection> c = getSections().values();
		Iterator<DalekSection> it  = c.iterator();
		
		while (it.hasNext()) {
			w = it.next().getWeapon();
			if (w != null) {
				if (w.canFire()) {
					weapons.add(w); 
				}
			} // some sections don't have weapons
		}
		return weapons;
	}
	public HashMap<String,Boolean> canFireMap () {
		
		HashMap<String,Boolean> fireMap = new HashMap<String,Boolean>();
		Set<String> s = getSections().keySet();
		Iterator<String> it;

		it = s.iterator();
		while (it.hasNext()) {
			String sectionName = it.next();
			if (getSections().get(sectionName).getWeapon() != null) {
				fireMap.put (sectionName,getSections().get(sectionName).getWeapon().canFire());
			}
		}
		return fireMap;
	}
	
	public DalekSection getLocation (int l) { return locationArray.get(l); }
	
	public DalekSection getLocationFromName (String name) {
		if (this.locationMap.containsKey(name)) {
			return this.locationMap.get(name);
		}
		
		return null;
	}
	
	public int getDamage (String name) { return this.getLocationFromName(name).getDamage(); }
	public int getDamage (int location) { return this.getLocation(location).getDamage(); }
	
	public void damageLocation (int location, int damage) { locationArray.get(location).doDamage(damage); }
	
	public void reset() {
		movement = 0;
		this.setFacing(this.getDirection());
		setMoved(false);
		setTwist(false);
		setFired(false);
		old.setPosition(this.getPosition());
	}
	
	public Position newForwardsPosition() { return this.getPosition().newForwardsPosition(); }
	public Position newBackwardsPosition() { return this.getPosition().newBackwardsPosition(); }

//	Position newBackwardsPosition() { return newPosition(Tables.oppositeDirection(this.getDirection())); }

	public Position newPosition(Direction d) { return this.getPosition().newPosition(d); }

	public boolean canMove(Direction d) { return this.getHex().getMovementCost(this.getHexAtNewPosition(d)) + movement <= this.getRun() && this.getHex().canMove(this.getHexAtNewPosition(d)); }
	public boolean canMoveForwards() { return this.canMove(this.getDirection()); }
	public boolean canMoveBackwards() { return this.canMove(this.getDirection().reverseDirection()); }
	public boolean canTurn() { return movement < this.getRun(); }
	
	public boolean moveForwards () { return this.moveDalekDirection(this.getDirection()); }
	public boolean moveBackwards () { return this.moveDalekDirection(this.getDirection().reverseDirection()); }
	
	public void faceDalek(int d) {
		
		if (d == Tables.LEFT) {
			 this.faceLeft();
		}
		if (d == Tables.RIGHT) {
			 this.faceRight();
		}
	}		
	
	public boolean moveDalek (int d) {
	
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
	
	public boolean moveDalekDirection(Direction d) {
		boolean r = this.canMove(d);
		if (r) {
			movement += this.getHex().getMovementCost(this.getHexAtNewPosition(d));
			this.setPosition(this.newPosition(d));
		}
		return r;
	}
	
	
	public boolean turnLeft () { 			
		if (this.canTurn()) {
			this.getDirection().turnLeft();
			this.getFacing().turnLeft();
			movement ++;
			return true;
		}
		return false;
	}
	
	public boolean turnRight () { 			
		if (this.canTurn()) {
			this.getDirection().turnRight();
			this.getFacing().turnRight();

			movement ++;
			return true;
		}
		return false;

	}
	
	public void faceLeft() { 
	//	setFacing(this.getDirection());
		this.getFacing().turnLeft();
	}
	public void faceRight() { 
	//	setFacing(this.getDirection());
		this.getFacing().turnRight();

	}
	
	
	public double distanceMoved() { return this.getPosition().distanceTo(this.old); }
	public int baseHit() { return Tables.movementHitCost(this) + getBase(); }
	public int targetHitCost() { return Tables.targetHitCost(this.distanceMoved()); }
	public int getHeight() { return this.getHex().getElevation() + 1; } // Daleks stand 1 unit heigher than the ground
	public Hex getHex() { return this.getHexAt(this.getPosition()); }
	public Hex getHexAt(Position p) { return getMap().getHexAt(p); }
	public Hex getHexAtNewPosition(Direction d) { return this.getHexAt(this.newPosition(d)); }
	public Hex getForwardsHex() { return this.getHexAt(this.newForwardsPosition()); }
	public Hex getBackwardsHex() { return this.getHexAt(this.newBackwardsPosition()); }

	public ArrayList<Hex> getLineOfSight (Dalek d) { return getMap().getLineOfHex(this.getPosition(),d.getPosition()); }
	
	public int terrainLineCost (Dalek d) {
	
		int cost=0;
		int dalekHeight = java.lang.Math.max(this.getHeight(),d.getHeight());
		ArrayList line = getMap().getLineOfHex(this.getPosition(),d.getPosition());
		
		if (this.getHex().isDepth1Water()) { cost ++; }
		if (d.getHex().isDepth1Water()) { cost --; }
		
		// Don't include the first hex
		for (int index=1;index < line.size();index++) {
			Hex h = (Hex)line.get(index);
			//if (h.getWoodElevation() > dalekHeight) {
				// only woods blocking LOS count.
				// actually this rule is confusing me
				cost += h.getTargetCost();
			//}
		}
		return cost;
	}
	
	public boolean hasLOS(Dalek d) {

		int maxHeight = 0;
		int wood = 0;
		int dalekHeight = java.lang.Math.max(this.getHeight(),d.getHeight());
		
		// TODO: check that Dalek is facing.
		
		double angle = this.getPosition().getAngleTo(d.getPosition());
		double facingAngle = this.getFacing().getAngle();
		
		if (facingAngle - angle > java.lang.Math.PI) { facingAngle -= java.lang.Math.PI * 2; }
		if (angle - facingAngle > java.lang.Math.PI) { facingAngle += java.lang.Math.PI * 2; }
		
		if (java.lang.Math.abs(facingAngle - angle) > java.lang.Math.PI/3) { return false; }
		
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
	
	
	public double distanceTo (Dalek d) { return this.getPosition().distanceTo(d.getPosition()); }
	
	public void addSection(DalekSection ds) {
		ds.setDalek(this);
		locationArray.add(ds);
		locationMap.put(ds.getName(),ds);
	}
	
	public Dalek () { 
		this.movementDivisor = 1;
		this.locationArray = new ArrayList<DalekSection>();
		this.locationMap = new HashMap<String,DalekSection>();
		
		facing = new Direction(0);
		setPosition(new Position(-1,-1));
		this.old = new Position(-1,-1);
		
		this.reset();
	}		
	
	public Dalek (String name, int walk, int run, int base) {
		this();
		this.name = name;
		this.walk = walk;
		this.run = run;
		this.base = base;
		
	}
	
}
