package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;

public class MatchStatusChangeEvent extends GameEvent {

    private Arena.MatchStatus _prevMatchStatus;
    private Arena.MatchStatus _nextMatchStatus;

    public MatchStatusChangeEvent(Arena.MatchStatus newMatchStatus) {
        _prevMatchStatus = newMatchStatus;
    }

    @Override
    protected void doEvent(Arena arena) {
        _arena = arena;
        _prevMatchStatus = _arena.getStatus();
        _arena.setStatus(_nextMatchStatus);
    }

    @Override
    protected void undoEvent() {
        _arena.setStatus(_prevMatchStatus);
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
