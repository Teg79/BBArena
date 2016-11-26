package net.sf.bbarena.rules.crap.listeners;

import net.sf.bbarena.model.RollResult;
import net.sf.bbarena.model.Weather;
import net.sf.bbarena.model.event.BaseEventFlowListener;
import net.sf.bbarena.model.event.Event;
import net.sf.bbarena.model.event.game.EndDriveEvent;
import net.sf.bbarena.model.event.game.PlayerPlayableEvent;
import net.sf.bbarena.model.team.Player;

import java.util.Set;

import static net.sf.bbarena.model.Roll.roll;
import static net.sf.bbarena.model.event.Die.D6;

/**
 * See Sweltering Heat pag 20
 */
public class SwelteringHeat extends BaseEventFlowListener {

    @Override
    public void beforeDoEvent(Event e) {
        if (e instanceof EndDriveEvent && Weather.getWeather(Weather.WeatherType.SWELTERING_HEAT).equals(getEventManager().getArena().getWeather())) {
            Set<Player> playersOnThePitch = getEventManager().getArena().getPitch().getPlayers();
            for (Player player : playersOnThePitch) {
                RollResult roll = roll(1, D6, e, this.getClass().getSimpleName(), player.getTeam().getName() + " " + player.getNum());
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
