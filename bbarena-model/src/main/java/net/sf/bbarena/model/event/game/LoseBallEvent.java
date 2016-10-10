package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.pitch.Ball;
import net.sf.bbarena.model.pitch.Pitch;
import net.sf.bbarena.model.team.Player;

public class LoseBallEvent extends BallEvent {

	private static final long serialVersionUID = 3777543139880431274L;

	private long _playerId = 0;
	private Ball _ball = null;
	private Player _player = null;

	public LoseBallEvent(int ballId, long playerId) {
		super(ballId);
		_playerId = playerId;
	}

	@Override
	public void doEvent(Arena arena) {
		_arena = arena;
		_player = arena.getPlayerManager().getPlayer(_playerId);
		_ball = getBall(arena);

		arena.getPitch().ballLose(_ball, _player);
	}

	@Override
	public void undoEvent() {
		Pitch pitch = _arena.getPitch();
		pitch.ballPickUp(_ball, _player);
	}

	@Override
	public String getString() {
		StringBuilder res = new StringBuilder();
		res.append(getClass().getSimpleName())
			.append("[ballId:")
			.append(getBallId())
			.append(",playerId:")
			.append(_playerId)
			.append("]");
		return res.toString();
	}

	public long getPlayerId() {
		return _playerId;
	}

}
