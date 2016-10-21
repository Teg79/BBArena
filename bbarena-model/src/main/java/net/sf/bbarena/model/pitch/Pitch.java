package net.sf.bbarena.model.pitch;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.RangeRuler;
import net.sf.bbarena.model.exception.PitchException;
import net.sf.bbarena.model.pitch.BallMove.BallMoveType;
import net.sf.bbarena.model.pitch.Dugout.DugoutRoom;
import net.sf.bbarena.model.pitch.Square.SquareType;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.Team;

/**
 * The Pitch is the class to represent any BB Pitch. It is possible to
 * create a PitchFactory for classic BB Pitch, DungeonBowl, DeathBowl
 * and so on. This class is the only responsible of the consistency of the pitch
 * model, each change in players location, ball location and so on must be done
 * with this class and not modifing the single Square or Player objects.
 *
 * @author f.bellentani
 */
public class Pitch {

	private final String _name;
	private final Square[][] _squares;
	private final Set<Square> _squareSet;
	private final List<Dugout> _dugouts;
	private final List<Ball>  _balls;
	private final Set<Player> _players;

	/**
	 * Constructor for a general pitch The pitch MUST be rectangular or a
	 * square, each row must have the same number of squares. Fill every square
	 * out of bounds with a SquareType.OUT
	 * @param name
	 *            Name of the pitch
	 * @param width
     * 			  Width of the pitch
	 * @param height
	 * 			  Height of the pitch
	 */
	public Pitch(String name, int width, int height) {
		_name = name;

		_squareSet = new LinkedHashSet<>();
		_squares = new Square[width][height];
        for (int x = 0; x < width; x++) {
        	for (int y = 0; y < height; y++) {
				Square square = new Square(this, new Coordinate(x, y));
				_squareSet.add(square);
				_squares[x][y] = square;
			}
		}

		_dugouts = new ArrayList<>();

		_balls = new ArrayList<>();

		_players = new LinkedHashSet<>();
	}

	public boolean addDugout(Dugout dugout) {
		boolean res = false;
		if (dugout != null) {
			res = _dugouts.add(dugout);
		}
		return res;
	}

	public String getPitchName() {
		return _name;
	}

	public int getWidth() {
		return _squares.length;
	}

	public int getHeight() {
		return _squares[0].length;
	}

	/**
	 * Return the Square in a Coordinate
	 *
	 * @param xy
	 *            Square Coordinate from [0,0]
	 * @return The Square in the Coordinate
	 */
	public Square getSquare(Coordinate xy) {
		Square res = null;

		int x = xy.getX();
		int y = xy.getY();

		if (x >= 0 && _squares.length > x && y >= 0 && _squares[x].length > y) {
			res = _squares[x][y];
		}

		return res;
	}

	public Set<Square> getTeamSquares(Team team) {
		Set<Square> res = new LinkedHashSet<>();
		_squareSet.stream().filter(square -> square.getTeamOwner() != null ? square.getTeamOwner().equals(team) : false).forEach(square -> res.add(square));
		return res;
	}

	public List<Ball> getBalls() {
		return _balls;
	}

	/**
	 * Returns the first (default one) ball.
	 *
	 * @return Default Ball, null if there are no balls.
	 */
	public Ball getBall() {
		return getBall(0);
	}

	public Ball getBall(int ballId) {
		Ball res = null;
		if (_balls.size() > ballId) {
			res = _balls.get(ballId);
		}
		return res;
	}

	public int addBall(Ball ball) {
		int res = _balls.size();

		ball.setId(res);
		_balls.add(ball);

		return res;
	}

	/**
	 * Counts the number of active Tackle Zones in a Square
	 *
	 * @param xy
	 *            Square Coordinate
	 * @return the number of Tackle Zones
	 */
	public int getTZCount(Coordinate xy) {
		int res = 0;

		for (Direction d : Direction.values()) {
			Square s = getNextSquare(xy, d);
			if (s != null && s.getType() != SquareType.OUT) {
				Player p = s.getPlayer();
				if (p != null && p.hasTZ()) {
					res++;
				}
			}
		}

		return res;
	}

