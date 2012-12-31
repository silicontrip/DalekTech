package org.silicontrip.dalektech.ui.gui;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.*;
import java.awt.*;

import org.silicontrip.dalektech.dalek.Weapon;

public class WeaponUI extends DalekTechUI {

	public static final int DOME = 0;
	public static final int NECK = 1;
	public static final int SHOULDER = 2;
	public static final int LEFT_SHOULDER = 3;
	public static final int RIGHT_SHOULDER = 4;
	public static final int LEFT_SKIRT = 5;
	public static final int RIGHT_SKIRT = 6;

	
	
	String name;
	int min;
	int shortRange;
	int medRange;
	int longRange;
	
	int shortDamage;
	int medDamage;
	int longDamage;

	Image baseImage;
	Color backColour;
	
	Graphics2D canvas;
	
	Font small;
	Font large;
	
	
	/*
	public static WeaponUI factory (String dal, String weapon)  {
		// a factory style constructor
	
		if (dal.equalsIgnoreCase("Red Commander")) {
			if (weapon.equalsIgnoreCase("Medium Laser")) {
				return new WeaponUI("Medium Laser",0,3,6, 9, 5, 5, 5,Guitwo.getImageWithFilename("Images/RedLaser.png"));
			}
			if (weapon.equalsIgnoreCase("Pincer")) {
				return new WeaponUI("Pincer",0,1, 0, 2, 6, 6, 6,Guitwo.getImageWithFilename("Images/RedPincer.png"));
			}
			if (weapon.equalsIgnoreCase("Auto Bomb/10")) {
				return new WeaponUI("Auto Bomb/10",0,1,2,3,10,10,10,Guitwo.getImageWithFilename("Images/BlackBomb.png"));
			}
			
		}
		
		return null;
				
	}
		*/
	
	public WeaponUI (Weapon w, Image base) {
	
		this(w.getName(), w.getMin(), w.getShortRange(), w.getMedRange(), w.getLongRange(), w.getShortDamage(), w.getMedDamage(), w.getLongDamage(),base);
		
	}
	
	public WeaponUI (String n, int m, int sr, int mr, int lr, int sd, int md, int ld, Image base) {
		this(n,m,sr,mr,lr,sd,md,ld,base,Color.WHITE);
	}
	
	
	public WeaponUI (String n, int m, int sr, int mr, int lr, int sd, int md, int ld, Image base, Color c) {
		super (160,40,BufferedImage.TYPE_4BYTE_ABGR);

		canvas = this.createGraphics();
		canvas.setStroke (new BasicStroke(4, java.awt.BasicStroke.CAP_ROUND , java.awt.BasicStroke.JOIN_ROUND ));
		canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		large  = new Font("Eurostile",0,12);
		small  = new Font("Eurostile",0,9);

		
		setName(n);
		setMin(m);
		setShortRange(sr);
		setMedRange(mr);
		setLongRange(lr);
		setShortDamage(sd);
		setMedDamage(md);
		setLongDamage(ld);
		setImage(base);
		setColour(c); 
		
		//this.repaint();
		
	}
	
	public void setName (String n) { this.name = n; }
	public void setMin (int i) { this.min = i; }
	public void setShortRange (int i) { this.shortRange = i; }
	public void setMedRange (int i) { this.medRange = i; }
	public void setLongRange (int i) { this.longRange = i; }
	public void setShortDamage (int i) { this.shortDamage = i; }
	public void setMedDamage (int i) { this.medDamage = i; }
	public void setLongDamage (int i) { this.longDamage = i; }
	public void setImage (Image i) { this.baseImage = i; }
	public void setColour (Color c) { this.backColour = c;  this.repaint(); }
	public Color getColour () { return backColour;}

	public int scaleHeight(int width) { return width * this.getHeight() / this.getWidth(); }
	public int scaleWidth(int height) { return height * this.getWidth() / this.getHeight(); }

	public void repaint () {
		
		
		int h = 20; 
		int w = (int)(h * baseImage.getWidth(null) / baseImage.getHeight(null));
		

		canvas.setColor(backColour);
		canvas.fillRect(0,0,this.getWidth() , this.getHeight());

		canvas.setColor(Color.BLACK);
		canvas.drawRect(0,0,this.getWidth() , this.getHeight());
		
		canvas.drawImage(baseImage,this.getWidth() - 4 - w ,4,w,h,null);

		canvas.setFont(large);
		canvas.drawString(name,8,16); 
		canvas.setFont(small);
		canvas.drawString("DAM: " + shortDamage+"/"+medDamage+"/"+longDamage+" RANGE: "+shortRange+"/"+medRange+"/"+longRange,16,34);

		
	}
	
}
