package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;

public class FansEvent extends GameEvent {

    private Integer _fans = 0;
    private Integer _team = null;

    public FansEvent() {

    }

    public FansEvent(Integer team) {
        _team = team;
    }

    public void setTeam(Integer team) {
        this._team = team;
    }

    public void setFans(int fans) {
        this._fans = fans;
    }

    @Override
    protected void doEvent(Arena arena) {
        _arena = arena;
        _arena.getScoreBoard(_team).setFans(_fans);
    }

    @Override
    protected void undoEvent() {
        _arena.getScoreBoard(_team).setFans(0);
    }

    @Override
    public String getString() {
        StringBuilder res = new StringBuilder();
        res.append(getClass().getSimpleName())
                .append("[team").append(_team).append(" fans: ")
                .append(_fans)
                .append("]");
        return res.toString();
    }
}
