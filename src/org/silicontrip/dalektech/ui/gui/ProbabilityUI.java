package org.silicontrip.dalektech.ui.gui;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.awt.Image;

import java.awt.*;

public class ProbabilityUI extends BufferedImage {
	
	static  final int prob[] = { 1,3,6,10,15,21,26,30,33,35,36 };
	Integer targetCost;
	Font probFont;
	Graphics2D canvas; 

	public ProbabilityUI () { 
		super (256,256,BufferedImage.TYPE_4BYTE_ABGR);

		canvas = this.createGraphics();
	 	
		canvas.setStroke (new BasicStroke(4, java.awt.BasicStroke.CAP_ROUND , java.awt.BasicStroke.JOIN_ROUND ));
		canvas.setFont(new Font("Eurostile",0,48));

	}

	public ProbabilityUI (int cost) {
		this();
		setTargetCost(cost);
	}
	
	public int getXscale() { return this.getWidth() / 12; }
	public int getYscale() { return this.getHeight() / 48; }
	
	public void setTargetCost(Integer targetCost) {
	
		int end = prob.length -1;
		int cost = targetCost - 2;
		int difficulty = 0;
		AffineTransform aftProb = new AffineTransform();
		
		int height = this.getHeight();
		
		aftProb.translate(0,height-50);
		aftProb.scale(1, -1);
		
		Polygon pg = new Polygon();
		
		canvas.setColor(java.awt.Color.WHITE);

		
		
		if (cost >= prob.length) { 
			cost = end; 
			difficulty = 100;
			canvas.drawString("Not Possible", 0, height);
			canvas.setColor(java.awt.Color.RED);
		} else {
			difficulty = 100 * prob[cost] / 36;
			canvas.drawString(difficulty +"%", 0, height);
			canvas.setColor(java.awt.Color.ORANGE);
		}
		
		if (cost < 5) { canvas.setColor(java.awt.Color.GREEN); }
		
		for (int i=0; i<= cost; i++) {
			pg.addPoint(i*getXscale(),  prob[i] * getYscale());
		}
		pg.addPoint(cost*getXscale(),0);
		pg.addPoint(0,0);
		

		canvas.setTransform(aftProb);
		
		canvas.fillPolygon(pg);
		canvas.setColor(java.awt.Color.WHITE);
		
		for (int i=1; i< prob.length; i++) {
			canvas.drawLine((i-1)*getXscale() ,  prob[i-1] * getYscale() , getXscale()*i,   getYscale()*prob[i]);
		}
		
		canvas.drawLine(cost*getXscale(),0,cost*getXscale(),prob[cost]*getYscale());
		canvas.drawLine(0,0,end*getXscale(),0);
		canvas.drawLine(end*getXscale(),0,end*getXscale(),prob[end]*getYscale());
		

		//canvas.setFont( getFont() );
		
		
	}
	
	public void setFont(Font f) { 		canvas.setFont(f); }
	
	//public void setGraphics (Graphics g)
	
	

	
	
}
