import java.awt.Rectangle;

public class ImperialTactical extends TacticalUI {
	
	public ImperialTactical() {
		super(Guitwo.getImageWithFilename ("Images/ImperialTactical.png"));
	}

	public Rectangle getDome() { return null; }
	public Rectangle getNeck() { return null; }
	public Rectangle getRightShoulder() { return new Rectangle(36,166,47,45); } // 108, 216
	public Rectangle getLeftShoulder() { return new Rectangle(154,166,46,45); } // 172,171 - 208,216
	public Rectangle getRightSkirt() { return new Rectangle (79,235,39,205); } // 94,236 - 136,424
	public Rectangle getLeftSkirt() { return new Rectangle (119,235,39,205); }	// 139,236 - 180,424
	public Rectangle getShoulder() { return null; }
}