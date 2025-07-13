package org.example.weatherproducer.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.weatherproducer.service.WeatherSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class WeatherProducerScheduler {
    @Value("${weather-topic}")
    private String TOPIC;
    private final WeatherSenderService weatherSenderService;

    @Scheduled(fixedRate = 5000)
    public void sendWeather() {
        weatherSenderService.sendWeather(TOPIC);
    }
}
