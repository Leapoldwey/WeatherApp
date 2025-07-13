package org.example.weatherproducer.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherproducer.dto.CityDto;
import org.example.weatherproducer.entity.City;
import org.example.weatherproducer.exception.custom.CityNotFoundException;
import org.example.weatherproducer.mapper.CityMapper;
import org.example.weatherproducer.repository.CityRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    @Override
    @Caching(
            put = @CachePut(value = "city", key = "#result.id"),
            evict = @CacheEvict(value = "city", key = "'сities'")
    )
    public CityDto save(CityDto cityDto) {
        City city = new City();
        city.setId(UUID.randomUUID());
        city.setName(cityDto.name());

        cityRepository.save(city);

        return cityMapper.mapTo(city);
    }

    @Override
    @Caching(
            put = @CachePut(value = "city", key = "#result.id"),
            evict = @CacheEvict(value = "city", key = "'сities'")
    )
    public CityDto update(CityDto cityDto) {
        if (cityDto.id() == null || !cityRepository.existsById(cityDto.id())) {
            throw new CityNotFoundException("invalid ID");
        }

        City city = cityMapper.mapTo(cityDto);

        cityRepository.save(city);

        return cityMapper.mapTo(city);
    }

    @Override
    @Cacheable(value = "city", key = "#id")
    public CityDto findById(UUID id) {
        return cityRepository.findById(id).map(cityMapper::mapTo).orElseThrow(() ->
                new CityNotFoundException(String.format("city with id %s not found", id))
        );
    }

    @Override
    @Cacheable(value = "city", key = "'сities'")
    public List<CityDto> findAll() {
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "city", key = "#id"),
            @CacheEvict(value = "city", key = "'сities'")
    })
    public void deleteById(UUID id) {
        if (!cityRepository.existsById(id)) {
            throw new CityNotFoundException(String.format("city with id %s not found", id));
        }
        cityRepository.deleteById(id);
    }
}
