package bbarena.model.event.game;

import bbarena.model.Arena;
import bbarena.model.TurnMarker;
import bbarena.model.util.Concat;

import java.util.ArrayList;
import java.util.List;

public class NewHalfEvent extends GameEvent {

    private static final long serialVersionUID = -1919966604189789283L;
    private int _half;
    private List<TurnMarker> _oldMarkers = new ArrayList<>();

    public NewHalfEvent() {
        super();
    }

    @Override
    public void doEvent(Arena arena) {
        _half = arena.getHalf();
        arena.setHalf(_half + 1);
        List<TurnMarker> turnMarkers = arena.getTurnMarkers();
        for (TurnMarker turnMarker : turnMarkers) {
            TurnMarker oldMarker = new TurnMarker(turnMarker.getTeam());
            oldMarker.setRerolls(turnMarker.getRerolls());
            oldMarker.setUsedReroll(turnMarker.isUsedReroll());
            oldMarker.setTurn(turnMarker.getTurn());
            oldMarker.setStatus(turnMarker.getStatus());
            _oldMarkers.add(oldMarker);

            turnMarker.setTurn(0);
            turnMarker.setUsedReroll(false);
            turnMarker.setRerolls(turnMarker.getTeam().getReRolls());
        }
    }

    @Override
    protected void undoEvent(Arena arena) {
        arena.setHalf(_half);

        for (int i = 0; i < arena.getTurnMarkers().size(); i++) {
            TurnMarker oldMarker = _oldMarkers.get(i);
            TurnMarker turnMarker = arena.getTurnMarkers().get(i);
            turnMarker.setStatus(oldMarker.getStatus());
            turnMarker.setTurn(oldMarker.getTurn());
            turnMarker.setUsedReroll(oldMarker.isUsedReroll());
            turnMarker.setRerolls(oldMarker.getRerolls());
        }
    }

    @Override
    public String getString() {
        return Concat.INSTANCE.buildLog(getClass());
    }

}
