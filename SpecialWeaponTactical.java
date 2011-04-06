import java.awt.Rectangle;

public class SpecialWeaponTactical extends TacticalUI {
	
	public SpecialWeaponTactical() {
		super(Guitwo.getImageWithFilename ("Images/SpecialWeaponTactical.png"));
	}

	public Rectangle getDome() { return null; }
	public Rectangle getNeck() { return null; }
	public Rectangle getRightShoulder() { return null; } // 108, 216
	public Rectangle getLeftShoulder() { return null; } // 172,171 - 208,216
	public Rectangle getRightSkirt() { return new Rectangle (84,232,49,205); } // 94,236 - 136,424
	public Rectangle getLeftSkirt() { return new Rectangle (134,232,48,203); }	// 139,236 - 180,424
	public Rectangle getShoulder() { return new Rectangle (93,118,80,55); ; }
}