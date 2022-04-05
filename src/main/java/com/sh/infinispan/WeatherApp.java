package com.sh.infinispan;

public class WeatherApp {

    static final String[] locations = { "Rome, Italy", "Como, Italy", "Basel, Switzerland", "Bern, Switzerland",
            "London, UK", "Newcastle, UK", "Bucarest, Romania", "Cluj-Napoca, Romania", "Ottawa, Canada",
            "Toronto, Canada", "Lisbon, Portugal", "Porto, Portugal", "Raleigh, USA", "Washington, USA" };
    private final WeatherService weatherService;

    public WeatherApp() throws InterruptedException {
        weatherService = initWeatherService();
    }

    private WeatherService initWeatherService() {
        String apiKey = System.getenv("OWMAPIKEY");
        if ( apiKey == null) {
            apiKey = "526a98c3b3e799451b6a4e55b8c551a2";
        }
        if (apiKey == null) {
            System.out.println("WARNING: OWMAPIKEY environment variable not set, using the RandomWeatherService.");
            return new RandomWeatherService();
        } else {
            return new OpenWeatherMapService(apiKey);
        }
    }

    public void fetchWeather() {
        System.out.println("---- Fetching weather information ----");
        long start = System.currentTimeMillis();
        for (String location : locations) {
            LocationWeather weather = weatherService.getWeatherForLocation(location);
            if ( weather == null) continue;
            System.out.printf("%s - %s\n", location, weather);
        }
        System.out.printf("---- Fetched in %dms ----\n", System.currentTimeMillis() - start);
    }

    public void shutdown() {
    }

    public static void main(String[] args) throws Exception {
        WeatherApp app = new WeatherApp();

        app.fetchWeather();

        app.fetchWeather();

        app.shutdown();
    }

}