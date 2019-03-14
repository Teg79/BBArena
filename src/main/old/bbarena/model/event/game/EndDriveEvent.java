package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.util.Concat;

public class EndDriveEvent extends GameEvent {

	private static final long serialVersionUID = -1919966604189789283L;
    private Integer _oldDrive;

    public EndDriveEvent() {
        super();
	}

	@Override
	public void doEvent(Arena arena) {
		_oldDrive = arena.getMatch().getCurrentDrive();
		arena.getMatch().setCurrentDrive(_oldDrive + 1);
	}

	@Override
	protected void undoEvent(Arena arena) {
		arena.getMatch().setCurrentDrive(_oldDrive);
	}

	@Override
	public String getString() {
		return Concat.INSTANCE.buildLog(getClass());
	}

}
