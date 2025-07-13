package org.example.weatherconsumer.service;

import org.example.weatherconsumer.dto.WeatherDto;

import java.time.LocalDateTime;

public interface WeatherService {
    WeatherDto save(WeatherDto weatherDto);
    void clearOldData(LocalDateTime deleteBefore);
}
