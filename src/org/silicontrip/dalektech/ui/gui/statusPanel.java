package org.silicontrip.dalektech.ui.gui;
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
	
	public void setBackground (Image i)  { this.panelBackground = i; }
	
	public int getPanelHeight() { return panelHeight; }
	public int getPanelWidth() { return panelWidth; }
	
	/*
	void setTacticalImages(HashMap<String,Image> hm) { dalekTacticalImages = hm; }
	void setDamageImages(HashMap<String,DamageUI> hm) { dalekDamageImages = hm; }
	*/
	public void setDalekName(String s) { 
		this.name = new String(s);
	//	setDamageImage(getDamageImageFromName());
	//	setTacticalImage(getTacticalImageFromName());
	}
	public void setDamageImage(Image i) { this.dalekDamageImage = i; }
	public void setTacticalImage(TacticalUI i) { this.dalekTacticalImage = i; }
	public TacticalUI getTacticalImage() { return dalekTacticalImage; }
	
	/*
	public void setFromSections(HashMap<String,DalekSection> hm) {
		getDamageImageFromName().setFromSections(hm);
	}
	*/
		
	public void setEngine(int current, int walk, int run) {
		this.setEngineCurrent(current);
		this.setEngineWalk(walk);
		this.setEngineRun(run);
	}
	public void setEngineCurrent (int i) { this.engineCurrent = new Integer(i); }
	public void setEngineWalk (int i) { this.engineWalk = new Integer(i); }
	public void setEngineRun (int i) { this.engineRun = new Integer(i); }
	
	public Integer getEngineCurrent() { return this.engineCurrent; }
	public Integer getEngineWalk() { return this.engineWalk; }
	public Integer getEngineRun() { return this.engineRun; }
	
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
		
		g.setFont( new Font("Eurostile",0,12));
		g.drawString ("Speed",x,y-engineHeight-2);
		
		
	}
	
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		Graphics2D canvas = (Graphics2D) g;
		
		if (panelBackground != null) {
			canvas.drawImage(panelBackground, 0,0,getPanelWidth(), getPanelHeight(), null);			
		}
		
		
		if ( dalekDamageImage != null) {
			// int w = 100;
			// int h = (int)  (dalekImage.getHeight(null) * w / dalekImage.getWidth(null)  );
			
			int h = 200;
			// int w = dalekDamageImage.scaleWidth(h);			
			
			int w = h * dalekDamageImage.getWidth(null) / dalekDamageImage.getHeight(null);
			
	
			BufferedImage thumbImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekDamageImage,0, 0, w, h, null);
			
			canvas.drawImage(thumbImage,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16 ,null);
			
		//	g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), 32 ,null);

			
		}
		if ( dalekTacticalImage != null) {
			
			//int h = 400;
			//int w = dalekTacticalImage.scaleWidth(h);			
			
			BufferedImage thumbImage = new BufferedImage(dalekTacticalImage.getWidth(), dalekTacticalImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(dalekTacticalImage,0, 0,  null);
			
			// g.drawImage(thumbImage,getPanelWidth()/2 - (w/2), getPanelHeight() - h - 16 ,null);
			
			canvas.drawImage(thumbImage,0,32,null);
			
			/*
			Iterator<WeaponUI> it = dalekTacticalImage.iterator();
			int y = 32;
			while (it.hasNext()) {
				g.drawImage(it.next(),0, y ,null);
				y += 40; // really want height of WeaponUI.
			}
			 */
			
		}
		
		
		// Move to Buffered Image
		if (this.getEngineRun() > 0) {
			this.drawEngineAt(canvas,16,256,this.getEngineWalk(), this.getEngineRun(), this.getEngineCurrent());
		}
		if (this.name != null) {
			canvas.setFont(techFont);
			canvas.drawString(this.name,16,24);
		}
	}
	
}
