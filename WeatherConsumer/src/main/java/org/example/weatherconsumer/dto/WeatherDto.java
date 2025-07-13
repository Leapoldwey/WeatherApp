package org.example.weatherconsumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;


public record WeatherDto (
        UUID id,
        String city,
        int temperature,
        String condition,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime time
){}