	/**
	 * Search the next Square in a specified Direction
	 *
	 * @param xy
	 *            Start Coordinate
	 * @param direction
	 *            Direction
	 * @return The Square near the start Coordinate
	 */
	public Square getNextSquare(Coordinate xy, Direction direction) {
		return getSquare(xy.getNext(direction));
	}

	/**
	 * Move a player to a Direction. If the Player will move out of the pitch a
	 * PitchException will be thrown (this doesn't apply for SquareType.OUT)
	 *
	 * @param player
	 *            Player to move
	 * @param direction
	 *            Direction of the movement
	 * @return The new Square occupied by the Player
	 */
	public Square movePlayer(Player player, Direction direction) {
		return movePlayer(player, direction, 1);
	}

	/**
	 * Move a player to a Direction. If the Player will move out of the pitch a
	 * PitchException will be thrown (this doesn't apply for SquareType.OUT)
	 *
	 * @param player
	 *            Player to move
	 * @param direction
	 *            Direction of the movement
	 * @param squares
	 *            Number of squares
	 * @return The new Square occupied by the Player
	 */
	public Square movePlayer(Player player, Direction direction, int squares) {
		if (squares < 0) {
			throw new PitchException(
					"Cannot move a negative number of squares!");
		}
		Square res = null;
		Square origin = player.getSquare();
		if (origin != null) {
			Coordinate pc = origin.getCoords();
			Square next = origin;
			for (int i = 0; i < squares; i++) {
				next = getNextSquare(pc, direction);

				if (next != null && next.getType() != SquareType.OUT) {
					pc = next.getCoords();
				} else {
					throw new PitchException("Player moved out of the pitch!");
				}
			}

			origin.removePlayer();
			next.setPlayer(player);
			player.setSquare(next);
			res = next;

		} else {
			throw new PitchException("Player is not on the pitch!");
		}
		return res;
	}

	/**
	 * Scatter the Ball in a Direction by one square
	 *
	 * @param ball
	 *            Ball to scatter
	 * @param direction
	 *            Direction
	 * @return The new Square occupied by the Ball
	 */
	public Square ballScatter(Ball ball, Direction direction) {
		return moveBall(ball, direction, 1, BallMoveType.SCATTER);
	}

	/**
	 * Pass the Ball to a destination
	 *
	 * @param ball
	 *            Ball to pass
	 * @param destination
	 *            Destination of the throw
	 * @return The new Square occupied by the Ball
	 */
	public Square ballPass(Ball ball, Coordinate destination) {
		return putBall(ball, destination, BallMoveType.PASS);
	}

	/**
	 * Kick Off the Ball to a destination
	 *
	 * @param ball
	 *            Ball to kick
	 * @param destination
	 *            Destination of the kick
	 * @return The new Square occupied by the Ball
	 */
	public Square ballKickOff(Ball ball, Coordinate destination) {
		return putBall(ball, destination, BallMoveType.KICK_OFF);
	}

	/**
	 * Kick-off the Ball to a destination
	 *
	 * @param ball
	 *            Ball to kick
	 * @param direction
	 *            Direction of the scatter
	 * @param range
	 *            Number of squares
	 * @return The new Square occupied by the Ball
	 */
	public Square ballKickOff(Ball ball, Direction direction, int range) {
		return moveBall(ball, direction, range, BallMoveType.KICK_OFF);
	}

	/**
	 * Throw-In the Ball to a destination
	 *
	 * @param ball
	 *            Ball to kick
	 * @param direction
	 *            Direction of the throw-in
	 * @param range
	 *            Number of squares
	 * @return The new Square occupied by the Ball
	 */
	public Square ballThrowIn(Ball ball, Direction direction, int range) {
		return moveBall(ball, direction, range, BallMoveType.THROW_IN);
	}

	/**
	 * Assign the Ball to a Player via Pick Up
	 *
	 * @param ball
	 *            Ball to Pick Up
	 * @param player
	 *            Player that gets the Ball
	 */
	public void ballPickUp(Ball ball, Player player) {
		setBallOwner(ball, player, BallMoveType.PICK_UP);
	}

