package org.example.weatherconsumer.scheduler;


import lombok.RequiredArgsConstructor;
import org.example.weatherconsumer.service.WeatherService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class DeleteThePreviousWeekScheduler {
    private final WeatherService weatherServiceImpl;

    @Scheduled(fixedRate = 35000)
    public void clearOldData() {
        weatherServiceImpl.clearOldData(LocalDateTime.now().minusSeconds(75));
    }
}
