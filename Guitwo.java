import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.net.URL;


// two for 2d
public class Guitwo extends Cli {

	JFrame frame;
	JPanel statusPanel;
	JPanel mapPanel;
	JLabel statusLabel;
	JLabel mapLabel;
	
	selectFactoryDaleksPanel factoryPanel;
	
	HashMap<String,Image> dalekImages;
	
	BufferedImage statusImage;
	BufferedImage mapImage;
	
	Graphics2D statusGraphic;
	Graphics2D mapGraphic;
	
	public Guitwo(Map m) {
		
		super(m);
		
		dalekImages = InitDalekImage();

		frame = new JFrame();
		frame.setTitle("Dalek Battle");

		// TODO: listener class

		
	//	statusImage = new BufferedImage (160,480,BufferedImage.TYPE_INT_ARGB);
	//	statusGraphic  = statusImage.createGraphics();

	//	mapImage = new BufferedImage (640,480,BufferedImage.TYPE_INT_ARGB);
	//	mapGraphic  = mapImage.createGraphics();

	//	statusLabel  = new JLabel(new ImageIcon(statusImage));
	//	mapLabel  = new JLabel(new ImageIcon(mapImage));

	//	statusLabel.setFocusable(true);
	//	mapLabel.setFocusable(true);

	//	statusLabel.setMinimumSize(new Dimension(160,480));
	//	statusLabel.setMinimumSize(new Dimension(640,480));

	//	statusPanel = new JPanel();
	//	mapPanel = new JPanel();
		
		//statusLabel.addMouseListener(this);
		//mapLabel.addMouseListener(this);
	//	statusLabel.addKeyListener(this);
	//	mapLabel.
		//statusLabel.addMouseMotionListener(this);
		//mapLabel.addMouseMotionListener(this);

		// statusPanel.add(statusLabel);
		// mapPanel.add(mapLabel);

		//frame.getContentPane().add(BorderLayout.EAST,mapPanel);
		//frame.getContentPane().add(BorderLayout.WEST,statusPanel);
		
		frame.setSize(800,480); // phone resolution
		frame.setVisible(true);
		
	}
	
	
	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {

		factoryPanel = new selectFactoryDaleksPanel(dalekList);
		factoryPanel.setDalekWidth(128);
		factoryPanel.setDalekIconWidth(48);

		frame.getContentPane().add(BorderLayout.EAST, factoryPanel);
		
		 factoryPanel.requestFocus();
		
		frame.pack();
		frame.setVisible(true);
		factoryPanel.setFocusable(true);

		
		while (true) { 	
			try {
				Thread.sleep(10000); 
			} catch (InterruptedException ie) { ; }
		}
	}
	
	HashMap<String,Image> InitDalekImage() {
		
		HashMap<String,Image> imageMap = new HashMap<String,Image>();
		
		imageMap.put("Black Renegade",getImageWithFilename("Images/BlackRenegade.png"));
		imageMap.put("Black Supreme",getImageWithFilename("Images/BlackSupreme.png"));
		imageMap.put("Blue Drone",getImageWithFilename("Images/BlueDrone.png"));
		imageMap.put("Emperor Time War",getImageWithFilename("Images/EmperorTimeWar.png"));
		imageMap.put("Gold Supreme",getImageWithFilename("Images/GoldSupreme.png"));
		imageMap.put("Gold Time War",getImageWithFilename("Images/GoldTimeWar.png"));
		imageMap.put("Grey Renegade",getImageWithFilename("Images/GreyRenegade.png"));
		imageMap.put("Imperial",getImageWithFilename("Images/Imperial.png"));
		imageMap.put("Red Commander",getImageWithFilename("Images/RedCommander.png"));
		imageMap.put("Red Saucer Pilot",getImageWithFilename("Images/RedSaucerPilot.png"));
		imageMap.put("Special Weapon",getImageWithFilename("Images/SpecialWeapon.png"));
					 
		return imageMap;
	}
	
	Image dalekImage(String name) {
	
		if (dalekImages.containsKey(name)) {
			return dalekImages.get(name);
		}
		System.out.println("Canot find: "+ name);
		return null;
	}
	
	Image getImageWithFilename (String fn) {
		URL imageURL = DalekTech.class.getResource(fn);
		if (imageURL != null) {
			return new ImageIcon(imageURL).getImage();
		}
		System.err.println ("Graphic Factory Returns Null");
		return null;
	}
	
	
	/*
	public void mouseReleased(MouseEvent e) { 		
		System.out.println("Action: " + e);
	}
	
	public void mousePressed(MouseEvent e) { 		
		System.out.println("Action: " + e);
	} 

	public void mouseEntered(MouseEvent e)
	{
		System.out.println("Action: " + e);
		
		//e.getComponent().setFocusable(true);
	}
	
	public void mouseExited(MouseEvent e)
	{
		System.out.println("Action: " + e);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		System.out.println("Action: " + e);
	}
	public void mouseDragged(MouseEvent e)  {
		System.out.println("Action: " + e);
	}
	
	public void mouseMoved(MouseEvent e) {
		System.out.println("Action: " + e);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action: " + e);
	}
	
	public void keyPressed(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	
	public void keyReleased(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	public void keyTyped(KeyEvent e) {
		System.out.println("Action: " + e);
    }
	*/
	
}