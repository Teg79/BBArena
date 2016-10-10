package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Coordinate;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

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
		_arena = arena;
		_ball = getBall(arena);
		_fromPlayer = _ball.getOwner();
		if (_ball.getSquare() != null) {
			_fromSquare = _ball.getSquare().getCoords();
		}

		Pitch pitch = arena.getPitch();
		pitch.ballRemove(_ball);
	}

	@Override
	public void undoEvent() {
		Pitch pitch = _arena.getPitch();

		if (_fromPlayer != null) {
			pitch.ballPickUp(_ball, _fromPlayer);
		} else if (_fromSquare != null) {
			pitch.ballKickOff(_ball, _fromSquare);
		}
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()));
	}

}
