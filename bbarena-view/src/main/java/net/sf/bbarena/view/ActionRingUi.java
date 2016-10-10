package net.sf.bbarena.view;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public abstract class ActionRingUi extends PluggableComponent
{
    private java.util.List< ActionButton > buttons;
    private Shape[] shapes;
    private Point2D[] positions;
    private ActionButton mouseOn;
    private Map< ActionButton, Zoomer > zoomers;
    private int x;
    private int y;
    private Dimension size;
    private Fader fader;
    
    private enum Fade 
    {
	FadeIn( 0.05f, 1.0f ) { boolean isInTarget( float alpha ) { return alpha >= FadeIn.target; } }, 
	FadeOut( -0.05f, 0.0f ) { boolean isInTarget( float alpha ) { return alpha <= FadeOut.target; } };
	private float delta;
	private float target;
	Fade( float delta, float target ) {
	    this.delta = delta;
	    this.target = target;	    
	}
	public float getDelta() {
	    return delta;
	}	
	abstract boolean isInTarget( float alpha );
    }
    
    private enum Zoom
    {
	ZoomIn( 0.06f, 1.20f ) { boolean isInTarget( float alpha ) { return alpha >= ZoomIn.target; } }, 
	ZoomOut( -0.06f, 0.85f ) { boolean isInTarget( float alpha ) { return alpha <= ZoomOut.target; } };
	private float delta;
	private float target;
	Zoom( float delta, float target ) {
	    this.delta = delta;
	    this.target = target;	    
	}
	public float getDelta() {
	    return delta;
	}	
	public float getTarget() {
	    return target;
	}	
	abstract boolean isInTarget( float alpha );
    }
    
    class Zoomer implements Runnable
    {
	private boolean cancelled = false;
	private boolean finished = false;
	private Zoom zoom;
	private float zoomFactor;
	private ActionButton target;

	Zoomer( Zoom zoom, ActionButton target ) {
	    if ( zoomers.containsKey( target ) ) {
		zoomers.get( target ).cancel();
	    }
	    this.zoom = zoom;
	    this.target = target;
	    this.zoomFactor = target.getZoomFactor();
	    new Thread( this ).start();
	    synchronized ( zoomers ) {
		zoomers.put( target, this );
	    }
	}

	public void run() {
	    try {
		synchronized ( this ) {
		    while ( !( cancelled || zoom.isInTarget( zoomFactor ) ) ) {
			wait( 1000 / 30 );
			zoomFactor += zoom.getDelta();
			target.setZoomFactor( zoomFactor );
			owner.repaint();
		    }
		if ( cancelled ) {
		    target.setZoomFactor( zoom.getTarget() );
		}
		notify();
		finished = true;
		}
	    } catch ( InterruptedException e ) {
		e.printStackTrace();
	    }
	    synchronized ( zoomers ) {
		zoomers.remove( target );
	    }
	}
	
	public void cancel() {
	    synchronized ( this ) {
		if ( !finished && !cancelled ) {
		    cancelled = true;
                    notify();
                    try {
                        wait();
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
		}
	    }
	}
    }
    
    class Fader implements Runnable
    {
	private float alpha;
	private Fade fade;
	private boolean visible;
	private boolean cancelled = false;
	
	Fader( Fade fade, float alpha, boolean visible ) {
	    this.fade = fade;
	    this.alpha = alpha;
	    this.visible = visible;
	    new Thread( this ).start();
	}
	
	Composite getAlphaComposite() {
	    return AlphaComposite.getInstance( AlphaComposite.SRC_OVER, Math.min( 1.0f, Math.max( alpha, 0.0f ) ) );
	}
	
	public void run() {
	    try {
		synchronized ( this ) {
		    while ( !( cancelled || fade.isInTarget( alpha ) ) ) {
			wait( 1000 / 30 );
			alpha += fade.getDelta();
			owner.repaint();
		    }
		setVisible( visible );
		fader = null;
		notify();
		}
	    } catch ( InterruptedException e ) {
		e.printStackTrace();
	    }
	}
	
	public float cancel() {
	    synchronized ( this ) {
		cancelled = true;
		notify();
		try {
		    wait();
		} catch ( InterruptedException e ) {
		    e.printStackTrace();
		}
	    }
	return Math.min( 1.0f, Math.max( alpha, 0.0f ) );
	}
    }   
    
    protected ActionRingUi( Component owner ) {
	super( owner );
	buttons = new ArrayList< ActionButton >();
	createButtons( buttons );
	size = new Dimension( 200, 200 );
	shapes = new Shape[ 0 ];
	positions = new Point2D[ 0 ];
	zoomers = new HashMap< ActionButton, Zoomer >();
    }

    public void fadeIn() {
	float sourceAlpha = 0.0f;
	if ( fader != null ) {
	    sourceAlpha = fader.cancel();
	}
	setVisible( true );
	fader = new Fader( Fade.FadeIn, sourceAlpha, true );
    }

    public void fadeOut() {
	float sourceAlpha = 1.0f;
	if ( fader != null ) {
	    sourceAlpha = fader.cancel();
	}
	fader = new Fader( Fade.FadeOut, sourceAlpha, false );
    }
    
    public void setLocation( int x, int y ) {
	this.x = x;
	this.y = y;
	recalculateLocations();
    }
    
    public Dimension getSize() {
	return size;
    }
    
    @Override
    public void setVisible( boolean visible ) {
	super.setVisible( visible );
	if ( !visible ) {
	    for ( ActionButton b : buttons ) {
		b.reset();
	    }
	    synchronized ( zoomers ) {
                for ( Zoomer z : zoomers.values() ) {
                    z.cancel();
                }
	    }
	}
    }
       
    public void paint(Graphics g) {
	if ( isVisible() ) {	    
            Graphics2D g2 = ( Graphics2D ) g.create();
            if ( fader != null ) {
        	g2.setComposite( fader.getAlphaComposite() );
            }
            paintButtons( g2 );
            g2.dispose();
	}
    }   

    public void mouseMoved( MouseEvent e ) {
	ActionButton c = getActionButtonAt( e.getX(), e.getY() );
	if ( c != null ) {
	    if ( c == mouseOn ) {
		return;
	    }
	    if ( mouseOn != null ) {
		new Zoomer( Zoom.ZoomOut, mouseOn );
	    }
	    mouseOn = c;
	    new Zoomer( Zoom.ZoomIn, mouseOn );
	} else {
	    if ( mouseOn != null ) {
		new Zoomer( Zoom.ZoomOut, mouseOn );
		mouseOn = null;
	    }
	}
    }

    public void mouseClicked( MouseEvent e ) {
    }
    
    public void mousePressed( MouseEvent e ) {
    }

    public void mouseReleased( MouseEvent e ) {
    }
    
    protected abstract void createButtons( java.util.List< ActionButton > buttons );

    protected void paintButtons( Graphics2D g ) {
	int i = 0;
	for ( JComponent c : buttons ) {
	    AffineTransform transform = g.getTransform();
	    Point2D p = positions[ i++ ];
	    g.translate( p.getX(), p.getY() );
	    c.paint( g );
	    g.setTransform( transform );
	}
    }
    
    private void recalculateLocations() {
	shapes = new Shape[ buttons.size() ];
	positions = new Point2D[ buttons.size() ];
	double angle = 0;
	double step = ( Math.PI * 2.0 ) / ( double ) buttons.size();
	int i = 0;
	Point2D psrc = new Point2D.Float( 0, 0 );
	for ( JComponent c : buttons ) {
	    Rectangle r = new Rectangle( c.getSize() );  
	    AffineTransform at = new AffineTransform();
	    at.translate( x, y );
	    at.translate( getSize().width / 2, getSize().height / 2 );
	    at.rotate( angle );
	    at.translate( 0, -30 );
	    at.rotate( -angle );
	    at.translate( -c.getWidth()/2, -c.getHeight()/2 );
	    positions[ i ] = at.transform( psrc, null );
	    shapes[ i++ ] = at.createTransformedShape( r );
	    angle += step;
	}
    }
	
    private ActionButton getActionButtonAt( int x, int y ) {
        int i = 0;
        ActionButton result = null;
        for( Shape s : shapes ) {
            if ( s.contains( x, y ) ) {
        	result = buttons.get( i );
        	break;
            }
            i++;
        }
        return result;
    }	
}
