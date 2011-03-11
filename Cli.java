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
	
	Position positionDalek(Dalek d) {
		
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
	
	int directionFromString (String dir) {
	
		// lot's of conditional logic
		
		if (dir.equalsIgnoreCase("n") ) { return Tables.NORTH; }
		if (dir.equalsIgnoreCase("ne") ) { return Tables.NORTHEAST; }
		if (dir.equalsIgnoreCase("se") ) { return Tables.SOUTHEAST; }
		if (dir.equalsIgnoreCase("s") ) { return Tables.SOUTH; }
		if (dir.equalsIgnoreCase("sw") ) { return Tables.SOUTHWEST; }
		if (dir.equalsIgnoreCase("nw") ) { return Tables.NORTHWEST; }

		return -1;
		
	}
	
	int movementFromString (String dir) {
		
		if (dir.equalsIgnoreCase("f")) { return Tables.FORWARD; }
		if (dir.equalsIgnoreCase("b")) { return Tables.BACKWARD; }
		if (dir.equalsIgnoreCase("l")) { return Tables.LEFT; }
		if (dir.equalsIgnoreCase("r")) { return Tables.RIGHT; }

		return Tables.NONE;
	}
	
	int directionDalek (Dalek d) {
	
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
	
	ArrayList<Dalek> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		Dalek dal;
		ArrayList<Dalek> dalekSelection = new ArrayList<Dalek>();
		
		do {
			dal = this.selectDalek(dalekList);
			if (dal != null) {
				dalekSelection.add(dal);
			}
		} while (dal != null);
		return dalekSelection;
	}
	
	Dalek selectDalekNoEnd (ArrayList<Dalek> dalekList) {
		
		String choice;
		int ichoice;
		int i;
		
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		do {
			System.out.print("? ");
			choice = readln();
			try {
				ichoice = Integer.parseInt(choice) -1;
			} catch (NumberFormatException nfe) { ichoice = -1; }
			if (ichoice >= 0 && ichoice<i) {
				return dalekList.get(ichoice);
			}
		} while (true);
	}
	
	
	Dalek selectDalek (ArrayList<Dalek> dalekList) {
		
		String choice;
		int ichoice;
		int i;
		
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		System.out.println ((i+1) + ". End");
		do {
			System.out.print("? ");
			choice = readln();
			try {
				ichoice = Integer.parseInt(choice) -1;
			} catch (NumberFormatException nfe) { ichoice = -1; }
			if (ichoice >= 0 && ichoice<i) {
				return dalekList.get(ichoice);
			}
		} while (ichoice != i);
		return null;
	}
	
	int moveDalek (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost) { 

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

	int twistDalek (Dalek d) {
		String input;
		
		System.out.println (d.toString());
		
		System.out.print ("Enter Left/Right/End: ");
		input = readln();
		return this.movementFromString(input);
		
	}
	
	Dalek fireDalek (Dalek d, ArrayList<Dalek> targetDaleks) {
		int i=0;
		System.out.println ((i+1) + ". End");
		return targetDaleks.get(0);
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
	
	
}