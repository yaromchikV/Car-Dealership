package com.yaromchikv.dealership.connection;

import javafx.application.Application;

import java.sql.*;
import java.util.ResourceBundle;

public class MyConnection {

    public static Connection connection;

    public static void setConnection() {
        try {
            ResourceBundle reader = ResourceBundle.getBundle("database");
            connection = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            System.out.println("Connection to Dealership database successful!");
        } catch (Exception e) {
            System.out.println("Connection failed..." + e.getMessage());
            connection = null;
        }
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
