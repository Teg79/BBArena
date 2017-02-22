package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.team.Player;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

public class PlayerPlayableEvent extends PlayerEvent {

	private Player _player = null;
	private final boolean _playable;
	private boolean _oldPlayable = true;

	public PlayerPlayableEvent(long playerId, boolean playable) {
		super(playerId);
		_playable = playable;
	}

	@Override
	public void doEvent(Arena arena) {
		_player = getPlayer(arena);
		_oldPlayable = _player.isPlayable();
		_player.setPlayable(_playable);
	}

	@Override
	public void undoEvent(Arena arena) {
		_player.setPlayable(_oldPlayable);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("playerId", getPlayerId()),
				new Pair("playable", _playable));
	}
}
