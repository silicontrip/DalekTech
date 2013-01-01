package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;
import java.io.FileNotFoundException;


public class GreyRenegadeTactical extends TacticalUI {
	
	public GreyRenegadeTactical() throws FileNotFoundException {
		super(4);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() throws FileNotFoundException { return new WeaponUI ("Plunger",0,1, 0, 2, 6, 6, 6,getImageWithFilename("Images/GreyPlunger.png")); } 
	public WeaponUI getLeftShoulder() throws FileNotFoundException { return new WeaponUI ("Mediam Laser",0,3, 6, 9, 5, 5, 5,getImageWithFilename("Images/GreyLaser.png")); } 
	public WeaponUI getRightSkirt() throws FileNotFoundException { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BlackBomb.png")); } 
	public WeaponUI getLeftSkirt() throws FileNotFoundException { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/BlackBomb.png")); }	
	public WeaponUI getShoulder() { return null; }
}
