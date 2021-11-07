package com.yaromchikv.dealership.data.tableModels;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableCar {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty make;
    private final SimpleStringProperty model;
    private final SimpleStringProperty styleName;
    private final SimpleIntegerProperty year;
    private final SimpleDoubleProperty price;

    public TableCar(int id, String styleName, String make, String model, int year, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.styleName = new SimpleStringProperty(styleName);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.year = new SimpleIntegerProperty(year);
        this.price = new SimpleDoubleProperty(price);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleStringProperty styleNameProperty() {
        return styleName;
    }

    public SimpleStringProperty makeProperty() {
        return make;
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }
}
