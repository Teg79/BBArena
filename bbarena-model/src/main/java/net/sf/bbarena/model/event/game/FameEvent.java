package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.MatchTeamInfo;
import net.sf.bbarena.model.Weather;

public class FameEvent extends GameEvent {

    private Integer fameCoach0 = 0;
    private Integer fameCoach1 = 0;

    public FameEvent() {
    }

    public void setFameCoach0(int fameCoach0) {
        this.fameCoach0 = fameCoach0;
    }

    public void setFameCoach1(int fameCoach1) {
        this.fameCoach1 = fameCoach1;
    }

    @Override
    protected void doEvent(Arena arena) {
        _arena = arena;
        _arena.getScoreBoard(0).setFame(fameCoach0);
        _arena.getScoreBoard(1).setFame(fameCoach1);
    }

    @Override
    protected void undoEvent() {
        _arena.getScoreBoard(0).setFame(0);
        _arena.getScoreBoard(1).setFame(0);
    }

    @Override
    public String getString() {
        StringBuilder res = new StringBuilder();
        res.append(getClass().getSimpleName())
                .append("[fame:")
                .append(fameCoach0)
                .append("]");
        return res.toString();
    }
}
