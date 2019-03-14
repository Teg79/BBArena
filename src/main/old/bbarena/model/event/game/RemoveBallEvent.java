package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.Coordinate;
import bbarena.model.pitch.Ball;
import bbarena.model.pitch.Pitch;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class RemoveBallEvent extends BallEvent {

	private static final long serialVersionUID = 3975130415257010810L;

	private Ball _ball = null;
	private Coordinate _fromSquare = null;
	private Player _fromPlayer = null;

	public RemoveBallEvent(int ballId) {
		super(ballId);
	}

	@Override
	public void doEvent(Arena arena) {
		_ball = getBall(arena);
		_fromPlayer = _ball.getOwner();
		if (_ball.getSquare() != null) {
			_fromSquare = _ball.getSquare().getCoords();
		}

		Pitch pitch = arena.getPitch();
		pitch.ballRemove(_ball);
	}

	@Override
	public void undoEvent(Arena arena) {
		Pitch pitch = arena.getPitch();

		if (_fromPlayer != null) {
			pitch.ballPickUp(_ball, _fromPlayer);
		} else if (_fromSquare != null) {
			pitch.ballKickOff(_ball, _fromSquare);
		}
	}

	@Override
	public String getString() {
		return Concat.INSTANCE.buildLog(getClass(),
				new Pair("ballId", getBallId()));
	}

}
