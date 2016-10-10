package net.sf.bbarena.view;

import java.awt.*;
import java.awt.image.*;

import net.sf.bbarena.model.*;
import net.sf.bbarena.model.pitch.Square;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.view.util.*;

public class RangeRulerUi extends PluggableComponent {
    
    private PitchView pitchView;
    private RangeRuler rangeRuler;
    private Square start;
    private Square end;
    private BufferedImage rangeRulerImage;
    
    public RangeRulerUi( PitchView owner, RangeRuler rangeRuler ) {
	super( owner );
	pitchView = owner;
	this.rangeRuler = rangeRuler;
	rangeRulerImage = BitmapManager.loadIconPreserveAlpha( "data/gfx/field/rangeruler.png" ).getImage();
    }
        
    public void setStart(Square start) {
	this.start = start;
	owner.repaint();
    }

    public Square getStart() {
	return start;
    }

    public void setEnd( Square end ) {
	this.end = end;
	owner.repaint();
    }
    
    public Square getEnd() {
	return end;
    }

    @Override
    public void setVisible( boolean visible ) {
	super.setVisible( visible );
	if (!visible) {
	    start = end = null;
	}
    }
    
    public void paint(Graphics g) {
	if ( start != null ) {
	    Coordinate sc = pitchView.squareCenterInViewCoord( start );
	    Coordinate ec = sc;
	    if ( end != null ) {
		ec = pitchView.squareCenterInViewCoord( end );
	    }
	    int length = ( int ) length( sc, ec );
	    if ( length > 0 ) {
                Graphics2D g2 = ( Graphics2D ) g.create();
        	g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        	g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
                g2.translate( sc.getX(), sc.getY() );
		double dx = ec.getX() - sc.getX();
		double dy = ec.getY() - sc.getY();
		double angle = 0;
		if ( dx == 0 ) {
		    if ( dy < 0 ) {
			angle = -Math.PI / 2;
		    } else {
			angle = Math.PI / 2;
		    }
		} else if ( dy == 0 ) {
		    
		    if ( dx < 0 ) {
			angle += Math.PI;
		    }
		} else {
		    angle = Math.atan( dy / dx );
		    if ( dx < 0 ) {
			angle += Math.PI;
		    }
		}
		g2.rotate( angle );
		g2.drawImage( rangeRulerImage, -9, -rangeRulerImage.getHeight()/2, null );
		if(pitchView.getUseHelpers() && start.getPlayer() != null) {
		    
		    // Highlight Pass Blockers in interception zones
//		    for(Square square : rangeRuler.getSquaresInInterceptionRange(start.getCoords(), end.getCoords(), 3, start.getPlayer().getTeam())) {
//			pitchView.paintHighlightedSquare(g, square, Color.yellow);
//		    }

		    // Highlight DP/PB from thrower
//		    for(Square square : pitchView.pitch.getSquaresInRange(start.getCoords(), 4)) {
//			pitchView.paintHighlightedSquare(g, square, Color.blue);
//		    }
		    
		    // Highlight DP/PB from catcher
//		    for(Square square : pitchView.pitch.getSquaresInRange(end.getCoords(), 4)) {
//			pitchView.paintHighlightedSquare(g, square, Color.blue);
//		    }
		    
		    // Highlight interception players
		    for(Player player : rangeRuler.getInterceptionOpponents(start.getCoords(), end.getCoords(), start.getPlayer().getTeam())) {
			pitchView.paintHighlightedSquare(g, player.getSquare(), Color.red);
		    }
		}
//		  int width = Math.round( pitchView.squareSizeInPixels().width * 1.65f );
//                g2.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f ) );                             
//                g2.setColor( getRangeRulerColor() );
//                g2.fillRect( -5, -width/2, length, width );
		
		// this draws only as much as needed
//		g2.drawImage( rangeRulerImage, 
//			-5, -rangeRulerImage.getHeight()/2, length - 5, rangeRulerImage.getHeight()/2,
//			0, 0, length, rangeRulerImage.getHeight(),
//			null );
                g2.dispose();
	    }
	}
    }
    
    private double length( Coordinate f, Coordinate t ) {
	int x = f.getX() - t.getX();
	int y = f.getY() - t.getY();
	return Math.sqrt( x * x + y * y );
    }
    
//    private Color getRangeRulerColor() {
//	Coordinate from = start.getCoords();
//	Coordinate to = from; 
//	if ( end != null ) {
//	    to = end.getCoords();
//	}
//	Color c = Color.gray;
//	RangeRuler.Range range = rangeRuler.getRange( from, to );
//	if ( range == RangeRuler.Range.QUICK ) {
//	    c = Color.green;
//	} else if ( range == RangeRuler.Range.SHORT ) {
//	    c = Color.yellow;
//	} else if ( range == RangeRuler.Range.LONG ) {
//	    c = Color.orange;
//	} else if ( range == RangeRuler.Range.BOMB ) {
//	    c = Color.red;
//	}
//	return c;
//    }
}
