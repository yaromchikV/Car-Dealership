package com.yaromchikv.dealership;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyConnection {

    public static Connection connection;

    public static void setConnection() {
        try {
            ResourceBundle reader = ResourceBundle.getBundle("database");
            Connection connection = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            System.out.println("Connection to Dealership database succesfull!");
            MyConnection.connection = connection;
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex.getMessage());
            MyConnection.connection = null;
        }
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
