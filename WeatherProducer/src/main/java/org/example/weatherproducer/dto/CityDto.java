package org.example.weatherproducer.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;


import java.util.UUID;

public record CityDto(
        UUID id,
        @NotBlank @Column(unique = true) String name
) {
}
