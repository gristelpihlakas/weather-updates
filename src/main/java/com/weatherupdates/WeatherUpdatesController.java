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
    public String addLocationToList(@RequestBody LocationRequest request) {
        return weatherUpdatesService.addLocationToList(request.getLocation());
    }

    @GetMapping("get-info")
    public ResponseEntity<List<DisplayInfoDto>> getLocationsWeatherUpdates(@RequestBody LocationRequest request) {
        return weatherUpdatesService.getLocationsWeatherUpdates(request.getLocation());
    }

    @GetMapping("display-list")
    public List<String> displayMyList() {
        return weatherUpdatesService.displayMyList();
    }

    @DeleteMapping("delete-location")
    public String deleteLocationFromList(@RequestBody LocationRequest request) {
        return weatherUpdatesService.deleteLocationFromList(request.getLocation());
    }
}
