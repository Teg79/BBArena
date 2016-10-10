package net.sf.bbarena.view.bloodbowl.play;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import javax.swing.SwingUtilities;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.event.EventFlowListener;
import net.sf.bbarena.model.event.game.BallEvent;
import net.sf.bbarena.model.event.game.KickOffBallEvent;
import net.sf.bbarena.model.event.game.PassBallEvent;
import net.sf.bbarena.model.event.game.PickUpBallEvent;
import net.sf.bbarena.model.event.game.ScatterBallEvent;
import net.sf.bbarena.model.event.game.ThrowInBallEvent;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Square;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.view.Animation;
import net.sf.bbarena.view.AnimationObserver;
import net.sf.bbarena.view.BallUi;
import net.sf.bbarena.view.Draggable;
import net.sf.bbarena.view.GuiStateAdapter;
import net.sf.bbarena.view.MovingAnimation;
import net.sf.bbarena.view.PlayerUi;
import net.sf.bbarena.view.util.BitmapManager;
import net.sf.bbarena.view.util.GuiIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PitchView extends net.sf.bbarena.view.PitchView implements EventFlowListener {
    
    private static final Logger log = LoggerFactory.getLogger("bb.pitchview");

    private static final long serialVersionUID = -4082618916082041216L;
    
    private static Random randomizer;
        
    // Setup state
    class GuiStateSetup extends GuiStateAdapter {
	public void paint(Graphics g) {
	    paintPitch(g);
            paintPitchPlayers(g);
            paintBalls(g);
	    Square currentSquare = getCurrentSquare();
	    if (getIsDragging() && getDragged() instanceof PlayerUi) {
                PlayerUi pui = (PlayerUi) getDragged();
                java.util.List<Square> squares = getOpponentSquares(pui.getPlayer().getTeam());
                paintHighlightedSquares(g, squares, Color.black, 0.25f);
                if (!squares.contains(currentSquare)) {
                    paintHighlightedSquare(g, currentSquare, Color.yellow);
                }
            }
            if (getIsDragging() && currentSquare != null) {
        	Draggable dragged = getDragged();
                Graphics2D g2 = (Graphics2D) g.create();
                Coordinate c = new Coordinate(mouseCoordinate.getX() - dragged.getSize().width / 2, mouseCoordinate.getY() - dragged.getSize().height / 2);
                dragged.paintDragged(g2,c);
                Player p = currentSquare.getPlayer();
                PlayerUi pui = (PlayerUi) dragged;
                if((p != null && p != pui.getPlayer()) || pui.getPlayer().getTeam() != currentSquare.getTeamOwner()) {
                    c = new Coordinate(mouseCoordinate.getX(), mouseCoordinate.getY());
                    paintNotAllowedIcon(g2,c,true);
                }		
                g2.dispose();
            }
	}
	
        public void mouseEnterSquare(Square square) {
            if (getDragged() == null) {
                if(square.getPlayer() != null) {
                    setCursor( new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(null);
                }
            }		
        } 

        public void mousePressedSquare(Square square, MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                for(PlayerUi pui : pitchPlayers ) {
                    if(square.equals( pui.getPlayer().getSquare())) {
                        setDragged(pui);
                    }
                }
            }		
        }
        
        public void mouseReleasedSquare(Square square, MouseEvent e) {
            Draggable d = getDragged(); 
            if (d != null) {
                for (PlayerUi pui : pitchPlayers ) {
                    if (d == pui) {
                        if(square.getPlayer() == null && pui.getPlayer().getTeam() == square.getTeamOwner()) {
                            pitch.putPlayer(pui.getPlayer(), square.getCoords());
                        }
                        break;
                    }
                }
                setDragged(null);
            }		
        }
    }

    // KickOff state
    class GuiStateKickOff extends GuiStateAdapter {
	public void enter() { 
            setDragged(balls.get(0));
	}
	
	public void exit() { 
	    setDragged(null);
	}
	
	public void paint(Graphics g) {
	    paintPitch(g);
            paintPitchPlayers(g);
            paintBalls(g);
            if (getIsDragging()) {
                Draggable dragged = getDragged();            
                Coordinate c = new Coordinate(mouseCoordinate.getX() - dragged.getSize().width / 2, mouseCoordinate.getY() - dragged.getSize().height / 2);
                Graphics2D g2 = (Graphics2D) g.create();
                dragged.paintDragged(g2,c);
                g2.dispose();
            }
	}

	public void mouseClickedSquare(Square square, MouseEvent e) {
	    setBallToSquare(square);
	    changeState(new GuiStatePlay());		
	}
    }

    // RangeRuler state (when measuring the distance OR choosing pass target)
    class GuiStateRangeRuler extends GuiStateAdapter {	
	GuiStateRangeRuler(Square square) {
            getRangeRuler().setStart(square);
	}
	
	public void enter() {
            getRangeRuler().setVisible(true);
	}
	
	public void exit() {
	    getRangeRuler().setVisible(false);
	}
	
	public void paint(Graphics g) {
	    paintPitch(g);
	    getRangeRuler().paint(g);
            paintPitchPlayers(g);
            paintBalls(g);
	}
                
        public void mouseExitedSquare(Square square) {
            getRangeRuler().setEnd(null);
        } 
        
        public void mouseEnterSquare(Square square) {
            getRangeRuler().setEnd(square);
        } 
        
        public void mouseClickedSquare(Square square, MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && getRangeRuler().getStart() != null) {
        	boolean containsBall = squareContainsBall(getRangeRuler().getStart());
        	boolean startAndEndAreSame = getRangeRuler().getStart().equals(getRangeRuler().getEnd()) || (getRangeRuler().getEnd() == null);
                changeState(new GuiStatePlay());
                if (containsBall && !startAndEndAreSame) {
                    setBallToSquare(square);
                }
            }
        }
    }

    // Action selection state (when showing an action ring)
    class GuiStateSelectAction extends GuiStateAdapter {
	private Square square;
	
	public GuiStateSelectAction(Square square) {
	    this.square = square;
	    updateLocation();
	}
	
	public void enter() {
            getActionRing().fadeIn();
	}
	
	public void exit() {
	    getActionRing().fadeOut();
	}

	public void paint(Graphics g) {
	    paintPitch(g);
            paintPitchPlayers(g);
            paintBalls(g);
	    getActionRing().paint(g);
	}
	    
        public void pitchLayoutChanged() {
            updateLocation();
        }
	    
	public void mouseClickedSquare(Square square, MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
               changeState(new GuiStatePlay());
            }
        }	
        
	public void mouseMoved(MouseEvent e) {
            getActionRing().mouseMoved( e );
        }
	
	private void updateLocation() {
            Coordinate c = squareCenterInViewCoord(square);
            getActionRing().setLocation(c.getX() - getActionRing().getSize().width / 2, c.getY() - getActionRing().getSize().height / 2);
	}
    }
    
    // Play state
    class GuiStatePlay extends GuiStateAdapter {
	public void paint(Graphics g) {
	    paintPitch(g);
            paintPitchPlayers(g);
            paintBalls(g);
	    Square currentSquare = getCurrentSquare();
	    if (currentSquare != null && !getIsDragging()) {
		paintHighlightedSquare(g, currentSquare, Color.cyan);
	    }
	}
        
	public void mouseClickedSquare(Square square, MouseEvent e) {
           if (SwingUtilities.isRightMouseButton(e)) {
               changeState(new GuiStateSelectAction(square));
           } else if (SwingUtilities.isLeftMouseButton(e)) {
               	changeState(new GuiStateRangeRuler(square));
           }		
        }
    }
      
    // Animating state (base class for animation states)    
    abstract class GuiStateAnimating extends GuiStateAdapter implements AnimationObserver {

	private GuiIcon backGround;
	
	protected Animation animation;
	
	protected GuiStateAnimating(Animation animation) {
	    this.animation = animation;
	}

	public void prepare() {
            captureBackground(true);
	}
	
	public void enter() {
            animation.addObserver(this);
            animation.start();
	}	
		
	public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            backGround.paint(g2);
            animation.paint(g2);
            g2.dispose();
	}

	public void pitchLayoutChanged() {
	    captureBackground(false);
        }

	private void captureBackground(boolean current) {
	    Dimension d = getSize();
	    backGround = new GuiIcon(BitmapManager.getGC().createCompatibleImage(d.width, d.height));
	    beginCapture();
	    capture(backGround.getGraphics(), current);
	    endCapture();
	}
		
        abstract void beginCapture();
        
        abstract void endCapture();

        // from AnimationObserver
        public void animationFinished() {
	    changeState(getPreviousState());
	    play();
	    repaint();
	}
	
    }
    
    // Ball animations
    class GuiStateAnimatingBall extends GuiStateAnimating {
	
	protected Square start;
	protected Square target;
	
	GuiStateAnimatingBall(MovingAnimation animation, Square start, Square target) {
	    super(animation);
	    this.start = start;
	    this.target = target;
	}
	
	public void pitchLayoutChanged() {
	    animation.pause();
	    super.pitchLayoutChanged();
	    MovingAnimation movingAnimation = (MovingAnimation) animation;
	    movingAnimation.updateStartEnd(squareToViewCoord(start), squareToViewCoord(target));
	    animation.resume();
        }
	
	public void mouseClickedSquare(Square square, MouseEvent e) {
	    animation.stop();
	}
	
	protected void beginCapture() {
	    balls.get(0).setIsVisible(false);
	    setDisableHighlight(true);
	}
	
	protected void endCapture() {
	    balls.get(0).setIsVisible(true);
	    setDisableHighlight(false);
	}
    }
    
    // Passing animation (flying ball)
    class GuiStateAnimatingPass extends GuiStateAnimatingBall {
	
	GuiStateAnimatingPass(MovingAnimation animation, Square start, Square target) {
	    super(animation, start, target);
	}
	
	public void exit() {
	    if (target.getPlayer() == null) {
		scatterBall(target);
	    } else {
		pickupBall(target);
	    }
	}	
    }

    // Scatter animation (flying ball)
    class GuiStateAnimatingScatter extends GuiStateAnimatingBall {
	
	GuiStateAnimatingScatter(MovingAnimation animation, Square start, Square target) {
	    super(animation, start, target);
	}
	
	public void exit() {
	    pickupBall(target);
	}	
    }
    
    // ThrowIn animation (flying ball)
    class GuiStateAnimatingThrowIn extends GuiStateAnimatingPass {	
	GuiStateAnimatingThrowIn(MovingAnimation animation, Square start, Square target) {
	    super(animation, start, target);
	}	
    }
    
    private PitchView self;
    
    public PitchView(Arena arena) {
	super(arena, Layout.Portrait);
	setEventFlowListener(this);
	self = this;
    }
    
    public void endSetup() {
	changeState(new GuiStateKickOff());
    }
    
    public boolean getIsInSetup() {
	return getCurrentState() instanceof GuiStateSetup;
    }

    public boolean getIsKickOff() {
	return getCurrentState() instanceof GuiStateKickOff;
    }
    
    protected void construct() {
	try {
	    randomizer = java.security.SecureRandom.getInstance("SHA1PRNG");
	} catch (java.security.NoSuchAlgorithmException e) {
	    log.warn("could not create SecureRandom with algorithm SHA1PRNG, using java.util.Random instead");
	    randomizer = new java.util.Random();
	}
	setInitialState(new GuiStateSetup());
	setActionRing(new PlayerActionRing(this));
        addMouseMotionListener( new MouseMotionAdapter() {
            public void mouseMoved( MouseEvent e ) {
        	getCurrentState().mouseMoved(e);
            } 
	} );
    }
    
    private void setBallToSquare(Square square) {
        BallUi ballUi = balls.get(0);
        ballUi.setIsVisible(true);
        Ball ball = ballUi.getBall();
        Coordinate c = square.getCoords();
        net.sf.bbarena.model.event.Event event = null;
        if (square.getPlayer() == null) {
            event = new KickOffBallEvent(ball.getId(), c.getX(), c.getY());
        } else {
            if (getIsKickOff()) {
        	event = new PickUpBallEvent(ball.getId(), square.getPlayer().getId());
            } else {
        	event = new PassBallEvent(ball.getId(), c.getX(), c.getY());
            }
        }
        addEvent(event, true);
    }
    
    private void pickupBall(Square square) {
        BallUi ballUi = balls.get(0);
	if (square != null && square.getPlayer() != null) {
	    net.sf.bbarena.model.event.Event event = new PickUpBallEvent(ballUi.getBall().getId(), square.getPlayer().getId());
            addEvent(event, true);
	}
    }
    
    private void scatterBall(Square square) {
        Ball ball = balls.get(0).getBall();
        int direction = randomizer.nextInt(8) + 1;
        boolean outOfPitch = (pitch.getNextSquare(square.getCoords(), Direction.getDirection(direction)) == null);
        net.sf.bbarena.model.event.Event event = null;
        if (outOfPitch) {
            // throw-in range is 2d6
            int range = randomizer.nextInt(6) + randomizer.nextInt(6) + 2;
            Direction d = Direction.getDirection(direction);
            event = new ThrowInBallEvent(ball.getId(), d.inverse().ordinal() + 1, range);
        } else {
            event = new ScatterBallEvent(ball.getId(), direction);
        }
        addEvent(event, false);
    }
    
    private boolean squareContainsBall(Square square) {
	return (square != null) && ((square.getBall() != null) || (square.getPlayer() != null && square.getPlayer().getBall() != null));
    }
        
    private void animatePass(BallUi ballUi, Square to) {
	Ball ball = ballUi.getBall();
	Square from = null;
	if (ball.getOwner() == null) {
	    from = ball.getSquare();
	} else {
	    from = ball.getOwner().getSquare();
	}
        MovingAnimation animation = new MovingAnimation(self, squareToViewCoord(from), squareToViewCoord(to), ballUi.getPassFrames(), 5);                
	changeState(new GuiStateAnimatingPass(animation, from, to));
    }
    
    private void animateScatter(BallUi ballUi, Square to) {
	Ball ball = ballUi.getBall();
	Square from = null;
	if (ball.getOwner() == null) {
	    from = ball.getSquare();
	} else {
	    from = ball.getOwner().getSquare();
	}
	MovingAnimation animation = new MovingAnimation(self, squareToViewCoord(from), squareToViewCoord(to), ballUi.getScatterFrames(), 2);                
	changeState(new GuiStateAnimatingScatter(animation, from, to));
    }
    
    private void handlePassBallEvent(PassBallEvent e, BallUi ballUi) {
	animatePass(ballUi, arena.getPitch().getSquare(e.getDestination()));
    }
    
    private void handleKickOffBallEvent(KickOffBallEvent e, BallUi ballUi) {
	animatePass(ballUi, arena.getPitch().getSquare(e.getDestination()));
    }
    
    private void handleScatterBallEvent(ScatterBallEvent e, BallUi ballUi) {
	Ball ball = ballUi.getBall();
	Square to = ball.getOwner() == null ? ball.getSquare() : ball.getOwner().getSquare();
	to = arena.getPitch().getNextSquare(to.getCoords(), e.getDirection());
	animateScatter(ballUi, to);
    }
    
    private void handleThrowInBallEventEvent(ThrowInBallEvent e, BallUi ballUi) {
	// MovingAnimation animation, Square start, Square target
	Ball ball = ballUi.getBall();
	Square from = ball.getOwner() == null ? ball.getSquare() : ball.getOwner().getSquare();	
	Square to = from;
	int range = e.getRange();
	while (range-- > 0) {
	    to = arena.getPitch().getNextSquare(to.getCoords(), e.getDirection());
	}
        MovingAnimation animation = new MovingAnimation(self, squareToViewCoord(from), squareToViewCoord(to), ballUi.getThrowInFrames(), 5);                
	changeState(new GuiStateAnimatingThrowIn(animation, from, to));
    }
    
    // from EventFlowListener
    public void beforeDoEvent(net.sf.bbarena.model.event.Event e) {
	if (e instanceof BallEvent) {
	    BallUi ballUi = getBallUi(((BallEvent)e).getBall(arena));
	    if(e instanceof PassBallEvent) {
		handlePassBallEvent((PassBallEvent)e, ballUi);
            } else if (e instanceof KickOffBallEvent) {
        	if(!getIsKickOff()) {
        	    handleKickOffBallEvent((KickOffBallEvent)e, ballUi);
        	}
            } else if (e instanceof ScatterBallEvent) {
                handleScatterBallEvent((ScatterBallEvent)e, ballUi);
            } else if (e instanceof ThrowInBallEvent) {
        	handleThrowInBallEventEvent((ThrowInBallEvent)e, ballUi);
            }
	}
    }
    
    public void afterDoEvent(net.sf.bbarena.model.event.Event e) {
    }
    
    public void beforeUndoEvent(net.sf.bbarena.model.event.Event e) {
    }
    
    public void afterUndoEvent(net.sf.bbarena.model.event.Event e) {
    }
    
    public void eventSizeChanged(int newSize) {
    }
}
