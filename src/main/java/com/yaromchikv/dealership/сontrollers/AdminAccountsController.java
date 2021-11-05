package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Converter;
import com.yaromchikv.dealership.data.tableModels.TableAccount;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;
import static com.yaromchikv.dealership.Constants.ADMIN_EMPLOYEES_DASHBOARD;

public class AdminAccountsController implements Initializable {

    public TableView<TableAccount> usersTableView;
    public TableColumn<TableAccount, Integer> idTableColumn;
    public TableColumn<TableAccount, String> usernameTableColumn;
    public TableColumn<TableAccount, String> passwordTableColumn;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    public Button applyButton;
    public Button clearButton;

    public TextField usernameTextField;
    public VBox passwordBox;
    public TextField passwordTextField;
    public TextField password2TextField;

    private Converter converter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        converter = new Converter();
        showAccounts();
    }

    private void showAccounts() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        usernameTableColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordTableColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        ObservableList<TableAccount> resultList = converter.getAllTableAccounts();
        usersTableView.setItems(resultList);
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
        //
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
        clearButton.setVisible(true);

        passwordBox.setVisible(true);
        fieldsSetEnabled(true);

        clearFields();
    }

    @FXML
    private void editToggleButtonClick() {
        applyButton.setText("Применить");
        editToggleButton.setSelected(true);
        clearButton.setVisible(false);

        passwordBox.setVisible(true);
        fieldsSetEnabled(true);

        clearFields();

        // Если не выбрана ячейка, enabled = false
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        clearButton.setVisible(false);

        passwordBox.setVisible(true);
        fieldsSetEnabled(false);

        clearFields();

        // Заполнить ячейки
    }

    private void fieldsSetEnabled(boolean isEnabled) {
        usernameTextField.setDisable(!isEnabled);
        passwordTextField.setDisable(!isEnabled);
        password2TextField.setDisable(!isEnabled);
    }

    @FXML
    public void filterToggleButtonClick() {
        applyButton.setText("Показать");
        filterToggleButton.setSelected(true);
        clearButton.setVisible(true);

        passwordBox.setVisible(false);
        usernameTextField.clear();
    }

    @FXML
    private void clearButtonClick() {
        clearFields();
    }

    private void clearFields() {
        usernameTextField.clear();
        passwordTextField.clear();
        password2TextField.clear();
    }
}
