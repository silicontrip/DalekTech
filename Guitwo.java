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
	//DalekSectionUI dsui;
	
	HashMap<String,Image> dalekImages;
	HashMap<String,DamageUI> dalekDamageImages;
	HashMap<String,DamageUI> dalekRemoteImages;
	HashMap<String,TacticalUI> dalekTacticalImages;
	
	
	Position selectedPosition;
	Direction selectedDirection = null;
	int selectedMovement;
	
	BufferedImage statusImage;
	
	Graphics2D statusGraphic;
	Graphics2D mapGraphic;
	
	public Guitwo(Map m) {
		
		super(m);
		
		dalekImages = InitDalekImage();
		dalekDamageImages = InitDalekImageDamage();
		dalekRemoteImages = InitDalekImageRemote();
		dalekTacticalImages = InitDalekImageTactical();
		
		frame = new JFrame();
		frame.setTitle("Dalek Battle");
		
		// dsui = new DalekSectionUI();
		statusp = new statusPanel();
		// mop = new mapOverlayPanel();
		
		// statusp.setDamageImages (dalekDamageImages);
		// statusp.setTacticalImages (dalekImages);
		statusp.setBackground(getImageWithFilename("Images/statusPanel.png"));
		
		mapImage = getImageWithFilename (new String ("Images/").concat(super.getMap().getImageName()));
		mapp = new mapPanel(mapImage,m,this);
		
		mapp.setSelectorImage(getImageWithFilename("Images/hexSelector.png"));
		mapp.setArrowImage(getImageWithFilename("Images/Arrow.png"));
		
		// for testing the selector registration...
		// mapp.setSelectorPosition(new Position(0,0));
		// mapp.setTargetCost(7);
		
		// TacticalUI weaponTestArray = new TacticalUI(4);
		
		/*
		 ArrayList<WeaponUI> weaponTestArray = new ArrayList<WeaponUI>();
		 weaponTestArray.add( WeaponUI.factory ("Red Commander","Medium Laser"));
		 weaponTestArray.add(WeaponUI.factory ("Red Commander","Pincer"));
		 weaponTestArray.add(WeaponUI.factory ("Red Commander","Auto Bomb/10"));
		 weaponTestArray.add(WeaponUI.factory ("Red Commander","Auto Bomb/10"));
		 */
		
		// statusp.setTacticalImage(weaponTestArray);
		
		frame.getContentPane().add(BorderLayout.WEST, statusp);
		//frame.pack();
		//frame.setVisible(true);
		
		frame.setSize(800,480); // phone resolution
		frame.setVisible(true);
		
	}
	
	mapPanel getMapPanel() { return mapp; }
	statusPanel getStatusPanel() { return statusp; }
	// DalekSectionUI getDalekSectionUI() { return dsui; }
	// mapOverlayPanel getMapOverlayPanel() { return mop; }
	
	void setSelectedPosition(Position p) { this.selectedPosition = p; }
	void setSelectedDirection(Direction d) { this.selectedDirection = d; }
	void setSelectedMovement(int d) { this.selectedMovement = d; }
	
	void setInterfaceMessage(String s) { getMapPanel().setInterfaceMessage(s); getMapPanel().repaint(); }
	
	Position getDalekPosition(Dalek d) {
		
		selectedPosition = null;
		
		getMapPanel().positionDalek(dalekImage(d.getName()));
		
		while (selectedPosition == null) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		
		return selectedPosition;
	}
	
	Direction getDalekDirection (Dalek d) {
		
		selectedDirection  = null;
		getMapPanel().directDalek(dalekImage(d.getName()),d.getPosition());
		
		while (selectedDirection == null) {
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
		
		selectedDirection  = null;
		getMapPanel().directDalek(dalekImage(d.getName()),selectedPosition);
		
		while (selectedDirection == null) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
			
		}
		
		selectedPosition.setDirection(selectedDirection);
		
		return selectedPosition;
	}
	
	int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn) { 
		
		getStatusPanel().setDalekName(d.getName());
		getStatusPanel().setEngine(currentMove,walk,run);
		getStatusPanel().repaint();
		
		// not sure where to put forward/backward cost.
		
		getMapPanel().setMovementCost(d.getPosition(),new Integer(forwardCost), new Integer(backwardCost),forward,backward);
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();
		
		//	getMapPanel().moveDalek(d.getName());
		selectedMovement = Tables.NONE;
		
		while (selectedMovement == Tables.NONE) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		getMapPanel().setMovementCost(null,null,null,null,null);
		
		return selectedMovement;
		
	}
	
	int getDalekTwist(Dalek d) {
		
		getStatusPanel().setDalekName(d.getName());
		getStatusPanel().repaint();
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();
		
		
		// not sure where to put forward/backward cost.
		
		//	getMapPanel().moveDalek(d.getName());
		do {
			selectedMovement = Tables.NONE;
			
			while (selectedMovement == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
		} while (selectedMovement != Tables.LEFT &&
				 selectedMovement != Tables.RIGHT &&
				 selectedMovement != Tables.DONE) ;
		
		return selectedMovement;
		
	}
	
	int selectDalek (ArrayList<Dalek> dalekList) {
		
		int currentSelection = 0;
		int dalekSize = dalekList.size();
		DamageUI dalekStatus;
		TacticalUI dalekWeapon;
		
		getMapPanel().centreOn(dalekList.get(currentSelection).getPosition());
		getStatusPanel().setDalekName(dalekList.get(currentSelection).getName());
		dalekStatus = dalekDamageImages.get(dalekList.get(currentSelection).getName());
		dalekStatus.setFromSections(dalekList.get(currentSelection).getSections());
		getStatusPanel().setDamageImage(dalekStatus);
		
		dalekWeapon = dalekTacticalImages.get(dalekList.get(currentSelection).getName());
		getStatusPanel().setTacticalImage(dalekWeapon);
		
		getStatusPanel().repaint();
		
		if (dalekSize > 1) {
			getMapPanel().setFocusable(true);
			getMapPanel().requestFocus();
			
			do {
				getStatusPanel().setDalekName(dalekList.get(currentSelection).getName());
				
				dalekStatus = dalekDamageImages.get(dalekList.get(currentSelection).getName());
				// dalekStatus.setFromWeapon(dalekList.get(currentSelection).getSections());
				getStatusPanel().setDamageImage(dalekStatus);
				
				dalekWeapon = dalekTacticalImages.get(dalekList.get(currentSelection).getName());
				getStatusPanel().setTacticalImage(dalekWeapon);

				
				// getStatusPanel().setFromSections(dalekList.get(currentSelection).getSections());
				getStatusPanel().repaint();
				
				getMapPanel().centreOn(dalekList.get(currentSelection).getPosition());
				getMapPanel().setSelectorPosition(dalekList.get(currentSelection).getPosition());
				getMapPanel().repaint();
				//System.out.println("**** current selection position : " + dalekList.get(currentSelection).getPosition() +" ****");
				
				selectedMovement = Tables.NONE;
				
				while (selectedMovement == Tables.NONE) {
					try {
						Thread.sleep(250); 
					} catch (InterruptedException ie) { ; }
				}
				
				if (selectedMovement == Tables.LEFT || selectedMovement == Tables.FORWARD) {
					currentSelection --;
					if (currentSelection < 0 ) { currentSelection += dalekSize; }
				}
				if (selectedMovement == Tables.RIGHT || selectedMovement == Tables.BACKWARD) {
					currentSelection++;
					if (currentSelection >= dalekSize) {currentSelection -= dalekSize;}
				}
			} while (selectedMovement != Tables.SELECT) ;
			
			getMapPanel().setSelectorPosition(null);
			getMapPanel().repaint();
			
			return currentSelection;
		}
		return 0;
		
	}
	
	//int selectTargetDalek (Dalek d, ArrayList<Dalek> dalekList, ArrayList<Integer> targetCost) 
	int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList, ArrayList<Integer> targetCost, ArrayList<Double> distance, ArrayList<ArrayList<Hex>> los)
	{
		
		int currentSelection = 0;
		int dalekSize = targetList.size();
		DamageUI remoteImage;
		// ProbabilityUI probImage;
		
		getMapPanel().centreOn(targetList.get(currentSelection).getPosition());
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();
		
		do {
			// getStatusPanel().setDalekName(targetList.get(currentSelection).getName());
			
			getMapPanel().setProbabilityUI(new ProbabilityUI(targetCost.get(currentSelection)));
			getMapPanel().setTargetDistance(distance.get(currentSelection));
			getMapPanel().setLineOfSight(los.get(currentSelection));
			
			remoteImage = dalekRemoteImages.get(targetList.get(currentSelection).getName());
			remoteImage.setFromSections(targetList.get(currentSelection).getSections());
			
			getMapPanel().setTarget(remoteImage);
			getMapPanel().setSelectorPosition(targetList.get(currentSelection).getPosition());
			getMapPanel().centreOn(targetList.get(currentSelection).getPosition());
			getMapPanel().repaint();
			//System.out.println("**** current selection position : " + dalekList.get(currentSelection).getPosition() +" ****");
			
			selectedMovement = Tables.NONE;
			
			while (selectedMovement == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
			
			if (selectedMovement == Tables.LEFT || selectedMovement == Tables.FORWARD) {
				currentSelection --;
				if (currentSelection < 0 ) { currentSelection += dalekSize; }
			}
			if (selectedMovement == Tables.RIGHT || selectedMovement == Tables.BACKWARD) {
				currentSelection++;
				if (currentSelection >= dalekSize) {currentSelection -= dalekSize;}
			}
		} while (selectedMovement != Tables.SELECT && selectedMovement != Tables.DONE) ;
		
		getMapPanel().setProbabilityUI(null);
		getMapPanel().setTargetDistance(null);
		getMapPanel().setLineOfSight(null);
		getMapPanel().setTarget(null);
		
		getMapPanel().setSelectorPosition(null);
		getMapPanel().repaint();
		
		if (selectedMovement == Tables.DONE) {
			return -1;
		}
		
		return currentSelection;
		
	}
	
	
	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		
		int currentSelection = 0;
		DamageUI dalekStatus;
		TacticalUI weaponList;
		int dalekSize = dalekList.size();
		ArrayList<Integer> selectedDaleks = new ArrayList<Integer>();
		String dalekName;
		
		factoryPanel = new selectFactoryDaleksPanel(dalekImages,dalekList,this);
		
		factoryPanel.setDalekWidth(128);
		factoryPanel.setDalekIconWidth(48);
		factoryPanel.setSelectorImage(getImageWithFilename("Images/statusPanel.png"));
		factoryPanel.setOverlayImage(getImageWithFilename("Images/selectionOverlay.png"));
		
		
		// want this to return each keypress.
		frame.getContentPane().add(BorderLayout.EAST, factoryPanel);
		
		dalekName = dalekList.get(currentSelection).getName();
		
		getStatusPanel().setDalekName(dalekName);
		dalekStatus = dalekDamageImages.get(dalekName);
		dalekStatus.setFromSections(dalekList.get(currentSelection).getSections());
		getStatusPanel().setDamageImage(dalekStatus);
		
		
		factoryPanel.requestFocus();
		
		frame.pack();
		frame.setVisible(true);
		factoryPanel.setFocusable(true);
		
		do {
			dalekName = dalekList.get(currentSelection).getName();
			
			getStatusPanel().setDalekName(dalekName);
			
			dalekStatus = dalekDamageImages.get(dalekName);
			dalekStatus.setFromSections(dalekList.get(currentSelection).getSections());
			getStatusPanel().setDamageImage(dalekStatus);
			
			weaponList = dalekTacticalImages.get(dalekName);
			getStatusPanel().setTacticalImage(weaponList);
			
			// getStatusPanel().setFromSections(dalekList.get(currentSelection).getSections());
			getStatusPanel().repaint();
			
			factoryPanel.centreOn(currentSelection);
			// getMapPanel().setSelectorPosition(dalekList.get(currentSelection).getPosition());
			factoryPanel.repaint();
			//System.out.println("**** current selection position : " + dalekList.get(currentSelection).getPosition() +" ****");
			
			selectedMovement = Tables.NONE;
			
			while (selectedMovement == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
			
			if (selectedMovement == Tables.RIGHT || selectedMovement == Tables.BACKWARD) {
				currentSelection --;
				if (currentSelection < 0 ) { currentSelection += dalekSize; }
			}
			if (selectedMovement == Tables.LEFT || selectedMovement == Tables.FORWARD) {
				currentSelection++;
				if (currentSelection >= dalekSize) {currentSelection -= dalekSize;}
			}
			if (selectedMovement == Tables.SELECT) {
				selectedDaleks.add(currentSelection);
				factoryPanel.addDalek(currentSelection);
				factoryPanel.repaint();
			}
		} while (selectedMovement != Tables.DONE || selectedDaleks.size() == 0) ;
		
		frame.getContentPane().remove (factoryPanel);
		
		frame.getContentPane().add(BorderLayout.EAST, mapp);
		frame.pack();
		frame.setVisible(true);
		
		// mapp.addArray(dalekImage("Black Renegade"),new Position(0,0));
		
		
		return selectedDaleks;
	}
	
	int selectWeapon(ArrayList<Weapon> w) {
		
		int currentSelection = 0;
		int weaponSize = w.size();
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();

		
		getStatusPanel().getTacticalImage().setFromWeaponArray(w);

		do {
			
			
		//	System.out.println("selectWeapon currentSelection: " + currentSelection);
			
			getStatusPanel().getTacticalImage().setSelected(currentSelection);
			getStatusPanel().repaint();
			
			
			selectedMovement = Tables.NONE;
			while (selectedMovement == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
			
				if (selectedMovement == Tables.LEFT || selectedMovement == Tables.FORWARD) {

				currentSelection --;
				if (currentSelection < 0 ) { currentSelection += weaponSize; }
			}
			
			if (selectedMovement == Tables.RIGHT || selectedMovement == Tables.BACKWARD) {

				currentSelection++;
				if (currentSelection >= weaponSize) {currentSelection -= weaponSize;}
			}
			
			//System.out.println ("Selected Movement: " + selectedMovement);
			
		} while (selectedMovement != Tables.SELECT && selectedMovement != Tables.DONE)  ;
		
		getStatusPanel().repaint();

		
		if (selectedMovement == Tables.SELECT) {
			return currentSelection;
		} else {
			return -1;
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
	
	HashMap<String,DamageUI> InitDalekImageDamage() {
		
		HashMap<String,DamageUI> imageMap = new HashMap<String,DamageUI>();
		
		
		imageMap.put("Black Renegade",new BlackRenegadeDamage());
		imageMap.put("Black Supreme",new InvasionDamage());
		imageMap.put("Blue Drone",new InvasionDamage());
		// imageMap.put("Emperor Time War",getImageWithFilename("Images/EmperorTimeWar.png"));
		imageMap.put("Gold Supreme",new GoldSupremeDamage());
		// imageMap.put("Gold Time War",getImageWithFilename("Images/GoldTimeWar.png"));
		imageMap.put("Grey Renegade",new GreyRenegadeDamage());
		imageMap.put("Imperial",new ImperialDamage());
		imageMap.put("Red Commander",new InvasionDamage());
		imageMap.put("Red Saucer Pilot",new RedSaucerDamage());
		imageMap.put("Special Weapon",new SpecialWeaponDamage());
		
		
		return imageMap;
	}
	
	HashMap<String,DamageUI> InitDalekImageRemote() {
		
		HashMap<String,DamageUI> imageMap = new HashMap<String,DamageUI>();
		
		
		imageMap.put("Black Renegade",new BlackRenegadeDamage("Images/RenegadeRemote.png",true));
		imageMap.put("Black Supreme",new InvasionDamage("Images/InvasionRemote.png",true));
		imageMap.put("Blue Drone",new InvasionDamage("Images/InvasionRemote.png",true));
		// imageMap.put("Emperor Time War",getImageWithFilename("Images/EmperorTimeWar.png"));
		imageMap.put("Gold Supreme",new GoldSupremeDamage("Images/GoldSupremeRemote.png",true));
		// imageMap.put("Gold Time War",getImageWithFilename("Images/GoldTimeWar.png"));
		imageMap.put("Grey Renegade",new GreyRenegadeDamage("Images/RenegadeRemote.png",true));
		imageMap.put("Imperial",new ImperialDamage("Images/ImperialRemote.png",true));
		imageMap.put("Red Commander",new InvasionDamage("Images/InvasionRemote.png",true));
		imageMap.put("Red Saucer Pilot",new RedSaucerDamage("Images/RedSaucerRemote.png",true));
		imageMap.put("Special Weapon",new SpecialWeaponDamage("Images/SpecialWeaponRemote.png",true));
		
		
		return imageMap;
	}
	
	HashMap<String,TacticalUI> InitDalekImageTactical() {
		
		HashMap<String,TacticalUI> imageMap = new HashMap<String,TacticalUI>();
		
		
		imageMap.put("Black Renegade",new BlackRenegadeTactical());
		imageMap.put("Black Supreme",new BlackSupremeTactical());
		imageMap.put("Blue Drone",new BlueDroneTactical());
		// imageMap.put("Emperor Time War",getImageWithFilename("Images/EmperorTimeWar.png"));
		imageMap.put("Gold Supreme",new GoldSupremeTactical());
		// imageMap.put("Gold Time War",getImageWithFilename("Images/GoldTimeWar.png"));
		imageMap.put("Grey Renegade",new GreyRenegadeTactical());
		imageMap.put("Imperial",new ImperialTactical());
		imageMap.put("Red Commander",new RedCommanderTactical());
		imageMap.put("Red Saucer Pilot",new RedSaucerTactical());
		imageMap.put("Special Weapon",new SpecialWeaponTactical());
		
		
		return imageMap;
	}
	
	
	
	void notifyEngine (int current, int walk, int run) {
		
		getStatusPanel().setEngineCurrent(current);
		getStatusPanel().setEngineWalk(walk);
		getStatusPanel().setEngineRun(run);
		
		getStatusPanel().repaint();
	}
	
	void notifyDalekPosition(Dalek d) {
		getMapPanel().notifyDalek(d.getName(),dalekImage(d.getName()),d.getPosition());
	}
	
	void notifyEnd(boolean destroyed) {
		if (destroyed) { this.setInterfaceMessage("All Daleks Destroyed - Mission Failed"); }
		if (!destroyed) { this.setInterfaceMessage("Enemy Daleks Destroyed - Mission Accomplished"); }
	}
	
	void notifyDamage(Dalek d) {
		
		DamageUI dalekStatus;
		TacticalUI dalekWeapon;
		
		BufferedImage oldImage; 
		
		getStatusPanel().setDalekName(d.getName());
		
		dalekStatus = dalekDamageImages.get(d.getName());
		
		
		oldImage= new BufferedImage (dalekStatus.getWidth(null),dalekStatus.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D canvas  = oldImage.createGraphics();
		canvas.drawImage(dalekStatus,0,0,null);
		
		dalekStatus.setFromSections(d.getSections());
		// implement some kind of flasher

		for (int i=0 ; i < 3; i++) {
			getStatusPanel().setDamageImage(dalekStatus);
			getStatusPanel().repaint();
			try {
				Thread.sleep(400); 
			} catch (InterruptedException ie) { ; }
			getStatusPanel().setDamageImage(oldImage);
			getStatusPanel().repaint();

			try {
				Thread.sleep(400); 
			} catch (InterruptedException ie) { ; }

		}

		getStatusPanel().setDamageImage(dalekStatus);
		getStatusPanel().repaint();
		
	}
	
	Image dalekImage(String name) {
		
		if (dalekImages.containsKey(name)) {
			return dalekImages.get(name);
		}
		System.out.println("Canot find: "+ name);
		return null;
	}
	
	DamageUI getDamageImage(String name) {
		
		if (dalekImages.containsKey(name)) {
			return dalekDamageImages.get(name);
		}
		System.out.println("Canot find: "+ name);
		return null;
	}
	
	
	public static Image getImageWithFilename (String fn) {
		URL imageURL = DalekTech.class.getResource(fn);
		if (imageURL != null) {
			return new ImageIcon(imageURL).getImage();
		}
		System.err.println ("getImageWithFilename (" + fn + ") Returns Null");
		return null;
	}
	
	
	
	
	
}
