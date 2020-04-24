package org.example.dao;

import org.example.model.City;

import java.util.List;

public interface CityDao {

    List<City> getAll();

    List<City> getFromDistrict(String district);

    City findById(int id);

    boolean createNewCity(City city);

    City updateCity(City city);

    boolean removeCityById(int city_id);
}
