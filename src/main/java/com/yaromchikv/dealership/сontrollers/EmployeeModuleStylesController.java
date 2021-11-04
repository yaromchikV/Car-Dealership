package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Style;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeModuleStylesController implements Initializable {

    public TableView<Style> stylesTableView;
    public TableColumn<Style, Integer> idTableColumn;
    public TableColumn<Style, String> nameTableColumn;

    Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
        showStyles();
    }

    private void showStyles() {
        ObservableList<Style> listOfCar = repository.getAllStyles();

        idTableColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        stylesTableView.setItems(listOfCar);
    }
}
