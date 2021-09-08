package com.weatherupdates;

import com.weatherupdates.dto.AddLocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("weather-updates")
public class WeatherUpdatesController {

    @Autowired
    private WeatherUpdatesService weatherUpdatesService;


    //get city name input
    @PostMapping("add-location")
    public String addLocationToList(@RequestBody AddLocationRequest request) {
        return weatherUpdatesService.addLocationToList(request.getLocation());
    }

}
