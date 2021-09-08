package com.weatherupdates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WeatherUpdatesRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void addLocationToList(String location) {
        String sql = "INSERT INTO location (name) VALUES (:name)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", location);
        jdbcTemplate.update(sql, paramMap);
    }

    public boolean isAlreadyInList(String location) {
        String sql = "SELECT name FROM location WHERE name ILIKE :name";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", location);
        try {
            jdbcTemplate.queryForObject(sql, paramMap, String.class);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

    }

}
