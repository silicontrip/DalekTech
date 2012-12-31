package org.silicontrip.dalektech.ui;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.*;
import java.awt.*;

public abstract class TacticalUI extends BufferedImage {
	
	HashMap<String,WeaponUI> weaponMap;
	ArrayList<WeaponUI> weaponArray;
	Graphics2D canvas;
	
	public abstract WeaponUI getDome();
	public abstract WeaponUI getNeck();
	public abstract WeaponUI getShoulder();
	public abstract WeaponUI getLeftShoulder();
	public abstract WeaponUI getRightShoulder();
	public abstract WeaponUI getLeftSkirt();
	public abstract WeaponUI getRightSkirt();
	
	
	public TacticalUI(int weapons) {
		// these values need to be queried from WeaponUI
		super (160,weapons * 40 ,BufferedImage.TYPE_4BYTE_ABGR);
		/*
		weaponMap = new ArrayList<WeaponUI>();
		
		weaponMap.add(getRightShoulder());
		weaponMap.put(getLeftShoulder());
		weaponMap.put(getShoulder());
		weaponMap.put(getRightSkirt());
		weaponMap.put(getLeftSkirt());
		*/
		weaponMap = new HashMap<String,WeaponUI>();
		weaponMap.put("dome",null);
		weaponMap.put("neck",null);
		weaponMap.put("right shoulder",getRightShoulder());
		weaponMap.put("left shoulder",getLeftShoulder());
		weaponMap.put("shoulder",getShoulder());
		weaponMap.put("right skirt",getRightSkirt());
		weaponMap.put("left skirt",getLeftSkirt());
		
		
		canvas = this.createGraphics();
		canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		// init the image
		
		
		Collection<WeaponUI> c;
		Iterator<WeaponUI> it;
		int y=0;
		WeaponUI wui = null;
		
		

		
		c = weaponMap.values();
		it = c.iterator();
		
		weaponArray = new ArrayList<WeaponUI>();
		
		while (it.hasNext()) {
			wui = it.next();
			if (wui != null) {
				weaponArray.add (wui);
				wui.setColour(Color.WHITE);
				wui.repaint();
				canvas.drawImage(wui,0,y,null);
				
				y += wui.getHeight();
				
			}
		}
		
		
	}
	
	//	private Image getBaseImage() { return baseImage; }
	//	public void setBaseImage(Image i) { baseImage = i; }
	
	public int scaleHeight(int width) { return width * this.getHeight() / this.getWidth(); }
	public int scaleWidth(int height) { return height * this.getWidth() / this.getHeight(); }
	
	public void setSelected (int s) {
		Iterator<WeaponUI> it;
		WeaponUI wui = null;
		int count = 0;
		int y = 0;
		
		it = weaponArray.iterator();

		
		while (it.hasNext()) {
			Color c;
			wui = it.next();
			c = wui.getColour();

			// System.out.println("count: " + count + " select: " + s);
			
			if (count == s) {
				wui.setColour(Color.GREEN);
			}
			canvas.drawImage(wui,0,y,null);

			wui.setColour(c);


			count++;
			y += wui.getHeight();

		}
		
	}
	
	public void setFromWeaponArray( ArrayList<Weapon> weapon) { 
		
		Iterator<Weapon> it;
		int y=0;
		WeaponUI wui = null;
		
		it = weapon.iterator();
		weaponArray = new ArrayList<WeaponUI>();

		canvas.setColor(Color.WHITE);
		canvas.fillRect(0,0,this.getWidth() , this.getHeight());

		
		while (it.hasNext()) {
			Weapon w = it.next();
			String sectionName = w.getDalekSection().getName();			
// System.out.println("setFromWeaponArray()  - " + sectionName);

			wui = weaponMap.get(sectionName.toLowerCase());
			if (wui != null) {
				if (w.canFire()) {
					wui.setColour(Color.WHITE);
				} else {
					wui.setColour(Color.RED);
				}
				weaponArray.add (wui);

			//	System.out.println("setFromWeaponArray() not null - " + sectionName);
				
				canvas.drawImage(wui,0,y,null);
				
				y += wui.getHeight();
				
			}
		}
		
		
	}
	
	
}