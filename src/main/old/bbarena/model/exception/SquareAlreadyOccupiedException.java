package bbarena.model.exception;

public class SquareAlreadyOccupiedException extends RuntimeException {

	private static final long serialVersionUID = 4974875412417420841L;

	public SquareAlreadyOccupiedException() {
		super();
	}

	public SquareAlreadyOccupiedException(String arg0) {
		super(arg0);
	}

	public SquareAlreadyOccupiedException(Throwable arg0) {
		super(arg0);
	}

	public SquareAlreadyOccupiedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
