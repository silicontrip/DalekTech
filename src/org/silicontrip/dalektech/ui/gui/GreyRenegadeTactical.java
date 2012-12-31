package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;

public class GreyRenegadeTactical extends TacticalUI {
	
	public GreyRenegadeTactical() {
		super(4);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return new WeaponUI ("Plunger",0,1, 0, 2, 6, 6, 6,getImageWithFilename("Images/GreyPlunger.png")); } 
	public WeaponUI getLeftShoulder() { return new WeaponUI ("Mediam Laser",0,3, 6, 9, 5, 5, 5,getImageWithFilename("Images/GreyLaser.png")); } 
	public WeaponUI getRightSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BlackBomb.png")); } 
	public WeaponUI getLeftSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BlackBomb.png")); }	
	public WeaponUI getShoulder() { return null; }
}
