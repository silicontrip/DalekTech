import java.awt.Rectangle;
public class InvasionDamage extends DamageUI {

	public InvasionDamage() {
		super(Guitwo.getImageWithFilename ("Images/InvasionDamage.png"));
	}
	
	public Rectangle getDome() { return new Rectangle (128,36,4,2); }
	public Rectangle getNeck() { return new Rectangle (128,88,3,2); }
	public Rectangle getRightShoulder() { return new Rectangle(90,170,2,4); }
	public Rectangle getLeftShoulder() { return new Rectangle(166,170,2,4); }
	public Rectangle getRightSkirt() { return new Rectangle (82,324,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (174,324,2,8); }	

	public Rectangle getShoulder() { return null; }


}
