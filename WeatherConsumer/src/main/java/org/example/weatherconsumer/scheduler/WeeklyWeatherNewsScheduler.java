package org.example.weatherconsumer.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.weatherconsumer.service.WeatherCalculateService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class WeeklyWeatherNewsScheduler {
    public final WeatherCalculateService weatherCalculateServiceImpl;

    @Scheduled(fixedRate = 35000)
    public void scheduled() {
        weatherCalculateServiceImpl.weeklyWeatherNews();
    }
}
