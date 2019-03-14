package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.pitch.Ball;
import bbarena.model.pitch.Pitch;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class CatchBallEvent extends BallEvent {

	private static final long serialVersionUID = -8383445710083022190L;

	public static final String CATCH_ROLL = "CATCH";

    private long _playerId;
    private boolean _failed = false;

    public CatchBallEvent(int ballId, long playerId) {
        super(ballId);
        _playerId = playerId;
    }

	@Override
	public void doEvent(Arena arena) {
        Ball ball = getBall(arena);
        Player player = arena.getPlayerManager().getPlayer(_playerId);

		if (!_failed) {
            arena.getPitch().ballCatch(ball, player);
        }
	}

	@Override
	public void undoEvent(Arena arena) {
		if (!_failed) {
			Pitch pitch = arena.getPitch();
            Ball ball = getBall(arena);
            Player player = arena.getPlayerManager().getPlayer(_playerId);
            pitch.ballLose(ball, player);
        }
	}

	@Override
	public String getString() {
		return Concat.INSTANCE.buildLog(getClass(),
				new Pair("ballId", getBallId()),
                new Pair("playerId", _playerId),
                new Pair("catched", !_failed));
	}

    public void setFailed(boolean failed) {
    	_failed = failed;
    }
}
