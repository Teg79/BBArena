package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class CatchBallEvent extends BallEvent {

	private static final long serialVersionUID = -8383445710083022190L;

	public static final String CATCH_ROLL = "CATCH";

	private Ball _ball = null;
	private Player _player = null;
	private boolean _failed = false;

	public CatchBallEvent(int ballId) {
		super(ballId);
	}

	public Ball getBall() {
		return _ball;
	}

	public void setBall(Ball ball) {
		_ball = ball;
	}

	public Player getPlayer() {
		return _player;
	}

	public void setPlayer(Player player) {
		_player = player;
	}

	@Override
	public void doEvent(Arena arena) {
		_arena = arena;
		_ball = getBall(arena);

		if (!_failed) {
			arena.getPitch().ballCatch(_ball, _player);
		}
	}

	@Override
	public void undoEvent() {
		if (!_failed) {
			Pitch pitch = _arena.getPitch();
			pitch.ballLose(_ball, _player);
		}
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("playerId", _player.getId()),
				new Pair("catched", !_failed));
	}

    public void setFailed(boolean failed) {
    	_failed = failed;
    }
}
