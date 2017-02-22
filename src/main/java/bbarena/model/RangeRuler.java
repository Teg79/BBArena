package bbarena.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bbarena.model.pitch.Dugout;
import bbarena.model.pitch.Pitch;
import bbarena.model.pitch.Square;
import bbarena.model.pitch.Square.SquareType;
import bbarena.model.team.Player;
import bbarena.model.team.Team;

/**
 * This class is responsible to calculate pass ranges and available interception
 * zones. Double precision used for calculation, not as perfect as with
 * BigDecimal but faster.
 * 
 * @author f.bellentani
 */
public class RangeRuler {

	private static final long serialVersionUID = -7151698894803437642L;

	public enum Range implements Choice {
		NO_RANGE, QUICK, SHORT, LONG, BOMB;

		public char getSymbol() {
			char res = 'O';
			if (this == NO_RANGE) {
				res = 'N';
			} else if (this == QUICK) {
				res = 'Q';
			} else if (this == SHORT) {
				res = 'S';
			} else if (this == LONG) {
				res = 'L';
			} else if (this == BOMB) {
				res = 'B';
			}
			return res;
		}
	}

	public static final double RULER_WIDTH = 4.25D;

	protected static final double RULER_WIDTH_RATIO = RULER_WIDTH
			/ Square.SQUARE_WIDTH;

	private Pitch pitch = null;

	public RangeRuler(Pitch pitch) {
		this.pitch = pitch;
	}

	public Range getRange(Coordinate from, Coordinate to) {
		Range res = Range.NO_RANGE;

		int x = Math.abs(from.getX() - to.getX());
		int y = Math.abs(from.getY() - to.getY());

		if (y > x) {
			int z = x;
			x = y;
			y = z;
		}

		if (x > 0 || y > 0) {
			if (isQuick(x, y)) {
				res = Range.QUICK;
			} else if (isShort(x, y)) {
				res = Range.SHORT;
			} else if (isLong(x, y)) {
				res = Range.LONG;
			} else if (isBomb(x, y)) {
				res = Range.BOMB;
			}
		}

		return res;
	}

	/**
	 * Calculate all players available for an interception
	 * 
	 * @param from
	 *            Thrower coordinate
	 * @param to
	 *            Catcher coordinate
	 * @return All players that can try an interception
	 */
	public List<Player> getInterceptionPlayers(Coordinate from, Coordinate to) {
		return getInterceptionOpponents(from, to, null);
	}

	/**
	 * Calculate all opponent players available for an interception
	 * 
	 * @param from
	 *            Thrower coordinate
	 * @param to
	 *            Catcher coordinate
	 * @param throwingTeam
	 *            Team that is throwing the ball, only players of opponent team
	 *            will be returned
	 * @return All opponent players that can try an interception
	 */
	public List<Player> getInterceptionOpponents(Coordinate from,
			Coordinate to, Team throwingTeam) {
		List<Player> res = new ArrayList<Player>();

		List<Dugout> dugouts = pitch.getDugouts();
		for (Dugout dugout : dugouts) {

			Team team = dugout.getTeam();
			if (team != throwingTeam) {

				Set<Player> players = team.getPlayers();
				for (Player p : players) {

					if (p.getSquare() != null) {
						Coordinate inter = p.getSquare().getCoords();

						if (isInterceptable(from, to, inter)) {
							res.add(p);
						}
					}
				}
			}
		}

		return res;
	}

	/**
	 * Calculate all available squares for an interception
	 * 
	 * @param from
	 *            Thrower coordinate
	 * @param to
	 *            Catcher coordinate
	 * @param throwingTeam
	 *            Team that is throwing the ball, only players of opponent team
	 *            will be returned
	 * @return All opponent players that can try an interception
	 */
	public List<Square> getInterceptionSquares(Coordinate from, Coordinate to) {
		List<Square> res = new ArrayList<Square>();

		for (int x = 0; x < pitch.getWidth(); x++) {
			for (int y = 0; y < pitch.getHeight(); y++) {
				Coordinate inter = new Coordinate(x, y);
				if (isInterceptable(from, to, inter)) {
					Square s = pitch.getSquare(inter);
					if (s.getType() != SquareType.OUT) {
						res.add(s);
					}
				}
			}
		}

		return res;
	}

	/**
	 * Seach for players within a range from the interception zones. No filter
	 * applied, to get only opponent players try getOpponentsInInterceptionRange
	 * 
	 * @param from
	 *            Thrower coordinates
	 * @param to
	 *            Catcher coordinates
	 * @param squareRange
	 *            Range for the search
	 * @return All the players within the range from the interception zones
	 */
	public Set<Player> getPlayersInInterceptionRange(Coordinate from,
			Coordinate to, int squareRange) {
		return getOpponentsInInterceptionRange(from, to, squareRange, null);
	}

