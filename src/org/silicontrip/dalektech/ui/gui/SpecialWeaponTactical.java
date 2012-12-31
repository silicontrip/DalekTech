package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;

public class SpecialWeaponTactical extends TacticalUI {
	
	public SpecialWeaponTactical() {
		super(3);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return null; } 
	public WeaponUI getLeftShoulder() { return null; } 
	public WeaponUI getRightSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BronzeBomb.png")); } 
	public WeaponUI getLeftSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BronzeBomb.png")); }	
	public WeaponUI getShoulder() { return new WeaponUI ("PPC",3,6,12,18,10,10,10,getImageWithFilename("Images/SpecialWeaponWeapon.png"));  }
}
