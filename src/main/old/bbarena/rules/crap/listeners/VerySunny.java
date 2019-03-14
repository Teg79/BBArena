package bbarena.rules.crap.listeners;

import bbarena.model.RollResult;
import bbarena.model.Weather;
import bbarena.model.event.BaseEventFlowListener;
import bbarena.model.event.Event;
import bbarena.model.event.game.PassBallEvent;

import java.util.Optional;

/**
 * See Very Sunny pag 20
 */
public class VerySunny extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {
        if (e instanceof PassBallEvent
                && Weather.Companion.getWeather(Weather.WeatherType.VERY_SUNNY).equals(getEventManager().getArena().getWeather())) {

            Optional<RollResult> passRoll = e.getDiceRolls().stream().filter(
                    rollResult -> PassBallEvent.Companion.getPASS_ROLL().equals(rollResult.getWhy())
            ).findFirst();
            if (passRoll.isPresent()) {
                passRoll.get().addModifier(-1, this.getClass().getSimpleName());
            }
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
