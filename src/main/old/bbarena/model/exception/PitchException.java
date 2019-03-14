package bbarena.model.exception;

public class PitchException extends RuntimeException {

	private static final long serialVersionUID = 3679179606657289120L;

	public PitchException() {
		super();
	}

	public PitchException(String message) {
		super(message);
	}

	public PitchException(Throwable cause) {
		super(cause);
	}

	public PitchException(String message, Throwable cause) {
		super(message, cause);
	}

}
