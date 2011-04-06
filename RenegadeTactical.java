import java.awt.Rectangle;

public class GoldSupremeTactical extends TacticalUI {
	
	public GoldSupremeDamage() {
		super(Guitwo.getImageWithFilename ("Images/RenegadeTactical.png"));
	}

	public Rectangle getDome() { return null; }
	public Rectangle getNeck() { return null; }
	public Rectangle getRightShoulder() { return new Rectangle(50,178,44,48); } // 108, 216
	public Rectangle getLeftShoulder() { return new Rectangle(160,178,40,48); } // 172,171 - 208,216
	public Rectangle getRightSkirt() { return new Rectangle (80,245,44,194); } // 94,236 - 136,424
	public Rectangle getLeftSkirt() { return new Rectangle (126,245,45,194); }	// 139,236 - 180,424
	public Rectangle getShoulder() { return null; }
}