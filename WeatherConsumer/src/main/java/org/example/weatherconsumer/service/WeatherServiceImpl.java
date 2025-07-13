package org.example.weatherconsumer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherconsumer.dto.WeatherDto;
import org.example.weatherconsumer.entity.Weather;
import org.example.weatherconsumer.mapper.WeatherMapper;
import org.example.weatherconsumer.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final WeatherMapper weatherMapper;
    private final WeatherRepository weatherRepository;

    @Override
    public WeatherDto save(WeatherDto weatherDto) {
        Weather weather = new Weather(
                UUID.randomUUID(),
                weatherDto.city(),
                weatherDto.temperature(),
                weatherDto.condition(),
                weatherDto.time()
        );

        weatherRepository.save(weather);

        return weatherMapper.mapTo(weather);
    }

    @Override
    public void clearOldData(LocalDateTime deleteBefore) {
        weatherRepository.deleteOldWeatherByTimeBefore(deleteBefore);
    }
}