	/**
	 * Deprive a Player the Ball. The Ball will be assigned to the Player
	 * Square, waiting to be scattered
	 *
	 * @param ball
	 *            Ball
	 * @param player
	 *            Player to deprive
	 * @return The new Square of the Ball
	 */
	public Square ballLose(Ball ball, Player player) {
		return putBall(ball, player.getSquare().getCoords(), BallMoveType.LOSE);
	}

	/**
	 * Assign the Ball to a Player via Catch
	 *
	 * @param ball
	 *            Ball to Catch
	 * @param player
	 *            Player that gets the Ball
	 */
	public void ballCatch(Ball ball, Player player) {
		setBallOwner(ball, player, BallMoveType.CATCH);
	}

	/**
	 * Move a Ball to a Direction. If the Ball will move out of the pitch a
	 * PitchException will be thrown (this doesn't apply for SquareType.OUT)
	 *
	 * @param ball
	 *            Ball to move
	 * @param direction
	 *            Direction of the movement
	 * @param squares
	 *            Number of squares
	 * @param moveType
	 *            Type of the Ball movement, default if range == 1 or == 0 is
	 *            SCATTER, if range > 1 is THROW_IN
	 * @return The new Square occupied by the Ball
	 */
	private Square moveBall(Ball ball, Direction direction, int squares,
			BallMoveType moveType) {
		if (squares < 0) {
			throw new PitchException(
					"Cannot move a negative number of squares!");
		}

		if (moveType == null) {
			if (squares <= 1) {
				moveType = BallMoveType.SCATTER;
			} else if (squares > 1) {
				moveType = BallMoveType.THROW_IN;
			}
		}

		Square res = null;
		Square origin = ball.getSquare();
		if (origin != null) {
			Coordinate bc = origin.getCoords();
			Square next = origin;
			for (int i = 0; i < squares; i++) {
				next = getNextSquare(bc, direction);

				if (next != null && next.getType() != SquareType.OUT) {
					bc = next.getCoords();
				} else {
					throw new PitchException("Ball moved out of the pitch!");
				}
			}

			removeBall(ball);
			next.setBall(ball);
			ball.setSquare(next, moveType);
			res = next;

		} else {
			throw new PitchException("Ball is not on the pitch!");
		}
		return res;
	}

	/**
	 * Assign the Ball owner
	 *
	 * @param ball
	 *            Ball to assign
	 * @param player
	 *            Player that will have the Ball
	 * @param moveType
	 *            Type of the Ball movement, default is PICK_UP
	 */
	private void setBallOwner(Ball ball, Player player, BallMoveType moveType) {
		if (player.getSquare() == null || player.isInDugout()) {
			throw new PitchException("Player is not on the pitch!");
		} else if (player.hasBall()) {
			throw new PitchException("Player has already a Ball!");
		}

		if (moveType == null) {
			moveType = BallMoveType.PICK_UP;
		}

		removeBall(ball);
		ball.setOwner(player, moveType);
		player.setBall(ball);
	}

	/**
	 * Put a Player in a specified Coordinate on the pitch. If the Player is in
	 * the Dugout it will be removed.
	 *
	 * @param player
	 *            Player to put in the pitch
	 * @param xy
	 *            Coordinate where to put the Player
	 * @return The new Square for the Player
	 */
	public Square putPlayer(Player player, Coordinate xy) {
		Square res = getSquare(xy);
		if (res == null || res.getType() == SquareType.OUT) {
			throw new PitchException("Square " + xy.toString()
					+ " is out of the pitch!");
		}

		if (player.isInDugout()) {
			Dugout dugout = getDugout(player.getTeam());
			dugout.removePlayer(player);
			_players.add(player);
		}

		Square origin = player.getSquare();
		if (origin != null) {
			origin.removePlayer();
			player.setSquare(null);
		}

		res.setPlayer(player);
		player.setSquare(res);

		return res;
	}

