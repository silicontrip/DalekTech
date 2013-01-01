package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;
import java.io.FileNotFoundException;

public class InvasionDamage extends DamageUI {

	public InvasionDamage() throws FileNotFoundException {
		super(getImageWithFilename ("Images/InvasionDamage.png"));
	}
	
	public InvasionDamage(String imageName, boolean white) throws FileNotFoundException {
		super(getImageWithFilename (imageName));
			  if (white) {
			  enableWhite();
			  }
			  }
			  
	
	public Rectangle getDome() { return new Rectangle (128,36,4,2); }
	public Rectangle getNeck() { return new Rectangle (128,88,3,2); }
	public Rectangle getRightShoulder() { return new Rectangle(90,170,2,4); }
	public Rectangle getLeftShoulder() { return new Rectangle(166,170,2,4); }
	public Rectangle getRightSkirt() { return new Rectangle (82,324,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (174,324,2,8); }	

	public Rectangle getShoulder() { return null; }


}
