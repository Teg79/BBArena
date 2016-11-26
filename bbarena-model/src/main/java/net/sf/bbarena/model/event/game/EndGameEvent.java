package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Match;
import net.sf.bbarena.model.util.Concat;

public class EndGameEvent extends GameEvent {

    private static final long serialVersionUID = -1919966604189789283L;
    private Match.Status _prevStatus;

    public EndGameEvent() {
        super();
    }

    @Override
    public void doEvent(Arena arena) {
        _arena = arena;
        _prevStatus = _arena.getMatch().getStatus();
        _arena.getMatch().setStatus(Match.Status.ENDING);
    }

    @Override
    protected void undoEvent() {
        _arena.getMatch().setStatus(_prevStatus);
    }

    @Override
    public String getString() {
        return Concat.buildLog(getClass());
    }

}
