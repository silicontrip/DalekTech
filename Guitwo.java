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
	
	Image mapImage;
	
	mapPanel mapp;
	selectFactoryDaleksPanel factoryPanel;
	statusPanel statusp;
	
	HashMap<String,Image> dalekImages;
	HashMap<String,Image> dalekDamageImages;

	
	ArrayList<Integer> selectedDaleks = null;
	Position selectedPosition;
	int selectedDirection = -1;
	int selectedMovement;
	
	BufferedImage statusImage;
	
	Graphics2D statusGraphic;
	Graphics2D mapGraphic;
	
	public Guitwo(Map m) {
		
		super(m);
		
		dalekImages = InitDalekImage();
		dalekDamageImages = InitDalekImageDamage();
		
		frame = new JFrame();
		frame.setTitle("Dalek Battle");

		// TODO: listener class

		statusp = new statusPanel();
		
		statusp.setDamageImages (dalekDamageImages);
		statusp.setTacticalImages (dalekImages);
		statusp.setBackground(getImageWithFilename("Images/statusPanel.png"));
		
		mapImage = getImageWithFilename (new String ("Images/").concat(super.getMap().getImageName()));
		mapp = new mapPanel(mapImage,m,this);
		
		frame.getContentPane().add(BorderLayout.WEST, statusp);
		//frame.pack();
		//frame.setVisible(true);
		
		
		frame.setSize(800,480); // phone resolution
		frame.setVisible(true);
		
	}
	
	mapPanel getMapPanel() { return mapp; }
	statusPanel getStatusPanel() { return statusp; }
	
	
	void setSelectedDaleks(ArrayList<Integer> selectedDaleks) {
		this.selectedDaleks = selectedDaleks;
	}
	
	void setSelectedPosition(Position p) {
		this.selectedPosition = p;
	}
	
	void setSelectedDirection(int d) {
		this.selectedDirection = d;
	}
	
	void setSelectedMovement(int d) { 
		this.selectedMovement = d;
	}
	
	Position getDalekPosition(Dalek d) {

		selectedPosition = null;

		getMapPanel().positionDalek(dalekImage(d.getName()));
		
		while (selectedPosition == null) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		
		// getMapPanel().addArray(d.getName(),dalekImage(d.getName()),selectedPosition);
		
		return selectedPosition;
	}
	
	int getDalekDirection (Dalek d) {
		
		selectedDirection  = -1;
		getMapPanel().directDalek(dalekImage(d.getName()),d.getPosition());
		
		while (selectedDirection == -1) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
			
		}
		return selectedDirection;
	}
	
	Position getDalekPositionAndDirection(Dalek d) {
		
		selectedPosition = null;

		getMapPanel().positionDalek(dalekImage(d.getName()));
		
		while (selectedPosition == null) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		
		selectedDirection  = -1;
		getMapPanel().directDalek(dalekImage(d.getName()),selectedPosition);
		
		while (selectedDirection == -1) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
			
		}
		
		selectedPosition.setDirection(selectedDirection);
		
		return selectedPosition;
	}
	
	int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn) { 
		selectedMovement = -2;
		
		getStatusPanel().setEngineCurrent(currentMove);
		getStatusPanel().setEngineWalk(walk);
		getStatusPanel().setEngineRun(run);
		
		getStatusPanel().repaint();

		
		mapp.setFocusable(true);
		mapp.requestFocus();


		// not sure where to put forward/backward cost.
		
	//	getMapPanel().moveDalek(d.getName());
		
		while (selectedMovement == -2) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		
		return selectedMovement;
		
	}
	
	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {

		factoryPanel = new selectFactoryDaleksPanel(dalekImages,dalekList,this);
		factoryPanel.setDalekWidth(128);
		factoryPanel.setDalekIconWidth(48);

		frame.getContentPane().add(BorderLayout.EAST, factoryPanel);
		
		factoryPanel.requestFocus();
		
		frame.pack();
		frame.setVisible(true);
		factoryPanel.setFocusable(true);

		
		while (selectedDaleks == null) { 	
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		frame.getContentPane().remove (factoryPanel);
		
		frame.getContentPane().add(BorderLayout.EAST, mapp);
		frame.pack();
		frame.setVisible(true);

		// mapp.addArray(dalekImage("Black Renegade"),new Position(0,0));
	
		
		return this.selectedDaleks;
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
	
	HashMap<String,Image> InitDalekImageDamage() {
		
		HashMap<String,Image> imageMap = new HashMap<String,Image>();
		
		imageMap.put("Black Renegade",getImageWithFilename("Images/BlackRenegadeDamage.png"));
		imageMap.put("Black Supreme",getImageWithFilename("Images/InvasionDamage.png"));
		imageMap.put("Blue Drone",getImageWithFilename("Images/InvasionDamage.png"));
		imageMap.put("Emperor Time War",getImageWithFilename("Images/EmperorTimeWarDamage.png"));
		imageMap.put("Gold Supreme",getImageWithFilename("Images/GoldSupremeDamage.png"));
		imageMap.put("Gold Time War",getImageWithFilename("Images/GoldTimeWardamage.png"));
		imageMap.put("Grey Renegade",getImageWithFilename("Images/GreyRenegadeDamage.png"));
		imageMap.put("Imperial",getImageWithFilename("Images/ImperialDamage.png"));
		imageMap.put("Red Commander",getImageWithFilename("Images/InvasionDamage.png"));
		imageMap.put("Red Saucer Pilot",getImageWithFilename("Images/RedSaucerDamage.png"));
		imageMap.put("Special Weapon",getImageWithFilename("Images/SpecialWeaponDamage.png"));
		
		return imageMap;
	}
	
	
	
	void notifyDalekPosition(Dalek d) {
	
		getMapPanel().notifyDalek(d.getName(),dalekImage(d.getName()),d.getPosition());
		
	}
	
	Image dalekImage(String name) {
	
		if (dalekImages.containsKey(name)) {
			return dalekImages.get(name);
		}
		System.out.println("Canot find: "+ name);
		return null;
	}
	
	Image getDamageImage(String name) {
		
		if (dalekImages.containsKey(name)) {
			return dalekDamageImages.get(name);
		}
		System.out.println("Canot find: "+ name);
		return null;
	}
	
	
	Image getImageWithFilename (String fn) {
		URL imageURL = DalekTech.class.getResource(fn);
		if (imageURL != null) {
			return new ImageIcon(imageURL).getImage();
		}
		System.err.println ("getImageWithFilename (" + fn + ") Returns Null");
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