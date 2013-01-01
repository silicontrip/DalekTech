package org.silicontrip.dalektech;
import java.lang.Exception;

public class NoSuchInterfaceException extends Exception {

	public NoSuchInterfaceException(String message) { super(message); }
	public NoSuchInterfaceException(){ super(); }
	public NoSuchInterfaceException(String message, Throwable cause) { super(message,cause); }
	public NoSuchInterfaceException(Throwable cause) { super(cause); }

}