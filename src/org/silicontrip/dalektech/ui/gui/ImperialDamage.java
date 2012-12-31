package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;

public class ImperialDamage extends DamageUI {

	public ImperialDamage() {
		super(getImageWithFilename ("Images/ImperialDamage.png"));
	}
	
	public ImperialDamage(String imageName, boolean white) {
		super(getImageWithFilename (imageName));
		if (white) {
			enableWhite();
		}
	}
	
	
	public Rectangle getDome() { return new Rectangle (120,35,4,2); }
	public Rectangle getNeck() { return new Rectangle (120,90,3,2); }
	public Rectangle getRightShoulder() { return new Rectangle(80,173,2,4); }
	public Rectangle getLeftShoulder() { return new Rectangle(157,173,2,4); }
	public Rectangle getRightSkirt() { return new Rectangle (74,337,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (164,337,2,8); }	

        public Rectangle getShoulder() { return null; }


}
