package org.silicontrip.dalektech.ui;
import org.silicontrip.dalektech.map.Map;
import org.silicontrip.dalektech.NoSuchInterfaceException;

import java.io.FileNotFoundException;

public class UserInterfaceFactory {

	
	public UserInterfaceFactory () { ; }

	public static UserInterface interfaceFactory (String s,Map m) throws NoSuchInterfaceException, FileNotFoundException {
		
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
		
		throw (new NoSuchInterfaceException("Unknown Interface: " + s));
	}
} 