package org.example.weatherproducer.enums;

import lombok.Getter;

@Getter
public enum WeatherCondition {
    SUNNY("Солнечно"),
    CLOUDY("Облачно"),
    RAINY("Дождливо"),;

    private final String description;

    WeatherCondition(String description) {
        this.description = description;
    }

}
