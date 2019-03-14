package bbarena.rules.crap.listeners;

import bbarena.model.RollResult;
import bbarena.model.Weather;
import bbarena.model.event.BaseEventFlowListener;
import bbarena.model.event.Event;
import bbarena.model.event.game.EndDriveEvent;
import bbarena.model.event.game.PlayerPlayableEvent;
import bbarena.model.team.Player;

import java.util.Set;

import static bbarena.model.Roll.roll;
import static bbarena.model.event.Die.D6;

/**
 * See Sweltering Heat pag 20
 */
public class SwelteringHeat extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {
        if (e instanceof EndDriveEvent && Weather.Companion
            .getWeather(Weather.WeatherType.SWELTERING_HEAT).equals(getEventManager().getArena().getWeather())) {
            Set<Player> playersOnThePitch = getEventManager().getArena().getPitch().getPlayers();
            for (Player player : playersOnThePitch) {
                RollResult roll = Companion
                    .roll(1, D6, e, this.getClass().getSimpleName(), player.getTeam().getName() + " " + player.getNum());
                if (roll.getSum() == 1) {
                    getEventManager().putEvent(new PlayerPlayableEvent(player.getId(), false));
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
