package com.yaromchikv.dealership.data;

import com.yaromchikv.dealership.data.models.*;
import com.yaromchikv.dealership.data.tableModels.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Converter {

    Repository repository;

    public Converter() {
        repository = new Repository();
    }

    public ObservableList<TableAccount> getAllTableAccounts() {
        ObservableList<Account> listOfAccounts = repository.getAllAccounts();

        List<TableAccount> resultList = listOfAccounts.stream()
                .map(account -> new TableAccount(account.getId(), account.getUsername(), account.getPassword()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    public ObservableList<TableEmployee> getAllTableEmployees() {
        ObservableList<Employee> listOfEmployees = repository.getAllEmployees();
        ObservableList<Position> listOfPositions = repository.getAllPositions();
        Map<Integer, Position> mapOfPositions = listOfPositions.stream().collect(Collectors.toMap(Position::getId, item -> item));

        List<TableEmployee> resultList = listOfEmployees.stream()
                .map(employee -> {
                    String positionName = mapOfPositions.get(employee.getId()).getName();
                    double positionSalary = mapOfPositions.get(employee.getId()).getSalary();

                    return new TableEmployee(employee.getId(), employee.getSurname(), employee.getName(),
                            employee.getMiddleName(), toString(employee.getDateOfBirth()), employee.getPhoneNumber(),
                            positionName, positionSalary, toString(employee.getStartDate()));
                })
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    public ObservableList<TablePosition> getAllTablePositions() {
        ObservableList<Position> listOfPositions = repository.getAllPositions();

        List<TablePosition> resultList = listOfPositions.stream()
                .map(position -> new TablePosition(position.getId(), position.getName(), position.getSalary()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    public ObservableList<TableCar> getAllTableCars() {
        ObservableList<Car> listOfCars = repository.getAllCars();
        ObservableList<Style> listOfStyles = repository.getAllStyles();
        Map<Integer, Style> mapOfPositions = listOfStyles.stream().collect(Collectors.toMap(Style::getId, item -> item));

        List<TableCar> resultList = listOfCars.stream()
                .map(car -> {
                    String styleName = mapOfPositions.get(car.getId()).getName();
                    return new TableCar(car.getId(), styleName, car.getMake(), car.getModel(), car.getYear(), car.getPrice());
                })
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    public ObservableList<TableCustomer> getAllTableCustomers() {
        ObservableList<Customer> listOfCustomers = repository.getAllCustomers();

        List<TableCustomer> resultList = listOfCustomers.stream()
                .map(customer -> new TableCustomer(
                        customer.getId(), customer.getSurname(), customer.getName(), customer.getMiddleName(),
                        toString(customer.getDateOfBirth()), customer.getPhoneNumber(), customer.getEmail()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    public ObservableList<TableOrder> getAllTableOrders() {
        ObservableList<Order> listOfOrders = repository.getAllOrders();

        ObservableList<Customer> listOfCustomers = repository.getAllCustomers();
        ObservableList<Car> listOfCars = repository.getAllCars();
        ObservableList<Employee> listOfEmployees = repository.getAllEmployees();

        Map<Integer, Customer> mapOfCustomers = listOfCustomers.stream().collect(Collectors.toMap(Customer::getId, item -> item));
        Map<Integer, Car> mapOfCars = listOfCars.stream().collect(Collectors.toMap(Car::getId, item -> item));
        Map<Integer, Employee> mapOfEmployees = listOfEmployees.stream().collect(Collectors.toMap(Employee::getId, item -> item));

        List<TableOrder> resultList = listOfOrders.stream()
                .map(order -> {
                    int customerId = mapOfCustomers.get(order.getId()).getId();
                    String customerFullName = mapOfCustomers.get(order.getId()).getSurname() + ' '
                            + mapOfCustomers.get(order.getId()).getName().charAt(0) + '.'
                            + mapOfCustomers.get(order.getId()).getMiddleName().charAt(0) + '.';
                    int carId = mapOfCars.get(order.getId()).getId();
                    String carName = mapOfCars.get(order.getId()).getMake() + ' ' + mapOfCars.get(order.getId()).getModel();
                    int employeeId = mapOfEmployees.get(order.getId()).getId();
                    String employeeFullName = mapOfEmployees.get(order.getId()).getSurname() + ' '
                            + mapOfEmployees.get(order.getId()).getName().charAt(0) + '.'
                            + mapOfEmployees.get(order.getId()).getMiddleName().charAt(0) + '.';
                    String status = order.getCompleted() ? "Завершён" : "В обработке";

                    return new TableOrder(
                            order.getId(), toString(order.getDateTime()), customerId, customerFullName,
                            carId, carName, employeeId, employeeFullName, status);
                })
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    public ObservableList<TableStyle> getAllTableStyles() {
        ObservableList<Style> listOfStyles = repository.getAllStyles();

        List<TableStyle> resultList = listOfStyles.stream()
                .map(style -> new TableStyle(style.getId(), style.getName()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(resultList);
    }

    private String toString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    private String toString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
