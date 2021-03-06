package com.yaromchikv.dealership.data.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty name;
    private final SimpleStringProperty middleName;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;

    public Customer(int id, String surname, String name, String middleName, String dateOfBirth, String phoneNumber, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.middleName = new SimpleStringProperty(middleName);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
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

    public SimpleStringProperty emailProperty() {
        return email;
    }
}
