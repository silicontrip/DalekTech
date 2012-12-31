package org.silicontrip.dalektech.ui;
import java.awt.Rectangle;

public class GoldSupremeDamage extends DamageUI {

	public GoldSupremeDamage() {
		super(Guitwo.getImageWithFilename ("Images/GoldSupremeDamage.png"));
	}
	
	public GoldSupremeDamage(String imageName, boolean white) {
		super(Guitwo.getImageWithFilename (imageName));
			  if (white) {
			  enableWhite();
			  }
			  }
			  
	
	public Rectangle getDome() { return new Rectangle (137,38,5,2); }
	public Rectangle getNeck() { return new Rectangle (137,93,4,2); }
	public Rectangle getRightShoulder() { return new Rectangle(99,174,3,4); }
	public Rectangle getLeftShoulder() { return new Rectangle(175,174,3,4); }
	public Rectangle getRightSkirt() { return new Rectangle (92,330,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (182,330,2,8); }	
        public Rectangle getShoulder() { return null; }

}
