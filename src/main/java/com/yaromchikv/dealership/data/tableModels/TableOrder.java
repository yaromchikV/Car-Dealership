package com.yaromchikv.dealership.data.tableModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableOrder {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty dateTime;
    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty customerFullName;
    private final SimpleIntegerProperty carId;
    private final SimpleStringProperty carName;
    private final SimpleIntegerProperty employeeId;
    private final SimpleStringProperty employeeFullName;
    private final SimpleStringProperty status;

    public TableOrder(int id, String dateTime, int customerId, String customerFullName, int carId, String carName, int employeeId, String employeeFullName, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.dateTime = new SimpleStringProperty(dateTime);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerFullName = new SimpleStringProperty(customerFullName);
        this.carId = new SimpleIntegerProperty(carId);
        this.carName = new SimpleStringProperty(carName);
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.employeeFullName = new SimpleStringProperty(employeeFullName);
        this.status = new SimpleStringProperty(status);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty dateTimeProperty() {
        return dateTime;
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public SimpleStringProperty customerFullNameProperty() {
        return customerFullName;
    }

    public SimpleIntegerProperty carIdProperty() {
        return carId;
    }

    public SimpleStringProperty carNameProperty() {
        return carName;
    }

    public SimpleIntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public SimpleStringProperty employeeFullNameProperty() {
        return employeeFullName;
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}
