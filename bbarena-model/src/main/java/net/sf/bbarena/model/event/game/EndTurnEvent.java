package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.util.Concat;

public class EndTurnEvent extends GameEvent {

    private static final long serialVersionUID = -1919966604189789283L;

    public EndTurnEvent() {
        super();
    }

    @Override
    public void doEvent(Arena arena) {
        _arena = arena;
    }

    @Override
    protected void undoEvent() {
    }

    @Override
    public String getString() {
        return Concat.buildLog(getClass());
    }

}
