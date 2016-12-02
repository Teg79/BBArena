package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.util.Concat;

public class EndDriveEvent extends GameEvent {

	private static final long serialVersionUID = -1919966604189789283L;
    private Integer _oldDrive;

    public EndDriveEvent() {
        super();
	}

	@Override
	public void doEvent(Arena arena) {
		_arena = arena;

        _oldDrive = _arena.getMatch().getCurrentDrive();
        _arena.getMatch().setCurrentDrive(_oldDrive + 1);
    }

	@Override
	protected void undoEvent() {
        _arena.getMatch().setCurrentDrive(_oldDrive);
    }

	@Override
	public String getString() {
		return Concat.buildLog(getClass());
	}

}
