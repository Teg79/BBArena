package net.sf.bbarena.model.exception;

public class EventException extends RuntimeException {

	private static final long serialVersionUID = -423032035887941614L;

	public EventException() {
		super();
	}

	public EventException(String arg0) {
		super(arg0);
	}

	public EventException(Throwable arg0) {
		super(arg0);
	}

	public EventException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
