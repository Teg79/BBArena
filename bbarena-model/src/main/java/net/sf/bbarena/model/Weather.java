package net.sf.bbarena.model;

public interface Weather {

    enum WeatherType {
        SWELTERING_HEAT,
        VERY_SUNNY,
        NICE,
        POURING_RAIN,
        BLIZZARD
    }

    static Weather getWeather(WeatherType weatherType) {
        return getWeather(weatherType.name());
    }

    static Weather getWeather(String weatherType) {
        return new Weather() {
            @Override
            public String getName() {
                return weatherType;
            }
            @Override
            public String toString() {
                return getName();
            }
        };
    }

    String getName();

}