	/**
	 * Put a Ball in a specified Coordinate on the pitch.
	 *
	 * @param ball
	 *            Ball to put in the pitch
	 * @param xy
	 *            Coordinate where to put the Ball
	 * @param moveType
	 *            Type of the Ball movement, default is KICK_OFF
	 * @return The new Square for the Ball
	 */
	private Square putBall(Ball ball, Coordinate xy, BallMoveType moveType) {
		Square res = getSquare(xy);
		if (res == null || res.getType() == SquareType.OUT) {
			throw new PitchException("Square " + xy.toString()
					+ " is out of the pitch!");
		}

		if (moveType == null) {
			moveType = BallMoveType.KICK_OFF;
		}

		removeBall(ball);
		ball.setSquare(res, moveType);
		res.setBall(ball);

		return res;
	}

	/**
	 * Remove the Ball from the pitch
	 *
	 * @param ball
	 *            Ball to remove
	 */
	private void removeBall(Ball ball) {
		Square origin = ball.getSquare();
		if (origin != null) {
			origin.removeBall();
		}
		Player player = ball.getOwner();
		if (player != null) {
			player.removeBall();
		}
	}

	public void ballRemove(Ball ball) {
		removeBall(ball);
		ball.remove(BallMoveType.OUT);
	}

	/**
	 * Put a Player in a specified Room of his Team Dugout. If the player is in
	 * a Square it will be removed.
	 *
	 * @param player
	 *            Player to put in Dugout
	 * @param room
	 *            The DugoutRoom
	 * @return true if the player is successfully added, false otherwise
	 */
	public boolean putPlayer(Player player, DugoutRoom room) {
		boolean res;

		Dugout dugout = getDugout(player.getTeam());
		res = dugout.addPlayer(room, player);
		if (res) {
			Square square = player.getSquare();
			if (square != null) {
				square.removePlayer();
				player.setSquare(null);
				_players.remove(player);
			}
		}

		return res;
	}

	public List<Dugout> getDugouts() {
		return _dugouts;
	}

	public Dugout getDugout(Team team) {
		Dugout res = null;
		for (Dugout dugout : _dugouts) {
			if (dugout.getTeam().equals(team)) {
				res = dugout;
				break;
			}
		}
		return res;
	}

	/**
	 * Given a coordinate and a range return every square that is in the range
	 * from that coordinate.
	 *
	 * @param ref
	 *            Coordinate from where will be calculated the range
	 * @param squareRange
	 *            Number of squares for the range
	 * @return The list of Squares within range
	 */
	public List<Square> getSquaresInRange(Coordinate ref, int squareRange) {
		List<Square> res = new ArrayList<Square>();

		int x1 = Math.max(0, ref.getX() - squareRange);
		int x2 = Math.min(_squares.length - 1, ref.getX() + squareRange);
		int y1 = Math.max(0, ref.getY() - squareRange);
		int y2 = Math.min(_squares[x2].length - 1, ref.getY() + squareRange);

		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				Square square = getSquare(new Coordinate(x, y));
				if (square != null && square.getType() != SquareType.OUT) {
					res.add(square);
				}
			}
		}

		return res;
	}

	/**
	 * Given a coordinate and a range return every player that is in the range
	 * from that coordinate. This will be useful when testing which players are,
	 * for example, 3 squares away from another player. Here there are no
	 * controls over player skills or if the players have active tackle zones.
	 *
	 * @param ref
	 *            Coordinate from where will be calculated the range
	 * @param squareRange
	 *            Number of squares for the range
	 * @return The list of Players within range
	 */
	public List<Player> getPlayersInRange(Coordinate ref, int squareRange) {
		List<Player> res = new ArrayList<Player>();

		List<Square> squares = getSquaresInRange(ref, squareRange);
		for (Square square : squares) {
			Player player = square.getPlayer();
			if (player != null) {
				res.add(player);
			}
		}

		return res;
	}

	public RangeRuler getRangeRuler() {
		return new RangeRuler(this);
	}

	public Set<Player> getPlayers() {
		return _players;
	}

	void setSquare(Coordinate xy, SquareType type) {
	    setSquare(xy, type, null);
    }

	void setSquare(Coordinate xy, SquareType type, Team teamOwner) {
		Square s = new Square(this, xy, type, teamOwner);
		Square oldSquare = _squares[xy.getX()][xy.getY()];
		_squareSet.remove(oldSquare);
		_squareSet.add(s);
		_squares[xy.getX()][xy.getY()] = s;
	}

}
