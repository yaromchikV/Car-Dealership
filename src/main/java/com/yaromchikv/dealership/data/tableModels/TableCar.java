package com.yaromchikv.dealership.data.tableModels;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableCar {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty styleId;
    private final SimpleStringProperty make;
    private final SimpleStringProperty model;
    private final SimpleIntegerProperty year;
    private final SimpleDoubleProperty price;

    public TableCar(SimpleIntegerProperty id, SimpleIntegerProperty styleId, SimpleStringProperty make, SimpleStringProperty model, SimpleIntegerProperty year, SimpleDoubleProperty price) {
        this.id = id;
        this.styleId = styleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getStyleId() {
        return styleId.get();
    }

    public SimpleIntegerProperty styleIdProperty() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId.set(styleId);
    }

    public String getMake() {
        return make.get();
    }

    public SimpleStringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
