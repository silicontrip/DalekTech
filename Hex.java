
public class Hex {
	
	int type;
	int elevation;
	
	public Hex(int type, int elevation) { this.type = type; this.elevation=elevation;}

	public void setHex(int type, int elevation) { this.type = type; this.elevation=elevation; }
	public int getType() { return type; }
	public int getElevation() { return elevation; }
	public int getWoodElevation() { return elevation + Tables.terrainHeight(type); } 
	public int woodDensity() { return Tables.woodDensity(type); }
	public int getTerrainMovementCost() { return Tables.terrainMovementCost(this); }
	public int getTargetCost() { return this.woodDensity(); } // currently only woods density affects targeting.
	public boolean canMove (Hex h) { return java.lang.Math.abs(h.getElevation() - this.getElevation()) < 3; }
	public int getMovementCost (Hex h) { return h.getTerrainMovementCost() + java.lang.Math.abs(h.getElevation() - this.getElevation()); }
	public boolean isWater() { return type==Tables.WATER; }
	public boolean isWoods() { return type==Tables.WOODS || type==Tables.HEAVYWOODS; }
	public boolean isDepth2Water() { return type==Tables.WATER && elevation <= -2; }
	public boolean isDepth1Water() { return type==Tables.WATER && elevation == -1; }
		
}
