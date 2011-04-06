
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.*;
import java.awt.*;

public abstract class TacticalUI extends BufferedImage {
	
	
	public abstract Rectangle getDome();
	public abstract Rectangle getNeck();
	public abstract Rectangle getShoulder();
	public abstract Rectangle getLeftShoulder();
	public abstract Rectangle getRightShoulder();
	public abstract Rectangle getLeftSkirt();
	public abstract Rectangle getRightSkirt();
	
	
	public TacticalUI(Image baseImage) {
		super (baseImage.getWidth(null),baseImage.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
		this.setBaseImage(baseImage);
	}
	
	private Image getBaseImage() { return baseImage; }
	public void setBaseImage(Image i) { baseImage = i; }
	
	public int scaleHeight(int width) { return width * this.getHeight() / this.getWidth(); }
	public int scaleWidth(int height) { return height * this.getWidth() / this.getHeight(); }
	
	public void setFromSections( HashMap<String,Boolean> sections) { 
		
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
			Boolean sectionAvail = sections.get(sectionName);
			
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
				if (sectionAvail) {
					canvas.setColor(Color.GREEN);
				} else {
					canvas.setColor(Color.RED);
				}
				
				// make composite
				canvas.setComposite()
				// draw rect
				canvas.fillrect()
				
			}
		}
		
		
	}
	
	
}