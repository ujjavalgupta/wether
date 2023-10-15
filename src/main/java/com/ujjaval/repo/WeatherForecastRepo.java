package com.ujjaval.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ujjaval.entity.WeatherForecast;

@Repository
public interface WeatherForecastRepo extends JpaRepository<WeatherForecast, Long> {

	Optional<WeatherForecast> findByCityAndCountry(String city, String country);
}