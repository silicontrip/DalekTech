import java.awt.Rectangle;

public class RedSaucerTactical extends TacticalUI {
	
	public RedSaucerTactical() {
		super(Guitwo.getImageWithFilename ("Images/RedSaucerTactical.png"));
	}

	public Rectangle getDome() { return null; }
	public Rectangle getNeck() { return null; }
	public Rectangle getRightShoulder() { return new Rectangle(57,166,42,49); } // 108, 216
	public Rectangle getLeftShoulder() { return new Rectangle(158,167,42,46); } // 172,171 - 208,216
	public Rectangle getRightSkirt() { return new Rectangle (85,231,42,184); } // 94,236 - 136,424
	public Rectangle getLeftSkirt() { return new Rectangle (129,231,42,184); }	// 139,236 - 180,424
	public Rectangle getShoulder() { return null; }
}