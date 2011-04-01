
import java.util.*;
import java.awt.*;


public class DalekSectionUI {
	
	ArrayList<Point> damage;
	ArrayList<Point> undamage;
	ArrayList<Color> damageColour;
	
	String dalekName;
	
	int spacing = 23;
	
	// dot registration data.
	
	/*
	 int[] dome = { 88, 20, 114, 20, 140, 20, 166, 20, 88, 46, 114,46, 140,46, 166,46 };
	 int[] neck = { 101, 74, 127, 74, 153, 74, 101, 100, 127, 100, 153, 100 };
	 
	 int[] rightShoulder = { 88, 128, 88, 154, 88, 180, 88, 206, 114, 128, 114, 154, 114, 180, 114, 206 };
	 int[] leftShoulder = { 140, 128, 140, 154, 140, 180, 140, 206, 166, 128, 166, 154, 166, 180, 166, 206 };
	 */
	
	public DalekSectionUI () { ; } 
	
	
	Rectangle getDome() { return new Rectangle (92,24,4,2); }
	Rectangle getNeck() { return new Rectangle (104,74,3,2); }
	Rectangle getRightShoulder() { return new Rectangle(88,132,2,4); }
	Rectangle getLeftShoulder() { return new Rectangle(140,132,2,4); }
	Rectangle getRightSkirt() { return new Rectangle (77,238,2,8); }
	Rectangle getLeftSkirt() { return new Rectangle (151,238,2,8); }
	
	ArrayList<Color> getDamageColour() { return damageColour; }
	ArrayList<Point> getUndamaged() { return undamage; }
	ArrayList<Point> getDamaged() { return damage; }
	
	public void setNoDamage () {
		
	}
	
	public void setFromSections( HashMap<String,DalekSection> sections) { 
		
		Set<String> s = sections.keySet();
		Iterator<String> it;
		int armour;
		Rectangle r = null;
		Color c;
		
		it = s.iterator();

		System.out.println("setFromSections()");
		
		damage = new ArrayList<Point>();
		undamage = new ArrayList<Point>();
		damageColour = new ArrayList<Color>();
		
		while (it.hasNext()) {
			
			String sectionName = it.next();
			
			if (sectionName.equalsIgnoreCase("dome")) { r = getDome(); }
			if (sectionName.equalsIgnoreCase("neck")) { r = getNeck(); }
			if (sectionName.equalsIgnoreCase("right shoulder")) { r = getRightShoulder(); }
			if (sectionName.equalsIgnoreCase("left shoulder")) { r = getLeftShoulder(); }
			if (sectionName.equalsIgnoreCase("right skirt")) { r = getRightSkirt(); }
			if (sectionName.equalsIgnoreCase("left skirt")) { r = getLeftSkirt(); }
			
			System.out.println("setFromSections() - " + sectionName);

			
			if (r != null) {
				// test r is not null
				
				armour = sections.get(sectionName).getDamage();
				
				System.out.println("setFromSections() - " + sectionName + " : " + armour);

				
				c = Color.ORANGE;
				if (armour == sections.get(sectionName).getArmour()) {
					c = Color.GREEN;
				}
				if (armour == 0) { 
					c = Color.RED;
				}
				
				for (int y=0; y<r.getHeight() ; y++) {
					for (int x=0; x<r.getWidth() ; x++) {
						
						// calculate position
						int xp = spacing * x + (int)r.getX();
						int yp = spacing * y + (int)r.getY();
						damageColour.add(c);
						if (armour > 0) {
							undamage.add(new Point(xp,yp));
						} else {
							damage.add(new Point(xp,yp));
						}
						armour--;
					}
				}
			}
		}
		
		
	}
	
	
	
}