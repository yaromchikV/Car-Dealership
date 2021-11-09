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
        String query = "SELECT employees.ID, employees.SURNAME, employees.NAME, employees.MIDDLE_NAME, DATE_FORMAT(employees.DATE_OF_BIRTH, '%d.%m.%Y') AS DATE_OF_BIRTH, employees.PHONE_NUMBER, positions.NAME AS POSITION, positions.SALARY, DATE_FORMAT(employees.START_DATE, '%d.%m.%Y') AS START_DATE, accounts.USERNAME, accounts.PASSWORD " +
                "FROM employees " +
                "JOIN positions ON employees.POSITION_ID = positions.ID " +
                "JOIN accounts ON employees.ID = accounts.ID " +
                filter +
                " ORDER BY ID ASC;";

        return getByQuery(query, rs -> new Employee(rs.getInt(ID), rs.getString(SURNAME), rs.getString(SURNAME), rs.getString(MIDDLE_NAME), rs.getString(DATE_OF_BIRTH), rs.getString(PHONE_NUMBER), rs.getString(POSITION), rs.getDouble(SALARY), rs.getString(START_DATE), rs.getString(USERNAME), rs.getString(PASSWORD)));
    }

    public ObservableList<Position> getPositions() {
        //language=SQL
        String query = "SELECT positions.ID, positions.NAME, positions.SALARY " +
                "FROM positions " +
                "ORDER BY ID ASC;";
        return getByQuery(query, rs -> new Position(rs.getInt(ID), rs.getString(NAME), rs.getDouble(SALARY)));
    }

    public ObservableList<Car> getCars(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT cars.ID, cars.MAKE, cars.MODEL, styles.NAME AS STYLE, cars.YEAR, cars.PRICE " +
                "FROM cars " +
                "JOIN styles ON cars.STYLE_ID = styles.ID " +
                filter +
                " ORDER BY ID ASC;";
        return getByQuery(query,
                rs -> new Car(rs.getInt(ID), rs.getString(MAKE), rs.getString(MODEL), rs.getString(STYLE), rs.getInt(YEAR), rs.getDouble(PRICE)));
    }

    public ObservableList<Customer> getCustomers(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT customers.ID, customers.SURNAME, customers.NAME, customers.MIDDLE_NAME, DATE_FORMAT(customers.DATE_OF_BIRTH, '%d.%m.%Y') AS DATE_OF_BIRTH, customers.PHONE_NUMBER, customers.EMAIL " +
                "FROM customers " +
                filter +
                " ORDER BY ID ASC;";
        return getByQuery(query,
                rs -> new Customer(rs.getInt(ID), rs.getString(SURNAME), rs.getString(NAME), rs.getString(MIDDLE_NAME), rs.getString(DATE_OF_BIRTH), rs.getString(PHONE_NUMBER), rs.getString(EMAIL)));
    }

    public ObservableList<Order> getOrders(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT orders.ID, DATE_FORMAT(orders.DATE_TIME, '%d.%m.%Y %H:%i:%S') AS DATE_TIME, orders.CUSTOMER_ID, CONCAT(customers.SURNAME,' ', SUBSTRING(customers.NAME, 1, 1), '. ', SUBSTRING(customers.MIDDLE_NAME, 1, 1), '.') AS CUSTOMER_FULLNAME, orders.CAR_ID, CONCAT_WS(' ', cars.MAKE, cars.MODEL) AS CAR_NAME, orders.EMPLOYEE_ID, CONCAT(employees.SURNAME,' ', SUBSTRING(employees.NAME, 1, 1), '. ', SUBSTRING(employees.MIDDLE_NAME, 1, 1), '.') AS EMPLOYEE_FULLNAME, IF (orders.IS_COMPLETED = 1, 'Завершён', 'В обработке') AS STATUS " +
                "FROM orders " +
                "JOIN customers ON orders.CUSTOMER_ID = customers.ID " +
                "JOIN cars ON orders.CAR_ID = cars.ID " +
                "JOIN employees ON orders.EMPLOYEE_ID = employees.ID " +
                filter +
                " ORDER BY ID ASC;";
        return getByQuery(query, rs -> new Order(rs.getInt(ID), rs.getString(DATE_TIME), rs.getInt(CUSTOMER_ID), rs.getString(CUSTOMER_FULLNAME), rs.getInt(CAR_ID), rs.getString(CAR_NAME), rs.getInt(EMPLOYEE_ID), rs.getString(EMPLOYEE_FULLNAME), rs.getString(STATUS)));
    }

    public ObservableList<Style> getStyles() {
        //language=SQL
        String query = "SELECT styles.ID, styles.NAME " +
                "FROM styles " +
                "ORDER BY ID ASC;";
        return getByQuery(query, rs -> new Style(rs.getInt(ID), rs.getString(NAME)));
    }

    public ObservableList<String> getPositionNames() {
        //language=SQL
        String query = "SELECT NAME FROM positions;";
        return getByQuery(query, rs -> rs.getString(NAME));
    }

    public ObservableList<String> getStyleNames() {
        //language=SQL
        String query = "SELECT NAME FROM styles;";
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
                    "WHERE USERNAME = '" + username + "';";
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
                    "WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "';";

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
