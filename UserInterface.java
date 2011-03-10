import java.util.ArrayList;

public abstract class UserInterface {

	abstract ArrayList<Dalek> selectFactoryDaleks (ArrayList<Dalek> dalekList);
	abstract int moveDalek (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost, int turnCost);
	abstract int twistDalek (Dalek d);
	abstract Dalek fireDalek (Dalek d, ArrayList<Dalek> targetDaleks);
	abstract void dalekStatus (Dalek d);
	
	abstract Dalek selectDalek(ArrayList<Dalek> dalekList);
	abstract Position positionDalek(Dalek d);
	abstract int directionDalek (Dalek d);

	
	// status 
	abstract void notifyEngine(int current, int walk, int run);
	abstract void notifyDifficulty(int cost);
	abstract void notifyLOS(ArrayList<Hex> line);
	

}