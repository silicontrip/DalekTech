import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class statusPanel extends JPanel {

	
	static final int DAMAGE = 1;
	static final int TACTICAL = 2;
	String name;
	
	HashMap<String,Image> dalekDamageImages;
	HashMap<String,Image> dalekTacticalImages;
	// HashMap<String,ArrayList<Integer>> dalekDamageValue;
	HashMap<String,ArrayList<Point>> dalekSpotRegistration; // this needs a composite key
	HashMap<String,ArrayList<Point>> dalekDamageRegistration;
	
	int displayFlag;
	
	Image panelBackground;
	
	Image dalekTacticalImage;
	Image dalekDamageImage;
	ArrayList<Point> dalekSpot;
	ArrayList<Point> dalekSpotDamage;
	ArrayList<Color> dalekSpotColour;
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
		
		displayFlag = TACTICAL;
		
		techFont = new Font("Eurostile",0,16);
		
	}
	
	void setBackground (Image i)  { this.panelBackground = i; }
	
	int getPanelHeight() { return panelHeight; }
	int getPanelWidth() { return panelWidth; }

	void setTacticalImages(HashMap<String,Image> hm) { dalekTacticalImages = hm; }
	void setDamageImages(HashMap<String,Image> hm) { dalekDamageImages = hm; }
	
	void setDalekName(String s) { 
		this.name = new String(s);
		setDamageImage(getDamageImageFromName(s));
		setTacticalImage(getTacticalImageFromName(s));
	}
	void setDamageImage(Image i) { this.dalekDamageImage = i; }
	void setTacticalImage(Image i) { this.dalekTacticalImage = i; }
	void setSpotColour(ArrayList<Color> c) { this.dalekSpotColour = c; }
	void setSpot(ArrayList<Point> p) { this.dalekSpot = p; }
	void setSpotDamage(ArrayList<Point> p) { this.dalekSpotDamage = p; }

	
	Image getTacticalImageFromName (String s) { return dalekTacticalImages.get(name); }
	
	Image getDamageImageFromName (String s) { return dalekDamageImages.get(name); }
	
	
	void setEngineCurrent (int i) { this.engineCurrent = new Integer(i); }
	void setEngineWalk (int i) { this.engineWalk = new Integer(i); }
	void setEngineRun (int i) { this.engineRun = new Integer(i); }
	
	Integer getEngineCurrent() { return this.engineCurrent; }
	Integer getEngineWalk() { return this.engineWalk; }
	Integer getEngineRun() { return this.engineRun; }


	void tacticalView() { dalekImage = this.dalekTacticalImage;  displayFlag = TACTICAL;}
	void damageView() { dalekImage = this.dalekDamageImage; displayFlag = DAMAGE; }

	public Dimension getPreferredSize() { return new Dimension(panelWidth,panelHeight); }
	
	void drawEngineAt(Graphics g,int x, int y,Integer walk, Integer run, Integer current) {
	
		  final int engineWidth = 128;
		  final int engineHeight = 16;


		int walkLocation = walk * engineWidth / run;
		int currentLocation = current * engineWidth / run;
		
		if (current <= walk) {
			g.setColor(java.awt.Color.GREEN);
		} else {
			g.setColor(java.awt.Color.ORANGE);
		}
		
		g.fillRect(x,y-12,currentLocation, 8);
		
		
		g.setColor(java.awt.Color.BLACK);
		
		for (int i =0; i <= run; i++) {
			int l = i * engineWidth / run;
			g.drawLine(x+l,y,x+l,y-engineHeight/2);			
		}
		
		g.drawLine(x,y,x,y-engineHeight);
		g.drawLine(x,y,x+engineWidth,y);
		g.drawLine(x+engineWidth,y,x+engineWidth,y-engineHeight);
		g.drawLine(x+walkLocation,y,x+walkLocation,y-engineHeight);
		
		g.drawString(run.toString(),x+engineWidth-4,y-engineHeight-2);
		g.drawString(walk.toString(),x+walkLocation-4,y-engineHeight-2);

		g.drawString ("Speed",x,y-engineHeight-2);
		
		
	}
	
	void paintDamage (Graphics g,int x,int y, double scale) {
		
		
		Iterator<Point> itd;
		Point paintPoint;
		
		int size = (int) (20 * scale);
		
		if (dalekSpotDamage != null ) {
		
		itd = this.dalekSpotDamage.iterator();

		while (itd.hasNext()) {
			paintPoint = itd.next();
			// translate point
			g.fillOval ((int)(paintPoint.getX()*scale) + x - size / 2 ,(int)(paintPoint.getY()*scale)+y - size / 2,size,size);
		}
		}
		if (dalekSpot != null ) {
		itd = this.dalekSpot.iterator();
		while (itd.hasNext()) {
			paintPoint = itd.next();
			// translate point
			g.drawOval  ((int)(paintPoint.getX()*scale) + x - size / 2 ,(int)(paintPoint.getY()*scale)+y - size /2 ,size,size);
		}
		}
		
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		if (panelBackground != null) {
			g.drawImage(panelBackground, 0,0,getPanelWidth(), getPanelHeight(), null);			
		}
		
		
		if ( dalekImage != null) {
			// int w = 100;
			// int h = (int)  (dalekImage.getHeight(null) * w / dalekImage.getWidth(null)  );
			
			int h = 200;
			int w = (int) (dalekImage.getWidth(null) * h / dalekImage.getHeight(null));
			
			
			double scale =  1.0 *  h / dalekImage.getHeight(null);
			
			BufferedImage thumbImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekImage,0, 0, w, h, null);
			g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16 ,null);
			if (displayFlag == DAMAGE) {
				// draw damage
				paintDamage(g,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16,scale);
			}
			
		}
		
		if (this.getEngineRun() > 0) {
			this.drawEngineAt(g,16,256,this.getEngineWalk(), this.getEngineRun(), this.getEngineCurrent());
		}
		if (this.name != null) {
			g.setFont(techFont);
			g.drawString(this.name,16,24);
		}
	}
	
}
