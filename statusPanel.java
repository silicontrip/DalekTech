import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class statusPanel extends JPanel {
	
	static final int DAMAGE = 1;
	static final int TACTICAL = 2;
	String name;
		
	Image panelBackground;
	
	TacticalUI dalekTacticalImage;
	DamageUI dalekDamageImage;
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
	
	void setBackground (Image i)  { this.panelBackground = i; }
	
	int getPanelHeight() { return panelHeight; }
	int getPanelWidth() { return panelWidth; }
	
	/*
	void setTacticalImages(HashMap<String,Image> hm) { dalekTacticalImages = hm; }
	void setDamageImages(HashMap<String,DamageUI> hm) { dalekDamageImages = hm; }
	*/
	void setDalekName(String s) { 
		this.name = new String(s);
	//	setDamageImage(getDamageImageFromName());
	//	setTacticalImage(getTacticalImageFromName());
	}
	void setDamageImage(DamageUI i) { this.dalekDamageImage = i; }
	void setTacticalImage(TacticalUI i) { this.dalekTacticalImage = i; }
	
	/*
	public void setFromSections(HashMap<String,DalekSection> hm) {
		getDamageImageFromName().setFromSections(hm);
	}
	*/
		
	void setEngine(int current, int walk, int run) {
		this.setEngineCurrent(current);
		this.setEngineWalk(walk);
		this.setEngineRun(run);
	}
	void setEngineCurrent (int i) { this.engineCurrent = new Integer(i); }
	void setEngineWalk (int i) { this.engineWalk = new Integer(i); }
	void setEngineRun (int i) { this.engineRun = new Integer(i); }
	
	Integer getEngineCurrent() { return this.engineCurrent; }
	Integer getEngineWalk() { return this.engineWalk; }
	Integer getEngineRun() { return this.engineRun; }
	
	/*
	void tacticalView() { dalekImage = this.dalekTacticalImage;  displayFlag = TACTICAL;}
	void damageView() { dalekImage = this.dalekDamageImage; displayFlag = DAMAGE; }
	*/
	
	public Dimension getPreferredSize() { return new Dimension(panelWidth,panelHeight); }
	
	void drawEngineAt(Graphics g,int x, int y,Integer walk, Integer run, Integer current) {
		
		// Move this to a buffered Image
		
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
	
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		if (panelBackground != null) {
			g.drawImage(panelBackground, 0,0,getPanelWidth(), getPanelHeight(), null);			
		}
		
		
		if ( dalekDamageImage != null) {
			// int w = 100;
			// int h = (int)  (dalekImage.getHeight(null) * w / dalekImage.getWidth(null)  );
			
			int h = 200;
			int w = dalekDamageImage.scaleWidth(h);			
			
	
			BufferedImage thumbImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekDamageImage,0, 0, w, h, null);
			
			g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16 ,null);
			
		//	g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), 32 ,null);

			
		}
		if ( dalekTacticalImage != null) {
			
			int h = 200;
			int w = dalekTacticalImage.scaleWidth(h);			
			
			BufferedImage thumbImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekTacticalImage,0, 0, w, h, null);
			
			// g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16 ,null);
			
				g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), 32 ,null);
			
			
		}
		
		
		// Move to Buffered Image
		if (this.getEngineRun() > 0) {
			this.drawEngineAt(g,16,256,this.getEngineWalk(), this.getEngineRun(), this.getEngineCurrent());
		}
		if (this.name != null) {
			g.setFont(techFont);
			g.drawString(this.name,16,24);
		}
	}
	
}
