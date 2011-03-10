import java.util.*;

public class Player {

	UserInterface ui;
	ArrayList<Dalek> daleks;
	Map map;
	DalekTech game;
	
	public  Player (UserInterface ui, DalekTech game) {
		this.ui = ui;
		this.game = game;
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
	
	
	boolean allMoved () {
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			if (!it.next().hasMoved()) {
				return false;
			}
		}
		return true;
	}
	
	boolean allTwist () {
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			if (!it.next().hasTwist()) {
				return false;
			}
		}
		return true;
	}
	
	boolean allFired () {
		Iterator<Dalek> it = daleks.iterator();
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
				dalekPosition=getUI().positionDalek(dal);
				// check it doesn't clobber another dalek
			} while (dalekPosition.isIn(getGame().allDalekPositions()));
			dal.setPosition(dalekPosition);
			// get direction
			direction = getUI().directionDalek(dal);
			dal.setDirection(direction);
		}
	}
	
	
	void selectFactoryDaleks(ArrayList<Dalek> dalekList) {
		Iterator<Dalek> it;
		daleks = getUI().selectFactoryDaleks(dalekList);
		
		it  = daleks.iterator();
		while (it.hasNext()) {
			it.next().setPlayer(this);
		}
		
	}
	
	void moveDalek () {
		int dir;
		if (!this.allMoved()) {
			Dalek dal;
			dal = getUI().selectDalek(getHaventMoved());
			dal.setMoved(true);
			
			do {
			dir = getUI().moveDalek(dal,
									dal.getMovement(),
									dal.getWalk(),
									dal.getRun(),
									dal.getHex().getMovementCost(dal.getForwardsHex()),
									dal.getHex().getMovementCost(dal.getBackwardsHex()));
			} while (dal.moveDalek(dir));
		}
	}
	

}