package com.ujjaval.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ujjaval.entity.WeatherForecast;
import com.ujjaval.repo.WeatherForecastRepo;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    
    private final WeatherForecastRepo weatherForecastRepository;
    @Value("${weather.api.url}")
    private String weatherApiUrl;
    @Value("${weather.api.api-key}")
    private String weatherApiKey;

    @Autowired
    public WeatherService(RestTemplate restTemplate, WeatherForecastRepo weatherForecastRepository) {
        this.restTemplate = restTemplate;
        this.weatherForecastRepository = weatherForecastRepository;
    }

    @Async
    public CompletableFuture<WeatherForecast> getWeatherForecast(String city, String country) {
        Optional<WeatherForecast> cachedForecast = weatherForecastRepository.findByCityAndCountry(city, country);
        if (cachedForecast.isPresent()) {
            return CompletableFuture.completedFuture(cachedForecast.get());
        }
        
        String apiUrl = weatherApiUrl + "?q=" + city + "," + country + "&appid=" + weatherApiKey;
        WeatherForecast forecast = restTemplate.getForObject(apiUrl, WeatherForecast.class);
        if (forecast != null) {
        	
            weatherForecastRepository.save(forecast);
        }

        return CompletableFuture.completedFuture(forecast);
    
    }
    
    
}
