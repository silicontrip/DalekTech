package org.silicontrip.dalektech.ui;
import java.util.ArrayList;


public abstract class UserInterface {

	
	// Ask user for information
	abstract Position getDalekPosition(Dalek d);
	abstract Direction getDalekDirection (Dalek d);	
	abstract Position getDalekPositionAndDirection(Dalek d);
	abstract int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn)  ;

	abstract int getDalekTwist (Dalek d);
	
	// Show and ask user selections
	abstract ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList);
	abstract int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList, ArrayList<Integer> targetCost,ArrayList<Double> distance, ArrayList<ArrayList<Hex>> los);
	abstract int selectWeapon(ArrayList<Weapon> w);
	abstract int selectDalek(ArrayList<Dalek> dalekList);
	abstract int selectDalekWithExit(ArrayList<Dalek> dalekList);

	// it's a toss up between geting the calling class to do the work
	// or getting this class to do the work.
	
	// status 
	abstract void setInterfaceMessage(String s);
	abstract void notifyDalekPosition(Dalek d);
	abstract void notifyName(Dalek d);
	abstract void notifyEngine(int current, int walk, int run);
	abstract void notifyDamage(Dalek d);
	abstract void notifyDifficulty(int cost);
	abstract void notifyLOS(ArrayList<Hex> line);
	abstract void notifyTargetDalek(Dalek d);
	abstract void notifyDalekDamage(Dalek d,int location, Weapon w, int damage);
	abstract void notifyFire(Dalek d, Weapon w);
	abstract void notifyMiss(Dalek d, Weapon w);
	abstract void notifyEnd(boolean destroyed);
}