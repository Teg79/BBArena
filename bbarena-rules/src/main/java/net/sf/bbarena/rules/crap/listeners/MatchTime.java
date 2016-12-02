package net.sf.bbarena.rules.crap.listeners;

import net.sf.bbarena.model.TurnMarker;
import net.sf.bbarena.model.event.BaseEventFlowListener;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.game.*;

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
                    EndDriveEvent endDriveEvent = new EndDriveEvent();
                    getEventManager().forward(endDriveEvent);
                    EndGameEvent endGameEvent = new EndGameEvent();
                    getEventManager().forward(endGameEvent);
                } else {
                    EndDriveEvent endDriveEvent = new EndDriveEvent();
                    getEventManager().forward(endDriveEvent);
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
