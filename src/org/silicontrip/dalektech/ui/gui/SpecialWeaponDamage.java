package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;

public class SpecialWeaponDamage extends DamageUI {

	public SpecialWeaponDamage() {
		super(getImageWithFilename ("Images/SpecialWeaponDamage.png"));
	}
	
	public SpecialWeaponDamage(String imageName, boolean white) {
		super(getImageWithFilename (imageName));
		if (white) {
			enableWhite();
		}
	}
	
	
	public Rectangle getDome() { return new Rectangle (133,23,6,1); }
	public Rectangle getNeck() { return new Rectangle (133,66,6,2); }
	public Rectangle getShoulder() { return new Rectangle(133,140,5,4); }
	public Rectangle getRightSkirt() { return new Rectangle (71,334,4,5); }
	public Rectangle getLeftSkirt() { return new Rectangle (194,334,4,5); }	
        public Rectangle getRightShoulder() { return null; }
        public Rectangle getLeftShoulder() { return null; }



}
