package org.silicontrip.dalektech;
//import java.util.*;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import org.silicontrip.dalektech.ui.UserInterface;
import org.silicontrip.dalektech.dalek.Dalek;
import org.silicontrip.dalektech.dalek.Weapon;

import org.silicontrip.dalektech.Tables;


import org.silicontrip.dalektech.map.*;


public class Player {
	
	UserInterface ui;
	UserInterface oui;
	ArrayList<Dalek> daleks;
	ArrayList<Player> players;
	Map map;
// 	DalekTech game;
	HashMap<Weapon,Dalek> firing;
	
	public Player () {
		daleks = new ArrayList<Dalek>();
		// firing = new HashMap<Weapon,Dalek>();
	}
	
	public  Player (UserInterface ui, UserInterface ui2, ArrayList<Player> pl) {
		this();
		this.ui = ui;
		this.oui = ui2;
		this.players = pl;
		
	}
	
	void setUI (UserInterface ui) { this.ui = ui; }
	public UserInterface getUI() { return ui; }
	void setOtherUI (UserInterface ui) { this.oui = ui; }
	UserInterface getOtherUI() { return oui; }
	
	public void setFiring(HashMap<Weapon,Dalek> hm) { this.firing = hm; } 
	public HashMap<Weapon,Dalek> getFiring() { return this.firing; }
	
	// Map getMap() { return getGame().getMap(); }
	Map getMap() { return Map.getInstance(); }
	
	
	public ArrayList<Dalek> getDaleks() { 
		// don't return dead daleks
		ArrayList<Dalek> alive = new ArrayList<Dalek>();
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			Dalek d = it.next();
			if (!d.isDestroyed()) {
				alive.add(d);
			}
		}
		return alive;
		
