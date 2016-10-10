package net.sf.bbarena.view;

import java.awt.*;
import net.sf.bbarena.model.*;

public abstract class Paintable {
   
    private boolean isVisible = true;

    public boolean getIsVisible() {
	return isVisible;
    }
    
    public void setIsVisible( boolean isVisible ) {
	this.isVisible = isVisible;
    }
    
    public void paint(Graphics2D g) {
	if ( isVisible ) {
	    paint( g, 0, 0 );
	}
    }
    
    public void paint(Graphics2D g, Coordinate c) {
	if ( isVisible ) {
	    paint( g, c.getX(), c.getY() );
	}
    }

    public abstract void paint(Graphics2D g, int x, int y);

    public abstract Dimension getSize();
    
    protected Composite setAlpha(Graphics2D g, float alpha) {
	Composite c = g.getComposite();
	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	return c;
    }
}
