package net.sf.bbarena.model.event.roster;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.team.Player;

public abstract class RosterEvent extends Event {

	private static final long serialVersionUID = 8447866046452336969L;
	protected long _playerId = 0;

	public RosterEvent(long playerId) {
		super();
		_playerId = playerId;
	}

	public long getPlayerId() {
		return _playerId;
	}

	public Player getPlayer(Arena arena) {
		return arena.getPlayerManager().getPlayer(_playerId);
	}

}