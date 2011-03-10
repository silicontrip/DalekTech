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

		return 0;
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
		int i;
		String choice;
		int ichoice=0;
		ArrayList<Dalek> dalekSelection = new ArrayList<Dalek>();
		
		do {
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
				if (ichoice >0 && ichoice<i) {
					dalekSelection.add(dalekList.get(ichoice));
				}
			} while (ichoice >0 && ichoice <i);
		} while (ichoice != i);
		return dalekSelection;
	}
	
	Dalek selectDalek (ArrayList<Dalek> dalekList) {
		int i;
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		System.out.println ((i+1) + ". End");
		
		return dalekList.get(0);
		
	}
	
	int moveDalek (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost, int turnCost) { 
		int dir = -1;
		String input;
		String output = new String();
		boolean allowTurn, allowForwards, allowBackwards;
		
		allowTurn = currentMove + turnCost <= run;
		allowForwards = currentMove + forwardCost <= run;
		allowBackwards = currentMove + backwardCost <= run;
		
		if (allowForwards) { output.concat ("Forward(" + forwardCost +")/"); }
		if (allowForwards) { output.concat ("Backward(" + backwardCost +")/"); }
		if (allowTurn) { output.concat ("Left/Right"); }

		System.out.println (d.toString());
		
		do {
			System.out.print ("Enter Movement " + output + " : ");
			input = readln();
			dir = this.movementFromString(input);
		} while (dir == 0);
		
		return dir;
	}

	int twistDalek (Dalek d) {
		int dir = -1;
		String input;
		
		System.out.println (d.toString());
		
		do {
			System.out.print ("Enter Left Right: ");
			input = readln();
			dir = this.movementFromString(input);
		} while (dir == 0);
		
		return dir;
	}
	
	Dalek fireDalek (Dalek d, ArrayList<Dalek> targetDaleks) {
		int i=0;
		System.out.println ((i+1) + ". End");
		return targetDaleks.get(0);
	}
	
	void dalekStatus (Dalek d) {
	
		System.out.println (d.toString());
		
	}
	
	
}