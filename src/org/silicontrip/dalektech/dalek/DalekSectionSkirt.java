package org.silicontrip.dalektech.dalek;
public class DalekSectionSkirt extends DalekSection {
	
	void auxilaryDamage () {
		this.dalek.setMoveDiv(this.dalek.getMoveDiv() * 2);
		super.auxilaryDamage();
	}
	
	public DalekSectionSkirt () { ; } 
	
	public DalekSectionSkirt (String name, int damage, DalekSection transfer) {
		super(name,damage,transfer);
	}
	
	public DalekSectionSkirt (String name, int damage, DalekSection transfer, Weapon weapon) {
		super(name,damage,transfer,weapon);
	}
}
