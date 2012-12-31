package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;

public class BlackSupremeTactical extends TacticalUI {
	
	public BlackSupremeTactical() {
		super(4);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return new WeaponUI ("Plunger",0,1, 0, 2, 6, 6, 6,getImageWithFilename("Images/BlackOldPlunger.png")); }
	public WeaponUI getLeftShoulder() { return new WeaponUI ("Medium Laser",0,3, 6, 9, 5, 5, 5,getImageWithFilename("Images/BlackOldLaser.png")); } 
	public WeaponUI getRightSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/SilverBomb.png")); } 
	public WeaponUI getLeftSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/GoldBomb.png")); }	
	public WeaponUI getShoulder() { return null; }
}
