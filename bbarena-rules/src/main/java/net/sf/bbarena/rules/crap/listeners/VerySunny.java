package net.sf.bbarena.rules.crap.listeners;

import net.sf.bbarena.model.RollResult;
import net.sf.bbarena.model.Weather;
import net.sf.bbarena.model.event.BaseEventFlowListener;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.EventFlowListener;
import net.sf.bbarena.model.event.game.PassBallEvent;

import java.util.Optional;

/**
 * See Very Sunny pag 20
 */
public class VerySunny extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {
        if (e instanceof PassBallEvent
                && Weather.getWeather(Weather.WeatherType.VERY_SUNNY).equals(e.getArena().getWeather())) {

            Optional<RollResult> passRoll = e.getDiceRolls().stream().filter(
                    rollResult -> PassBallEvent.PASS_ROLL.equals(rollResult.getWhy())
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
