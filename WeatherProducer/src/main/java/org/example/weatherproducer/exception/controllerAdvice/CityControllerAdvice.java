package org.example.weatherproducer.exception.controllerAdvice;

import org.example.weatherproducer.exception.custom.CityNotFoundException;
import org.example.weatherproducer.exception.ResponseOnError.ResponseOnError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CityControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseOnError handleCityNotFoundException(CityNotFoundException ex) {
        return new ResponseOnError(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseOnError handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        });

        return new ResponseOnError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                errors.toString()
        );
    }
}
