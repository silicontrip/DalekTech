import java.util.*;
import java.io.*;

public class Player {

	UserInterface ui;
	ArrayList<Dalek> daleks;
	Map map;
	DalekTech game;
	
	public  Player (UserInterface ui, DalekTech game) {
		this.ui = ui;
		this.game = game;
		
		daleks = new ArrayList<Dalek>();
	}
	
	void setUI (UserInterface ui) { this.ui = ui; }
	UserInterface getUI() { return ui; }
	Map getMap() { return getGame().getMap(); }
	ArrayList<Dalek> getDaleks() { return daleks; }
	
	DalekTech getGame() { return game; }
	
	boolean allDestroyed() {
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			if (!it.next().isDestroyed()) {
				return false;
			}
		}
		return true;
	}
	
	ArrayList<Position> getAllPositions () {
		ArrayList<Position> allPositions = new ArrayList<Position>();
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			allPositions.add(it.next().getPosition());
		}
		return allPositions;
	}
	
	ArrayList<Dalek> getHaventMoved () {
		ArrayList<Dalek> selected = new ArrayList<Dalek>();
		Iterator<Dalek> it = daleks.iterator();
		Dalek dal;
		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasMoved()) {
				selected.add(dal);
			}
		}
		return selected;
	}
	
	ArrayList<Dalek> getHaventTwist () {
		ArrayList<Dalek> selected = new ArrayList<Dalek>();
		Iterator<Dalek> it = daleks.iterator();
		Dalek dal;
		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasTwist()) {
				selected.add(dal);
			}
		}
		return selected;
	}
	
	ArrayList<Dalek> getHaventFired () {
		ArrayList<Dalek> selected = new ArrayList<Dalek>();
		Iterator<Dalek> it = daleks.iterator();
		Dalek dal;
		while(it.hasNext()) {
			dal = it.next();
			if (!dal.hasFired()) {
				selected.add(dal);
			}
		}
		return selected;
	}
	
	void allReset () {
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			it.next().reset();
		}
	}
	
	boolean allMoved () {
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			if (!it.next().hasMoved()) {
				return false;
			}
		}
		return true;
	}
	
	boolean allTwist () {
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			if (!it.next().hasTwist()) {
				return false;
			}
		}
		return true;
	}
	
	boolean allFired () {
		Iterator<Dalek> it = getDaleks().iterator();
		while(it.hasNext()) {
			if (!it.next().hasFired()) {
				return false;
			}
		}
		return true;		
	
	}
	
	void positionDaleks() {
		Dalek dal;
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			int direction;
			Position dalekPosition;
			dal=it.next();
			do {
				dalekPosition=getUI().getDalekPosition(dal);
				// check it doesn't clobber another dalek
			} while (dalekPosition.isIn(getGame().allDalekPositions()));
			dal.setPosition(dalekPosition);
			// get direction
			direction = getUI().getDalekDirection(dal);
			dal.setDirection(direction);
		}
	}
	
	
	void selectFactoryDaleks(ArrayList<Dalek> dalekList) {
		Iterator<Integer> it;
		ArrayList<Integer> select;
		select = getUI().selectFactoryDaleks(dalekList);
		
		it  = select.iterator();
		while (it.hasNext()) {
			daleks.add(
					   dalekList.get(
									 it.next()));
		}
		
	}
	
	void moveDalek () {
		if (!this.allMoved()) {
			int dir;
			Dalek dal;
			ArrayList<Dalek> havent = getHaventMoved();
			int select;
			
			select = getUI().selectDalek(havent);
			
			if (select != -1) {
				dal = havent.get(select);

			dal.setMoved(true);
			
			do {
				getUI().notifyDalekPosition(dal);
				getUI().notifyEngine(dal.getMovement(),
									 dal.getWalk(),
									 dal.getRun());
									 
			dir = getUI().getDalekMove(dal,
									dal.getMovement(),
									dal.getWalk(),
									dal.getRun(),
									dal.getHex().getMovementCost(dal.getForwardsHex()),
									dal.getHex().getMovementCost(dal.getBackwardsHex()),
									   dal.canMoveForwards(),
									   dal.canMoveBackwards(),
									   dal.canTurn()
				);
			} while (dal.moveDalek(dir) && dal.canTurn());
			}		
		}
	}
	
	void twistDalek () {
		if (!this.allTwist()) {
			ArrayList<Dalek> havent = getHaventTwist();
			Dalek dal;
			dal = havent.get(getUI().selectDalek(havent));
			dal.setTwist(true);
			dal.moveDalek( getUI().getDalekTwist(dal));
		}
	}
	
	HashMap<Weapon,Dalek> fireDalek (ArrayList<Dalek> targetDaleks) {
		HashMap<Weapon,Dalek> fireMap = new HashMap<Weapon,Dalek>();
		if (!this.allFired()) {
			ArrayList<Weapon> weaponArray;
			Weapon weap;
			int target;
			Dalek dal;
			ArrayList<Dalek> havent = getHaventFired();
			int select;

			dal = havent.get(getUI().selectDalek(havent));
			dal.setFired(true);
			weaponArray = dal.getWeaponArray();
			do {
				select = getUI().selectWeapon(weaponArray);
				if (select != -1) {
					int selectDalek;
					weap = weaponArray.get(select);
					ArrayList<Integer> cost = new ArrayList<Integer>();
					
					for (int i=0; i< targetDaleks.size(); i++) {
                        // calculate difficulty of shot
						cost.add(new Integer(weap.costFire(targetDaleks.get(i))));
					}                                       
					
					target=getUI().selectTargetDalek(dal,targetDaleks,cost);
					if (target != -1) {
						fireMap.put(weap, targetDaleks.get(target));
					}
				}
			} while (select != -1);
		}
		return fireMap;
	}
	
	void endGame () {
		getUI().notifyEnd(allDestroyed());
	}

}
