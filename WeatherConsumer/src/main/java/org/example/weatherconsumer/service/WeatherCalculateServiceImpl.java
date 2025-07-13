package org.example.weatherconsumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weatherconsumer.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherCalculateServiceImpl implements WeatherCalculateService {
    private final WeatherRepository weatherRepository;

    @Override
    public void weeklyWeatherNews() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.now().minusSeconds(35);

        log.info("The rainiest week (city, amount rainiest day) {}", rainiestWeek(start, end));
        log.info("The hottest day (city, temp, date) {}", hottestDay());
        log.info("The lowest average temperature in the city (city, temp) {}",
                lowestAvgTemp(start, end));
    }

    private Object[] rainiestWeek(LocalDateTime start, LocalDateTime end) {
        return weatherRepository.findTheRainiestWeek(start, end);
    }

    private Object[] hottestDay() {
        return weatherRepository.findTheHottestDay();
    }

    private Object[] lowestAvgTemp(LocalDateTime start, LocalDateTime end) {
        return weatherRepository.findCityWithLowestAvgTemp(start, end);
    }

}
