package org.example.event;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEvent {
    private String city;
    private int temperature;
    private String condition;
    private LocalDateTime time;
}
