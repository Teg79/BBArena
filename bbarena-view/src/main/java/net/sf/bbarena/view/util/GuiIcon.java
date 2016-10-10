package net.sf.bbarena.view.util;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import net.sf.bbarena.view.Paintable;

public class GuiIcon extends Paintable {
    
    private BufferedImage image;
    
    private Dimension size;
    
    private boolean enableZoom;
    
    private double zoom;
    
    public GuiIcon( BufferedImage image ) {
	this.image = image;
	size = new Dimension( image.getWidth(), image.getHeight() );
    }
    
    public BufferedImage getImage() {
	return image;
    }
    
    public Graphics getGraphics() {
	return image.getGraphics();
    }
    
    public void setEnableZoom(boolean enableZoom) {
	this.enableZoom = enableZoom;
    }
    
    public void setZoom(double zoom) {
	this.zoom = zoom;
    }

    public double getZoom() {
	return zoom;
    }
    
    @Override
    public Dimension getSize() {
	return size;
    }

    @Override
    public void paint( Graphics2D g, int x, int y ) {
	if (enableZoom) {
	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
	    double dx = ( image.getWidth() - zoom * image.getWidth() ) / 2;
	    double dy = ( image.getHeight() - zoom * image.getHeight() ) / 2;
	    g2.translate(x + dx, y + dy);
	    g2.scale(zoom, zoom);
	    g2.drawImage(image, 0, 0, null);
	    g2.dispose();
	} else {
	    g.drawImage(image, x, y, null);
	}
    }
}
