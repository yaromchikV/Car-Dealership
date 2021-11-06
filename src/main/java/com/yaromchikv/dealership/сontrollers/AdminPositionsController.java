package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Converter;
import com.yaromchikv.dealership.data.tableModels.TablePosition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;

public class AdminPositionsController implements Initializable {

    public TableView<TablePosition> positionsTableView;
    public TableColumn<TablePosition, Integer> idTableColumn;
    public TableColumn<TablePosition, String> nameTableColumn;
    public TableColumn<TablePosition, Double> salaryTableColumn;

    public TextField nameTextField;
    public TextField salaryTextField;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;

    public Button applyButton;
    public Button clearButton;

    private Converter converter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        converter = new Converter();
        showPositions();
    }

    private void showPositions() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        salaryTableColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());

        ObservableList<TablePosition> resultList = converter.getAllTablePositions();
        positionsTableView.setItems(resultList);
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
    private void addToggleButtonClick() {
        applyButton.setText("Добавить");
        addToggleButton.setSelected(true);
        clearButton.setVisible(true);
        updatingBoxFieldsIsEnabled(true);

        clearFields();

        // Очищать все поля
    }

    @FXML
    private void editToggleButtonClick() {
        applyButton.setText("Применить");
        editToggleButton.setSelected(true);
        clearButton.setVisible(false);
        updatingBoxFieldsIsEnabled(true);

        clearFields();

        // Если не выбрана ячейка, enabled = false
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        clearButton.setVisible(false);
        updatingBoxFieldsIsEnabled(false);

        clearFields();
    }

    private void updatingBoxFieldsIsEnabled(boolean isEnabled) {
        nameTextField.setDisable(!isEnabled);
        salaryTextField.setDisable(!isEnabled);
    }

    @FXML
    private void clearButtonClick() {
        clearFields();
    }

    private void clearFields() {
        nameTextField.clear();
        salaryTextField.clear();
    }
}
