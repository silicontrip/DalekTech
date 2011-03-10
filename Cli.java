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
			System.out.print ("Enter co-ordinates (0,0-" + getMap().getSizeX() +"," + getMap().getSizeY() +") ? ");
		
			input = readln();
			sValues = input.split(",");
			try {
				x = Integer.parseInt(sValues[0]);
				y = Integer.parseInt(sValues[2]);

			} catch (NumberFormatException nfe) { x = -1; y = -1; }
		} while ( x < 0 || x > getMap().getSizeX() || y < 0 || y > getMap().getSizeY());
		
		return new Position(x,y);

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
					ichoice = Integer.parseInt(choice);
				} catch (NumberFormatException nfe) { ichoice = -1; }
				if (ichoice >0 && ichoice<=i) {
					dalekSelection.add(dalekList.get(ichoice));
				}
			} while (ichoice >0 && ichoice <=i);
		} while (ichoice != i+1);
		return dalekList;
	}
	
	Dalek selectDalek (ArrayList<Dalek> dalekList) {
		int i;
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		System.out.println ((i+1) + ". End");
		
		return dalekList.get(0);
		
	}
	
	void moveDalek (ArrayList<Dalek> dalekList) {
		int i;
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		System.out.println ((i+1) + ". End");
		
	}

	void twistDalek (ArrayList<Dalek> dalekList) {
		int i;
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		System.out.println ((i+1) + ". End");
		
	}
	
	Dalek fireDalek (ArrayList<Dalek> dalekList,ArrayList<Dalek> targetDaleks) {
		int i;
		for (i=0; i< dalekList.size(); i++) {
			System.out.println ((i+1) + ". " + dalekList.get(i));
		}					
		System.out.println ((i+1) + ". End");
		return targetDaleks.get(0);
	}
	
	void dalekStatus (Dalek d) {
	
		System.out.println (d.toString());
		
	}
	
	
}