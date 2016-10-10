package net.sf.bbarena.view.exception;

public class AnimationException extends RuntimeException {
    
    private static final long serialVersionUID = 8376730052501801403L;

    public AnimationException() {
    	super();
    }
    
    public AnimationException(String arg0) {
    	super(arg0);
    }
    
    public AnimationException(Throwable arg0) {
    	super(arg0);
    }
    
    public AnimationException(String arg0, Throwable arg1) {
    	super(arg0, arg1);
    }
}
