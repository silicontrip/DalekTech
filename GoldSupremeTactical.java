import java.awt.Rectangle;

public class GoldSupremeTactical extends TacticalUI {
	
	public GoldSupremeTactical() {
		super(Guitwo.getImageWithFilename ("Images/GoldSupremeTactical.png"));
	}

	public Rectangle getDome() { return null; }
	public Rectangle getNeck() { return null; }
	public Rectangle getRightShoulder() { return new Rectangle(67,171,43,45); } // 108, 216
	public Rectangle getLeftShoulder() { return new Rectangle(172,171,36,45); } // 172,171 - 208,216
	public Rectangle getRightSkirt() { return new Rectangle (94,236,42,188); } // 94,236 - 136,424
	public Rectangle getLeftSkirt() { return new Rectangle (139,236,41,188); }	// 139,236 - 180,424
	public Rectangle getShoulder() { return null; }
}