package org.example.dao;

import org.example.model.City;

import java.util.List;

public interface CityDao {

    List<City> getAll();

    List<City> getFromDistrict(String district);

    City findById(int id);

    //Way 1 of creating
    boolean createNewCity(City city);

    //Way 2 of creating
    public City createNewCity_ReturnsCity(City city);

    //Way 3 of creating
    public City createCity(City city);

    City updateCity(City city);

    boolean removeCityById(int city_id);
}
