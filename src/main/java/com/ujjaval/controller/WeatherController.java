package com.ujjaval.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ujjaval.entity.WeatherForecast;
import com.ujjaval.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherForecast> getWeatherForecast(@RequestParam String city, @RequestParam String country) {
        try {
            CompletableFuture<WeatherForecast> future = weatherService.getWeatherForecast(city, country);
            WeatherForecast forecast = future.get(10, TimeUnit.SECONDS);
            if (forecast != null) {
                return ResponseEntity.ok(forecast);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}







