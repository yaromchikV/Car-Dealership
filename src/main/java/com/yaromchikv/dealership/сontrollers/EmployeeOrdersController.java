package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Converter;
import com.yaromchikv.dealership.data.tableModels.TableOrder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;
import static com.yaromchikv.dealership.Constants.ADMIN_ACCOUNTS_DASHBOARD;

public class EmployeeOrdersController implements Initializable {


    public TableView<TableOrder> ordersTableView;
    public TableColumn<TableOrder, Integer> idTableColumn;
    public TableColumn<TableOrder, String> timeTableColumn;
    public TableColumn<TableOrder, Integer> customerIdTableColumn;
    public TableColumn<TableOrder, String> customerTableColumn;
    public TableColumn<TableOrder, Integer> carIdTableColumn;
    public TableColumn<TableOrder, String> carTableColumn;
    public TableColumn<TableOrder, Integer> employeeIdTableColumn;
    public TableColumn<TableOrder, String> employeeNameTableColumn;
    public TableColumn<TableOrder, String> statusTableColumn;

    public Button applyButton;
    public Button clearButton;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    public HBox updatingBox;
    public TextField customerIdTextField;
    public TextField carIdTextField;
    public TextField employeeIdTextField;
    public RadioButton processingRadioButton;
    public ToggleGroup orderStatus;
    public RadioButton completedRadioButton;
    public TextField customerFullNameFilterTextField;

    public HBox filterBox;
    public TextField carMakeFilterTextField;
    public TextField carModelFilterTextField;
    public TextField employeeFullNameFilterTextField;
    public RadioButton processingFilterRadioButton;
    public ToggleGroup orderStatusFilter;
    public RadioButton completedFilterRadioButton;
    public RadioButton anyFilterRadioButton;

    Converter converter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        converter = new Converter();
        showOrders();
    }

    private void showOrders() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        timeTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        customerIdTableColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());
        customerTableColumn.setCellValueFactory(cellData -> cellData.getValue().customerFullNameProperty());
        carIdTableColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());
        carTableColumn.setCellValueFactory(cellData -> cellData.getValue().carNameProperty());
        employeeIdTableColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        employeeNameTableColumn.setCellValueFactory(cellData -> cellData.getValue().employeeFullNameProperty());
        statusTableColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        ObservableList<TableOrder> resultList = converter.getAllTableOrders();
        ordersTableView.setItems(resultList);
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
    }

    private void updatingBoxFieldsIsEnabled(boolean isEnabled) {
        customerIdTextField.setDisable(!isEnabled);
        carIdTextField.setDisable(!isEnabled);
        employeeIdTextField.setDisable(!isEnabled);
        processingRadioButton.setDisable(!isEnabled);
        completedRadioButton.setDisable(!isEnabled);
        customerFullNameFilterTextField.setDisable(!isEnabled);
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
        customerIdTextField.clear();
        carIdTextField.clear();
        employeeIdTextField.clear();
        processingRadioButton.setSelected(true);
        customerFullNameFilterTextField.clear();
    }

    private void clearFilterFields() {
        carMakeFilterTextField.clear();
        carModelFilterTextField.clear();
        employeeFullNameFilterTextField.clear();
        carModelFilterTextField.clear();
        anyFilterRadioButton.setSelected(true);
    }
}
