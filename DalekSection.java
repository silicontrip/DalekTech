import java.io.*;
public class DalekSection implements Serializable {

	String name;
	int armour;
	DalekSection transfer;
	Weapon weapon; // only one for the time being

	transient Dalek dalek;
	transient int currentArmour;
	
	void setDalek(Dalek dalek) { this.dalek = dalek; }
	Dalek getDalek() { return this.dalek; }
	
	public void setWeapon(Weapon weapon) { this.weapon = weapon; weapon.setDalekSection(this); }
	public Weapon getWeapon() { return this.weapon; }
	
	public String getName() { return name; }
	public void setName(String s) { this.name = s; }

	boolean nameEquals(String s) { return s.equals(this.name); }
	public int getArmour() { return armour; }
	public void setArmour(int i) { this.armour = i; }
	
	int getDamage() { return currentArmour; }
	void destroyArmour() { currentArmour = 0; }
	
	boolean armourHolds (int damage) { return currentArmour > damage;}
	boolean armourDestroyed (int damage) { return currentArmour == damage; }
	boolean armourTransfers (int damage) { return currentArmour < damage; }
	
 	void setDamage(int damage) { currentArmour = damage; }
	void takeDamage(int damage) { currentArmour -= damage; }
	DalekSection getTransfer() { return transfer; }
	boolean isDestroyed() { return currentArmour == 0; }
	void destroy() { currentArmour = 0; }

	public String toString() { 
		if (weapon != null) {
			return name + ":(" + currentArmour + "/" + armour + " " + weapon + ")" ;
		} else {
			return name + ":(" + currentArmour + "/" + armour +")";
		}
	}
	void doDamage (int damage) {
	
		if (this.armourHolds(damage)) {
			this.takeDamage(damage);
		} else if (this.armourDestroyed(damage)) {
			this.destroy();
			this.auxilaryDamage();
		} else if (this.armourTransfers(damage)) {
			damage -= currentArmour;
			this.destroy();
			this.auxilaryDamage();
			if (this.transfer != null) {
				this.transfer.doDamage(damage);
			}
		}		
	}
	
	void auxilaryDamage () { 
		this.weapon = null; // let the garbage collector take the broken weapon
//		if (this.weapon != null) {
//			this.weapon.destroy(); 
//		}
	}
	
	public DalekSection () { ; } 
	public DalekSection (String name, int damage) {	this(name,damage,null); }
	public DalekSection (String name, int damage, DalekSection transfer) { this(name,damage,transfer,null);}	
	public DalekSection (String name, int damage, DalekSection transfer, Weapon weapon) { this(name,damage,transfer,weapon,null); }
	public DalekSection (String name, int damage, DalekSection transfer, Weapon weapon, Dalek dalek) {
		this.name = name;
		this.armour = damage;
		this.currentArmour = damage;
		this.transfer = transfer;
		this.weapon = weapon;
		if (this.weapon != null) {
			this.weapon.setDalekSection(this);
		}
		this.dalek = dalek;
	}
	
	
}
