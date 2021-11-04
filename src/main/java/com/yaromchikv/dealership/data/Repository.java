package com.yaromchikv.dealership.data;

import com.yaromchikv.dealership.MyConnection;
import com.yaromchikv.dealership.data.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.yaromchikv.dealership.Constants.*;

public class Repository {

    @FunctionalInterface
    private interface ResultConverter<T> {
        T convert(ResultSet resultSet) throws SQLException;
    }

    public <T> ObservableList<T> getAll(String query, ResultConverter<T> converter) {
        ObservableList<T> list = FXCollections.observableArrayList();
        Connection connection = MyConnection.connection;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                T t = converter.convert(rs);
                list.add(t);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ObservableList<Car> getAllCars() {
        return getAll("SELECT * FROM " + CARS_TABLE,
                rs -> new Car(rs.getInt(ID), rs.getInt(STYLE_ID), rs.getString(MAKE), rs.getString(MODEL), rs.getInt(YEAR), rs.getDouble(PRICE)));
    }

    public ObservableList<Customer> getAllCustomers() {
        return getAll("SELECT * FROM " + CUSTOMERS_TABLE,
                rs -> new Customer(rs.getInt(ID), rs.getString(SURNAME), rs.getString(NAME), rs.getString(MIDDLE_NAME), rs.getDate(DATE_OF_BIRTH).toLocalDate(), rs.getString(PHONE_NUMBER), rs.getString(EMAIL)));
    }

    public ObservableList<Employee> getAllEmployees() {
        return getAll("SELECT * FROM " + EMPLOYEES_TABLE,
                rs -> new Employee(rs.getInt(ID), rs.getString(NAME), rs.getString(SURNAME), rs.getString(MIDDLE_NAME), rs.getDate(DATE_OF_BIRTH).toLocalDate(), rs.getString(PHONE_NUMBER), rs.getInt(POSITION_ID), rs.getDate(START_DATE).toLocalDate()));
    }

    public ObservableList<Order> getAllOrders() {
        return getAll("SELECT * FROM " + ORDERS_TABLE,
                rs -> new Order(rs.getInt(ID), rs.getObject(DATE_TIME, LocalDateTime.class), rs.getInt(CUSTOMER_ID), rs.getInt(CAR_ID), rs.getInt(EMPLOYEE_ID), rs.getBoolean(IS_COMPLETED)));
    }

    public ObservableList<Position> getAllPositions() {
        return getAll("SELECT * FROM " + POSITIONS_TABLE,
                rs -> new Position(rs.getInt(ID), rs.getString(NAME), rs.getDouble(SALARY)));
    }

    public ObservableList<Style> getAllStyles() {
        return getAll("SELECT * FROM " + STYLES_TABLE,
                rs -> new Style(rs.getInt(ID), rs.getString(NAME)));
    }

    public ObservableList<User> getAllUsers() {
        return getAll("SELECT * FROM " + USERS_TABLE,
                rs -> new User(rs.getInt(ID), rs.getString(USERNAME), rs.getString(PASSWORD)));
    }
}






