package com.weatherupdates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherUpdatesService {

    @Autowired
    private WeatherUpdatesRepository weatherUpdatesRepository;


    //get city name input
    public String addLocationToList(String location) {
        boolean alreadyExists = weatherUpdatesRepository.isAlreadyInList(location);
        if (alreadyExists) {
            return location + " is already on the list";
        }
        weatherUpdatesRepository.addLocationToList(location);
        return location + " added to list.";
    }

}
