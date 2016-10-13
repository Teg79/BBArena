package net.sf.bbarena.model.event.game;

import net.sf.bbarena.model.Arena;
import net.sf.bbarena.model.Weather;

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
        _arena = arena;
        _oldWeather = _arena.getWeather();
        arena.setWeather(_newWeather);
    }

    @Override
    protected void undoEvent() {
        _arena.setWeather(_oldWeather);
    }

    @Override
    public String getString() {
        StringBuilder res = new StringBuilder();
        res.append(getClass().getSimpleName())
                .append("[weather:")
                .append(_newWeather)
                .append("]");
        return res.toString();
    }
}
