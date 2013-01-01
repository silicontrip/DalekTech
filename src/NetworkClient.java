import java.io.*;
import java.net.*;
// import java.util.*;

import java.util.ArrayList;

import org.silicontrip.dalektech.ui.*;
import org.silicontrip.dalektech.map.*;
import org.silicontrip.dalektech.dalek.Dalek;
import org.silicontrip.dalektech.dalek.Weapon;
import org.silicontrip.dalektech.NoSuchInterfaceException;


public class NetworkClient {
	
	final static int PORT = 5555;
	
	
	
	public static void main(String[] args) {
		
		UserInterface intf = null;
		Socket comm;
		Map map = null;
		
		if (args.length < 2) { 
			System.out.println("Requires an interface and destination address.  Where interface is cli or computer"); 
		} else {
			
			
			
			try {
				
				comm = new Socket(args[1],PORT);
				ObjectInputStream ois = new ObjectInputStream(comm.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(comm.getOutputStream());
				
				try {
					map = (Map)ois.readObject();
				} catch (IOException ioe) {
					System.out.println("NetworkClient IO error " + ioe);
				} catch (ClassNotFoundException cnfe) {
					System.out.println("NetworkClient Class error " + cnfe);
				}
				
				
				
				
				intf = UserInterfaceFactory.interfaceFactory(args[0],map);
				
				
				if (intf != null) {
					
					while (true) {
						
						try {
							String message = (String) ois.readObject();
							
							 System.out.println("Message: " + message);
							
							if (message.equals("getDalekPosition")) {
								
								
								oos.writeObject(intf.getDalekPosition( (Dalek) ois.readObject()));
							}
							if (message.equals("getDalekDirection")) {
								oos.writeObject(intf.getDalekDirection((Dalek) ois.readObject()));
							}
							if (message.equals("getDalekPositionAndDirection")) {
								oos.writeObject(intf.getDalekPositionAndDirection((Dalek) ois.readObject()));
							}
							
							if (message.equals("getDalekMove")) {
								oos.writeObject(new Integer(intf.getDalekMove((Dalek)ois.readObject(), 
																			  (Integer) ois.readObject(),
																			  (Integer) ois.readObject(),
																			  (Integer) ois.readObject(),
																			  (Integer) ois.readObject(),
																			  (Integer) ois.readObject(),
																			  (Boolean) ois.readObject(),
																			  (Boolean) ois.readObject(),
																			  (Boolean) ois.readObject()
															)));
							}
							if (message.equals("getDalekTwist")) {
								oos.writeObject(intf.getDalekTwist((Dalek)ois.readObject()));
							}
							if (message.equals("selectFactoryDaleks")) {
								oos.writeObject(intf.selectFactoryDaleks( (ArrayList<Dalek>) ois.readObject()));
							}
							if (message.equals("selectTargetDalek")) {
								oos.writeObject(new Integer(intf.selectTargetDalek((Dalek) ois.readObject(),
																				   (ArrayList<Dalek>) ois.readObject(),
																				   (ArrayList<Integer>) ois.readObject(),
																				   (ArrayList<Double>) ois.readObject(),
																				   (ArrayList<ArrayList<Hex>>) ois.readObject()
																				   )));
								
							}
							if (message.equals("selectWeapon")) {
								oos.writeObject(new Integer(intf.selectWeapon((ArrayList<Weapon>) ois.readObject())));
							}
							if (message.equals("selectDalek")) {
								oos.writeObject(new Integer(intf.selectDalek((ArrayList<Dalek>) ois.readObject())));
							}
							if (message.equals("selectDalekWithExit")) {
								oos.writeObject(new Integer(intf.selectDalekWithExit((ArrayList<Dalek>) ois.readObject())));
							}
							
							
							if (message.equals("notifyDalekPosition")) { 
								intf.notifyDalekPosition((Dalek) ois.readObject()); 
							}
							if (message.equals("notifyName")) { intf.notifyName((Dalek) ois.readObject()); }
							if (message.equals("notifyDamage")) { intf.notifyDamage((Dalek) ois.readObject()); }
							if (message.equals("notifyTargetDalek")) { intf.notifyTargetDalek((Dalek) ois.readObject()); }
							
							
							if (message.equals("notifyEngine")) {
								intf.notifyEngine((Integer) ois.readObject(),
												  (Integer) ois.readObject(),
												  (Integer) ois.readObject());
							}
							
							if (message.equals("notifyDifficulty")) { intf.notifyDifficulty((Integer) ois.readObject()); }
							if (message.equals("notifyLOS")) { intf.notifyLOS((ArrayList<Hex>) ois.readObject()); }
							
							if (message.equals("notifyDalekDamage")) {
								intf.notifyDalekDamage((Dalek) ois.readObject(),
													   (Integer) ois.readObject(),
													   (Weapon) ois.readObject(),
													   (Integer) ois.readObject()
								);
							}
							
							if (message.equals("notifyMiss")) {
								intf.notifyMiss((Dalek) ois.readObject(),
												(Weapon) ois.readObject());
							}
							if (message.equals("notifyFire")) {
								intf.notifyFire((Dalek) ois.readObject(),
												(Weapon) ois.readObject());
							}
							
							if (message.equals("notifyEnd")) { intf.notifyEnd((Boolean) ois.readObject()); }
							
							if (message.equals("setInterfaceMessage")) { intf.setInterfaceMessage((String) ois.readObject()); }
							
							
						} catch (ClassNotFoundException cnfe) {
							System.out.println ("Class not found (network protocol error) occured: " + cnfe);
						}
						
					}
				} else {
					System.out.println("An invalid interface was chosen");
				}
				
			} catch (NoSuchInterfaceException e) {
				System.out.println(e.getMessage());
			} catch (FileNotFoundException e) {
				System.out.println("Error initialising Interface: " + e.getMessage());
			} 			catch (IOException ioe) {
				System.out.println ("main IOException occured: " + ioe);
			} 
			
		}
		
	}
	
}
