package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;
import java.io.FileNotFoundException;


public class GoldSupremeDamage extends DamageUI {

	public GoldSupremeDamage() throws FileNotFoundException {
		super(getImageWithFilename ("Images/GoldSupremeDamage.png"));
	}
	
	public GoldSupremeDamage(String imageName, boolean white) throws FileNotFoundException {
		super(getImageWithFilename (imageName));
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
