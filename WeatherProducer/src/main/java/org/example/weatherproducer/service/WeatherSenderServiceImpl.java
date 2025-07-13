package org.example.weatherproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.WeatherEvent;
import org.example.weatherproducer.dto.CityDto;
import org.example.weatherproducer.enums.WeatherCondition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherSenderServiceImpl implements WeatherSenderService {
    private final Random random = new Random();
    private final CityService cityService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendWeather(String topic) {
        WeatherCondition condition = WeatherCondition.values()[random.nextInt(WeatherCondition.values().length)];
        List<CityDto> cities = cityService.findAll();
        int citySize = cities.size();

        WeatherEvent weather = new WeatherEvent(
                cities.get(random.nextInt(citySize)).name(),
                random.nextInt(0, 35),
                condition.getDescription(),
                LocalDateTime.now()
        );


        kafkaTemplate.send(topic, weather.getCity(), weather)
                .whenComplete((r, e) -> {
                    if (e != null) {
                        log.error("Failed to send message", e);
                    } else {
                        log.info("Send weather to {}, partition {}", topic, r.getRecordMetadata().partition());
                        log.info("weather {}", weather);
                    }
                });
    }
}
