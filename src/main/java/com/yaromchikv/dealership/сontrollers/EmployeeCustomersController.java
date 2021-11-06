package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Converter;
import com.yaromchikv.dealership.data.tableModels.TableCustomer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;
import static com.yaromchikv.dealership.Constants.ADMIN_POSITIONS_DASHBOARD;

public class EmployeeCustomersController implements Initializable {

    public TableView<TableCustomer> customersTableView;
    public TableColumn<TableCustomer, Integer> idTableColumn;
    public TableColumn<TableCustomer, String> surnameTableColumn;
    public TableColumn<TableCustomer, String> nameTableColumn;
    public TableColumn<TableCustomer, String> middleNameTableColumn;
    public TableColumn<TableCustomer, String> birthDateTableColumn;
    public TableColumn<TableCustomer, String> phoneNumberTableColumn;
    public TableColumn<TableCustomer, String> emailTableColumn;

    public Button applyButton;
    public Button clearButton;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    public HBox updatingBox;
    public TextField surnameTextField;
    public TextField nameTextField;
    public DatePicker birthDateDatePicker;
    public TextField middleNameTextField;
    public TextField phoneNumberTextField;
    public TextField emailTextField;

    public HBox filterBox;
    public TextField surnameFilterTextField;
    public TextField nameFilterTextField;
    public TextField middleNameFilterTextField;
    public DatePicker minBirthDateFilterDatePicker;
    public DatePicker maxBirthDateFilterDatePicker;
    public TextField phoneNumberFilterTextField;
    public TextField emailFilterTextField;

    Converter converter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        converter = new Converter();
        showCustomers();
    }

    private void showCustomers() {

        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        surnameTableColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        middleNameTableColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
        birthDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfBirthProperty());
        phoneNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        ObservableList<TableCustomer> resultList = converter.getAllTableCustomers();
        customersTableView.setItems(resultList);
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
    private void carsMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_CARS_DASHBOARD);
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
        surnameTextField.setDisable(!isEnabled);
        nameTextField.setDisable(!isEnabled);
        middleNameTextField.setDisable(!isEnabled);
        birthDateDatePicker.setDisable(!isEnabled);
        phoneNumberTextField.setDisable(!isEnabled);
        emailTextField.setDisable(!isEnabled);
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
        surnameTextField.clear();
        nameTextField.clear();
        middleNameTextField.clear();
        birthDateDatePicker.getEditor().clear();
        phoneNumberTextField.clear();
        emailTextField.clear();
    }

    private void clearFilterFields() {
        surnameFilterTextField.clear();
        nameFilterTextField.clear();
        middleNameFilterTextField.clear();
        minBirthDateFilterDatePicker.getEditor().clear();
        maxBirthDateFilterDatePicker.getEditor().clear();
        phoneNumberFilterTextField.clear();
        emailFilterTextField.clear();
    }
}
