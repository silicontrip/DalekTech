import java.util.*;


public class DalekTech {

	
	public DalekTech () {
		
	}

	// int twodsix () {
	//	return rand.nextInt(6) + rand.nextInt(6) + 2;
	// }
	
	

	public static void main(String[] args) {

		Random rand;
		Map map;

		Player players[] = new Player[2];
		
		rand = new java.util.Random(java.lang.System.currentTimeMillis());

		players[0] = new Player(new Cli()); // UI2DGraphic();
		players[1] = new Player(new Cli()); // ultimately UI3dGraphic();
		
		// init map
		
		map = new Map(15,17);
		map.initSampleMap();
		
		// player 1 choose some daleks
		
		players[0].selectFactoryDaleks(DalekFactory.getAllDaleks());
		
		// player 2 choose some daleks
		
		players[1].selectFactoryDaleks(DalekFactory.getAllDaleks());
		
		// position daleks 
		
		players[0].positionDaleks();
		
		// repeat
		// x = player loses initiative
		// repeat
		// player x moves 1 dalek
		// player 3-x moves 1 dalek
		// until all daleks moved
		// repeat
		// player 3-x twist dalek
		// player x twist dalek
		// until all daleks twist
		// repeat
		// player x fires weapons of 1 dalek at another dalek
		// player 3-x fires weapons of 1 dalek at another dalek
		// until all daleks fired.
		// repeat
		// dalek x hit dalek y then damage dalek y
		// until all daleks fired
		// until all daleks on one team destroyed
		
		
		// show and choose factory daleks (ArrayList<Dalek>)
		// position daleks (randomly)
		// move dalek { select my_dalek; loop { turn/move/end  report_status }}
		// twist dalek { select my_dalek; turn/end }
		// fire weapon { select my_dalek; loop { select weapon; loop { select dalek; display difficulty; choose/cancel } fire/cancel } end }
		// report dalek status
		
	}
	
}