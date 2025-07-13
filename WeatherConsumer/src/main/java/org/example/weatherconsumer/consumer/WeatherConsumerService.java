package org.example.weatherconsumer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.WeatherEvent;
import org.example.weatherconsumer.dto.WeatherDto;
import org.example.weatherconsumer.service.WeatherService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${weather-topic}", groupId = "${spring.kafka.consumer.group-id}")
public class WeatherConsumerService {
    private final WeatherService weatherServiceImpl;

    @KafkaHandler
    public void handleEvent(@Payload WeatherEvent event, Acknowledgment ack) {
        try {
            WeatherDto weatherDto = new WeatherDto(
                    UUID.randomUUID(),
                    event.getCity(),
                    event.getTemperature(),
                    event.getCondition(),
                    event.getTime()
            );

            log.info("Received event: {}. Generated uuid {}", event, event.getCity());

            weatherServiceImpl.save(weatherDto);

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing event: {}", event, e);
        }
    }
}
