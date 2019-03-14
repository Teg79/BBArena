package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.util.Concat;
import bbarena.model.util.Pair;

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
        arena.getScoreBoard(_team).setFans(_fans);
    }

    @Override
    protected void undoEvent(Arena arena) {
        arena.getScoreBoard(_team).setFans(0);
    }

    @Override
    public String getString() {
        return Concat.INSTANCE.buildLog(getClass(),
                new Pair("team", _team),
                new Pair("fans", _fans));
    }
}
