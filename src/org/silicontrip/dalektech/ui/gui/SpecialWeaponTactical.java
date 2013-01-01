package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;
import java.io.FileNotFoundException;


public class SpecialWeaponTactical extends TacticalUI {
	
	public SpecialWeaponTactical() throws FileNotFoundException {
		super(3);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return null; } 
	public WeaponUI getLeftShoulder() { return null; } 
	public WeaponUI getRightSkirt() throws FileNotFoundException { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BronzeBomb.png")); } 
	public WeaponUI getLeftSkirt() throws FileNotFoundException { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BronzeBomb.png")); }	
	public WeaponUI getShoulder() throws FileNotFoundException { return new WeaponUI ("PPC",3,6,12,18,10,10,10,getImageWithFilename("Images/SpecialWeaponWeapon.png"));  }
}
