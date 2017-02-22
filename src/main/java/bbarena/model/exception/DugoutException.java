package bbarena.model.exception;

public class DugoutException extends RuntimeException {

	private static final long serialVersionUID = 331421843876752776L;

	public DugoutException() {
		super();
	}

	public DugoutException(String arg0) {
		super(arg0);
	}

	public DugoutException(Throwable arg0) {
		super(arg0);
	}

	public DugoutException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
