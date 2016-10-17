package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.MatchTeamInfo;

public class FansEvent extends GameEvent {

    private Integer fans = 0;

    public FansEvent() {
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    @Override
    protected void doEvent(Arena arena) {
        _arena = arena;
//        _arena.get
//        MatchTeamInfo matchTeamInfo0 = _arena.getScore().getTeamsInfo().get(0);
//        matchTeamInfo0.getNotes().put("FAME", fameCoach0.toString());
//        MatchTeamInfo matchTeamInfo1 = _arena.getScore().getTeamsInfo().get(1);
//        matchTeamInfo1.getNotes().put("FAME", fameCoach1.toString());
    }

    @Override
    protected void undoEvent() {
//        MatchTeamInfo matchTeamInfo0 = _arena.getScore().getTeamsInfo().get(0);
//        matchTeamInfo0.getNotes().put("FAME", "0");
//        MatchTeamInfo matchTeamInfo1 = _arena.getScore().getTeamsInfo().get(1);
//        matchTeamInfo1.getNotes().put("FAME", "0");
    }

    @Override
    public String getString() {
        StringBuilder res = new StringBuilder();
        res.append(getClass().getSimpleName())
                .append("[fame:")
//                .append(fameCoach0)
                .append("]");
        return res.toString();
    }
}
