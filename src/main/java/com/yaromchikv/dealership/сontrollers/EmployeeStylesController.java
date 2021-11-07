package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.tableModels.TableStyle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.*;

public class EmployeeStylesController implements Initializable {

    public TableView<TableStyle> stylesTableView;
    public TableColumn<TableStyle, Integer> idTableColumn;
    public TableColumn<TableStyle, String> nameTableColumn;

    public TextField nameTextField;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;

    public Button applyButton;
    public Button clearButton;

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
        showStyles();
    }

    private void showStyles() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        ObservableList<TableStyle> resultList = repository.getTableStyles();
        stylesTableView.setItems(resultList);
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
    public void positionsMenuButtonClick() {
        ScreenController.activate(ADMIN_POSITIONS_DASHBOARD);
    }

    @FXML
    private void addToggleButtonClick() {
        applyButton.setText("Добавить");
        addToggleButton.setSelected(true);
        clearButton.setVisible(true);
        nameTextField.setDisable(false);

        clearFields();
    }

    @FXML
    private void editToggleButtonClick() {
        applyButton.setText("Применить");
        editToggleButton.setSelected(true);
        clearButton.setVisible(false);
        nameTextField.setDisable(false);

        clearFields();

        // Если не выбрана ячейка, enabled = false
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        clearButton.setVisible(false);
        nameTextField.setDisable(true);

        clearFields();
    }

    @FXML
    public void clearButtonClick() {
        nameTextField.clear();
    }

    private void clearFields() {
        nameTextField.clear();
    }
}
