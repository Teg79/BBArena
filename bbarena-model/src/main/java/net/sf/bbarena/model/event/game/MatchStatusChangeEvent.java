package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Match;

public class MatchStatusChangeEvent extends GameEvent {

    private Match.Status _prevMatchStatus;
    private Match.Status _nextMatchStatus;

    public MatchStatusChangeEvent(Match.Status newMatchStatus) {
        _prevMatchStatus = newMatchStatus;
    }

    @Override
    protected void doEvent(Arena arena) {
        _arena = arena;
        _prevMatchStatus = _arena.getMatch().getStatus();
        _arena.getMatch().setStatus(_nextMatchStatus);
    }

    @Override
    protected void undoEvent() {
        _arena.getMatch().setStatus(_prevMatchStatus);
    }

    @Override
    public String getString() {
        StringBuilder res = new StringBuilder();
        res.append(getClass().getSimpleName())
                .append("[match:")
                .append(_nextMatchStatus)
                .append("]");
        return res.toString();
    }
}
