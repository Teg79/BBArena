package net.sf.bbarena.model.event.roster;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.team.Experience;
import net.sf.bbarena.model.team.Player;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class AddPlayerExperienceEvent extends RosterEvent {

	private static final long serialVersionUID = 6408478747912466903L;

	private Player _player = null;
	private Experience _newExperience = null;

	public AddPlayerExperienceEvent(long playerId, int comp, int td, int inter, int cas, int mvp, int old, int scars, int fouls, int rush) {
		super(playerId);
		_newExperience = new Experience(comp, td, inter, cas, mvp, old, scars, fouls, rush);
	}

	@Override
	public void doEvent(Arena arena) {
		_player = getPlayer(arena);
		arena.getPlayerManager().addPlayerExperience(_player, _newExperience);
	}

	@Override
	public void undoEvent(Arena arena) {
		arena.getPlayerManager().subPlayerExperience(_player, _newExperience);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass(),
				new Pair("playerId", getPlayerId()),
				new Pair("exp", _newExperience));
	}

	public Experience getNewExperience() {
		return _newExperience;
	}

}
