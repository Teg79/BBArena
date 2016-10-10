package net.sf.bbarena.view;

import java.awt.*;
import net.sf.bbarena.model.*;

public abstract class Draggable extends Paintable {

    private boolean isDragged;
    
    private Cursor cursor;
    
    public Cursor setDragged( boolean isDragged, Cursor cursor ) {
	this.isDragged = isDragged;
	Cursor old = this.cursor;
	this.cursor = cursor;
	if(isDragged) {
	    return null;
	} else {
	    return old;
	}
    }

    public boolean getIsDragged() {
	return isDragged;
    }
    
    public void paintDragged(Graphics2D g) {
	paintDragged( g, 0, 0 );
    }
    
    public void paintDragged(Graphics2D g, Coordinate c) {
	paintDragged( g, c.getX(), c.getY() );
    }    

    public abstract void paintDragged(Graphics2D g, int x, int y);    
}
