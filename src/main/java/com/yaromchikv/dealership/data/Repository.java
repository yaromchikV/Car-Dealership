package com.yaromchikv.dealership.data;

import com.yaromchikv.dealership.connection.MyConnection;
import com.yaromchikv.dealership.data.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.yaromchikv.dealership.utils.Constants.*;

public class Repository {

    @FunctionalInterface
    private interface ResultConverter<T> {
        T convert(ResultSet resultSet) throws SQLException;
    }

    private <T> ObservableList<T> getByQuery(String query, ResultConverter<T> converter) {
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

    public ObservableList<Employee> getEmployees(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT * FROM employeesView " +
                filter;
        return getByQuery(query, rs -> new Employee(rs.getInt(ID), rs.getString(SURNAME), rs.getString(SURNAME), rs.getString(MIDDLE_NAME), rs.getString(DATE_OF_BIRTH), rs.getString(PHONE_NUMBER), rs.getString(POSITION), rs.getDouble(SALARY), rs.getString(START_DATE), rs.getString(USERNAME), rs.getString(PASSWORD)));
    }

    public ObservableList<Position> getPositions() {
        //language=SQL
        String query = "SELECT * FROM positionsView";
        return getByQuery(query, rs -> new Position(rs.getInt(ID), rs.getString(NAME), rs.getDouble(SALARY)));
    }

    public ObservableList<Car> getCars(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT * FROM carsView " + filter;
        return getByQuery(query,
                rs -> new Car(rs.getInt(ID), rs.getString(MAKE), rs.getString(MODEL), rs.getString(STYLE), rs.getInt(YEAR), rs.getDouble(PRICE)));
    }

    public ObservableList<Customer> getCustomers(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT * FROM customersView " + filter;
        return getByQuery(query,
                rs -> new Customer(rs.getInt(ID), rs.getString(SURNAME), rs.getString(NAME), rs.getString(MIDDLE_NAME), rs.getString(DATE_OF_BIRTH), rs.getString(PHONE_NUMBER), rs.getString(EMAIL)));
    }

    public ObservableList<Order> getOrders(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT * FROM ordersView " + filter;
        return getByQuery(query, rs -> new Order(rs.getInt(ID), rs.getString(DATE_TIME), rs.getInt(CUSTOMER_ID), rs.getString(CUSTOMER_FULLNAME), rs.getInt(CAR_ID), rs.getString(CAR_NAME), rs.getInt(EMPLOYEE_ID), rs.getString(EMPLOYEE_FULLNAME), rs.getString(STATUS)));
    }

    public ObservableList<Style> getStyles() {
        //language=SQL
        String query = "SELECT * FROM stylesView";
        return getByQuery(query, rs -> new Style(rs.getInt(ID), rs.getString(NAME)));
    }

    public ObservableList<String> getPositionNames() {
        //language=SQL
        String query = "SELECT NAME FROM positions";
        return getByQuery(query, rs -> rs.getString(NAME));
    }

    public ObservableList<String> getStyleNames() {
        //language=SQL
        String query = "SELECT NAME FROM styles";
        return getByQuery(query, rs -> rs.getString(NAME));
    }

    public void executeUpdate(String query) {
        Connection connection = MyConnection.connection;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Integer getIdByUsername(String username) {
        Integer id = null;
        Connection connection = MyConnection.connection;
        try {
            //language=SQL
            String query = "SELECT ID " +
                    "FROM accounts " +
                    "WHERE USERNAME = '" + username + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs == null) return null;
            while (rs.next()) {
                id = rs.getInt(ID);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return id;
    }

    public Integer getLastUserId() {
        Integer id = null;
        Connection connection = MyConnection.connection;
        try {
            //language=SQL
            String query = "SELECT ID " +
                    "FROM accounts " +
                    "ORDER BY ID DESC LIMIT 1";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs == null) return null;
            while (rs.next()) {
                id = rs.getInt(ID);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return id;
    }

    public boolean checkUsernameAndPassword(String username, String password) {
        boolean hasAccess = false;
        Connection connection = MyConnection.connection;
        try {
            //language=SQL
            String query = "SELECT ID " +
                    "FROM accounts " +
                    "WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs == null) return false;
            while (rs.next()) {
                hasAccess = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return hasAccess;
    }
}
