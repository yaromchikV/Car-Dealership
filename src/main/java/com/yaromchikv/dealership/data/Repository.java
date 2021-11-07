package com.yaromchikv.dealership.data;

import com.yaromchikv.dealership.MyConnection;
import com.yaromchikv.dealership.data.models.*;
import com.yaromchikv.dealership.data.tableModels.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.yaromchikv.dealership.Constants.*;

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

    public void executeUpdate(String query) {
        Connection connection = MyConnection.connection;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ObservableList<TableEmployee> getTableEmployees(String filter) {
        if (filter == null) filter = "";
        //language=SQL
        String query = "SELECT employees.ID, employees.SURNAME, employees.NAME, employees.MIDDLE_NAME, " +
                "DATE_FORMAT(employees.DATE_OF_BIRTH, '%d.%m.%Y') AS " + DATE_OF_BIRTH + ", employees.PHONE_NUMBER, " +
                "positions.NAME AS " + POSITION + ", positions.SALARY, DATE_FORMAT(employees.START_DATE, '%d.%m.%Y') AS " + START_DATE + ", " +
                "accounts.USERNAME, accounts.PASSWORD " +
                "FROM employees " +
                "JOIN positions ON employees.POSITION_ID = positions.ID " +
                "JOIN accounts ON employees.ID = accounts.ID " +
                filter + "ORDER BY ID ASC;";

        return getByQuery(query, rs -> new TableEmployee(rs.getInt(ID), rs.getString(SURNAME), rs.getString(SURNAME),
                rs.getString(MIDDLE_NAME), rs.getString(DATE_OF_BIRTH), rs.getString(PHONE_NUMBER), rs.getString(POSITION),
                rs.getDouble(SALARY), rs.getString(START_DATE), rs.getString(USERNAME), rs.getString(PASSWORD)));
    }

    public ObservableList<Integer> getLastEmployeeId() {
        //language=SQL
        String query = "SELECT ID FROM employees ORDER BY ID DESC LIMIT 1";
        return getByQuery(query, rs -> rs.getInt(ID));
    }

    public ObservableList<TablePosition> getTablePositions() {
        //language=SQL
        String query = "SELECT positions.ID, positions.NAME, positions.SALARY FROM positions ORDER BY ID ASC;;";
        return getByQuery(query, rs -> new TablePosition(rs.getInt(ID), rs.getString(NAME), rs.getDouble(SALARY)));
    }

    public ObservableList<Integer> getPositionIdByPositionName(String positionName) {
        //language=SQL
        String query = "SELECT ID FROM positions WHERE NAME = '" + positionName + "' LIMIT 1;";
        return getByQuery(query, rs -> rs.getInt(ID));
    }

    public ObservableList<String> getTablePositionNames() {
        //language=SQL
        String query = "SELECT " + NAME + " FROM positions;";
        return getByQuery(query, rs -> rs.getString(NAME));
    }

    public ObservableList<TableCar> getTableCars() {
        //language=SQL
        String query = "SELECT cars.ID, cars.MAKE, cars.MODEL, styles.NAME AS " + STYLE + ", cars.YEAR, cars.PRICE " +
                "FROM cars " +
                "JOIN styles ON cars.STYLE_ID = styles.ID ORDER BY ID ASC;;";
        return getByQuery(query,
                rs -> new TableCar(rs.getInt(ID), rs.getString(MAKE), rs.getString(MODEL), rs.getString(STYLE), rs.getInt(YEAR), rs.getDouble(PRICE)));
    }

    public ObservableList<TableCustomer> getTableCustomers() {
        //language=SQL
        String query = "SELECT customers.ID, customers.SURNAME, customers.NAME, customers.MIDDLE_NAME, " +
                "DATE_FORMAT(customers.DATE_OF_BIRTH, '%d.%m.%Y') AS " + DATE_OF_BIRTH + ", " +
                "customers.PHONE_NUMBER, customers.EMAIL " +
                "FROM customers ORDER BY ID ASC;;";
        return getByQuery(query,
                rs -> new TableCustomer(rs.getInt(ID), rs.getString(SURNAME), rs.getString(NAME), rs.getString(MIDDLE_NAME), rs.getString(DATE_OF_BIRTH), rs.getString(PHONE_NUMBER), rs.getString(EMAIL)));
    }

    public ObservableList<TableOrder> getTableOrders() {
        //language=SQL
        String query = "SELECT orders.ID, " +
                "DATE_FORMAT(orders.DATE_TIME, '%d.%m.%Y %H:%i:%S') AS DATE_TIME, " +
                "orders.CUSTOMER_ID, " +
                "CONCAT(customers.SURNAME,' ', SUBSTRING(customers.NAME, 1, 1), '. ', SUBSTRING(customers.MIDDLE_NAME, 1, 1), '.') AS " + CUSTOMER_FULLNAME + ", " +
                "orders.CAR_ID, " +
                "CONCAT_WS(' ', cars.MAKE, cars.MODEL) as " + CAR_NAME + ", " +
                "orders.EMPLOYEE_ID, " +
                "CONCAT(employees.SURNAME,' ', SUBSTRING(employees.NAME, 1, 1), '. ', SUBSTRING(employees.MIDDLE_NAME, 1, 1), '.') AS " + EMPLOYEE_FULLNAME + ", " +
                "IF (orders.IS_COMPLETED = 1, 'Завершён', 'В обработке') AS " + STATUS + " " +
                "FROM orders " +
                "JOIN customers ON orders.CUSTOMER_ID = customers.ID " +
                "JOIN cars ON orders.CAR_ID = cars.ID " +
                "JOIN employees ON orders.EMPLOYEE_ID = employees.ID " +
                "ORDER BY " + ID + " ASC;";
        return getByQuery(query, rs -> new TableOrder(rs.getInt(ID), rs.getString(DATE_TIME), rs.getInt(CUSTOMER_ID),
                rs.getString(CUSTOMER_FULLNAME), rs.getInt(CAR_ID), rs.getString(CAR_NAME), rs.getInt(EMPLOYEE_ID),
                rs.getString(EMPLOYEE_FULLNAME), rs.getString(STATUS)));
    }

    public ObservableList<TableStyle> getTableStyles() {
        //language=SQL
        String query = "SELECT styles.ID, styles.NAME FROM styles ORDER BY ID ASC;";
        return getByQuery(query, rs -> new TableStyle(rs.getInt(ID), rs.getString(NAME)));
    }

    public ObservableList<String> getTableStyleNames() {
        //language=SQL
        String query = "SELECT styles.NAME FROM styles;";
        return getByQuery(query, rs -> rs.getString(NAME));
    }

    //////////////////////
    public void insertAccount(Account account) {
        //language=SQL
        String query = "INSERT INTO " + ACCOUNTS_TABLE + " VALUES (" +
                null + ",'" + account.getUsername() + "','" + account.getPassword() + "')";
        executeUpdate(query);
    }

    public void editAccount(Account account) {
        //language=SQL
        String query = "UPDATE " + ACCOUNTS_TABLE + " SET " +
                USERNAME + " = '" + account.getUsername() + "'," +
                PASSWORD + " = '" + account.getPassword() + "'," +
                " WHERE " + ID + " = " + account.getId();
        executeUpdate(query);
    }

    public void deleteAccount(int id) {
        //language=SQL
        String query = "DELETE FROM " + ACCOUNTS_TABLE + " WHERE " + ID + " = " + id;
        executeUpdate(query);
    }
}






