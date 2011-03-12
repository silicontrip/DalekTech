import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;


public class Cli extends UserInterface {

	Map map;
	BufferedReader is;
	
	public Cli(Map m) { 
		this.map = m;
		is = new BufferedReader(new InputStreamReader(System.in));
	}
	
	Map getMap() { return map; }
	
	private String readln() {
		String ln = "";
		
		try {
			ln = is.readLine();
			// if (ln.length() == 0 ) return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return ln;
	}
	
	private int directionFromString (String dir) {
	
		// lot's of conditional logic
		
		if (dir.equalsIgnoreCase("n") ) { return Tables.NORTH; }
		if (dir.equalsIgnoreCase("ne") ) { return Tables.NORTHEAST; }
		if (dir.equalsIgnoreCase("se") ) { return Tables.SOUTHEAST; }
		if (dir.equalsIgnoreCase("s") ) { return Tables.SOUTH; }
		if (dir.equalsIgnoreCase("sw") ) { return Tables.SOUTHWEST; }
		if (dir.equalsIgnoreCase("nw") ) { return Tables.NORTHWEST; }

		return -1;
		
	}
	
	private int movementFromString (String dir) {
		
		if (dir.equalsIgnoreCase("f")) { return Tables.FORWARD; }
		if (dir.equalsIgnoreCase("b")) { return Tables.BACKWARD; }
		if (dir.equalsIgnoreCase("l")) { return Tables.LEFT; }
		if (dir.equalsIgnoreCase("r")) { return Tables.RIGHT; }

		return Tables.NONE;
	}
	
	
	Position getDalekPosition(Dalek d) {
		
		String input;
		String sValues[];
		int x,y;
		
		System.out.println (d.toString());
		do {
			System.out.print ("Enter co-ordinates (1,1-" + getMap().getSizeX() +"," + getMap().getSizeY() +") ? ");
			
			input = readln();
			sValues = input.split(",");
			try {
				x = Integer.parseInt(sValues[0]);
				y = Integer.parseInt(sValues[1]);
				
			} catch (NumberFormatException nfe) { x = -1; y = -1; }
		} while ( x < 1 || x > getMap().getSizeX() || y < 1 || y > getMap().getSizeY());
		
		x--; y--;
		return new Position(x,y);
		
	}
	
	int getDalekDirection (Dalek d) {
	
		int dir = -1;
		String input;

		System.out.println (d.toString());

		do {
			System.out.print ("Enter direction (N,NE,SE,S,SW,NW): ");
			input = readln();
			dir = this.directionFromString(input);
		} while (dir == -1);

		return dir;
	}
	
	int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost) { 
		
		String input;
		String outString = new String("Enter Movement ");
		
		if (d.canMoveForwards()) { outString = outString.concat (new String("Forward(" + forwardCost +")/")); }
		if (d.canMoveBackwards()) { outString = outString.concat (new String("Backward(" + backwardCost +")/")); }
		if (d.canTurn()) { outString = outString.concat (new String("Left/Right")); }
		
		outString = outString.concat(new String ("/End : "));
		
		System.out.println (d.toString());
		
		System.out.print ( outString );
		input = readln();
		return this.movementFromString(input);
		
	}
	
	int getDalekTwist(Dalek d) {
		String input;
		
		System.out.println (d.toString());
		
		System.out.print ("Enter Left/Right/End: ");
		input = readln();
		return this.movementFromString(input);
		
	}
	
//////////////////////////////////////////////////////////////////////
	
	ArrayList<Dalek> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		Dalek dal;
		ArrayList<Dalek> dalekSelection = new ArrayList<Dalek>();
		
