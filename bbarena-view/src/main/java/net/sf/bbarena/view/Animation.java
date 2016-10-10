package net.sf.bbarena.view;

import java.awt.Component;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Animation extends PluggableComponent implements Runnable {
        
    private static final Logger log = LoggerFactory.getLogger("animation");

    protected static final long DEFAULT_DELAY = 1000 / 30; // ~30 frames per second
    
    private long delay;

    private Set<AnimationObserver> observers;
    
    enum State { IDLE, RUNNING, PAUSED, STOPPED };
    
    private State state = State.IDLE;
    
    protected Animation(Component owner) {
	super(owner);
	observers = new HashSet<AnimationObserver>();
	setDelay(DEFAULT_DELAY);
    }       
    
    public void start() {
	synchronized(this) {
	    if (state == State.IDLE) {
		log.debug(this + ": animation started");
		state = State.RUNNING;
		new Thread(this).start();
	    }
	}
    }

    public void stop() {
	synchronized(this) {
	    if (state == State.RUNNING) {
		log.debug(this + ": animation stopped");
		state = State.STOPPED;
		notify();
	    }
	}
    }

    public void pause() {
	synchronized(this) {
	    if (state == State.RUNNING) {
		log.debug(this + ": animation paused");
		state = State.PAUSED;
		notify();
	    }
	}
    }

    public void resume() {
	synchronized(this) {
	    if (state == State.PAUSED) {
		log.debug(this + ": animation resumed");
		state = State.RUNNING;
		notify();
	    }
	}
    }
    
    public void addObserver(AnimationObserver observer) {
	synchronized (this) {
	    observers.add(observer);
	}
    }
    
    protected void setDelay(long delay) {
	this.delay = delay;
    }
    
    public void paint(Graphics g) {
	synchronized (this) {
	    paintAnimation(g);
	}
    }

    abstract protected void update(); 
    
    abstract protected void paintAnimation(Graphics g); 
    
    public void run() {
	synchronized(this) {
	    try {
		while (state == State.RUNNING) {
		    do {
			wait(delay);
		    } while (state == State.PAUSED);
		    if (state != State.RUNNING) {			
			break;
		    }
		    update();
		    repaint();
		}
	    } catch (InterruptedException e) {
		log.error(e.toString());
	    }
	}	
        for (AnimationObserver observer : observers) {
    	log.debug("calling animationFinished()");
    	observer.animationFinished();
        }
    }

}
