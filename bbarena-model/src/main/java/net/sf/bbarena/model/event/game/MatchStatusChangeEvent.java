package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Match;

import java.util.Date;

public class MatchStatusChangeEvent extends GameEvent {

    private Match.Status _prevMatchStatus;
    private Match.Status _nextMatchStatus;

    public MatchStatusChangeEvent(Match.Status newMatchStatus) {
        _nextMatchStatus = newMatchStatus;
    }

    @Override
    protected void doEvent(Arena arena) {
        _arena = arena;
        _prevMatchStatus = _arena.getMatch().getStatus();
        _arena.getMatch().setStatus(_nextMatchStatus);
        if (Match.Status.STARTING == _nextMatchStatus) {
            _arena.getMatch().setStart(new Date());
        } else if (Match.Status.FINISHED == _nextMatchStatus) {
            _arena.getMatch().setEnd(new Date());
        }
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

    public Match.Status getMatchStatus() {
        return _nextMatchStatus;
    }
}
