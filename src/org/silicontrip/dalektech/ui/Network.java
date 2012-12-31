package org.silicontrip.dalektech.ui;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.util.*;
import java.net.*;

import java.util.ArrayList;

import org.silicontrip.dalektech.dalek.Dalek;
import org.silicontrip.dalektech.dalek.Weapon;

import org.silicontrip.dalektech.map.*;


public  class Network extends UserInterface {
	
	final int PORT = 5555;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	ServerSocket listen;
	Socket comm;
	
	
	public Network(Map m) {
		
		try {
			
			// System.out.println("Listen");
			
			
			listen = new ServerSocket(PORT);
			
			System.out.println("Listening on: " + listen.toString());
			// this will block :-(
			
			//System.out.println("accept");
			
			
			comm = listen.accept();
			
			// System.out.println("new Object Output stream");
			oos = new ObjectOutputStream(comm.getOutputStream());
			
			if (oos == null) {
				System.out.println("OOS is null");
			}
			
			
			// 	System.out.println("new Object Input stream");
			ois = new ObjectInputStream(comm.getInputStream());
			
			if (ois == null) {
				System.out.println("OIS is null");
			}
			
			try {
				oos.writeObject(m);
			} catch (IOException ioe) {
				System.out.println("Network IO error " + ioe);
			}
			
			
		} catch (IOException ioe) {
			System.out.println("network IO error " + ioe);
		}
	}
	
