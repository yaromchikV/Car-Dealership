package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.Main;
import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Style;
import com.yaromchikv.dealership.utils.AccessLevel;
import com.yaromchikv.dealership.utils.AlertDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.utils.Constants.*;

public class EmployeeStylesController implements Initializable {

    public TableView<Style> stylesTableView;
    public TableColumn<Style, Integer> idTableColumn;
    public TableColumn<Style, String> nameTableColumn;

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

        ObservableList<Style> resultList = repository.getStyles();
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
        if (!nameTextField.getText().isEmpty()) {
            String name = nameTextField.getText();

            //language=SQL
            String query = "INSERT INTO styles " +
                    "VALUES (null, '" + name + "');";
            repository.executeUpdate(query);

            clearFields();
            showStyles();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Тип кузова \"" + name + "\" добавлен.");
        } else {
            AlertDialog alert = new AlertDialog();
            alert.showErrorAlert("Обнаружена ошибка!", "Название должности отсутствует.");
        }
    }

    private void applyEditButton() {
        if (!nameTextField.getText().isEmpty()) {

            int id = stylesTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
            String name = nameTextField.getText();

            //language=SQL
            String query = "UPDATE styles SET " +
                    "NAME = '" + name + "', " +
                    "WHERE ID = " + id;
            repository.executeUpdate(query);

            clearFields();
            showStyles();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Тип кузова \"" + name + "\" обновлён.");
        } else {
            AlertDialog alert = new AlertDialog();
            alert.showErrorAlert("Обнаружена ошибка!", "Название должности отсутствует.");
        }
    }

    private void applyDeleteButton() {
        Style style = stylesTableView.getSelectionModel().getSelectedItem();
        int id = style.idProperty().getValue();
        String name = style.nameProperty().getValue();

        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Вы уверены?",
                "Вы действительно хотите удалить тип кузова \"" + name + "\"?");

        if (answer) {
            //language=SQL
            String query = "DELETE FROM styles " +
                    "WHERE ID = " + id;
            repository.executeUpdate(query);

            clearFields();
            showStyles();

            alert.showInformationAlert("Изменения применены", "Тип кузова \"" + name + "\" удалён.");
        }
    }

    @FXML
    public void tableItemSelect() {
        Style style = stylesTableView.getSelectionModel().getSelectedItem();
        if (style != null)
            fillFieldsIfCellIsSelected(style);
    }

    private void fillFieldsIfCellIsSelected(Style style) {
        Toggle selectedToggle = actions.getSelectedToggle();
        boolean isEdit = editToggleButton.equals(selectedToggle);
        boolean isDelete = deleteToggleButton.equals(selectedToggle);
        if (isEdit || isDelete) {
            nameTextField.setText(style.nameProperty().getValue());
            if (isEdit) nameTextField.setDisable(false);
        }
    }

    @FXML
    private void addToggleButtonClick() {
        applyButton.setText("Добавить");
        addToggleButton.setSelected(true);
        clearButton.setVisible(true);
        nameTextField.setDisable(false);
    }

    @FXML
    private void editToggleButtonClick() {
        applyButton.setText("Применить");
        editToggleButton.setSelected(true);
        clearButton.setVisible(false);

        Style style = stylesTableView.getSelectionModel().getSelectedItem();
        if (style != null) {
            nameTextField.setDisable(false);
            fillFieldsIfCellIsSelected(style);
        } else {
            nameTextField.setDisable(true);
            clearFields();
        }
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        clearButton.setVisible(false);
        nameTextField.setDisable(true);

        Style style = stylesTableView.getSelectionModel().getSelectedItem();
        if (style != null)
            fillFieldsIfCellIsSelected(style);
        else
            clearFields();
    }

    @FXML
    public void clearButtonClick() {
        clearFields();
    }

    private void clearFields() {
        nameTextField.clear();
    }

    @FXML
    private void backButtonClick() {
        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Выйти из системы?", "Вы действительно хотите вернуться на экран авторизации?");
        if (answer)
            ScreenController.activate(AUTH_SCREEN);
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
        if (Main.myAccessLevel == AccessLevel.ADMIN)
            ScreenController.activate(ADMIN_EMPLOYEES_DASHBOARD);
        else {
            AlertDialog alert = new AlertDialog();
            alert.showWarningAlert("Внимание!", "Недостаточно прав доступа!");
        }
    }

    @FXML
    public void positionsMenuButtonClick() {
        if (Main.myAccessLevel == AccessLevel.ADMIN)
            ScreenController.activate(ADMIN_POSITIONS_DASHBOARD);
        else {
            AlertDialog alert = new AlertDialog();
            alert.showWarningAlert("Внимание!", "Недостаточно прав доступа!");
        }
    }

}