	//return daleks; 
	}
	
	// DalekTech getGame() { return game; }
	
	public boolean allDestroyed() {
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			if (!it.next().isDestroyed()) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<Position> getAllPositions () {
		ArrayList<Position> allPositions = new ArrayList<Position>();
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			allPositions.add(it.next().getPosition());
		}
		return allPositions;
	}
	
	ArrayList<Dalek> getHaventMoved () {
		ArrayList<Dalek> selected = new ArrayList<Dalek>();
		Iterator<Dalek> it = getDaleks().iterator();
		Dalek dal;
		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasMoved() ) {
				selected.add(dal);
			}
		}
		return selected;
	}
	
	ArrayList<Dalek> getHaventTwist () {
		ArrayList<Dalek> selected = new ArrayList<Dalek>();
		Iterator<Dalek> it = getDaleks().iterator();
		Dalek dal;
		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasTwist() ) {
				selected.add(dal);
			}
		}
		return selected;
	}
	
	ArrayList<Dalek> getHaventFired () {
		ArrayList<Dalek> selected = new ArrayList<Dalek>();
		Iterator<Dalek> it = getDaleks().iterator();
		Dalek dal;
		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasFired() && !dal.isDestroyed()) {
				selected.add(dal);
			}
		}
		return selected;
	}
	
	public void allReset () {
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			it.next().reset();
		}
	}
	
	public boolean allMoved () {
		Iterator<Dalek> it = getDaleks().iterator();		
		Dalek dal;

		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasMoved()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean allTwist () {
		Iterator<Dalek> it = getDaleks().iterator();
		Dalek dal;

		while(it.hasNext()) {
			dal = it.next();

			if (!dal.hasTwist()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean allFired () {
		Iterator<Dalek> it = getDaleks().iterator();
		Dalek dal;

		while(it.hasNext()) {
			dal = it.next();

			if (!dal.hasFired() ) {
				return false;
			}
		}
		return true;
	}
	
	ArrayList<Position> allDalekPositions() {
		ArrayList<Position> allPositions = new ArrayList<Position>();
		
		Iterator<Player> it = players.iterator();
		
		while(it.hasNext()) {
			allPositions.addAll(it.next().getAllPositions());
		}
		return allPositions;
	}
	
	
	public void positionDaleks() {
		Dalek dal;
		Iterator<Dalek> it = getDaleks().iterator();
		
		getUI().setInterfaceMessage("Position");
		
		while(it.hasNext()) {
			int direction;
			Position dalekPosition;
			dal=it.next();
			do {
				dalekPosition=getUI().getDalekPositionAndDirection(dal);
				// check it doesn't clobber another dalek
			} while (dalekPosition.isIn(allDalekPositions()) || !Map.getInstance().validPosition(dalekPosition));
			dal.setPosition(dalekPosition);
			
			// tell both interfaces where the new dalek is.
			getUI().notifyDalekPosition(dal);
			getUI().notifyDamage(dal);
			getOtherUI().notifyDalekPosition(dal);
		}
		getUI().setInterfaceMessage(null);
		
	}
	
	
	public void selectFactoryDaleks(ArrayList<Dalek> dalekList) {
		Iterator<Integer> it;
		ArrayList<Integer> select;
		select = getUI().selectFactoryDaleks(dalekList);
		
		it  = select.iterator();
		while (it.hasNext()) {
			
			Dalek thisDalek = DalekFactory.getInstance().dalekWithName(dalekList.get(it.next()).getName());
			// randomise name
			
			thisDalek.setDalekID (new Long(System.nanoTime()));
			
			
			daleks.add(thisDalek);
		}
	}
	
	public void moveDalek () {
		
		if (!this.allMoved()) {
			int dir;
			Dalek dal;
			ArrayList<Dalek> havent = getHaventMoved();
			int select;
			boolean forward= true, backward=true;
			
			getUI().setInterfaceMessage("Select Dalek to Move");
			select = getUI().selectDalek(havent);
			
			if (select != -1) {
				dal = havent.get(select);
				
				dal.setMoved(true);
				
				do {

					getUI().notifyDalekPosition(dal);
					getOtherUI().notifyDalekPosition(dal);
					getUI().notifyEngine(dal.getMovement(),
										 dal.getWalk(),
										 dal.getRun());
					
					getUI().setInterfaceMessage("Move");
					
					forward= true;
					backward=true;
					if (dal.getPosition().newForwardsPosition().isIn(allDalekPositions())) { forward = false; }
					if (dal.getPosition().newBackwardsPosition().isIn(allDalekPositions())) { backward = false; }
					
					
					dir = getUI().getDalekMove(dal,
											   dal.getMovement(),
											   dal.getWalk(),
											   dal.getRun(),
											   dal.getHex().getMovementCost(dal.getForwardsHex()),
											   dal.getHex().getMovementCost(dal.getBackwardsHex()),
											   dal.canMoveForwards() && forward,
											   dal.canMoveBackwards() && backward,
											   dal.canTurn()
											   );
				} while (dal.moveDalek(dir) && dal.canTurn());
				getUI().notifyEngine(dal.getMovement(),
									 dal.getWalk(),
									 dal.getRun());
				
				getUI().notifyDalekPosition(dal);
				getOtherUI().notifyDalekPosition(dal);

			}	
			
			getUI().setInterfaceMessage(null);

		}
		
	}
	
	public void twistDalek () {
		
		getUI().setInterfaceMessage("Select Dalek to Twist");
		
		if (!this.allTwist()) {
			ArrayList<Dalek> havent = getHaventTwist();
			Dalek dal;
			dal = havent.get(getUI().selectDalek(havent));
			getUI().notifyDalekPosition(dal);
			getOtherUI().notifyDalekPosition(dal);
			dal.setTwist(true);
			getUI().setInterfaceMessage("Twist");

			dal.faceDalek( getUI().getDalekTwist(dal) );
		}
		getUI().setInterfaceMessage(null);
		
	}
	
	public HashMap<Weapon,Dalek> fireDalek (ArrayList<Dalek> targetDaleks) {
		HashMap<Weapon,Dalek> fireMap = new HashMap<Weapon,Dalek>();
		
		if (!this.allFired()) {
			ArrayList<Weapon> weaponArray;
			Weapon weap;
			int target;
			Dalek dal;
			ArrayList<Dalek> havent = getHaventFired();
			int select;
			
			getUI().setInterfaceMessage("Select Dalek to Fire");

			dal = havent.get(getUI().selectDalek(havent));
			dal.setFired(true);
			weaponArray = dal.getWeaponArray();
			do {
				getUI().setInterfaceMessage("Select Weapon to Fire");

				select = getUI().selectWeapon(weaponArray);
				if (select != -1) {
					int selectDalek;
					weap = weaponArray.get(select);
					ArrayList<Integer> cost = new ArrayList<Integer>();
					ArrayList<Double> dist = new ArrayList<Double>();
					ArrayList<ArrayList<Hex>> los = new ArrayList<ArrayList<Hex>>();
					
					for (int i=0; i< targetDaleks.size(); i++) {
                        // calculate difficulty of shot
						cost.add(new Integer(weap.costFire(targetDaleks.get(i))));
						// need distance and LOS
						dist.add(new Double(weap.distanceTo(targetDaleks.get(i))));
						los.add(weap.getLineOfSight(targetDaleks.get(i)));
						
					}                                       
					getUI().setInterfaceMessage("Select Target");
					
					target=getUI().selectTargetDalek(dal,targetDaleks,cost,dist,los);
					if (target != -1) {
						fireMap.put(weap, targetDaleks.get(target));
					}
				}
			} while (select != -1);
		}
		getUI().setInterfaceMessage(null);
		
		return fireMap;
	}
	
	public void endGame () {
		getUI().notifyEnd(allDestroyed());
	}
	
}
