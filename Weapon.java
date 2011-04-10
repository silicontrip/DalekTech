import java.io.*;
import java.util.ArrayList;

public class Weapon implements Serializable {
	
	String name;
	int min;
	int shortRange;
	int medRange;
	int longRange;
	
	int shortDamage;
	int medDamage;
	int longDamage;
	int ammo;
	
	boolean fired;
	DalekSection dalsec;

public String getName () { return name; }
public void setName (String s) { this.name = s; }
public int getMin() { return min; }
public void setMin(int i) { this.min = i; }
public int getShortRange() { return shortRange; }
public void setShortRange(int i) { this.shortRange = i; }
public int getMedRange() { return medRange; }
public void setMedRange(int i) { this.medRange = i; }
public int getLongRange() { return longRange; }
public void setLongRange(int i) { this.longRange = i; }
public int getShortDamage() { return shortDamage; }
public void setShortDamage(int i) { this.shortDamage = i; }
public int getMedDamage() { return medDamage; }
public void setMedDamage(int i) { this.medDamage = i; }
public int getLongDamage() { return longDamage; }
public void setLongDamage(int i) { this.longDamage = i; }
public int getAmmo() { return ammo; }
public void setAmmo(int i) { this.ammo = i; }

	public Weapon () { ; } 

	public Weapon (String n, int m, int sr, int mr, int lr, int sd, int md, int ld, DalekSection ds, int a) {
		this.name = n;
		this.min = m;
		this.shortRange = sr;
		this.medRange = mr;
		this.longRange = lr;
		this.shortDamage = sd;
		this.medDamage = md;
		this.longDamage = ld;
		this.dalsec = ds;
		this.ammo = a;
		
		this.fired = false;
	}
	
	public Weapon (String n, int m, int sr, int mr, int lr, int sd, int md, int ld, int a) {
		this(n,m,sr,mr,lr,sd,md,ld,null,a);
	}
	
	public String toString () { return name; }

	DalekSection getDalekSection() { return this.dalsec; }
	void setDalekSection(DalekSection ds) { this.dalsec = ds; }
	Dalek getDalek() {return this.getDalekSection().getDalek(); }
	void setFired(boolean b) { fired = b; }
	boolean canFire () { return !fired && ammo != 0; }
	boolean inRange(Dalek d) { return this.distanceTo(d) < longRange; }
	double distanceTo(Dalek d) { return this.getDalek().distanceTo(d); }
	ArrayList<Hex> getLineOfSight(Dalek d) { return this.getDalek().getLineOfSight(d); }
	
	void destroy() { ammo = 0; } // cheats way to disable the weapon
	
	int costFire (Dalek d) {
		return this.getRangeCost(this.distanceTo(d))  // Range
		+ this.getDalek().baseHit() // Attacker Movement
		+ this.getDalek().terrainLineCost(d)  // Terrain
		+ d.targetHitCost(); // Target
	}
	
	int getRangeCost (double d) {
		if (d <= min) { return min - (int)d + 1;}
		if (d <= shortRange) { return 0; }
		if (d <= medRange) { return 2; }
		if (d <= longRange) { return 4; }
		// longer than long range, cannot hit
		return 12;
	}
	
	int getDamage(double d) {
		if (d <= shortRange) { return shortDamage; }
		if (d <= medRange) { return medDamage; }
		if (d <= longRange) { return longDamage; }
		// longer than long range, cannot hit
		return 0;
	}
	
	int getDamage (int d) { return this.getDamage((double)d); }
	
}
