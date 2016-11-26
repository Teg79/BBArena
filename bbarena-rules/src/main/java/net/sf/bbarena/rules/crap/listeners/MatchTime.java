package net.sf.bbarena.rules.crap.listeners;

import net.sf.bbarena.model.TurnMarker;
import net.sf.bbarena.model.event.BaseEventFlowListener;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.game.EndGameEvent;
import net.sf.bbarena.model.event.game.EndTurnEvent;
import net.sf.bbarena.model.event.game.NewHalfEvent;
import net.sf.bbarena.model.event.game.TurnoverEvent;

import java.util.List;

/**
 * Created by Teg on 26/11/2016.
 */
public class MatchTime extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {

    }

    @Override
    public void afterDoEvent(Event e) {
        if (e instanceof EndTurnEvent) {
            int half = getEventManager().getArena().getHalf();
            List<TurnMarker> turnMarkers = getEventManager().getArena().getTurnMarkers();
            if (turnMarkers.get(0).getTurn() == 8 && turnMarkers.get(1).getTurn() == 8) {
                if (half == 2) {
                    EndGameEvent endGameEvent = new EndGameEvent();
                    getEventManager().forward(endGameEvent);
                } else {
                    NewHalfEvent newHalfEvent = new NewHalfEvent();
                    getEventManager().forward(newHalfEvent);
                }
            }
        } else if (e instanceof TurnoverEvent) {
            getEventManager().forward(new EndTurnEvent());
        }
    }

    @Override
    public void beforeUndoEvent(Event e) {

    }

    @Override
    public void afterUndoEvent(Event e) {

    }

    @Override
    public void eventSizeChanged(int newSize) {

    }
}
