package org.example.dao;

import org.example.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CityDaoImpl implements CityDao {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/world?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "1234";

    private static final String GET_ALL_CITIES = "SELECT * FROM city";
    private static final String GET_ALL_FROM_DISTRICT = "SELECT * FROM city WHERE District LIKE ?";//prepend statement
    private static final String CREATE_CITY = "INSERT INTO city (Name, CountryCode, District, Population) VALUES (?,?,?,?)";
    private static final String UPDATE_CITY = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
    private static final String Delete_CITY_BY_ID = "DELETE FROM city WHERE ID = ?";


    @Override
    public List<City> getAll() {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<City> cityList = new ArrayList();

        try {

            connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, USER_PASSWORD);

            statement = connection.createStatement();

            resultSet = statement.executeQuery(GET_ALL_CITIES);

            while (resultSet.next()) {
                cityList.add(
                        new City(
                                resultSet.getInt("ID"),
                                resultSet.getString("name"),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5)
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cityList;

    }

    @Override
    public List<City> getFromDistrict(String district) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<City> cityList = new ArrayList();

        try {

            connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, USER_PASSWORD);

            preparedStatement = connection.prepareStatement(GET_ALL_FROM_DISTRICT);

            preparedStatement.setString(1, district + '%');

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cityList.add(
                        new City(
                                resultSet.getInt("ID"),
                                resultSet.getString("name"),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5)
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cityList;

    }

    @Override
    public City findById(int id){

        City foundCity = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection(CONNECTION_STRING,USER_NAME,USER_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE ID = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

           while(resultSet.next()){
               foundCity = new City(
                       resultSet.getInt("ID"),
                       resultSet.getString("Name"),
                       resultSet.getString("CountryCode"),
                       resultSet.getString("District"),
                       resultSet.getInt("Population")
               );
           }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {

            try{
                if (resultSet != null){
                    resultSet.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            }catch (SQLException e){
                e.printStackTrace();
            }

        }
        return foundCity;
    }

    @Override
    public boolean createNewCity(City city){

        boolean successfullyCreated = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = DriverManager.getConnection(CONNECTION_STRING,USER_NAME,USER_PASSWORD);
            // "INSERT INTO city (Name, CountryCode, District, Population) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(CREATE_CITY);

            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());

            preparedStatement.executeUpdate();
            successfullyCreated = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


        return successfullyCreated;
    }

    private PreparedStatement createPreparedStatement_update(Connection connection, City city) throws SQLException {
        // UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CITY);
        preparedStatement.setString(1,city.getName());
        preparedStatement.setString(2,city.getCountryCode());
        preparedStatement.setString(3,city.getDistrict());
        preparedStatement.setInt(4, city.getPopulation());
        preparedStatement.setInt(5, city.getId());

        return preparedStatement;
    }


    @Override
    public City updateCity(City city){

        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,USER_NAME,USER_PASSWORD);
        PreparedStatement preparedStatement = createPreparedStatement_update(connection, city);
        ){
            preparedStatement.execute();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return findById(city.getId());
    }

    @Override
    public boolean removeCityById(int city_id){

        boolean successfullyRemoved = false;

        try(Connection connection = DriverManager.getConnection(CONNECTION_STRING,USER_NAME,USER_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(Delete_CITY_BY_ID);
        ){
            preparedStatement.setInt(1, city_id);
            preparedStatement.execute();
             successfullyRemoved = true;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return successfullyRemoved;
    }


}
