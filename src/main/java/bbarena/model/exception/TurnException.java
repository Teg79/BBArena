package bbarena.model.exception;

public class TurnException extends RuntimeException {
	
	private static final long serialVersionUID = 3616380707293641437L;

	public TurnException() {
		super();
	}

	public TurnException(String arg0) {
		super(arg0);
	}

	public TurnException(Throwable arg0) {
		super(arg0);
	}

	public TurnException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
