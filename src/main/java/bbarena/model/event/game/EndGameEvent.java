package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.Match;
import bbarena.model.util.Concat;

public class EndGameEvent extends GameEvent {

    private static final long serialVersionUID = -1919966604189789283L;
    private Match.Status _prevStatus;

    public EndGameEvent() {
        super();
    }

    @Override
    public void doEvent(Arena arena) {
        _prevStatus = arena.getMatch().getStatus();
        arena.getMatch().setStatus(Match.Status.ENDING);
    }

    @Override
    protected void undoEvent(Arena arena) {
        arena.getMatch().setStatus(_prevStatus);
    }

    @Override
    public String getString() {
        return Concat.buildLog(getClass());
    }

}
