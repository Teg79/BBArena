package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Weather;
import net.sf.bbarena.model.util.Concat;
import net.sf.bbarena.model.util.Pair;

public class ChangeWeatherEvent extends GameEvent {

    private Weather _newWeather;
    private Weather _oldWeather;

    public ChangeWeatherEvent() {
    }

    public void setNewWeather(Weather newWeather) {
        _newWeather = newWeather;
    }

    @Override
    protected void doEvent(Arena arena) {
        _oldWeather = arena.getWeather();
        arena.setWeather(_newWeather);
    }

    @Override
    protected void undoEvent(Arena arena) {
        arena.setWeather(_oldWeather);
    }

    @Override
    public String getString() {
        return Concat.buildLog(getClass(),
                new Pair("weather", _newWeather));
    }
}
