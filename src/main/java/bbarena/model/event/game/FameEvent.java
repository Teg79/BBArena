package bbarena.model.event.game;

import bbarena.model.Arena;

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
        arena.getScoreBoard(0).setFame(fameCoach0);
        arena.getScoreBoard(1).setFame(fameCoach1);
    }

    @Override
    protected void undoEvent(Arena arena) {
        arena.getScoreBoard(0).setFame(0);
        arena.getScoreBoard(1).setFame(0);
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
