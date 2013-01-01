package org.silicontrip.dalektech.ui;

import org.silicontrip.dalektech.ui.gui.*;
import org.silicontrip.dalektech.dalek.Dalek;
import org.silicontrip.dalektech.dalek.Weapon;
import org.silicontrip.dalektech.map.*;
import org.silicontrip.dalektech.Tables;


import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
//import java.util.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.net.URL;


// two for 2d
public class Guitwo extends Cli {
	
	JFrame frame;
	
	Image mapImage;
	
	Image SelectorImage;
	Image OverlayImage;
	
	mapPanel mapp;
	selectFactoryDaleksPanel factoryPanel;
	statusPanel statusp;
	//DalekSectionUI dsui;
	
	HashMap<String,Image> dalekImages;
	HashMap<String,DamageUI> dalekDamageImages;
	HashMap<String,DamageUI> dalekRemoteImages;
	HashMap<String,TacticalUI> dalekTacticalImages;
	
	
//	Position selectedPosition;
//	Direction selectedDirection = null;
//	int selectedMovement;
	
	BufferedImage statusImage;
	
	Graphics2D statusGraphic;
	Graphics2D mapGraphic;
	
	public Guitwo(Map m) throws FileNotFoundException {
		
		super(m);
		
		dalekImages = InitDalekImage();
		dalekDamageImages = InitDalekImageDamage();
		dalekRemoteImages = InitDalekImageRemote();
		dalekTacticalImages = InitDalekImageTactical();
		
		
		SelectorImage= (DalekTechUI.getImageWithFilename("Images/statusPanel.png"));
		OverlayImage= (DalekTechUI.getImageWithFilename("Images/selectionOverlay.png"));

		
		frame = new JFrame();
		frame.setTitle("Dalek Battle");
		
		// dsui = new DalekSectionUI();
		statusp = new statusPanel();
		// mop = new mapOverlayPanel();
		
		// statusp.setDamageImages (dalekDamageImages);
		// statusp.setTacticalImages (dalekImages);
		statusp.setBackground(DalekTechUI.getImageWithFilename("Images/statusPanel.png"));
		
		mapImage = DalekTechUI.getImageWithFilename (new String ("Images/").concat(super.getMap().getImageName()));
		mapp = new mapPanel(mapImage,m,this);
		
		mapp.setSelectorImage(DalekTechUI.getImageWithFilename("Images/hexSelector.png"));
		mapp.setArrowImage(DalekTechUI.getImageWithFilename("Images/Arrow.png"));
		mapp.setLeftImage(DalekTechUI.getImageWithFilename("Images/ArrowLeft.png"));
		mapp.setRightImage(DalekTechUI.getImageWithFilename("Images/ArrowRight.png"));

		
		// for testing the selector registration...
		// mapp.setSelectorPosition(new Position(0,0));
		// mapp.setTargetCost(7);
		
		// TacticalUI weaponTestArray = new TacticalUI(4);
		
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();

		
		// statusp.setTacticalImage(weaponTestArray);
		
		frame.getContentPane().add(BorderLayout.WEST, statusp);
		//frame.pack();
		//frame.setVisible(true);
		
		frame.setSize(800,480); // phone resolution
		frame.setVisible(true);
		
	}
	
	mapPanel getMapPanel() { return mapp; }
	statusPanel getStatusPanel() { return statusp; }
	
	public void setInterfaceMessage(String s) { getMapPanel().setInterfaceMessage(s); getMapPanel().repaint(); }
	
	public Position getDalekPosition(Dalek d) {
		
		getMapPanel().setSelectedPosition(null);
		getMapPanel().positionDalek(dalekImage(d.getName()));
		getMapPanel().setPositionDalek();

		while (getMapPanel().getSelectedPosition() == null) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		
		return getMapPanel().getSelectedPosition();
	}
	
