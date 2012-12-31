package org.silicontrip.dalektech.map;
import java.io.*;

public class Hex implements Serializable {

	public static final int CLEAR = 0;
	public static final int WOODS = 1;
	public static final int ROUGH = 2;
	public static final int HEAVYWOODS = 3;
	public static final int WATER = 4;
	public static final int PAVEMENT = 5;	
	
	static final String[] typeString = {"Clear","Woods","Rough","HeavyWoods","Water","Pavement"};

	
	int type;
	int elevation;
	
	public Hex() { this(0,0); }
	public Hex(int type, int elevation) { this.type = type; this.elevation=elevation;}

	public void setHex(int type, int elevation) { this.setType(type); this.setElevation(elevation); }
	public int getType() { return type; }
	public int getElevation() { return elevation; }
	public void setType (int i) { this.type = i;}
	public void setElevation(int i) { this.elevation = i;}
	public int getWoodElevation() { return elevation + Tables.terrainHeight(type); } 
	public int woodDensity() { return Tables.woodDensity(type); }
	public int getTerrainMovementCost() { return Tables.terrainMovementCost(this); }
	public int getTargetCost() { return this.woodDensity(); } // currently only woods density affects targeting.
	public boolean canMove (Hex h) { // cannot move into non-existant hexes
		if (h != null) {
			return java.lang.Math.abs(h.getElevation() - this.getElevation()) < 3; 
		}
		return false;
	}
	public int getMovementCost (Hex h) { // cannot move into non-existent hexes
		if (h != null) {
			return h.getTerrainMovementCost() + java.lang.Math.abs(h.getElevation() - this.getElevation()); 
		}
		return 65535; // a large cost
	}
	public boolean isWater() { return type==Tables.WATER; }
	public boolean isWoods() { return type==Tables.WOODS || type==Tables.HEAVYWOODS; }
	public boolean isDepth2Water() { return type==Tables.WATER && elevation <= -2; }
	public boolean isDepth1Water() { return type==Tables.WATER && elevation == -1; }
	
	public String toString() { return new String(typeString[type] + ":" + elevation); }
		
}
