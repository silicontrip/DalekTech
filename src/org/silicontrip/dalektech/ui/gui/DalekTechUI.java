package org.silicontrip.dalektech.ui.gui;

import java.awt.image.*;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;

import java.io.FileNotFoundException;

public class DalekTechUI extends BufferedImage {
	
	
	public DalekTechUI (int width , int height, int imageType) { 
		super (width,height,imageType);
	}
	
	public static Image getImageWithFilename (String fn)  throws FileNotFoundException {
		URL imageURL = DalekTechUI.class.getResource("/" + fn);
		if (imageURL != null) {
			return new ImageIcon(imageURL).getImage();
		}
		
		throw (new FileNotFoundException("Cannot Find: " + fn));
		
	}
	
}