package net.sf.bbarena.model;

/**
 * The directions as looking the field from the home team and having in front the opponent
 * NW  N  NE
 * W   O   E
 * SW  S  SE
 *
 * @author f.bellentani
 */
public enum Direction implements Choice {
	NW, N, NE, E, SE, S, SW, W, O;

	public Direction inverse() {
		Direction res = this;

		if(this == N) {
			res = S;
		} else if(this == NE) {
			res = SW;
		} else if(this == NW) {
			res = SE;
		} else if(this == E) {
			res = W;
		} else if(this == W) {
			res = E;
		} else if(this == SE) {
			res = NW;
		} else if(this == SW) {
			res = NE;
		} else if(this == S) {
			res = N;
		}

		return res;
	}

	/**
	 * Get a clockwise tour of the directions.
	 * The sequence is: N, NE, E, SE, S, SW, W, NW, N, etc...
	 *
	 * @return Next clockwise direction
	 */
	public Direction next() {
		Direction res = this;

		if(this == N) {
			res = NE;
		} else if(this == NE) {
			res = E;
		} else if(this == NW) {
			res = N;
		} else if(this == E) {
			res = SE;
		} else if(this == W) {
			res = NW;
		} else if(this == SE) {
			res = S;
		} else if(this == SW) {
			res = W;
		} else if(this == S) {
			res = SW;
		}

		return res;
	}

	/**
	 * Switch clockwise the directions of a number of steps.
	 * This method calls next() a number of times equals to steps parameters.
	 *
	 * @param steps Number of time to call next()
	 * @return Direction
	 */
	public Direction next(int steps) {
		Direction res = this;

		for(int i = 0; i < steps; i++) {
			res = res.next();
		}

		return res;
	}

	/**
	 * Converts from a int direction (from 1 to 8) to a Direction
	 *
	 * @param direction Direction from 1 to 8
	 * @return Direction object
	 */
	public static Direction getDirection(int direction) {
		Direction res = null;
		if(direction >= 1 && direction <= 8) {
			res = Direction.values()[direction - 1];
		}
		return res;
	}

}
