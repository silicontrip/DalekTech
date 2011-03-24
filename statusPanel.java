import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class statusPanel extends JPanel {

	String name;
	
	HashMap<String,Image> dalekDamageImages;
	HashMap<String,Image> dalekTacticalImages;

	Image panelBackground;
	
	Image dalekTacticalImage;
	Image dalekDamageImage;
	Image dalekImage;
	Integer engineCurrent;
	Integer engineWalk;
	Integer engineRun;

	public static final int panelWidth = 160;
	public static final int panelHeight = 480;

	Font techFont;
	
	public statusPanel () {
		super();
		
		engineCurrent = new Integer(0);
		engineWalk = new Integer(0);
		engineRun = new Integer(0);
		dalekImage = null;
		
		name = null;
		
		techFont = new Font("Eurostile",0,16);
		
	}
	
	void setBackground (Image i) 
		 { this.panelBackground = i; }
	
	int getPanelHeight() { return panelHeight; }
	int getPanelWidth() { return panelWidth; }

	void setTacticalImages(HashMap<String,Image> hm) {
		dalekTacticalImages = hm;
	}
	
	void setDamageImages(HashMap<String,Image> hm) {
		dalekDamageImages = hm;
	}
	
	
	void setDalekName(String s) { 
		this.name = new String(s);
		setDamageImage(getDamageImageFromName(s));
		setTacticalImage(getTacticalImageFromName(s));

	}
	void setDamageImage(Image i) { this.dalekDamageImage = i; }
	void setTacticalImage(Image i) { this.dalekTacticalImage = i; }
	
	Image getTacticalImageFromName (String s) { return dalekTacticalImages.get(name); }
	
	Image getDamageImageFromName (String s) { return dalekDamageImages.get(name); }
	
	
	void setEngineCurrent (int i) { engineCurrent = new Integer(i); }
	void setEngineWalk (int i) { engineWalk = new Integer(i); }
	void setEngineRun (int i) { engineRun = new Integer(i); }

	void tacticalView() { dalekImage = this.dalekTacticalImage; }
	void damageView() { dalekImage = this.dalekDamageImage; }

	public Dimension getPreferredSize() { return new Dimension(panelWidth,panelHeight); }
	
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);

		if (panelBackground != null) {
			BufferedImage thumbImage = new BufferedImage(getPanelWidth(), getPanelHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(panelBackground, 0,0,getPanelWidth(), getPanelHeight(), null);
			g.drawImage(thumbImage,0, 0 ,null);
			
		}
		
		
		if ( dalekImage != null) {
			int w = 100;
			int h = (int)  (dalekImage.getHeight(null) * w / dalekImage.getWidth(null)  );

			BufferedImage thumbImage = new BufferedImage(getPanelWidth(), getPanelHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekImage, 0,0, w, h, null);
			g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16 ,null);
		}
		
		if (this.engineRun > 0) {
				// draw engine graph
		}
		if (this.name != null) {
			g.setFont(techFont);
			g.drawString(this.name,16,24);
		}
	}
	
}
