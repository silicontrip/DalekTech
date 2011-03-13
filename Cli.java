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
	
	int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn) { 
		
		String input;
		String outString = new String("Enter Movement ");
		
		if (forward) { outString = outString.concat (new String("Forward(" + forwardCost +")/")); }
		if (backward) { outString = outString.concat (new String("Backward(" + backwardCost +")/")); }
		if (turn) { outString = outString.concat (new String("Left/Right")); }
		
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
	
	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		int dal;
		ArrayList<Integer> dalekSelection = new ArrayList<Integer>();
		
		do {
			dal = this.selectDalekWithExit(dalekList);
			if (dal != -1) {
				dalekSelection.add(new Integer(dal));
			}
		} while (dal != -1);
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
		
	
	int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList, ArrayList<Integer> targetCost) {
	
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< targetList.size(); i++) {
			// calculate difficulty of shot
			int difficulty  = targetCost.get(i);
			choiceList.add(difficulty + " = " + targetList.get(i).toString());
		}					
		choiceList.add ("End");
		choice = choose(choiceList);
		if (choice == i ) { return -1; }
		return choice;
	}
	
	int selectWeapon(ArrayList<Weapon> w) {
	
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< w.size(); i++) {
			choiceList.add(w.get(i).toString());
		}					
		choiceList.add ("End");
		choice = choose(choiceList);
		if (choice == i ) { return -1; }
		return choice;
		
	}
	
	int selectDalek (ArrayList<Dalek> dalekList) {
		
		int i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< dalekList.size(); i++) {
			choiceList.add(dalekList.get(i).toString());
		}					
		return choose(choiceList);
	}
	
	int selectDalekWithExit (ArrayList<Dalek> dalekList) {
		
		int choice,i;
		ArrayList<String> choiceList = new ArrayList<String>();
		
		for (i=0; i< dalekList.size(); i++) {
			choiceList.add(dalekList.get(i).toString());
		}		
		choiceList.add ("End");		
		choice = choose(choiceList);
		if (choice == i) { return -1; }

		return choice;
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
	
	void notifyDalekDamage (Dalek d, int location, Weapon w, int damage) {
		System.out.println (d.getName() + " " + d.getLocation(location) + " ** " + damage + " **");
	}

	void notifyMiss (Dalek d, Weapon w) {
		System.out.println (w.getDalekSection().getDalek().getName() + " Misses " + d.getName() + " with " + w.toString());
	}
	
	void notifyEnd(boolean destroyed) {
	
		if (destroyed) { System.out.println("All Daleks Destroyed - Mission Failed"); }
		if (!destroyed) { System.out.println("Enemy Daleks Destroyed - Mission Accomplished"); }
		
		
	}
}