package com.weatherupdates.dto;

import java.io.Serializable;
import java.util.Map;

public class WeatherResponseDto implements Serializable {

    private Map<String, Double> main;
    private Map<String, Double> wind;

    public Map<String, Double> getMain() {
        return main;
    }

    public void setMain(Map<String, Double> main) {
        this.main = main;
    }

    public Map<String, Double> getWind() {
        return wind;
    }

    public void setWind(Map<String, Double> wind) {
        this.wind = wind;
    }
}
