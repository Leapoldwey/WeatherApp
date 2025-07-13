package org.example.weatherproducer.service;

import org.example.weatherproducer.dto.CityDto;

import java.util.List;
import java.util.UUID;

public interface CityService {
    CityDto save(CityDto cityDto);
    CityDto update(CityDto cityDto);
    CityDto findById(UUID id);
    List<CityDto> findAll();
    void deleteById(UUID id);
}
