import java.awt.Rectangle;

public class RedSaucerDamage extends DamageUI {

	public RedSaucerDamage() {
		super(Guitwo.getImageWithFilename ("Images/RedSaucerDamage.png"));
	}
	
	public RedSaucerDamage(String imageName, boolean white) {
		super(Guitwo.getImageWithFilename (imageName));
		if (white) {
			enableWhite();
		}
	}
	
	
	public Rectangle getDome() { return new Rectangle (128,36,4,2); }
	public Rectangle getNeck() { return new Rectangle (128,88,3,2); }
	public Rectangle getRightShoulder() { return new Rectangle(89,170,2,4); }
	public Rectangle getLeftShoulder() { return new Rectangle(166,170,2,4); }
	public Rectangle getRightSkirt() { return new Rectangle (82,324,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (174,324,2,8); }	

        public Rectangle getShoulder() { return null; }


}
