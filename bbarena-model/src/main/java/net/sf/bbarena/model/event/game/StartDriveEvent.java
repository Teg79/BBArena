package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.util.Concat;

public class StartDriveEvent extends GameEvent {

	private static final long serialVersionUID = -1919966604189789283L;

	public StartDriveEvent() {
		super();
	}

	@Override
	public void doEvent(Arena arena) {
	}

	@Override
	protected void undoEvent(Arena arena) {
	}

	@Override
	public String getString() {
		return Concat.buildLog(getClass());
	}

}
