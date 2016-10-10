package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.team.Player;

public abstract class PlayerEvent extends Event {

	private static final long serialVersionUID = -5173908075142881459L;
	protected long playerId = 0;

	public PlayerEvent(long playerId) {
		super();
		this.playerId = playerId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public Player getPlayer(Arena arena) {
		return arena.getPlayerManager().getPlayer(playerId);
	}

}