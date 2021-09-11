package com.weatherupdates;

import com.weatherupdates.config.ApiPropertiesReader;
import com.weatherupdates.dto.DisplayInfoDto;
import com.weatherupdates.dto.LocationDto;
import com.weatherupdates.dto.WeatherInfoDto;
import com.weatherupdates.dto.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class WeatherUpdatesService {

    @Autowired
    private WeatherUpdatesRepository weatherUpdatesRepository;

    public String addLocationToList(String location) {
        boolean alreadyExists = weatherUpdatesRepository.isAlreadyInList(location);
        if (alreadyExists) {
            return location + " is already on the list";
        }
        if (isLocationValid(location)) {
            weatherUpdatesRepository.addLocationToList(location);
            return location + " added to list.";
        } else {
            return "Did not recognize location: " + location + ".";
        }
    }

    public boolean isLocationValid(String location) {
        try {
            requestWeatherInfo(location);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<List<DisplayInfoDto>> getLocationsWeatherUpdates(String location) {
        if (weatherUpdatesRepository.isAlreadyInList(location)) {
            return ResponseEntity.ok().body(weatherUpdatesRepository.getLocationsWeatherUpdates(location));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    public WeatherResponseDto requestWeatherInfo(String location) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponseDto> response = restTemplate.
                getForEntity("https://api.openweathermap.org/data/2.5/weather?q=" + location +
                        "&appid=" + ApiPropertiesReader.getProperty("API_KEY") + "&units=metric", WeatherResponseDto.class);
        return response.getBody();
    }

    public WeatherInfoDto parseWeatherResponseInfo(WeatherResponseDto response, LocationDto location) {
        Map<String, Double> main = response.getMain();
        Map<String, Double> wind = response.getWind();
        WeatherInfoDto info = new WeatherInfoDto();
        info.setLocationId(location.getId());
        info.setTemperature(main.get("temp"));
        info.setHumidity(main.get("humidity"));
        info.setWindSpeed(wind.get("speed"));
        info.setTime(LocalDateTime.now());
        return info;
    }

    //set time interval (every 15 minutes - 900000ms)
    @Scheduled(fixedRate = 900000)
    public void getScheduledWeatherUpdates() {
        List<LocationDto> locations = weatherUpdatesRepository.getAllLocationsOnList();
        for (LocationDto location : locations) {
            WeatherResponseDto weatherResponse = requestWeatherInfo(location.getName());
            WeatherInfoDto info = parseWeatherResponseInfo(weatherResponse, location);
            weatherUpdatesRepository.insertWeatherInfoIntoTable(info);
        }
    }

    public List<String> displayMyList() {
        return weatherUpdatesRepository.displayMyList();
    }



    //delete a city from the list, but keep its previous logs
    public String deleteLocationFromList(String location) {
        boolean isInList = weatherUpdatesRepository.isAlreadyInList(location);
        if (!isInList) {
            return location + " was not on the list.";
        }
        weatherUpdatesRepository.deleteLocationFromList(location);
        return location + " was removed from the list.";
    }
}
