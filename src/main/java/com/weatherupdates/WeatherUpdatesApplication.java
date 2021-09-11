package com.weatherupdates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherUpdatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherUpdatesApplication.class, args);
    }

}
