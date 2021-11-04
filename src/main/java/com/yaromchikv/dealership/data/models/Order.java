package com.yaromchikv.dealership.data.models;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private LocalDateTime dateTime;
    private int customerId;
    private int carId;
    private int employeeId;
    private Boolean isCompleted;

    public Order(int id, LocalDateTime dateTime, int customerId, int carId, int employeeId, Boolean isCompleted) {
        this.id = id;
        this.dateTime = dateTime;
        this.customerId = customerId;
        this.carId = carId;
        this.employeeId = employeeId;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
