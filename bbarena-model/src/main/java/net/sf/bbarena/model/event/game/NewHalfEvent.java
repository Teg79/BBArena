package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.util.Concat;

public class NewHalfEvent extends GameEvent {

	private static final long serialVersionUID = -1919966604189789283L;
    private int _half;

    public NewHalfEvent() {
        super();
	}

	@Override
	public void doEvent(Arena arena) {
		_arena = arena;

        _half = _arena.getHalf();
        _arena.setHalf(_half + 1);
    }

	@Override
	protected void undoEvent() {
        _arena.setHalf(_half);
    }

	@Override
	public String getString() {
		return Concat.buildLog(getClass());
	}

}
