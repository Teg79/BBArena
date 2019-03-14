package bbarena.rules.crap;

import bbarena.model.Weather;

public class WeatherTable {

    public static Weather getWeather(Integer roll) {
        Weather res = Weather.Companion.getWeather(Weather.WeatherType.NICE);

        if (roll == 2) {
            res = Weather.Companion.getWeather(Weather.WeatherType.SWELTERING_HEAT);
        } else if (roll == 3) {
            res = Weather.Companion.getWeather(Weather.WeatherType.VERY_SUNNY);
        } else if (roll == 11) {
            res = Weather.Companion.getWeather(Weather.WeatherType.POURING_RAIN);
        } else if (roll == 12) {
            res = Weather.Companion.getWeather(Weather.WeatherType.BLIZZARD);
        }

        return res;
    }

}
