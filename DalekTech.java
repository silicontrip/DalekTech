import java.util.*;
import java.io.*;


public class DalekTech {

	private static DalekTech instance = null;

	Random rand;
	Map map;
	
	Player players[];
	
	/*
	public static DalekTech getInstance() {
		if (instance == null) {
			instance = new DalekTech();
		}
		return instance;
	}
	 */
	
	UserInterface interfaceFactory (String s,Map m) {
	
		if (s.equalsIgnoreCase("cli")) {
			return new Cli(m);
		}
		if (s.equalsIgnoreCase("network"))  {
			return new Network(m);
		}
		if (s.equalsIgnoreCase("gui2"))  {
			return new Guitwo(m);
		}
		
		// TODO: add AI player!
		
		return null;
	}
	
	public DalekTech (String i1, String i2) {
		
		// Although it's only made here for 2 players, it shouldn't be too hard to extend to more.
		players = new Player[2];
		
		rand = new java.util.Random(java.lang.System.currentTimeMillis());

		map = Map.getInstance();
		map.initSampleMap();
		
		UserInterface ui1,ui2;
		
		ui1 = interfaceFactory(i1,map);
		ui2 = interfaceFactory(i2,map);

		
		players[0] = new Player(ui1,ui2,this); // UI2DGraphic();
		players[1] = new Player(ui2,ui1,this); // ultimately UI3dGraphic();
	}

	Map getMap() { return Map.getInstance(); }
	Player getPlayer(int i) { return this.players[i]; }
	
	ArrayList<Position> allDalekPositions() {
		ArrayList<Position> allPositions = new ArrayList<Position>();
		
		allPositions.addAll(getPlayer(0).getAllPositions());
		allPositions.addAll(getPlayer(1).getAllPositions());
		
		return allPositions;
	}
	
	boolean teamDestroyed() {
		return this.getPlayer(0).allDestroyed() || this.getPlayer(1).allDestroyed();
	}
	
	boolean allDaleksMoved() { return this.getPlayer(0).allMoved() && this.getPlayer(1).allMoved(); }
	boolean allDaleksTwist() { return this.getPlayer(0).allTwist() && this.getPlayer(1).allTwist(); }
	boolean allDaleksFired() { return this.getPlayer(0).allFired() && this.getPlayer(1).allFired(); }

	
	int twodsix () {
		return rand.nextInt(6) + rand.nextInt(6) + 2;
	 }
	
	ArrayList<Player> initiative () { 
	
		ArrayList<Player> playerOrder = new ArrayList<Player>();
		int r1,r2;
		
		// This is really just 50-50
		
		// could be extended to more players
		do {
			r1 = twodsix();
			r2 = twodsix();
		
			if (r1 > r2) { playerOrder.add(this.getPlayer(0)); playerOrder.add(this.getPlayer(1)); return playerOrder; }
			if (r1 < r2) { playerOrder.add(this.getPlayer(1)); playerOrder.add(this.getPlayer(0)); return playerOrder; }
		} while (true);
	}
	

	public static void main(String[] args) {

		if (args.length < 2) { 
			System.out.println("Requires 2 interfaces.  Where interface is cli or network"); 
			return;
		} 
		
		
		DalekTech Game = new DalekTech(args[0],args[1]);
		ArrayList<Player> playerOrder;	
		
		// init map
		
		// player 1 choose some daleks
		
		Game.getPlayer(0).selectFactoryDaleks(DalekFactory.getAllDaleks());
		
		// player 2 choose some daleks
		
		Game.getPlayer(1).selectFactoryDaleks(DalekFactory.getAllDaleks());
		

		
		// position daleks 
		
		Game.getPlayer(0).positionDaleks();
		Game.getPlayer(1).positionDaleks();
		
		// show all daleks
		
		// repeat

		do {
		// x = player loses initiative
			
			playerOrder = Game.initiative();
			
		// repeat
			do {
		// player x moves 1 dalek
				
				playerOrder.get(0).moveDalek();
				playerOrder.get(1).moveDalek();
				
		// player 3-x moves 1 dalek
		// until all daleks moved
			} while (!Game.allDaleksMoved());
			
			do {
		// repeat
				
				playerOrder.get(1).twistDalek();
				playerOrder.get(0).twistDalek();

		// player 3-x twist dalek
		// player x twist dalek
		// until all daleks twist
			} while (!Game.allDaleksTwist());
			
			playerOrder.get(0).setFiring(new  HashMap<Weapon,Dalek>());
			playerOrder.get(1).setFiring(new  HashMap<Weapon,Dalek>());

		// repeat
			do {
		// player x fires weapons of 1 dalek at another dalek
				
				playerOrder.get(0).getFiring().putAll(playerOrder.get(0).fireDalek(playerOrder.get(1).getDaleks()));				
				playerOrder.get(1).getFiring().putAll(playerOrder.get(1).fireDalek(playerOrder.get(0).getDaleks()));

		// player 3-x fires weapons of 1 dalek at another dalek
		// until all daleks fired.
		// repeat
		// dalek x hit dalek y then damage dalek y
		// until all daleks fired
			} while (!Game.allDaleksFired());
			
			Game.fire(playerOrder.get(0),playerOrder.get(1));
			Game.fire(playerOrder.get(1),playerOrder.get(0));
			
			playerOrder.get(0).allReset();
			playerOrder.get(1).allReset();
			
		// until all daleks on one team destroyed
		}	while (!Game.teamDestroyed());
		

		playerOrder.get(0).endGame();
		playerOrder.get(1).endGame();

		
	}
	
	public void fire(Player p1, Player p2) {
		
		HashMap<Weapon,Dalek> firing = p1.getFiring();
		
		Set<Weapon> w = firing.keySet();
		Iterator<Weapon> wit = w.iterator();
		while (wit.hasNext()) {
			
			Weapon weap = wit.next();
			// get cost to hit
			int cost = weap.costFire(firing.get(weap));
			
			// roll to hit
			int roll = this.twodsix();
			
			Dalek dal = firing.get(weap);
			
			// damage 
			if (roll >= cost) {
				//notify players
				int location = this.twodsix() -2 ;
				int damage = weap.getDamage(weap.getDalekSection().getDalek().distanceTo(firing.get(weap)));
				
				dal.damageLocation(location,damage);

				
				p1.getUI().notifyDalekDamage(dal,location,weap,damage);
				p2.getUI().notifyDamage(dal);
				
			} else {
				p1.getUI().notifyMiss(dal,weap);
				p2.getUI().notifyMiss(dal,weap);
			}
			
		}
	}
	
	
}
