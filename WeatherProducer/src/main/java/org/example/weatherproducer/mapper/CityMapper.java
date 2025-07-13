package org.example.weatherproducer.mapper;

import org.example.weatherproducer.dto.CityDto;
import org.example.weatherproducer.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City mapTo(CityDto cityDto);
    CityDto mapTo(City city);
}
