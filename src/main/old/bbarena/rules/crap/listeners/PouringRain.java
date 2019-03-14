package bbarena.rules.crap.listeners;

import bbarena.model.RollResult;
import bbarena.model.Weather;
import bbarena.model.event.BaseEventFlowListener;
import bbarena.model.event.Event;
import bbarena.model.event.game.CatchBallEvent;
import bbarena.model.event.game.PickUpBallEvent;

import java.util.Optional;

/**
 * See Pouring Rain pag 20
 */
public class PouringRain extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {
        if (Weather.Companion.getWeather(Weather.WeatherType.POURING_RAIN).equals(getEventManager().getArena().getWeather())) {
            if (e instanceof CatchBallEvent || e instanceof PickUpBallEvent) {

                Optional<RollResult> roll = e.getDiceRolls().stream().filter(rollResult ->
                        CatchBallEvent.Companion.getCATCH_ROLL()
                            .equals(rollResult.getWhy()) || PickUpBallEvent.Companion.getPICK_UP_BALL_ROLL().equals(rollResult.getWhy())
                ).findFirst();

                if (roll.isPresent()) {
                    roll.get().addModifier(-1, this.getClass().getSimpleName());
                }
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
