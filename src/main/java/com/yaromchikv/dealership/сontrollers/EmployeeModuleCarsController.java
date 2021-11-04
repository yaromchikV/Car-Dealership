package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Car;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeModuleCarsController implements Initializable {

    Repository repository;

    public TableView<Car> carsTableView;
    public TableColumn<Car, Integer> idTableColumn;
    public TableColumn<Car, Integer> styleTableColumn;
    public TableColumn<Car, String> makeTableColumn;
    public TableColumn<Car, String> modelTableColumn;
    public TableColumn<Car, Integer> yearTableColumn;
    public TableColumn<Car, Double> priceTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
        showCars();
    }

    private void showCars() {
        ObservableList<Car> listOfCar = repository.getAllCars();

        idTableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        styleTableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        makeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMake()));
        modelTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
        yearTableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());
        priceTableColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        carsTableView.setItems(listOfCar);
    }
}
