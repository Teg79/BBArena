package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.util.Concat;

public class KickOffEvent extends GameEvent {

    public KickOffEvent() {
    }

    @Override
    protected void doEvent(Arena arena) {
    }

    @Override
    protected void undoEvent(Arena arena) {
    }

    @Override
    public String getString() {
        return Concat.buildLog(getClass());
    }
}
