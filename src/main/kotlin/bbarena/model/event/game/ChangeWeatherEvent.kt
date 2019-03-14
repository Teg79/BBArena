package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Weather
import bbarena.model.util.Concat

class ChangeWeatherEvent(var newWeather: Weather, var oldWeather: Weather) : GameEvent() {

    override val string: String
        get() = Concat.buildLog(javaClass,
                Pair("weather", newWeather))

    override fun doEvent(arena: Arena) {
        oldWeather = arena.weather
        arena.weather = newWeather
    }

    override fun undoEvent(arena: Arena) {
        arena.weather = oldWeather
    }
}