		do {
			dal = this.selectDalekWithExit(dalekList);
			if (dal != null) {
				dalekSelection.add(dal);
			}
		} while (dal != null);
		return dalekSelection;
	}
	
	private int choose (ArrayList l) {
		String choice;
		int ichoice;
		int i;
		
		for (i=0; i< l.size(); i++) {
			System.out.println ((i+1) + ". " + l.get(i));
		}					
		
		if (l.size() == 1) { return 0; }
		
		while (true) {
			System.out.print("? ");
			choice = readln();
			try {
				ichoice = Integer.parseInt(choice) -1;
			} catch (NumberFormatException nfe) { ichoice = -1; }
			if (ichoice >= 0 && ichoice<i) {
				return ichoice;
			}
		} 
	}
		
	
	Dalek selectTargetDalek (Dalek d, Weapon w, ArrayList<Dalek> targetList) {
	
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< targetList.size(); i++) {
			// calculate difficulty of shot
			int difficulty  = w.costFire(targetList.get(i));
			choiceList.add(difficulty + " = " + targetList.get(i).toString());
		}					
		choiceList.add ("End");
		choice = choose(choiceList);
		if (choice == i ) { return null; }
		return targetList.get(choice);
	}
	
	Weapon selectWeapon(ArrayList<Weapon> w) {
	
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< w.size(); i++) {
			choiceList.add(w.get(i).toString());
		}					
		choiceList.add ("End");
		choice = choose(choiceList);
		if (choice == i ) { return null; }
		return w.get(choice);
		
	}
	
	Dalek selectDalek (ArrayList<Dalek> dalekList) {
		
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< dalekList.size(); i++) {
			choiceList.add(dalekList.get(i).toString());
		}					
		choice = choose(choiceList);
		return dalekList.get(choice);
	}
	
	Dalek selectDalekWithExit (ArrayList<Dalek> dalekList) {
		
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< dalekList.size(); i++) {
			choiceList.add(dalekList.get(i).toString());
		}		
		choiceList.add ("End");		
		choice = choose(choiceList);
		if (choice == i ) { return null; }

		return dalekList.get(choice);
	}
	
	
	
	Dalek getDalekFire(Dalek d, ArrayList<Dalek> targetDaleks) {
		int i=0;
		System.out.println ((i+1) + ". End");
		return targetDaleks.get(0);
	}
	
////////////////////////////////////////////////////////
// 
// Status update methods
//
///////////////////////////////////////////////////////
	
	void notifyEngine (int current, int walk, int run) {
		
		if (current == 0) {
			System.out.println("STATIONARY");
		} else if (current <= walk) {
			System.out.println("LOW " + current + "/" + walk);
		} else {
			System.out.println("HIGH " + current + "/" + run);
		}
		
	}
	
	void notifyDifficulty (int cost) {
		;
	}
	/*
	 2 - 1 - 1 - 0%
	 3 - 2 - 3 - 3%
	 4 - 3 - 6 - 8%
	 5 - 4 - 10 - 17%
	 6 - 5 - 15 - 28%
	 7 - 6 - 21 - 42%
	 8 - 5 - 26 - 58%
	 9 - 4 - 30 - 72%
	 10 - 3 - 33 - 83%
	 11 - 2 - 35 - 92%
	 12 - 1 - 36 - 97%
	 */	 
	void notifyLOS(ArrayList<Hex> line) {
		;
	}
	
	void notifyTargetDalek  (Dalek d) { 
		;
	}	
	
	void notifyDalekPosition(Dalek d) {
		System.out.println (d.getPosition().toString() + ":" + d.getDirection());
	}
	
	void notifyDamage(Dalek d) {
	
		Collection<DalekSection> c = d.getSections().values();
		Iterator<DalekSection> it  = c.iterator();
		
		while (it.hasNext()) {
			DalekSection section;
			section = it.next();
			System.out.print ( section.getDamage() + "/" + section.getArmour() + " ");
		}
		System.out.println ("");
		
	}
	
	void notifyName(Dalek d) {
		System.out.println (d.getName());
	}
	
	void notifyDalekDamage (Dalek d, int location, Weapon w) {
		int damage = w.getDamage(w.getDalekSection().getDalek().distanceTo(d));
		System.out.println (d.getName() + " " + d.getLocation(location) + " ** " + damage + " **");
	}

	void notifyMiss (Dalek d, Weapon w) {
		System.out.println (w.getDalekSection().getDalek().getName() + " Misses " + d.getName() + " with " + w.toString());
	}
	
	
}