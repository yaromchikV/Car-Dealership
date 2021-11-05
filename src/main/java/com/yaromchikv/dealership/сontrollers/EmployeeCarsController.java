package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Converter;
import com.yaromchikv.dealership.data.tableModels.TableCar;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;
import static com.yaromchikv.dealership.Constants.ADMIN_POSITIONS_DASHBOARD;

public class EmployeeCarsController implements Initializable {

    public TableView<TableCar> carsTableView;
    public TableColumn<TableCar, Integer> idTableColumn;
    public TableColumn<TableCar, String> styleTableColumn;
    public TableColumn<TableCar, String> makeTableColumn;
    public TableColumn<TableCar, String> modelTableColumn;
    public TableColumn<TableCar, Integer> yearTableColumn;
    public TableColumn<TableCar, Double> priceTableColumn;

    public Button applyButton;
    public Button clearButton;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    public HBox updatingBox;
    public TextField makeTextField;
    public TextField modelTextField;
    public ChoiceBox<String> styleChoiceBox;
    public TextField yearTextField;
    public TextField priceTextField;

    public HBox filterBox;
    public TextField makeFilterTextField;
    public TextField modelFilterTextField;
    public ChoiceBox<String> styleFilterChoiceBox;
    public ChoiceBox<String> minYearFilterChoiceBox;
    public ChoiceBox<String> maxYearFilterChoiceBox;
    public TextField minPriceFilterTextField;
    public TextField maxPriceFilterTextField;

    Converter converter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        converter = new Converter();
        showCars();
    }

    private void showCars() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        styleTableColumn.setCellValueFactory(cellData -> cellData.getValue().styleNameProperty());
        makeTableColumn.setCellValueFactory(cellData -> cellData.getValue().makeProperty());
        modelTableColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        yearTableColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        priceTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        ObservableList<TableCar> resultList = converter.getAllTableCars();
        carsTableView.setItems(resultList);
    }

    @FXML
    private void applyButtonClick() {
        Toggle selectedToggle = actions.getSelectedToggle();
        if (addToggleButton.equals(selectedToggle)) {
            applyAddButton();
        } else if (editToggleButton.equals(selectedToggle)) {
            applyEditButton();
        } else if (deleteToggleButton.equals(selectedToggle)) {
            applyDeleteButton();
        } else if (filterToggleButton.equals(selectedToggle)) {
            applyFilterButton();
        }
    }

    private void applyAddButton() {
        // TODO
    }

    private void applyEditButton() {
        // TODO
    }

    private void applyDeleteButton() {
        // TODO
    }

    private void applyFilterButton() {
        // TODO
    }

    @FXML
    private void backButtonClick() {
        ScreenController.activate(AUTH_SCREEN);
    }

    @FXML
    private void stylesMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_STYLES_DASHBOARD);
    }

    @FXML
    private void customersMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_CUSTOMERS_DASHBOARD);
    }

    @FXML
    private void ordersMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_ORDERS_DASHBOARD);
    }

    @FXML
    private void employeesMenuButtonClick() {
        ScreenController.activate(ADMIN_EMPLOYEES_DASHBOARD);
    }

    @FXML
    public void positionsMenuButtonClick() {
        ScreenController.activate(ADMIN_POSITIONS_DASHBOARD);
    }

    @FXML
    public void accountsMenuButtonClick() {
        ScreenController.activate(ADMIN_ACCOUNTS_DASHBOARD);
    }
    @FXML
    private void addToggleButtonClick() {
        applyButton.setText("Добавить");
        addToggleButton.setSelected(true);
        updatingBox.setVisible(true);
        filterBox.setVisible(false);
        clearButton.setVisible(true);
        updatingBoxFieldsIsEnabled(true);

        clearUpdateFields();
    }

    @FXML
    private void editToggleButtonClick() {
        applyButton.setText("Применить");
        editToggleButton.setSelected(true);
        updatingBox.setVisible(true);
        filterBox.setVisible(false);
        clearButton.setVisible(false);
        updatingBoxFieldsIsEnabled(true);

        clearUpdateFields();

        // Если не выбрана ячейка, enabled = false
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        updatingBox.setVisible(true);
        filterBox.setVisible(false);
        clearButton.setVisible(false);
        updatingBoxFieldsIsEnabled(false);

        clearUpdateFields();
    }

    private void updatingBoxFieldsIsEnabled(boolean isEnabled) {
        makeTextField.setDisable(!isEnabled);
        modelTextField.setDisable(!isEnabled);
        styleChoiceBox.setDisable(!isEnabled);
        yearTextField.setDisable(!isEnabled);
        priceTextField.setDisable(!isEnabled);
    }

    @FXML
    private void filterToggleButtonClick() {
        applyButton.setText("Показать");
        filterToggleButton.setSelected(true);
        updatingBox.setVisible(false);
        filterBox.setVisible(true);
        clearButton.setVisible(true);
    }

    @FXML
    private void clearButtonClick() {
        Toggle selectedToggle = actions.getSelectedToggle();
        if (addToggleButton.equals(selectedToggle)) {
            clearUpdateFields();
        } else if (filterToggleButton.equals(selectedToggle)) {
            clearFilterFields();
        }
    }

    private void clearUpdateFields() {
        makeTextField.clear();
        modelTextField.clear();
        styleChoiceBox.valueProperty().set(null);
        yearTextField.clear();
        priceTextField.clear();
    }

    private void clearFilterFields() {
        makeFilterTextField.clear();
        modelFilterTextField.clear();
        styleFilterChoiceBox.valueProperty().set(null);
        minYearFilterChoiceBox.valueProperty().set(null);
        maxYearFilterChoiceBox.valueProperty().set(null);
        minPriceFilterTextField.clear();
        maxPriceFilterTextField.clear();
    }
}
