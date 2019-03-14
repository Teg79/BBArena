package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.pitch.Ball;
import bbarena.model.pitch.Pitch;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class PickUpBallEvent extends BallEvent {

	private static final long serialVersionUID = 8477756281960807889L;

    public static final String PICK_UP_BALL_ROLL = "PICK_UP_BALL";

    private long _playerId = 0;
	private Ball _ball = null;
	private Player _player = null;
	private boolean _failed = false;

	public PickUpBallEvent(int ballId, long playerId) {
		super(ballId);
		_playerId = playerId;
	}

	@Override
	public void doEvent(Arena arena) {
		_player = arena.getPlayerManager().getPlayer(_playerId);
		_ball = getBall(arena);

		if (!_failed) {
			arena.getPitch().ballPickUp(_ball, _player);
		}
	}

	@Override
	public void undoEvent(Arena arena) {
		if (!_failed) {
			Pitch pitch = arena.getPitch();
			pitch.ballLose(_ball, _player);
		}
	}

	@Override
	public String getString() {
		return Concat.INSTANCE.buildLog(getClass(),
				new Pair("ballId", getBallId()),
				new Pair("playerId", _playerId),
				new Pair("catched", !_failed));
	}

	public long getPlayerId() {
		return _playerId;
	}

	public void setFailed(boolean failed) {
		_failed = failed;
	}
}
