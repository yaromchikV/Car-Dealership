package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Converter;
import com.yaromchikv.dealership.data.tableModels.TableEmployee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;

public class AdminEmployeesController implements Initializable {

    public TableView<TableEmployee> employeesTableView;
    public TableColumn<TableEmployee, Integer> idTableColumn;
    public TableColumn<TableEmployee, String> surnameTableColumn;
    public TableColumn<TableEmployee, String> nameTableColumn;
    public TableColumn<TableEmployee, String> middleNameTableColumn;
    public TableColumn<TableEmployee, String> birthDateTableColumn;
    public TableColumn<TableEmployee, String> phoneNumberTableColumn;
    public TableColumn<TableEmployee, String> positionTableColumn;
    public TableColumn<TableEmployee, Double> salaryTableColumn;
    public TableColumn<TableEmployee, String> startDateTableColumn;

    public VBox employeesButtonModule;

    public HBox updatingBox;
    public TextField surnameTextField;
    public TextField nameTextField;
    public TextField middleNameTextField;
    public DatePicker birthDatePicker;
    public TextField phoneNumberTextField;
    public ChoiceBox<String> positionChoiceBox;
    public DatePicker startDatePicker;

    public HBox filterBox;
    public TextField surnameFilterTextField;
    public TextField nameFilterTextField;
    public TextField middleNameFilterTextField;
    public DatePicker minBirthFilterDatePicker;
    public DatePicker maxBirthFilterDatePicker;
    public TextField phoneNumberFilterTextField;
    public ChoiceBox<String> positionFilterChoiceBox;
    public DatePicker minStartDateFilterDatePicker;
    public DatePicker maxStartDateFilterDatePicker;

    public Button applyButton;
    public Button clearButton;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    private Converter converter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        converter = new Converter();
        showEmployees();
    }

    private void showEmployees() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        surnameTableColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        middleNameTableColumn.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
        birthDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfBirthProperty());
        phoneNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        positionTableColumn.setCellValueFactory(cellData -> cellData.getValue().positionNameProperty());
        salaryTableColumn.setCellValueFactory(cellData -> cellData.getValue().positionSalaryProperty().asObject());
        startDateTableColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());

        ObservableList<TableEmployee> resultList = converter.getAllTableEmployees();
        employeesTableView.setItems(resultList);
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
    private void customersMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_CUSTOMERS_DASHBOARD);
    }

    @FXML
    private void ordersMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_ORDERS_DASHBOARD);
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
        surnameTextField.setDisable(!isEnabled);
        nameTextField.setDisable(!isEnabled);
        middleNameTextField.setDisable(!isEnabled);
        birthDatePicker.setDisable(!isEnabled);
        phoneNumberTextField.setDisable(!isEnabled);
        positionChoiceBox.setDisable(!isEnabled);
        startDatePicker.setDisable(!isEnabled);
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
        birthDatePicker.getEditor().clear();
        phoneNumberTextField.clear();
        positionChoiceBox.valueProperty().set(null);
        startDatePicker.getEditor().clear();
    }

    private void clearFilterFields() {
        surnameFilterTextField.clear();
        nameFilterTextField.clear();
        middleNameFilterTextField.clear();
        minBirthFilterDatePicker.getEditor().clear();
        maxBirthFilterDatePicker.getEditor().clear();
        phoneNumberFilterTextField.clear();
        positionFilterChoiceBox.valueProperty().set(null);
        minStartDateFilterDatePicker.getEditor().clear();
        maxStartDateFilterDatePicker.getEditor().clear();
    }
}
