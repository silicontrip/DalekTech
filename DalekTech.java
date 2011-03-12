import java.util.*;


public class DalekTech {

	private static DalekTech instance = null;

	Random rand;
	Map map;
	
	Player players[];
	
	
	public static DalekTech getInstance() {
		if (instance == null) {
			instance = new DalekTech();
		}
		return instance;
	}
	
	public DalekTech () {
		
		// Although it's only made here for 2 players, it shouldn't be too hard to extend to more.
		players = new Player[2];
		
		rand = new java.util.Random(java.lang.System.currentTimeMillis());

		map = new Map(15,17);
		map.initSampleMap();
		
		players[0] = new Player(new Cli(map),this); // UI2DGraphic();
		players[1] = new Player(new Cli(map),this); // ultimately UI3dGraphic();
	}

	Map getMap() { return this.map; }
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

		DalekTech Game = DalekTech.getInstance();
		ArrayList<Player> playerOrder;	
		
		// init map
		
		// player 1 choose some daleks
		
		Game.getPlayer(0).selectFactoryDaleks(DalekFactory.getAllDaleks());
		
		// player 2 choose some daleks
		
		Game.getPlayer(1).selectFactoryDaleks(DalekFactory.getAllDaleks());
		

		
		// position daleks 
		
		Game.getPlayer(0).positionDaleks();
		Game.getPlayer(1).positionDaleks();
		
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
			
			HashMap<Weapon,Dalek> firing = new  HashMap<Weapon,Dalek>();
			
		// repeat
			do {
		// player x fires weapons of 1 dalek at another dalek
				
				firing.putAll(playerOrder.get(0).fireDalek(playerOrder.get(1).getDaleks()));				
				firing.putAll(playerOrder.get(1).fireDalek(playerOrder.get(0).getDaleks()));

		// player 3-x fires weapons of 1 dalek at another dalek
		// until all daleks fired.
		// repeat
		// dalek x hit dalek y then damage dalek y
		// until all daleks fired
			} while (!Game.allDaleksFired());
			
			Set<Weapon> w = firing.keySet();
			Iterator<Weapon> wit = w.iterator();
			while (wit.hasNext()) {
			
				Weapon weap = wit.next();
				// get cost to hit
				int cost = weap.costFire(firing.get(weap));
				
				// roll to hit
				int roll = Game.twodsix();
				
				// damage 
				if (roll >= cost) {
					//notify players
					int location = Game.twodsix() -2 ;
					int damage = weap.getDamage(weap.getDalekSection().getDalek().distanceTo(firing.get(weap)));
					Dalek dal = firing.get(weap);
					
					playerOrder.get(0).getUI().notifyDalekDamage(dal,location,damage);
					playerOrder.get(1).getUI().notifyDalekDamage(dal,location,damage);
					
					dal.damageLocation(location,damage);
				}
				
			}
			
		// until all daleks on one team destroyed
		}	while (!Game.teamDestroyed());
		
		// show and choose factory daleks (ArrayList<Dalek>)
		// position daleks (randomly)
		// move dalek { select my_dalek; loop { turn/move/end  report_status }}
		// twist dalek { select my_dalek; turn/end }
		// fire weapon { select my_dalek; loop { select weapon; loop { select dalek; display difficulty; choose/cancel } fire/cancel } end }
		// report dalek status
		
	}
	
}