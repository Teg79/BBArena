package bbarena.model;

import bbarena.model.pitch.Square;
import bbarena.model.pitch.Square.SquareType;

public class ThrowInTemplate {

	private Arena arena = null;

	public ThrowInTemplate(Arena arena) {
		this.arena = arena;
	}

	/**
	 * Determines the Direction of a Throw In
	 *
	 * @param direction From 1 to 6
	 * @param xy Last Square crossed by the ball
	 * @return The Direction of the Throw In
	 */
	public Direction getThrowInDirection(int direction, Coordinate xy) {
		Direction res = null;
		int dir = halfDirection(direction);

		boolean n = checkBorder(xy, Direction.N);
		boolean e = checkBorder(xy, Direction.E);
		boolean s = checkBorder(xy, Direction.S);
		boolean w = checkBorder(xy, Direction.W);

		if (n && !e && !s && !w) { // North border
			res = Direction.N.next(2 + dir);
		} else if (!n && e && !s && !w) { // East border
			res = Direction.E.next(2 + dir);
		} else if (!n && !e && s && !w) { // South border
			res = Direction.S.next(2 + dir);
		} else if (!n && !e && !s && w) { // West border
			res = Direction.W.next(2 + dir);
		} else if (n && e && !s && !w) { // North East corner
			res = Direction.NE.next(2 + dir);
		} else if (n && !e && !s && w) { // North West corner
			res = Direction.NW.next(2 + dir);
		} else if (!n && e && s && !w) { // South East corner
			res = Direction.SE.next(2 + dir);
		} else if (!n && !e && s && w) { // South West corner
			res = Direction.SW.next(2 + dir);
		}

		return res;
	}

	private boolean checkBorder(Coordinate xy, Direction direction) {
		boolean res = false;

		Square s = arena.getPitch().getNextSquare(xy, direction);
		res = (s == null || s.getType() == SquareType.OUT);

		return res;
	}

	private int halfDirection(int direction) {
		int dir = (int) Math.ceil((double)direction / 2);
		return dir;
	}

}
