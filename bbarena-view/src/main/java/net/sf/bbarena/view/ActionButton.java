package net.sf.bbarena.view;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public final class ActionButton extends AbstractButton {
    
    private static final long serialVersionUID = -2740692278746583891L;
    private static final float DEFAULT_ZOOM_FACTOR = 0.85f;

    private BufferedImage image;
    private float zoomFactor = DEFAULT_ZOOM_FACTOR;
    
    public ActionButton( BufferedImage image ) {
	this.image = image;
	setSize( new Dimension( image.getWidth(), image.getHeight() ) );
	setModel( new DefaultButtonModel() );
    }

    public ActionButton() {
	setSize( new Dimension( 30, 30 ) );
    }
    
    public void paint( Graphics g ) {
	Graphics2D g2 = ( Graphics2D ) g.create();
	g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
	paint( g2 );
	g2.dispose();
    }

    void paint( Graphics2D g ) {
	if ( zoomFactor != DEFAULT_ZOOM_FACTOR ) {
	    float delta = zoomFactor - DEFAULT_ZOOM_FACTOR;
	    g.translate( -getSize().width / 2 * delta, -getSize().height / 2 * delta );
	}
	g.scale( zoomFactor, zoomFactor );
	if ( image != null ) {
	    g.drawImage( image, 0, 0, this );
	} else {
	    g.setColor( Color.red );
	    g.fillRect( 0, 0, getSize().width, getSize().height );
	}
    }
        
    public void reset() {
	zoomFactor = DEFAULT_ZOOM_FACTOR;
    }
    
    public float getZoomFactor() {
	return zoomFactor;
    }

    public void setZoomFactor( float zoomFactor ) {
	this.zoomFactor = zoomFactor;
    }
}
