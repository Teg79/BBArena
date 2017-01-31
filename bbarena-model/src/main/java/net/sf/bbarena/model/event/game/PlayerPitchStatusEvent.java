package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.team.PlayerPitchStatus;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class PlayerPitchStatusEvent extends PlayerEvent {

	private static final long serialVersionUID = 1790786618254181381L;

	private PlayerPitchStatus _pitchStatus = null;
	private PlayerPitchStatus _oldPitchStatus = null;

    public PlayerPitchStatusEvent(long playerId) {
        super(playerId);
    }

    public PlayerPitchStatusEvent(long playerId, PlayerPitchStatus pitchStatus) {
        super(playerId);
        _pitchStatus = pitchStatus;
    }

    public PlayerPitchStatus getPitchStatus() {
        return _pitchStatus;
    }

    public void setPitchStatus(PlayerPitchStatus pitchStatus) {
        _pitchStatus = pitchStatus;
    }

	@Override
	public void doEvent(Arena arena) {
		Player player = getPlayer(arena);
		_oldPitchStatus = player.getPitchStatus();
		player.setPitchStatus(_pitchStatus);
	}

	@Override
	public void undoEvent(Arena arena) {
		Player player = getPlayer(arena);
		player.setPitchStatus(_oldPitchStatus);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("playerId", getPlayerId()),
				new Pair("pitchStatus", _pitchStatus));
	}

}
