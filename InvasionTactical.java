import java.awt.Rectangle;

public class InvasionTactical extends TacticalUI {
	
	public InvasionTactical() {
		super(Guitwo.getImageWithFilename ("Images/InvasionTactical.png"));
	}

	public Rectangle getDome() { return null; }
	public Rectangle getNeck() { return null; }
	public Rectangle getRightShoulder() { return new Rectangle(57,168,43,46); } // 108, 216
	public Rectangle getLeftShoulder() { return new Rectangle(158,168,42,46); } // 172,171 - 208,216
	public Rectangle getRightSkirt() { return new Rectangle (85,232,43,185); } // 94,236 - 136,424
	public Rectangle getLeftSkirt() { return new Rectangle (129,232,43,185); }	// 139,236 - 180,424
	public Rectangle getShoulder() { return null; }
}