package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Direction;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class ThrowInBallEvent extends BallEvent {

	private static final long serialVersionUID = 8883483302218039478L;

	private int _range = 0;
	private Ball _ball = null;
	private Direction _direction = null;

	public ThrowInBallEvent(int ballId, int direction, int range) {
		super(ballId);
		_direction = Direction.getDirection(direction);
		_range = range;
	}

	@Override
	protected void doEvent(Arena arena) {
		_arena = arena;
		_ball = getBall(arena);

		arena.getPitch().ballThrowIn(_ball, _direction, _range);
	}

	@Override
	protected void undoEvent() {
		_arena.getPitch().ballThrowIn(_ball, _direction.inverse(), _range);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("direction", _direction),
				new Pair("range", _range));
	}

	public Direction getDirection() {
		return _direction;
	}

	public int getRange() {
		return _range;
	}

}
