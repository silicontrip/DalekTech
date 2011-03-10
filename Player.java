import java.util.*;

public class Player {

	UserInterface ui;
	ArrayList<Dalek> daleks;
	Map map;
	
	
	public  Player (UserInterface ui) {
		this.ui = ui;
	}
	
	void setUI (UserInterface ui) { this.ui = ui; }
	UserInterface getUI() { return ui; }
	void setMap (Map m) { this.map = m; }
	Map getMap() { return this.map; }
	
	
	boolean allDestroyed() {
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			if (!it.next().isDestroyed()) {
				return false;
			}
		}
		return true;
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
		Iterator<Dalek> it = daleks.iterator();
		while(it.hasNext()) {
			getUI().positionDalek(it.next());
			// check it doesn't clobber another dalek
			// get direction
			
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
		if (!this.allMoved()) {
			getUI().moveDalek(daleks);
		}
	}
	

}