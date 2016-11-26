package net.sf.bbarena.rules.crap.listeners;

import net.sf.bbarena.model.RollResult;
import net.sf.bbarena.model.Weather;
import net.sf.bbarena.model.event.BaseEventFlowListener;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.game.MovePlayerEvent;
import net.sf.bbarena.rules.crap.MoveAction;

import java.util.stream.Stream;

/**
 * See Blizzard pag 20
 */
public class Blizzard extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {
        if (Weather.getWeather(Weather.WeatherType.BLIZZARD).equals(getEventManager().getArena().getWeather()) && e instanceof MovePlayerEvent) {

            Stream<RollResult> gfi = e.getDiceRolls().stream().filter(rollResult -> MoveAction.GO_FOR_IT.equals(rollResult.getWhy()));
            gfi.forEach(rollResult -> rollResult.addModifier(-1, this.getClass().getSimpleName()));

        }
    }

    @Override
    public void afterDoEvent(Event e) {

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
