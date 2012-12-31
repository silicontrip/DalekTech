package org.silicontrip.dalektech.ui;
import java.util.ArrayList;

import org.silicontrip.dalektech.dalek.Dalek;
import org.silicontrip.dalektech.dalek.Weapon;
import org.silicontrip.dalektech.map.Position;
import org.silicontrip.dalektech.map.Direction;
import org.silicontrip.dalektech.map.Hex;


public abstract class UserInterface {

	
	// Ask user for information
	abstract public Position getDalekPosition(Dalek d);
	abstract public Direction getDalekDirection (Dalek d);	
	abstract public Position getDalekPositionAndDirection(Dalek d);
	abstract public int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn)  ;

	abstract public int getDalekTwist (Dalek d);
	
	// Show and ask user selections
	abstract public ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList);
	abstract public int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList, ArrayList<Integer> targetCost,ArrayList<Double> distance, ArrayList<ArrayList<Hex>> los);
	abstract public int selectWeapon(ArrayList<Weapon> w);
	abstract public int selectDalek(ArrayList<Dalek> dalekList);
	abstract public int selectDalekWithExit(ArrayList<Dalek> dalekList);

	// it's a toss up between geting the calling class to do the work
	// or getting this class to do the work.
	
	// status 
	abstract public void setInterfaceMessage(String s);
	abstract public void notifyDalekPosition(Dalek d);
	abstract public void notifyName(Dalek d);
	abstract public void notifyEngine(int current, int walk, int run);
	abstract public void notifyDamage(Dalek d);
	abstract public void notifyDifficulty(int cost);
	abstract public void notifyLOS(ArrayList<Hex> line);
	abstract public void notifyTargetDalek(Dalek d);
	abstract public void notifyDalekDamage(Dalek d,int location, Weapon w, int damage);
	abstract public void notifyFire(Dalek d, Weapon w);
	abstract public void notifyMiss(Dalek d, Weapon w);
	abstract public void notifyEnd(boolean destroyed);
}