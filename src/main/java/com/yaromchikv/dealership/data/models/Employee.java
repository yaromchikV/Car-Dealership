package com.yaromchikv.dealership.data.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty name;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty positionName;
    private final SimpleDoubleProperty positionSalary;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;

    public Employee(int id, String surname, String name, String middleName, String dateOfBirth, String phoneNumber, String positionName, Double positionSalary, String startDate, String username, String password) {
        this.id = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.middleName = new SimpleStringProperty(middleName);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.positionName = new SimpleStringProperty(positionName);
        this.positionSalary = new SimpleDoubleProperty(positionSalary);
        this.startDate = new SimpleStringProperty(startDate);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }

    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleStringProperty positionNameProperty() {
        return positionName;
    }

    public SimpleDoubleProperty positionSalaryProperty() {
        return positionSalary;
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }
}
