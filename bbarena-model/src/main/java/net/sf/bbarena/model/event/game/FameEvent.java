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
        MatchTeamInfo matchTeamInfo0 = _arena.getScore().getTeamsInfo().get(0);
        matchTeamInfo0.getNotes().put("FAME", fameCoach0.toString());
        MatchTeamInfo matchTeamInfo1 = _arena.getScore().getTeamsInfo().get(1);
        matchTeamInfo1.getNotes().put("FAME", fameCoach1.toString());
    }

    @Override
    protected void undoEvent() {
        MatchTeamInfo matchTeamInfo0 = _arena.getScore().getTeamsInfo().get(0);
        matchTeamInfo0.getNotes().put("FAME", "0");
        MatchTeamInfo matchTeamInfo1 = _arena.getScore().getTeamsInfo().get(1);
        matchTeamInfo1.getNotes().put("FAME", "0");
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
