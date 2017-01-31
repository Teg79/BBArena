package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.TurnMarker;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class NewTurnEvent extends GameEvent {

	private static final long serialVersionUID = -8405654897458550237L;

    private int _coachPos;
    private int _rerolls;
    private int _turn;
    private boolean _usedReroll;

    public NewTurnEvent() {
        super();
	}

    public int getCoachPos() {
        return _coachPos;
    }

    public void setCoachPos(int coachPos) {
        this._coachPos = coachPos;
    }

	@Override
	public void doEvent(Arena arena) {
        TurnMarker turnMarker = arena.getTurnMarkers().get(_coachPos);
        turnMarker.setStatus(TurnMarker.TurnStatus.PLAYING);
        _rerolls = turnMarker.getRerolls();
        _turn = turnMarker.getTurn();
        _usedReroll = turnMarker.isUsedReroll();

        turnMarker.setTurn(_turn + 1);
        turnMarker.setUsedReroll(false);
    }

	@Override
    protected void undoEvent(Arena arena) {
        TurnMarker turnMarker = arena.getTurnMarkers().get(_coachPos);
        turnMarker.setStatus(TurnMarker.TurnStatus.DONE);
        turnMarker.setUsedReroll(_usedReroll);
        turnMarker.setTurn(_turn);
        turnMarker.setRerolls(_rerolls);
    }

	@Override
	public String getString() {
        return Concat.buildLog(getClass(),
                new Pair("coach", _coachPos),
                new Pair("turn", _turn + 1));
    }

}
