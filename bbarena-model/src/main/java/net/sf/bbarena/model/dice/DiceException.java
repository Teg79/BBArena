package net.sf.bbarena.model.dice;


/**
 * Exception for dice related errors
 * 
 * @author f.bellentani
 */
public class DiceException extends Exception {

	private static final long serialVersionUID = 8282894689557943348L;

	public DiceException() {
		super();
	}

	public DiceException(String msg) {
		super(msg);
	}

	public DiceException(Throwable cause) {
		super(cause);
	}

	public DiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
