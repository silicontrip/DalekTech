import java.util.ArrayList;

public abstract class UserInterface {

	abstract ArrayList<Dalek> selectFactoryDaleks (ArrayList<Dalek> dalekList);
	abstract void moveDalek (ArrayList<Dalek> dalekList);
	abstract void twistDalek (ArrayList<Dalek> dalekList);
	abstract Dalek fireDalek (ArrayList<Dalek> dalekList, ArrayList<Dalek> targetDaleks);
	abstract void dalekStatus (Dalek d);
	abstract Dalek selectDalek(ArrayList<Dalek> dalekList);
	abstract Position positionDalek(Dalek d);
	
	// status 
	abstract void notifyEngine(int current, int walk, int run);
	abstract void notifyDifficulty(int cost);
	abstract void notifyLOS(ArrayList<Hex> line);
	

}