package org.example.weatherproducer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.weatherproducer.dto.CityDto;
import org.example.weatherproducer.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityDto> save(@Valid @RequestBody CityDto cityDto, BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return new ResponseEntity<>(cityService.save(cityDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CityDto> update(@Valid @RequestBody CityDto cityDto, BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return new ResponseEntity<>(cityService.update(cityDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(cityService.findById(UUID.fromString(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> findAll() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        cityService.deleteById(UUID.fromString(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
