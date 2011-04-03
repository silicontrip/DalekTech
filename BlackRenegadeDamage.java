import java.awt.Rectangle;

public class BlackRenegadeDamage extends DamageUI {

	public BlackRenegadeDamage() {
		super(Guitwo.getImageWithFilename ("Images/RenegadeDamage.png"));
	}
	
	public Rectangle getDome() { return new Rectangle (125,39,4,2); }
	public Rectangle getNeck() { return new Rectangle (125,94,4,2); }
	public Rectangle getRightShoulder() { return new Rectangle(84,182,3,3); }
	public Rectangle getLeftShoulder() { return new Rectangle(165,182,3,3); }
	public Rectangle getRightSkirt() { return new Rectangle (77,336,2,8); }
	public Rectangle getLeftSkirt() { return new Rectangle (172,336,2,8); }	
	public Rectangle getShoulder() { return null; }


}
