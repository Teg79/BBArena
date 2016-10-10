package net.sf.bbarena.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.RangeRuler;
import net.sf.bbarena.model.event.EventFlowListener;
import net.sf.bbarena.model.event.EventManager;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Dugout;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.pitch.Square;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;
import net.sf.bbarena.view.util.BitmapManager;
import net.sf.bbarena.view.util.GuiFrames;
import net.sf.bbarena.view.util.GuiIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PitchView extends JComponent {

	private static final long serialVersionUID = 30695225767998805L;

	private static final Logger log = LoggerFactory.getLogger("gui.pitchview");

	private static final String BASE_PITCH_IMAGE = "data/gfx/field/nice.jpg";
	private static final String HOME_TEAM_LOGO = "data/gfx/teamlogos/chaos.png";
	private static final String AWAY_TEAM_LOGO = "data/gfx/teamlogos/orc.png";
	private static final String FORBIDDEN_ICON = "data/gfx/icons/gui/forbidden.png";
	private static final String BLANKCURSOR_ICON = "data/gfx/icons/gui/blankcursor.png";
	private static final String[] chaosIcons = { "cbeastman1.png",
			"cbeastman2.png", "cbeastman3.png", "cbeastman4.png",
			"cwarrior1.png", "cwarrior2.png", "cwarrior3.png", "cwarrior4.png" };
	private static final String[] orcIcons = { "oblackorc1b.png",
			"oblackorc2b.png", "oblitzer1b.png", "oblitzer2b.png",
			"oblitzer3b.png", "olineman1b.png", "olineman2b.png",
			"olineman3b.png", "othrower1b.png", "othrower2b.png" };
	private static final String[] teamIconPaths = { "chaos/", "orc/" };
	private static final String[][] teamIcons = { chaosIcons, orcIcons };

	private static final String BALL_DRAGGED_ICON = "data/gfx/field/icons/ball_dragged.png";
	private static final String BALL_ONFIELD_ICON = "data/gfx/field/icons/ball_onfield.png";
	private static final String BALL_CARRIED_ICON = "data/gfx/field/icons/ball_carried.png";
	private static final String BALL_PASS_SMALL_ICON = "data/gfx/field/icons/frames/ball_passsmall.png";
	private static final String BALL_PASS_MEDIUM_ICON = "data/gfx/field/icons/frames/ball_passmedium.png";
	private static final String BALL_PASS_BIG_ICON = "data/gfx/field/icons/frames/ball_passbig.png";

	public enum Layout {
		Landscape, Portrait
	}

	protected Arena arena;

	protected Pitch pitch;

	protected Coordinate currentCoordinate;

	protected Coordinate mouseCoordinate;

	protected Layout pitchLayout;

	protected Set<PlayerUi> pitchPlayers;

	protected java.util.List<BallUi> balls;

	private ActionRingUi actionRing;

	private RangeRulerUi rangeRuler;

	private Draggable dragged;

	private GuiIcon forbidden;

	private Cursor blankCursor;

	private BufferedImage basePitchImage;

	private BufferedImage pitchImage;

	private boolean useHelpers;

	private GuiState guiState;

	private GuiState previousGuiState;

	private boolean disableHighlight;

	private Object paintLock = new Object();

	private EventManager eventManager;

	class PitchMouseMotionListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
			mouseCoordinate = new Coordinate(e.getX(), e.getY());
			if (contains(e.getX(), e.getY())) {
				updateCurrentCoordinate(eventCoordToFieldCoord(e));
			} else {
				updateCurrentCoordinate(null);
			}
			if (dragged != null) {
				repaint();
			}
		}

		public void mouseMoved(MouseEvent e) {
			mouseCoordinate = new Coordinate(e.getX(), e.getY());
			updateCurrentCoordinate(eventCoordToFieldCoord(e));
			if (dragged != null) {
				repaint();
			}
		}
	}

	class PitchMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if (currentCoordinate != null) {
				mouseClickedSquare(pitch.getSquare(currentCoordinate), e);
			}
		}

		public void mousePressed(MouseEvent e) {
			if (currentCoordinate != null) {
				mousePressedSquare(pitch.getSquare(currentCoordinate), e);
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (currentCoordinate != null) {
				mouseReleasedSquare(pitch.getSquare(currentCoordinate), e);
			}
		}

		public void mouseEntered(MouseEvent e) {
			updateCurrentCoordinate(eventCoordToFieldCoord(e));
		}

		public void mouseExited(MouseEvent e) {
			updateCurrentCoordinate(null);
		}
	}

	protected PitchView(Arena arena, Layout layout) {
		this.arena = arena;
		this.eventManager = new EventManager(this.arena);
		previousGuiState = guiState = new GuiStateAdapter();
		setPitch(arena);
		addMouseListener(new PitchMouseListener());
		addMouseMotionListener(new PitchMouseMotionListener());
		setBasePitchImageFromFile(BASE_PITCH_IMAGE);
		rangeRuler = new RangeRulerUi(this, new RangeRuler(arena.getPitch()));
		forbidden = BitmapManager.loadIconPreserveAlpha(FORBIDDEN_ICON);
		GuiIcon bc = BitmapManager.loadIconPreserveAlpha(BLANKCURSOR_ICON);
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				bc.getImage(), new Point(0, 0), "");
		construct();
		setPitchLayout(layout);
	}

	public void setUseHelpers(boolean useHelpers) {
		this.useHelpers = useHelpers;
		repaint();
	}

	public boolean getUseHelpers() {
		return useHelpers;
	}

	public Layout getPitchLayout() {
		return pitchLayout;
	}

	public void setPitchLayout(Layout layout) {
		if (layout != pitchLayout) {
			pitchLayout = layout;
			switch (layout) {
			case Landscape:
				setPitchImage(createCloneImage(basePitchImage));
				break;
			case Portrait:
				setPitchImage(createPortraitPitchImage(basePitchImage));
				break;
			}
			guiState.pitchLayoutChanged();
		}
	}

	public void paint(Graphics g) {
		synchronized (paintLock) {
			guiState.paint(g);
		}
	}

	protected void capture(Graphics g, boolean current) {
		synchronized (paintLock) {
			if (current) {
				guiState.paint(g);
			} else {
				previousGuiState.paint(g);
			}
		}
	}

	protected void paintHighlightedSquare(Graphics g, Square square, Color color) {
		paintHighlightedSquare(g, square, color, 0.5f);
	}

	protected void paintHighlightedSquare(Graphics g, Square square,
			Color color, float alpha) {
		if (square != null) {
			paintHighlightedSquares(g, Arrays.asList(square), color, alpha);
		}
	}

	protected void paintHighlightedSquares(Graphics g,
			java.util.List<Square> squares, Color color, float alpha) {
		if (squares != null && !disableHighlight) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					alpha));
			g2.setColor(color);
			for (Square s : squares) {
				Coordinate c = squareToViewCoord(s);
				Dimension d = squareSizeInPixels();
				g2.fillRect(c.getX(), c.getY(), d.width, d.height);
			}
			g2.dispose();
		}
	}

	protected void paintPitch(Graphics g) {
		g.drawImage(pitchImage, 0, 0, this);
	}

	protected void paintPitchPlayers(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		for (PlayerUi pui : pitchPlayers) {
			Coordinate c = squareToViewCoord(pui.getPlayer().getSquare());
			pui.paint(g2, c);
		}
		g2.dispose();
	}

	protected void paintBalls(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		for (BallUi bui : balls) {
			Coordinate c = null;
			if (bui.getIsVisible()) {
				Ball ball = bui.getBall();
				if (ball.getOwner() != null) {
					c = squareToViewCoord(ball.getOwner().getSquare());
				} else if (ball.getSquare() != null) {
					c = squareToViewCoord(ball.getSquare());
				}
				if (c != null) {
					bui.paint(g2, c);
				}
			}
		}
		g2.dispose();
	}

	protected void paintNotAllowedIcon(Graphics2D g, Coordinate c,
			boolean centered) {
		if (centered) {
			c = new Coordinate(c.getX() - forbidden.getSize().width / 2,
					c.getY() - forbidden.getSize().height / 2);
		}
		forbidden.paint(g, c);
	}

	protected void setBasePitchImageFromFile(String file) {
		basePitchImage = BitmapManager.loadImage(file, false);
	}

	protected void setTeamLogoFromFile(BufferedImage target, String file,
			boolean homeTeam) {
		burnTeamLogoToImage(target, BitmapManager.loadImage(file, true),
				homeTeam);
	}

	protected void burnTeamLogoToImage(BufferedImage target,
			BufferedImage logo, boolean homeTeam) {
		Graphics g = target.getGraphics();
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.5f));
		if (pitchLayout == Layout.Landscape) {
			if (homeTeam) {
				g2d.translate((target.getWidth() / 2 - logo.getWidth()) / 2,
						(target.getHeight() - logo.getHeight()) / 2);
			} else {
				g2d.translate(target.getWidth() / 2
						+ (target.getWidth() / 2 - logo.getWidth()) / 2,
						(target.getHeight() - logo.getHeight()) / 2);
			}
		} else {
			if (homeTeam) {
				g2d.translate((target.getWidth() - logo.getWidth()) / 2,
						(target.getHeight() + target.getHeight() / 2 - logo
								.getHeight()) / 2);
			} else {
				g2d.translate((target.getWidth() - logo.getWidth()) / 2,
						(target.getHeight() - target.getHeight() / 2 - logo
								.getHeight()) / 2);
			}
		}
		g2d.drawImage(logo, 0, 0, null);
		g2d.dispose();
		g.dispose();

	}

	protected BufferedImage createCloneImage(BufferedImage sourceImage) {
		BufferedImage image = BitmapManager.getGC().createCompatibleImage(
				sourceImage.getWidth(), sourceImage.getHeight());
		Graphics g = image.getGraphics();
		g.drawImage(sourceImage, 0, 0, null);
		g.dispose();
		return image;
	}

	protected BufferedImage createPortraitPitchImage(BufferedImage sourceImage) {
		BufferedImage image = BitmapManager.getGC().createCompatibleImage(
				sourceImage.getHeight(), sourceImage.getWidth());
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.rotate(-Math.PI / 2);
		g.drawImage(sourceImage, -sourceImage.getWidth(), 0, null);
		g.dispose();
		return image;
	}

	protected void setPitchImage(BufferedImage image) {
		pitchImage = image;
		setTeamLogoFromFile(pitchImage, HOME_TEAM_LOGO, true);
		setTeamLogoFromFile(pitchImage, AWAY_TEAM_LOGO, false);
		Dimension d = new Dimension(image.getWidth(), image.getHeight());
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setSize(d);
	}

	protected Coordinate eventCoordToFieldCoord(MouseEvent e) {
		int x = pitch.getWidth() * e.getX() / getSize().width;
		int y = pitch.getHeight() * e.getY() / getSize().height;
		if (pitchLayout == Layout.Portrait) {
			x = pitch.getWidth() - pitch.getWidth() * e.getY()
					/ getSize().height - 1;
			y = pitch.getHeight() * e.getX() / getSize().width;
		}
		Coordinate result = new Coordinate(x, y);
		return result;
	}

	protected Coordinate squareCenterInViewCoord(Square square) {
		Coordinate c = squareToViewCoord(square);
		Dimension d = squareSizeInPixels();
		return new Coordinate(c.getX() + d.width / 2, c.getY() + d.height / 2);
	}

	protected Coordinate squareToViewCoord(Square square) {
		Coordinate c = square.getCoords();
		int x = getSize().width * c.getX() / pitch.getWidth();
		int y = getSize().height * c.getY() / pitch.getHeight();
		if (pitchLayout == Layout.Portrait) {
			x = getSize().width * c.getY() / pitch.getHeight();
			y = getSize().height - getSize().height * (c.getX() + 1)
					/ pitch.getWidth();
		}
		Coordinate result = new Coordinate(x, y);
		return result;
	}

	protected Dimension squareSizeInPixels() {
		if (pitchLayout == Layout.Portrait) {
			int h = getSize().width / pitch.getHeight();
			int w = getSize().height / pitch.getWidth();
			return new Dimension(w, h);
		} else {
			int w = getSize().width / pitch.getWidth();
			int h = getSize().height / pitch.getHeight();
			return new Dimension(w, h);
		}
	}

	protected void setDragged(Draggable d) {
		if (d == null) {
			if (dragged != null) {
				setCursor(dragged.setDragged(false, null));
				dragged = d;
				repaint();
			}
		} else {
			if (dragged != null) {
				setCursor(dragged.setDragged(false, null));
			}
			dragged = d;
			dragged.setDragged(true, getCursor());
			setCursor(blankCursor);
			repaint();
		}

	}

	protected Draggable getDragged() {
		return dragged;
	}

	protected boolean getIsDragging() {
		return dragged != null;
	}

	private void updateCurrentCoordinate(Coordinate c) {
		if (c == null) {
			if (currentCoordinate != null) {
				mouseExitedSquare(pitch.getSquare(currentCoordinate));
			}
			currentCoordinate = c;
			repaint();
		} else if (!c.equals(currentCoordinate)) {
			if (currentCoordinate != null) {
				mouseExitedSquare(pitch.getSquare(currentCoordinate));
			}
			currentCoordinate = c;
			mouseEnteredSquare(pitch.getSquare(currentCoordinate));
			repaint();
		}
	}

	private void setPitch(Arena arena) {
		this.pitch = arena.getPitch();
		updatePitchPlayers(arena.getPitch());
		updateBalls(arena);
	}

	protected java.util.List<Square> getOpponentSquares(Team team) {
		java.util.List<Square> squares = new ArrayList<Square>();
		for (int x = 0; x < pitch.getWidth(); x++) {
			for (int y = 0; y < pitch.getHeight(); y++) {
				Square square = pitch.getSquare(new Coordinate(x, y));
				if (square.getTeamOwner() != team) {
					squares.add(square);
				}
			}
		}
		return squares;
	}

	public void updatePitchPlayers(Pitch pitch) {
		int teamIconsIndex = 0;
		pitchPlayers = new HashSet<PlayerUi>();
		for (Dugout dugout : pitch.getDugouts()) {
			String path = teamIconPaths[teamIconsIndex];
			String[] icons = teamIcons[teamIconsIndex++];
			for (Player player : dugout.getTeam().getPlayers()) {
				if (player.getSquare() != null) {
					String iconFile = path
							+ icons[player.getNum() % icons.length];
					pitchPlayers.add(new PlayerUi(player, BitmapManager
							.loadIcon("data/gfx/icons/" + iconFile)));
				}
			}
		}
	}

	public void updateBalls(Arena arena) {
		balls = new ArrayList<BallUi>();
		GuiIcon draggedIcon = BitmapManager.loadIcon(BALL_DRAGGED_ICON);
		GuiIcon onFieldIcon = BitmapManager.loadIcon(BALL_ONFIELD_ICON);
		GuiIcon carriedIcon = BitmapManager.loadIcon(BALL_CARRIED_ICON);
		GuiIcon b1 = BitmapManager.loadIcon(BALL_PASS_SMALL_ICON);
		GuiIcon b2 = BitmapManager.loadIcon(BALL_PASS_MEDIUM_ICON);
		GuiIcon b3 = BitmapManager.loadIcon(BALL_PASS_BIG_ICON);
		GuiIcon passIcons[] = new GuiIcon[] { b1, b2, b3, b2, b1 };
		GuiIcon scatterIcons[] = new GuiIcon[] { b1, b2, b1 };
		GuiIcon throwInIcons[] = new GuiIcon[] { b3, b2, b1 };
		GuiFrames passFrames = new GuiFrames(passIcons);
		GuiFrames scatterFrames = new GuiFrames(scatterIcons);
		GuiFrames throwInFrames = new GuiFrames(throwInIcons);
		for (Ball ball : arena.getPitch().getBalls()) {
			balls.add(new BallUi(ball, draggedIcon, onFieldIcon, carriedIcon,
					passFrames, scatterFrames, throwInFrames));
		}
	}

	public void setEventFlowListener(EventFlowListener listener) {
		eventManager.addListener(listener);
	}

	public void addEvent(net.sf.bbarena.model.event.Event event, boolean play) {
		eventManager.putEvent(event);
		if (play) {
			play();
		}
	}

	public void play() {
		eventManager.forward();
	}

	public void rewind() {
		eventManager.backward();
	}

	protected Square getCurrentSquare() {
		if (currentCoordinate != null) {
			return pitch.getSquare(currentCoordinate);
		}
		return null;
	}

	protected void setInitialState(GuiState state) {
		guiState = state;
		if (guiState != null) {
			guiState.enter();
		}
	}

	protected void changeState(GuiState state) {
		log.debug("changeState from " + guiState + " to " + state);

		if (state != null) {
			state.prepare();
		}

		if (guiState != null) {
			guiState.exit();
		}

		previousGuiState = guiState;
		guiState = state;

		if (guiState != null) {
			guiState.enter();
		}
	}

	protected GuiState getPreviousState() {
		return previousGuiState;
	}

	protected GuiState getCurrentState() {
		return guiState;
	}

	protected void setActionRing(ActionRingUi actionRing) {
		this.actionRing = actionRing;
	}

	protected ActionRingUi getActionRing() {
		return actionRing;
	}

	protected RangeRulerUi getRangeRuler() {
		return rangeRuler;
	}

	protected void setDisableHighlight(boolean disableHighlight) {
		this.disableHighlight = disableHighlight;
	}

	protected PlayerUi getPlayerUi(Player player) {
		for (PlayerUi playerUi : pitchPlayers) {
			if (playerUi.getPlayer() == player) {
				return playerUi;
			}
		}
		return null;
	}

	protected BallUi getBallUi(Ball ball) {
		for (BallUi ballUi : balls) {
			if (ballUi.getBall() == ball) {
				return ballUi;
			}
		}
		return null;
	}

	private void mouseExitedSquare(Square square) {
		guiState.mouseExitedSquare(square);
	}

	private void mouseEnteredSquare(Square square) {
		guiState.mouseEnterSquare(square);
	}

	private void mouseClickedSquare(Square square, MouseEvent e) {
		guiState.mouseClickedSquare(square, e);
	}

	private void mousePressedSquare(Square square, MouseEvent e) {
		guiState.mousePressedSquare(square, e);
	}

	private void mouseReleasedSquare(Square square, MouseEvent e) {
		guiState.mouseReleasedSquare(square, e);
	}

	protected abstract void construct();

}
