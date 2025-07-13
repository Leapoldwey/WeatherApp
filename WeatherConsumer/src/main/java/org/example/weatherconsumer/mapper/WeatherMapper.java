package org.example.weatherconsumer.mapper;

import org.example.weatherconsumer.dto.WeatherDto;
import org.example.weatherconsumer.entity.Weather;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    Weather mapTo(WeatherDto weatherDto);
    WeatherDto mapTo(Weather weather);
}
