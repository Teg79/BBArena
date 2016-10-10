package net.sf.bbarena.view;

import java.awt.*;

public abstract class PluggableComponent implements PaintableComponent {
    
    protected Component owner;
    
    protected boolean isVisible;

    protected PluggableComponent( Component owner ) {
	this.owner = owner;
    }
    
    public boolean isVisible() {
	return isVisible;
    }

    public void setVisible( boolean isVisible ) {
	boolean needRepaint = this.isVisible != isVisible; 
	this.isVisible = isVisible;
	if ( needRepaint ) {
	    owner.repaint();
	}
    }
    
    public void repaint() {
	owner.repaint();
    }
    
}
