package com.weatherupdates;

import com.weatherupdates.dto.DisplayInfoDto;
import com.weatherupdates.dto.LocationDto;
import com.weatherupdates.dto.WeatherInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

    public List<DisplayInfoDto> getLocationsWeatherUpdates(String location) {
        String sql = "SELECT * FROM weather_info wi JOIN location l ON wi.location_id = l.id WHERE name ILIKE :location";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", location);
        List<DisplayInfoDto> result = jdbcTemplate.query(sql, paramMap, new DisplayInfoRowMapper());
        return result;
    }

    public void insertWeatherInfoIntoTable(WeatherInfoDto info) {
        String sql = "INSERT INTO weather_info (location_id, temperature, wind_speed, humidity, time) " +
                "VALUES (:locationId, :temp, :windSpeed, :mainHumidity, :time)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("locationId", info.getLocationId());
        paramMap.put("temp", info.getTemperature());
        paramMap.put("windSpeed", info.getWindSpeed());
        paramMap.put("mainHumidity", info.getHumidity());
        paramMap.put("time", info.getTime());
        jdbcTemplate.update(sql, paramMap);
    }

    public List<LocationDto> getAllLocationsOnList() {
        String sql = "SELECT * FROM location";
        List<LocationDto> result = jdbcTemplate.query(sql, new LocationRowMapper());
        return result;
    }

    public List<String> displayMyList() {
        String sql = "SELECT name FROM location";
        Map<String, Object> paramMap = new HashMap<>();
        List<String> result = jdbcTemplate.queryForList(sql, paramMap, String.class);
        return result;
    }

    public void deleteLocationFromList(String location) {
        String sql = "DELETE FROM location WHERE name ILIKE :location";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("location", location);
        jdbcTemplate.update(sql, paramMap);
    }

    public static class DisplayInfoRowMapper implements RowMapper<DisplayInfoDto> {

        @Override
        public DisplayInfoDto mapRow(ResultSet resultSet, int i) throws SQLException {
            DisplayInfoDto result = new DisplayInfoDto();
            result.setName(resultSet.getString("name"));
            result.setTemperature(resultSet.getDouble("temperature"));
            result.setWindSpeed(resultSet.getDouble("wind_speed"));
            result.setHumidity(resultSet.getDouble("humidity"));
            result.setTime(resultSet.getTimestamp("time").toLocalDateTime());
            return result;
        }
    }

    public static class LocationRowMapper implements RowMapper<LocationDto> {

        @Override
        public LocationDto mapRow(ResultSet resultSet, int i) throws SQLException {
            LocationDto result = new LocationDto();
            result.setId(resultSet.getInt("id"));
            result.setName(resultSet.getString("name"));
            return result;
        }
    }
}
