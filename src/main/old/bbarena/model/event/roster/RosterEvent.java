package bbarena.model.event.roster;

import bbarena.model.Arena;
import bbarena.model.event.Event;
import bbarena.model.team.Player;

public abstract class RosterEvent extends Event {

	private static final long serialVersionUID = 8447866046452336969L;
	protected long _playerId = 0;

	public RosterEvent(long playerId) {
		super();
		setPlayerId(playerId);
	}

	public long getPlayerId() {
		return getPlayerId();
	}

	public Player getPlayer(Arena arena) {
		return arena.getPlayerManager().getPlayer(getPlayerId());
	}

}