	public Direction getDalekDirection (Dalek d) {
		
		getMapPanel().setSelectedDirection(null);
		getMapPanel().directDalek(dalekImage(d.getName()),d.getPosition());
		
		getMapPanel().setDirectDalek();
		
		while (getMapPanel().getSelectedDirection() == null) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
			
		}
		return getMapPanel().getSelectedDirection();
	}
	
	public Position getDalekPositionAndDirection(Dalek d) {
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();

		Position selectedPosition = this.getDalekPosition(d);
		d.setPosition(selectedPosition);
		selectedPosition.setDirection(this.getDalekDirection(d));
		getMapPanel().setNothing();
		d.setPosition(new Position(-1,-1));
		return selectedPosition;
	}
	
	public int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn) { 
		
		getStatusPanel().setDalekName(d.getName());
		getStatusPanel().setEngine(currentMove,walk,run);
		getStatusPanel().repaint();
		
		// not sure where to put forward/backward cost.
		
		// check for not moving onto dalek
		
		getMapPanel().setMovementCost(d.getPosition(),new Integer(forwardCost), new Integer(backwardCost),forward,backward);
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();
		
		//	getMapPanel().moveDalek(d.getName());
		getMapPanel().setSelectedMovement(Tables.NONE);
		
		while (getMapPanel().getSelectedMovement() == Tables.NONE) {
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		getMapPanel().setMovementCost(null,null,null,null,null);
		
		return getMapPanel().getSelectedMovement();
	}
	
	public int getDalekTwist(Dalek d) {
		
		getStatusPanel().setDalekName(d.getName());
		getStatusPanel().repaint();
		
		getMapPanel().setMovementCost(d.getPosition(),new Integer(0), new Integer(0),false,false);

		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();
		
		
		// not sure where to put forward/backward cost.
		
		//	getMapPanel().moveDalek(d.getName());
		do {
			getMapPanel().setSelectedMovement(Tables.NONE);
			
			while (getMapPanel().getSelectedMovement() == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
		} while (getMapPanel().getSelectedMovement() != Tables.LEFT &&
				 getMapPanel().getSelectedMovement() != Tables.RIGHT &&
				 getMapPanel().getSelectedMovement() != Tables.DONE) ;
		
		getMapPanel().setMovementCost(null,null,null,null,null);

		return getMapPanel().getSelectedMovement();
		
	}
	
	public int selectDalek (ArrayList<Dalek> dalekList) {
		
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
				
				getMapPanel().setSelectedMovement(Tables.NONE);
			//	getMapPanel().setSelectedPosition(null);
				
				while (getMapPanel().getSelectedMovement() == Tables.NONE ) {
					try {
						Thread.sleep(250); 
					} catch (InterruptedException ie) { ; }
				}
				
				if (getMapPanel().getSelectedMovement() == Tables.LEFT || getMapPanel().getSelectedMovement() == Tables.FORWARD) {
					currentSelection --;
					if (currentSelection < 0 ) { currentSelection += dalekSize; }
				}
				if (getMapPanel().getSelectedMovement() == Tables.RIGHT || getMapPanel().getSelectedMovement() == Tables.BACKWARD) {
					currentSelection++;
					if (currentSelection >= dalekSize) {currentSelection -= dalekSize;}
				}
			} while (getMapPanel().getSelectedMovement() != Tables.SELECT) ;
			
			getMapPanel().setSelectorPosition(null);
			getMapPanel().repaint();
			
			return currentSelection;
		}
		return 0;
		
	}
	
	//int selectTargetDalek (Dalek d, ArrayList<Dalek> dalekList, ArrayList<Integer> targetCost) 
	public int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList, ArrayList<Integer> targetCost, ArrayList<Double> distance, ArrayList<ArrayList<Hex>> los)
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
			
			
			getMapPanel().setSelectedMovement(Tables.NONE);
			
			while (getMapPanel().getSelectedMovement() == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
			
			if (getMapPanel().getSelectedMovement() == Tables.LEFT || getMapPanel().getSelectedMovement() == Tables.FORWARD) {
				currentSelection --;
				if (currentSelection < 0 ) { currentSelection += dalekSize; }
			}
			if (getMapPanel().getSelectedMovement() == Tables.RIGHT || getMapPanel().getSelectedMovement() == Tables.BACKWARD) {
				currentSelection++;
				if (currentSelection >= dalekSize) {currentSelection -= dalekSize;}
			}
		} while (getMapPanel().getSelectedMovement() != Tables.SELECT && getMapPanel().getSelectedMovement() != Tables.DONE) ;
		
		getMapPanel().setProbabilityUI(null);
		getMapPanel().setTargetDistance(null);
		getMapPanel().setLineOfSight(null);
		getMapPanel().setTarget(null);
		
		getMapPanel().setSelectorPosition(null);
		getMapPanel().repaint();
		
		if (getMapPanel().getSelectedMovement() == Tables.DONE) {
			return -1;
		}
		
		return currentSelection;
		
	}
	
	
	public ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList)  {
		
		int currentSelection = 0;
		DamageUI dalekStatus;
		TacticalUI weaponList;
		int dalekSize = dalekList.size();
		ArrayList<Integer> selectedDaleks = new ArrayList<Integer>();
		String dalekName;
		
		
		
		factoryPanel = new selectFactoryDaleksPanel(dalekImages,dalekList);
		
		factoryPanel.setDalekWidth(128);
		factoryPanel.setDalekIconWidth(48);
		factoryPanel.setSelectorImage(SelectorImage);
		factoryPanel.setOverlayImage(OverlayImage);
		
		
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
			
			factoryPanel.setSelectedMovement(Tables.NONE);
			
			while (factoryPanel.getSelectedMovement() == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
			
			if (factoryPanel.getSelectedMovement() == Tables.RIGHT || factoryPanel.getSelectedMovement() == Tables.BACKWARD) {
				currentSelection --;
				if (currentSelection < 0 ) { currentSelection += dalekSize; }
			}
			if (factoryPanel.getSelectedMovement() == Tables.LEFT || factoryPanel.getSelectedMovement() == Tables.FORWARD) {
				currentSelection++;
				if (currentSelection >= dalekSize) {currentSelection -= dalekSize;}
			}
			if (factoryPanel.getSelectedMovement() == Tables.SELECT) {
				selectedDaleks.add(currentSelection);
				factoryPanel.addDalek(currentSelection);
				factoryPanel.repaint();
			}
		} while (factoryPanel.getSelectedMovement() != Tables.DONE || selectedDaleks.size() == 0) ;
		
		frame.getContentPane().remove (factoryPanel);
		
		frame.getContentPane().add(BorderLayout.EAST, mapp);
		frame.pack();
		frame.setVisible(true);
		
		// mapp.addArray(dalekImage("Black Renegade"),new Position(0,0));
		
		
		return selectedDaleks;
	}
	
	public int selectWeapon(ArrayList<Weapon> w) {
		
		int currentSelection = 0;
		int weaponSize = w.size();
		
		getMapPanel().setFocusable(true);
		getMapPanel().requestFocus();

		
		getStatusPanel().getTacticalImage().setFromWeaponArray(w);

		do {
			
			// want to display firing arc
			
			getMapPanel().setFiringArc(w.get(currentSelection));
			
			getMapPanel().repaint();
		//	System.out.println("selectWeapon currentSelection: " + currentSelection);
			
			getStatusPanel().getTacticalImage().setSelected(currentSelection);
			getStatusPanel().repaint();
						
			getMapPanel().setSelectedMovement(Tables.NONE);
			while (getMapPanel().getSelectedMovement() == Tables.NONE) {
				try {
					Thread.sleep(250); 
				} catch (InterruptedException ie) { ; }
			}
			
				if (getMapPanel().getSelectedMovement() == Tables.LEFT || getMapPanel().getSelectedMovement() == Tables.FORWARD) {

				currentSelection --;
				if (currentSelection < 0 ) { currentSelection += weaponSize; }
			}
			
			if (getMapPanel().getSelectedMovement() == Tables.RIGHT || getMapPanel().getSelectedMovement() == Tables.BACKWARD) {

				currentSelection++;
				if (currentSelection >= weaponSize) {currentSelection -= weaponSize;}
			}
			
			//System.out.println ("Selected Movement: " + selectedMovement);
			
		} while (getMapPanel().getSelectedMovement() != Tables.SELECT && getMapPanel().getSelectedMovement() != Tables.DONE)  ;

		getMapPanel().unsetFiringArc();
		getMapPanel().repaint();

		getStatusPanel().repaint();

		
		if (getMapPanel().getSelectedMovement() == Tables.SELECT) {
			return currentSelection;
		} else {
			return -1;
		}
		
	}
	
	
	HashMap<String,Image> InitDalekImage() throws FileNotFoundException {
		
		HashMap<String,Image> imageMap = new HashMap<String,Image>();
		
		imageMap.put("Black Renegade",DalekTechUI.getImageWithFilename("Images/BlackRenegade.png"));
		imageMap.put("Black Supreme",DalekTechUI.getImageWithFilename("Images/BlackSupreme.png"));
		imageMap.put("Blue Drone",DalekTechUI.getImageWithFilename("Images/BlueDrone.png"));
		imageMap.put("Emperor Time War",DalekTechUI.getImageWithFilename("Images/EmperorTimeWar.png"));
		imageMap.put("Gold Supreme",DalekTechUI.getImageWithFilename("Images/GoldSupreme.png"));
		imageMap.put("Gold Time War",DalekTechUI.getImageWithFilename("Images/GoldTimeWar.png"));
		imageMap.put("Grey Renegade",DalekTechUI.getImageWithFilename("Images/GreyRenegade.png"));
		imageMap.put("Imperial",DalekTechUI.getImageWithFilename("Images/Imperial.png"));
		imageMap.put("Red Commander",DalekTechUI.getImageWithFilename("Images/RedCommander.png"));
		imageMap.put("Red Saucer Pilot",DalekTechUI.getImageWithFilename("Images/RedSaucerPilot.png"));
		imageMap.put("Special Weapon",DalekTechUI.getImageWithFilename("Images/SpecialWeapon.png"));
		
		
		return imageMap;
	}
	
	HashMap<String,DamageUI> InitDalekImageDamage() throws FileNotFoundException {
		
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
	
	HashMap<String,DamageUI> InitDalekImageRemote() throws FileNotFoundException {
		
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
	
	HashMap<String,TacticalUI> InitDalekImageTactical()  throws FileNotFoundException {
		
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
	
	
	
	public void notifyEngine (int current, int walk, int run) {
		
		getStatusPanel().setEngineCurrent(current);
		getStatusPanel().setEngineWalk(walk);
		getStatusPanel().setEngineRun(run);
		
		getStatusPanel().repaint();
	}
	
	public void notifyDalekPosition(Dalek d) {
		getMapPanel().notifyDalek(d.getDalekID(),dalekImage(d.getName()),d.getPosition());
	}
	
	public void notifyEnd(boolean destroyed) {
		if (destroyed) { this.setInterfaceMessage("All Daleks Destroyed - Mission Failed"); }
		if (!destroyed) { this.setInterfaceMessage("Enemy Daleks Destroyed - Mission Accomplished"); }
	}
	
	public void notifyDalekDamage (Dalek d, int location, Weapon w, int damage) {

		DamageUI dalekStatus;
		BufferedImage oldImage; 

		
		dalekStatus = dalekRemoteImages.get(d.getName());

		oldImage= new BufferedImage (dalekStatus.getWidth(null),dalekStatus.getHeight(null),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D canvas  = oldImage.createGraphics();
		canvas.drawImage(dalekStatus,0,0,null);
		
		
		dalekStatus.setFromSections(d.getSections());
		
		getMapPanel().centreOn(d.getPosition());

		
		for (int i=0 ; i < 3; i++) {
			getMapPanel().setTarget(dalekStatus);
			getMapPanel().repaint();
			try {
				Thread.sleep(400); 
			} catch (InterruptedException ie) { ; }
			getMapPanel().setTarget(oldImage);
			getMapPanel().repaint();
			
			try {
				Thread.sleep(400); 
			} catch (InterruptedException ie) { ; }
			
		}
		
		getMapPanel().setTarget(null);
		getMapPanel().repaint();
		
	}
	
	public void notifyDamage(Dalek d) {
		
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

		getMapPanel().centreOn(d.getPosition());

		
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
	
	
	
	
	
	
	
}
