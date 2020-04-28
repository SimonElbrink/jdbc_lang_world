package org.example;

import org.example.dao.CityDao;
import org.example.dao.CityDaoImpl;
import org.example.model.City;


public class App
{
    public static void main( String[] args )
    {
        CityDao cityDao = new CityDaoImpl();

        //cityDao.getFromDistrict("lisboa").forEach(System.out::println);

        //cityDao.getAll().forEach(System.out::println);

        City city = new City(698554555,"Kalmar", "SWE", "Kalmar Län", 40_000);

        //System.out.println(cityDao.findById(698554555));

        //System.out.println("updated successfully : " + cityDao.updateCity(city));


/*
        //CREATING -----------------------------------------------------------------------
        System.out.println("Successfully created? : " + cityDao.createNewCity(city));

        System.out.println(cityDao.createNewCity_ReturnsCity(city));

        System.out.println(cityDao.createCity(city));
*/

/*
        //FIND DELETE FIND ---------------------------------------------------------------
        //System.out.println(cityDao.findById(4080));
        //System.out.println(cityDao.removeCityById(4080));
        //System.out.println(cityDao.findById(4080));
*/

    }
}
