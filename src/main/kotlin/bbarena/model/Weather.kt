package bbarena.model

fun getWeather(weatherType: WeatherType): Weather = getWeather(weatherType.name)

fun getWeather(weatherType: String): Weather = Weather(weatherType)

enum class WeatherType {
    SWELTERING_HEAT,
    VERY_SUNNY,
    NICE,
    POURING_RAIN,
    BLIZZARD
}

data class Weather(val name: String = WeatherType.NICE.name)
