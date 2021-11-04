package com.yaromchikv.dealership.data;

import com.yaromchikv.dealership.data.models.Employee;
import com.yaromchikv.dealership.data.models.Position;
import com.yaromchikv.dealership.data.tableModels.TableEmployee;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Converter {

    Repository repository;

    public Converter() {
        repository = new Repository();
    }

    public ObservableList<TableEmployee> getAllTableEmployees() {
        ObservableList<TableEmployee> resultList = FXCollections.observableArrayList();

        ObservableList<Employee> listOfEmployees = repository.getAllEmployees();
        ObservableList<Position> listOfPositions = repository.getAllPositions();
        Map<Integer, Position> mapOfPositions = listOfPositions.stream().collect(Collectors.toMap(Position::getId, item -> item));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        listOfEmployees.forEach(employee -> {
            String positionName = mapOfPositions.get(employee.getId()).getName();
            Double positionSalary = mapOfPositions.get(employee.getId()).getSalary();

            TableEmployee tableEmployee = new TableEmployee(employee.getId(), employee.getSurname(),
                    employee.getName(), employee.getMiddleName(), employee.getDateOfBirth().format(formatter),
                    employee.getPhoneNumber(), positionName, positionSalary, employee.getStartDate().format(formatter));

            resultList.add(tableEmployee);
        });

        return resultList;
    }
}
