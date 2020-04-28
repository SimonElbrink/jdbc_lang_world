package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final DatabaseConnection instance = new DatabaseConnection();
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/world?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "1234";

    public DatabaseConnection() {
    }

    public static DatabaseConnection getInstance(){
        return instance;
    }

    public Connection getConnection(){

        Connection connection = null;

        try{
            connection = DriverManager.getConnection(CONNECTION_STRING,USER_NAME,USER_PASSWORD);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }
}
