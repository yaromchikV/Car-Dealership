package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Position;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.utils.Constants.*;

public class AdminPositionsController implements Initializable {

    public TableView<Position> positionsTableView;
    public TableColumn<Position, Integer> idTableColumn;
    public TableColumn<Position, String> nameTableColumn;
    public TableColumn<Position, Double> salaryTableColumn;

    public TextField nameTextField;
    public TextField salaryTextField;

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
        showPositions();
    }

    private void showPositions() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        salaryTableColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());

        ObservableList<Position> resultList = repository.getTablePositions();
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
        String name = nameTextField.getText();
        String salaryText = salaryTextField.getText();
        double salary = Double.parseDouble(salaryText);

        //language=SQL
        String query = "INSERT INTO " + POSITIONS_TABLE + " VALUES (" +
                null + ",'" + name + "'," + salary + ");";
        repository.executeUpdate(query);

        clearFields();
        showPositions();
    }

    private void applyEditButton() {
        int id = positionsTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
        String name = nameTextField.getText();
        String salary = salaryTextField.getText();

        //language=SQL
        String query = "UPDATE " + POSITIONS_TABLE + " SET " +
                NAME + " = '" + name + "'," +
                SALARY + " = " + Double.parseDouble(salary) +
                " WHERE " + ID + " = " + id;
        repository.executeUpdate(query);

        clearFields();
        showPositions();
    }

    private void applyDeleteButton() {
        int id = positionsTableView.getSelectionModel().getSelectedItem().idProperty().getValue();

        //language=SQL
        String query = "DELETE FROM " + POSITIONS_TABLE + " WHERE " + ID + " = " + id;
        repository.executeUpdate(query);

        clearFields();
        showPositions();
    }

    @FXML
    public void tableItemSelect() {
        Position position = positionsTableView.getSelectionModel().getSelectedItem();
        if (position != null)
            fillFieldsIfCellIsSelected(position);
    }

    private void fillFieldsIfCellIsSelected(Position position) {
        Toggle selectedToggle = actions.getSelectedToggle();
        boolean isEdit = editToggleButton.equals(selectedToggle);
        boolean isDelete = deleteToggleButton.equals(selectedToggle);
        if (isEdit || isDelete) {
            nameTextField.setText(position.nameProperty().getValue());
            salaryTextField.setText(position.salaryProperty().getValue().toString());
            if (isEdit) updatingBoxFieldsIsEnabled(true);
        }
    }

    @FXML
    private void addToggleButtonClick() {
        applyButton.setText("Добавить");
        addToggleButton.setSelected(true);
        clearButton.setVisible(true);
        updatingBoxFieldsIsEnabled(true);

        clearFields();
    }

    @FXML
    private void editToggleButtonClick() {
        applyButton.setText("Применить");
        editToggleButton.setSelected(true);
        clearButton.setVisible(false);

        Position position = positionsTableView.getSelectionModel().getSelectedItem();
        if (position != null) {
            updatingBoxFieldsIsEnabled(true);
            fillFieldsIfCellIsSelected(position);
        } else {
            updatingBoxFieldsIsEnabled(false);
            clearFields();
        }
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        clearButton.setVisible(false);
        updatingBoxFieldsIsEnabled(false);

        Position position = positionsTableView.getSelectionModel().getSelectedItem();
        if (position != null)
            fillFieldsIfCellIsSelected(position);
        else
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
}