	/**
	 * Seach for opponent players within a range from the interception zones.
	 * 
	 * @param from
	 *            Thrower coordinates
	 * @param to
	 *            Catcher coordinates
	 * @param squareRange
	 *            Range for the search
	 * @return All the players within the range from the interception zones
	 */
	public Set<Player> getOpponentsInInterceptionRange(Coordinate from,
			Coordinate to, int squareRange, Team throwingTeam) {
		Set<Player> res = new HashSet<Player>();

		List<Square> interZones = getInterceptionSquares(from, to);
		for (Square square : interZones) {
			Coordinate xy = square.getCoords();
			List<Player> players = pitch.getPlayersInRange(xy, squareRange);

			for (Player player : players) {
				if (player.getTeam() != throwingTeam) {
					res.add(player);
				}
			}
		}

		return res;
	}

	/**
	 * Seach for squares within a range from the interception zones.
	 * 
	 * @param from
	 *            Thrower coordinates
	 * @param to
	 *            Catcher coordinates
	 * @param squareRange
	 *            Range for the search
	 * @return All the Squares within the range from the interception zones
	 */
	public Set<Square> getSquaresInInterceptionRange(Coordinate from,
			Coordinate to, int squareRange, Team throwingTeam) {
		Set<Square> res = new HashSet<Square>();

		List<Square> interZones = getInterceptionSquares(from, to);
		for (Square square : interZones) {
			Coordinate xy = square.getCoords();
			List<Square> squares = pitch.getSquaresInRange(xy, squareRange);

			res.addAll(squares);
		}

		return res;
	}

	/**
	 * Determines if a coordinate is a valid interception coordinate
	 * 
	 * @param from
	 *            Thrower coordinates
	 * @param to
	 *            Catcher coordinates
	 * @param inter
	 *            Interception coordinates
	 * @return true if the interception coordinate is valid, false otherwise
	 */
	private boolean isInterceptable(Coordinate from, Coordinate to,
			Coordinate inter) {
		boolean res = false;

		double a = getLength(from, to);
		double b = getLength(from, inter);
		double c = getLength(inter, to);

		if (b < a && c < a
				&& getDistanceFromThrowLine(a, b, c) <= RULER_WIDTH_RATIO) {
			res = true;
		}

		return res;
	}

	/**
	 * Given the 3 distances between a, b, c calculates the distance from ab
	 * line to c
	 * 
	 * @param throwLength
	 *            AB
	 * @param fromThrowerDistance
	 *            AC
	 * @param fromCatcherDistance
	 *            CB
	 * @return AB to C distance
	 */
	private double getDistanceFromThrowLine(double throwLength,
			double fromThrowerDistance, double fromCatcherDistance) {
		double res = 0D;

		double a = throwLength;
		double b = fromThrowerDistance;
		double c = fromCatcherDistance;

		double s = (a + b + c) / 2;

		// If the difference between half-perimeter and the throw length is
		// lower than 0.01 square than we assume that the intercepting player is
		// over the throw path
		if (Math.abs(s - a) > 0.01D) {
			double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
			res = area * 2 / a;
		}

		return res;
	}

	private double getLength(Coordinate from, Coordinate to) {
		double res = 0D;

		int x = Math.abs(from.getX() - to.getX());
		int y = Math.abs(from.getY() - to.getY());

		res = Math.sqrt(x * x + y * y);

		return res;
	}

	private boolean isQuick(int x, int y) {
		boolean res = false;

		if ((x < 4 && y < 2) || (x < 3 && y < 3)) {
			res = true;
		}

		return res;
	}

	private boolean isShort(int x, int y) {
		boolean res = false;

		if ((x < 7 && y < 4) || (x < 6 && y < 5)) {
			res = true;
		}

		return res;
	}

	private boolean isLong(int x, int y) {
		boolean res = false;

		if ((x < 11 && y < 3) || (x < 10 && y < 5) || (x < 9 && y < 7)
				|| (x < 8 && y < 8)) {
			res = true;
		}

		return res;
	}

	private boolean isBomb(int x, int y) {
		boolean res = false;

		if ((x < 14 && y < 2) || (x < 13 && y < 5) || (x < 12 && y < 7)
				|| (x < 11 && y < 9) || (x < 10 && y < 10)) {
			res = true;
		}

		return res;
	}

}
