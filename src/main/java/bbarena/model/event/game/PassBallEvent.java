package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.Coordinate;
import bbarena.model.pitch.Ball;
import bbarena.model.pitch.Pitch;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class PassBallEvent extends BallEvent {

	private static final long serialVersionUID = 7623559099296775845L;

	public static String PASS_ROLL = "Pass";

	private Ball _ball = null;
	private Player _from = null;
	private Coordinate _to = null;

	public PassBallEvent(int ballId, int toX, int toY) {
		super(ballId);
		_to = new Coordinate(toX, toY);
	}

	@Override
	public void doEvent(Arena arena) {
		_ball = getBall(arena);

		Pitch pitch = arena.getPitch();
		_from = _ball.getOwner();

		pitch.ballPass(_ball, _to);
	}

	@Override
	public void undoEvent(Arena arena) {
		Pitch pitch = arena.getPitch();
		pitch.ballCatch(_ball, _from);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("playerId", _from.getId()),
				new Pair("to", _to));
	}

	public Coordinate getDestination() {
		return _to;
	}

}