	// Ask user for information
	public Position getDalekPosition(Dalek d) {
		
		try {
			oos.reset();
			oos.writeObject("getDalekPosition");
			oos.writeObject(d);
			return (Position)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("getDalekPosition IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return null;
	} 
	
	
public 	Direction getDalekDirection (Dalek d) {
		try {
			oos.reset();

			oos.writeObject("getDalekDirection");
			oos.writeObject(d);
			return (Direction)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("getDalekDirection IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return null;
		
	}
public 	int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn)  {
		
		try {
			oos.reset();

			oos.writeObject("getDalekMove");
			oos.writeObject(d);
			oos.writeObject(new Integer(currentMove));
			oos.writeObject(new Integer(walk));
			oos.writeObject(new Integer(run));
			oos.writeObject(new Integer(forwardCost));
			oos.writeObject(new Integer(backwardCost));
			oos.writeObject(new Boolean(forward));
			oos.writeObject(new Boolean(backward));
			oos.writeObject(new Boolean(turn));
			
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("getDalekMove IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		
		return -1;
		
	}
	
public 	int getDalekTwist (Dalek d){
		
		try {
			oos.reset();

			oos.writeObject("getDalekTwist");
			oos.writeObject(d);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("getDalekTwist IO error " + ioe);
		}  catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
		
	}
public 	
	Position getDalekPositionAndDirection(Dalek d) {
		
		try {
			oos.reset();

			oos.writeObject("getDalekPositionAndDirection");
			oos.writeObject(d);
			return (Position)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("getDalekTwist IO error " + ioe);
		}  catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		return null;
	}
	
	// Show and ask user selections
public 	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		try {
			oos.reset();

			oos.writeObject("selectFactoryDaleks");
			oos.writeObject(dalekList);
			return (ArrayList<Integer>)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("selectFactoryDaleks IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return null;
		
	}
	
	
public 	int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList, ArrayList<Integer> targetCost,ArrayList<Double> distance, ArrayList<ArrayList<Hex>> los) {
		try {
			oos.reset();

			oos.writeObject("selectTargetDalek");
			oos.writeObject(d);
			oos.writeObject(targetList);
			oos.writeObject(targetCost);
			oos.writeObject(distance);
			oos.writeObject(los);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("selectTargetDalek IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
		
	}
public 	int selectWeapon(ArrayList<Weapon> w) {
		try {
			oos.reset();

			oos.writeObject("selectWeapon");
			oos.writeObject(w);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("selectWeapon IO error " + ioe);
		}  catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
	}
	
public 	int selectDalek(ArrayList<Dalek> dalekList) {
		try {
			oos.reset();

			oos.writeObject("selectDalek");
			oos.writeObject(dalekList);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("selectDalek IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
	}
	
public 	int selectDalekWithExit(ArrayList<Dalek> dalekList) {
		try {
			oos.reset();

			oos.writeObject("selectDalekWithExit");
			oos.writeObject(dalekList);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("selectDalekWithExit IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
	}
	
	
	// it's a toss up between geting the calling class to do the work
	// or getting this class to do the work.
	
public 	void notifyDalek (String s, Dalek d) {
		try {
			oos.reset();

			// oos.writeObject("notifyDalek");
			oos.writeObject(s);
			oos.writeObject(d);
		} catch (IOException ioe) {
			System.out.println(s + " IO error " + ioe);
		} 		
	}
	
	// status 
	public void notifyDalekPosition(Dalek d) { notifyDalek("notifyDalekPosition",d); }
	public void notifyName(Dalek d) { notifyDalek("notifyName",d); }
	public void notifyDamage(Dalek d) { notifyDalek("notifyDamage",d); }
	public void notifyTargetDalek(Dalek d) { notifyDalek("notifyTargetDalek",d); }
	
	
	public void notifyEngine(int current, int walk, int run) {
		try {
			oos.reset();

			oos.writeObject("notifyEngine");
			oos.writeObject(new Integer(current));
			oos.writeObject(new Integer(walk));
			oos.writeObject(new Integer(run));
			
		} catch (IOException ioe) {
			System.out.println("notifyEngine IO error " + ioe);
		}
		
		
	}
	public void notifyDifficulty(int cost) {
		try {
			oos.reset();

			oos.writeObject("notifyDifficulty");
			oos.writeObject((Integer)cost);			
		} catch (IOException ioe) {
			System.out.println("notifyDifficulty IO error " + ioe);
		}
		
	}
	public void notifyLOS(ArrayList<Hex> line) {
		try {
			oos.reset();

			oos.writeObject("notifyLOS");
			oos.writeObject(line);			
		} catch (IOException ioe) {
			System.out.println("notifyLOS IO error " + ioe);
		}
	}
	public void notifyDalekDamage(Dalek d,int location, Weapon w, int damage) {
		try {
			oos.reset();

			oos.writeObject("notifyDalekDamage");
			oos.writeObject(d);	
			oos.writeObject(new Integer(location));			
			oos.writeObject(w);			
			oos.writeObject(new Integer(damage));			
			
		} catch (IOException ioe) {
			System.out.println("notifyDalekDamage IO error " + ioe);
		}
	}
	public void notifyFire(Dalek d, Weapon w) {
		try {
			oos.reset();
			
			oos.writeObject("notifyFire");
			oos.writeObject(d);	
			oos.writeObject(w);			
			
		} catch (IOException ioe) {
			System.out.println("notifyMiss IO error " + ioe);
		}
		
	}
	
	public void notifyMiss(Dalek d, Weapon w) {
		try {
			oos.reset();

			oos.writeObject("notifyMiss");
			oos.writeObject(d);	
			oos.writeObject(w);			
			
		} catch (IOException ioe) {
			System.out.println("notifyMiss IO error " + ioe);
		}
		
	}
	public void notifyEnd(boolean destroyed) {
		try {		
			oos.reset();

			oos.writeObject("notifyEnd");
			oos.writeObject(new Boolean(destroyed));							 
		} catch (IOException ioe) {
			System.out.println("notifyEnd IO error " + ioe);
		}
		
	}
	public void setInterfaceMessage(String s) {
		try {
			oos.reset();

			oos.writeObject("setInterfaceMessage");
			oos.writeObject(s);							 
		} catch (IOException ioe) {
			System.out.println("notifyEnd IO error " + ioe);
		}
		
	}
}
