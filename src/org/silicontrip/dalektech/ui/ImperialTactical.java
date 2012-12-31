package org.silicontrip.dalektech.ui;
import java.awt.Rectangle;

public class ImperialTactical extends TacticalUI {
	
	public ImperialTactical() {
		super(4);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return new WeaponUI("Plunger",0,1, 0, 2, 6, 6, 6,Guitwo.getImageWithFilename("Images/ImperialPlunger.png")); } 
	public WeaponUI getLeftShoulder() { return new WeaponUI("Medium Laser",0,3, 6, 9, 5, 5, 5,Guitwo.getImageWithFilename("Images/ImperialLaser.png")); }
	public WeaponUI getRightSkirt() { return new WeaponUI("Auto Bomb/10",0,1,2,3,10,10,10,Guitwo.getImageWithFilename("Images/BronzeBomb.png")); } 
	public WeaponUI getLeftSkirt() { return new WeaponUI("Auto Bomb/10",0,1,2,3,10,10,10,Guitwo.getImageWithFilename("Images/BronzeBomb.png")); }	
	public WeaponUI getShoulder() { return null; }
}
