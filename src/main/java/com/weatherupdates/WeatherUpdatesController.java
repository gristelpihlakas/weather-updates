package com.weatherupdates;

import com.weatherupdates.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("weather-updates")
public class WeatherUpdatesController {

    @Autowired
    private WeatherUpdatesService weatherUpdatesService;

    @PostMapping("add-location")
    public String addLocationToList(@RequestBody AddLocationRequest request) {
        return weatherUpdatesService.addLocationToList(request.getLocation());
    }

    @GetMapping("{location}")
    public ResponseEntity<List<DisplayInfoDto>> getLocationsWeatherUpdates(@PathVariable("location") String location) {
        return weatherUpdatesService.getLocationsWeatherUpdates(location);
    }

    @GetMapping("display-list")
    public List<String> displayMyList() {
        return weatherUpdatesService.displayMyList();
    }


    //delete a city from the list, but keep its previous logs
    @DeleteMapping("delete-location")
    public String deleteLocationFromList(@RequestBody DeleteLocationRequest request) {
        return weatherUpdatesService.deleteLocationFromList(request.getLocation());
    }
}
