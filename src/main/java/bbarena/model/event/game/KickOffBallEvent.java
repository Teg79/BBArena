package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.Coordinate;
import bbarena.model.pitch.Ball;
import bbarena.model.pitch.Pitch;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class KickOffBallEvent extends BallEvent {

	private static final long serialVersionUID = 941485053747283672L;

	private Coordinate _to = null;

	public KickOffBallEvent(int ballId, int toX, int toY) {
		super(ballId);
		_to = new Coordinate(toX, toY);
	}

	@Override
	public void doEvent(Arena arena) {
		Ball ball = getBall(arena);

		Pitch pitch = arena.getPitch();
		pitch.ballKickOff(ball, _to);
	}

	@Override
	public void undoEvent(Arena arena) {
		Ball ball = getBall(arena);
		Pitch pitch = arena.getPitch();
		pitch.ballRemove(ball);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("to", _to));
	}

	public Coordinate getDestination() {
		return _to;
	}

}
