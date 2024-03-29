package org.silicontrip.dalektech.ui.gui;
import java.awt.Rectangle;
import java.io.FileNotFoundException;


public class GreyRenegadeDamage extends DamageUI {

	public GreyRenegadeDamage() throws FileNotFoundException {
		super(getImageWithFilename ("Images/RenegadeDamage.png"));
	}
	
	public GreyRenegadeDamage(String imageName, boolean white) throws FileNotFoundException {
		super(getImageWithFilename (imageName));
		if (white) {
			enableWhite();
		}
	}
	
	
	public Rectangle getDome() { return new Rectangle (125,39,4,2); }
	public Rectangle getNeck() { return new Rectangle (125,94,3,2); }
	public Rectangle getRightShoulder()  { return new Rectangle(84,182,2,4); }
	public Rectangle getLeftShoulder()  { return new Rectangle(165,182,2,4); }
	public Rectangle getRightSkirt()  { return new Rectangle (77,336,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (172,336,2,8); }	
        public Rectangle getShoulder() { return null; }



}
