import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.*;
import java.awt.*;

public abstract class DamageUI extends BufferedImage {

	Image baseImage;
	int spacing;
	int size;
	
	public abstract Rectangle getDome();
	public abstract Rectangle getNeck();
	public abstract Rectangle getShoulder();
	public abstract Rectangle getLeftShoulder();
	public abstract Rectangle getRightShoulder();
	public abstract Rectangle getLeftSkirt();
	public abstract Rectangle getRightSkirt();
	 
	public DamageUI(Image baseImage) {
		super (baseImage.getWidth(null),baseImage.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
		this.setBaseImage(baseImage);
		this.setSize(17);
		this.setSpacing(23);
	}
		
	public DamageUI(int size, int spacing, Image baseImage) {
		this(baseImage);
		this.setSize(size);
		this.setSpacing(spacing);
	}
	
	private Image getBaseImage() { return baseImage; }
	public void setBaseImage(Image i) { baseImage = i; }
	public void setSpacing (int i) { spacing = i; }
	public void setSize (int i) { size = i; }
	public int getSize() { return size; }
	private int getSizeOn2() { return size/2; }

	public void setFromSections( HashMap<String,DalekSection> sections) { 
		
		Set<String> s = sections.keySet();
		Iterator<String> it;
		int armour;
		Rectangle r = null;
		Color c;
		
		Graphics2D canvas = this.createGraphics();
		canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		canvas.drawImage(getBaseImage(),0, 0, null);
		
		it = s.iterator();
				
		while (it.hasNext()) {
			String sectionName = it.next();
			
			armour = sections.get(sectionName).getDamage();
			
			// string to dot registration array
			if (sectionName.equalsIgnoreCase("dome")) { r = getDome(); }
			if (sectionName.equalsIgnoreCase("neck")) { r = getNeck(); }
			if (sectionName.equalsIgnoreCase("right shoulder")) { r = getRightShoulder(); }
			if (sectionName.equalsIgnoreCase("left shoulder") ) { r = getLeftShoulder(); }
			if (sectionName.equalsIgnoreCase("shoulder") ) { r = getShoulder(); }
			if (sectionName.equalsIgnoreCase("right skirt") ) { r = getRightSkirt(); }
			if (sectionName.equalsIgnoreCase("left skirt") ) { r = getLeftSkirt(); }			
			
			//  System.out.println("setFromSections() - " + sectionName);
			
			if (r != null) {
				// test r is not null
				canvas.setColor(Color.ORANGE);
				if (armour == sections.get(sectionName).getArmour()) {
					canvas.setColor(Color.GREEN);
				}
				if (armour == 0) { 
					canvas.setColor(Color.RED);
				}
				
				System.out.println("Armour: " + armour + " " + this.getWidth(null) + "," + this.getHeight(null) + " size:"+size);

				
				for (int y=0; y<r.getHeight() ; y++) {
					for (int x=0; x<r.getWidth() ; x++) {
						
						// calculate centre of dot rectangle
						int spaceWidth = spacing * ((int)r.getWidth()-1);
						int spaceHeight = spacing * ((int)r.getHeight()-1);
						
						// calculate position
						int xp = spacing * x + (int)r.getX() - spaceWidth/2;
						int yp = spacing * y + (int)r.getY() - spaceHeight/2;
						if (armour > 0) {
							canvas.drawOval (xp - getSizeOn2() , yp - getSizeOn2() ,getSize(),getSize());
						} else {
							canvas.fillOval (xp - getSizeOn2() , yp - getSizeOn2() ,getSize(),getSize());
						}
						armour--;
						
						
					}
				}
			}
		}
		
		//this.drawImage(
		
	}
}
