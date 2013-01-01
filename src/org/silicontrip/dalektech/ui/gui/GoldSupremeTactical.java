package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;

public class GoldSupremeTactical extends TacticalUI {
	
	public GoldSupremeTactical() {
		super(4);
	}

	public WeaponUI getDome() { return null; }
	public WeaponUI getNeck() { return null; }
	public WeaponUI getRightShoulder() { return new WeaponUI ("Plunger",0,1, 0, 2, 6, 6, 6,getImageWithFilename("Images/DarkBlackPlunger.png"));	} 
	public WeaponUI getLeftShoulder() { return new WeaponUI ("Large Laser", 0,5,10,15, 8, 8, 8,getImageWithFilename("Images/DarkBlackLaser.png")); }
	public WeaponUI getRightSkirt() { return new  WeaponUI("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/YellowBomb.png")); } 
	public WeaponUI getLeftSkirt() { return new  WeaponUI("Auto Bomb/10",0,1,2,3,10,10,10,getImageWithFilename("Images/YellowBomb.png"));  }
	public WeaponUI getShoulder() { return null; }
}