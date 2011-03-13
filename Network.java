import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;


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
	Position getDalekPosition(Dalek d) {
		
		try {
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
	
	
	int getDalekDirection (Dalek d) {
		try {
			oos.writeObject("getDalekDirection");
			oos.writeObject(d);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("getDalekDirection IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
	}
	int getDalekMove (Dalek d, int currentMove, int walk, int run, int forwardCost, int backwardCost,boolean forward, boolean backward, boolean turn)  {
		
		try {
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
	
	int getDalekTwist (Dalek d){
		
		try {
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
	
	// Show and ask user selections
	ArrayList<Integer> selectFactoryDaleks (ArrayList<Dalek> dalekList) {
		try {
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
	
	
	int selectTargetDalek (Dalek d, ArrayList<Dalek> targetList,ArrayList<Integer> targetCost) {
		try {
			oos.writeObject("selectTargetDalek");
			oos.writeObject(d);
			oos.writeObject(targetList);
			oos.writeObject(targetCost);
			return (Integer)ois.readObject();
		} catch (IOException ioe) {
			System.out.println("selectTargetDalek IO error " + ioe);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ac Class error " + cnfe);
		}
		
		return -1;
		
		
	}
	int selectWeapon(ArrayList<Weapon> w) {
		try {
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
	
	int selectDalek(ArrayList<Dalek> dalekList) {
		try {
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
	
	int selectDalekWithExit(ArrayList<Dalek> dalekList) {
		try {
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
	
	void notifyDalek (String s, Dalek d) {
		try {
			oos.writeObject(s);
			oos.writeObject(d);
		} catch (IOException ioe) {
			System.out.println(s + " IO error " + ioe);
		} 		
	}
	
	// status 
	void notifyDalekPosition(Dalek d) { notifyDalek("notifyDalekPosition",d); }
	void notifyName(Dalek d) { notifyDalek("notifyName",d); }
	void notifyDamage(Dalek d) { notifyDalek("notifyDamage",d); }
	void notifyTargetDalek(Dalek d) { notifyDalek("notifyTargetDalek",d); }
	
	
	void notifyEngine(int current, int walk, int run) {
		try {
			oos.writeObject("notifyEngine");
			oos.writeObject(new Integer(current));
			oos.writeObject(new Integer(walk));
			oos.writeObject(new Integer(run));
			
		} catch (IOException ioe) {
			System.out.println("notifyEngine IO error " + ioe);
		}
		
		
	}
	void notifyDifficulty(int cost) {
		try {
			oos.writeObject("notifyDifficulty");
			oos.writeObject((Integer)cost);			
		} catch (IOException ioe) {
			System.out.println("notifyDifficulty IO error " + ioe);
		}
		
	}
	void notifyLOS(ArrayList<Hex> line) {
		try {
			oos.writeObject("notifyLOS");
			oos.writeObject(line);			
		} catch (IOException ioe) {
			System.out.println("notifyLOS IO error " + ioe);
		}
	}
	void notifyDalekDamage(Dalek d,int location, Weapon w, int damage) {
		try {
			oos.writeObject("notifyDalekDamage");
			oos.writeObject(d);	
			oos.writeObject(new Integer(location));			
			oos.writeObject(w);			
			oos.writeObject(new Integer(damage));			
			
		} catch (IOException ioe) {
			System.out.println("notifyDalekDamage IO error " + ioe);
		}
	}
	void notifyMiss(Dalek d, Weapon w) {
		try {
			oos.writeObject("notifyMiss");
			oos.writeObject(d);	
			oos.writeObject(w);			
			
		} catch (IOException ioe) {
			System.out.println("notifyMiss IO error " + ioe);
		}
		
	}
	void notifyEnd(boolean destroyed) {
		try {
			oos.writeObject("notifyEnd");
			oos.writeObject(new Boolean(destroyed));							 
		} catch (IOException ioe) {
			System.out.println("notifyEnd IO error " + ioe);
		}
		
	}
}