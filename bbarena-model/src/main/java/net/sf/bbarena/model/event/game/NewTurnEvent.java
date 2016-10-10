package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.TurnMarker;
import net.sf.bbarena.model.util.Concat;

public class NewTurnEvent extends GameEvent {

	private static final long serialVersionUID = -8405654897458550237L;

	private TurnMarker _marker = null;

	public NewTurnEvent() {
		super();
	}

	@Override
	public void doEvent(Arena arena) {
		_arena = arena;
		_marker = arena.getTurnMarker().clone();

		TurnMarker tm = arena.getTurnMarker();
		tm.newTurn();
	}

	@Override
	protected void undoEvent() {
		_arena.getTurnMarker().restore(_marker);
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass());
	}

}
