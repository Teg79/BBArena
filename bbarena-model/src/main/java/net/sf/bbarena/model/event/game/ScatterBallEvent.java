package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class ScatterBallEvent extends BallEvent {

	private static final long serialVersionUID = -6262949311066708660L;

	private Ball _ball = null;
	private Direction _direction = null;
	private Integer _distance = null;

	public ScatterBallEvent(int ballId, int direction) {
		this(ballId, direction, 1);
	}
	public ScatterBallEvent(int ballId, int direction, int distance) {
		super(ballId);
		_direction = Direction.getDirection(direction);
		_distance = distance;
	}

	@Override
	protected void doEvent(Arena arena) {
		_arena = arena;
		_ball = getBall(arena);

		arena.getPitch().ballScatter(_ball, _direction);
	}

	@Override
	protected void undoEvent() {
		_arena.getPitch().ballScatter(_ball, _direction.inverse());
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("direction", _direction),
				_distance > 1 ? new Pair("distance", _distance) : null);
	}

	public Direction getDirection() {
		return _direction;
	}

}
