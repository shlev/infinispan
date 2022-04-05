package com.sh.infinispan;

public interface WeatherService {
    LocationWeather getWeatherForLocation(String location);
}
