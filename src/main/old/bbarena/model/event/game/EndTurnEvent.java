package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.util.Concat;

public class EndTurnEvent extends GameEvent {

    private static final long serialVersionUID = -1919966604189789283L;

    public EndTurnEvent() {
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
        return Concat.INSTANCE.buildLog(getClass());
    }

}
