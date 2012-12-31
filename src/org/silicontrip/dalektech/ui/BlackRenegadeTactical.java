package org.silicontrip.dalektech.ui;
import java.awt.Rectangle;

public class BlackRenegadeTactical extends TacticalUI {
	
	public BlackRenegadeTactical() {
		super(4);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return new WeaponUI ("Plunger",0,1, 0, 2, 6, 6, 6,Guitwo.getImageWithFilename("Images/BlackPlunger.png")); } 
	public WeaponUI getLeftShoulder() { return new WeaponUI ("Large Laser", 0,5,10,15, 8, 8, 8,Guitwo.getImageWithFilename("Images/BlackLaser.png")); } 
	public WeaponUI getRightSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,Guitwo.getImageWithFilename("Images/SilverBomb.png")); } 
	public WeaponUI getLeftSkirt() { return new WeaponUI ("Auto Bomb/10",0,1,2,3,10,10,10,Guitwo.getImageWithFilename("Images/SilverBomb.png")); }	
	public WeaponUI getShoulder() { return null; }
}
