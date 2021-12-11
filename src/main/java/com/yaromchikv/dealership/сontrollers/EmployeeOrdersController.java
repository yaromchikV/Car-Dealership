package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.Main;
import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Order;
import com.yaromchikv.dealership.utils.AccessLevel;
import com.yaromchikv.dealership.utils.AlertDialog;
import com.yaromchikv.dealership.utils.controls.CustomDatePicker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.utils.Constants.*;

public class EmployeeOrdersController implements Initializable {


    public TableView<Order> ordersTableView;
    public TableColumn<Order, Integer> idTableColumn;
    public TableColumn<Order, String> timeTableColumn;
    public TableColumn<Order, Integer> customerIdTableColumn;
    public TableColumn<Order, String> customerTableColumn;
    public TableColumn<Order, Integer> carIdTableColumn;
    public TableColumn<Order, String> carTableColumn;
    public TableColumn<Order, Integer> employeeIdTableColumn;
    public TableColumn<Order, String> employeeNameTableColumn;
    public TableColumn<Order, String> statusTableColumn;

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
    public ToggleGroup orderStatus;
    public RadioButton processingRadioButton;
    public RadioButton completedRadioButton;

    public HBox filterBox;
    public TextField carMakeFilterTextField;
    public TextField carModelFilterTextField;
    public TextField customerSurnameFilterTextField;
    public TextField employeeSurnameFilterTextField;
    public RadioButton processingFilterRadioButton;
    public ToggleGroup orderStatusFilter;
    public RadioButton completedFilterRadioButton;
    public RadioButton anyFilterRadioButton;
    public CustomDatePicker minOrderDateFilterDatePicker;
    public CustomDatePicker maxOrderDateFilterDatePicker;

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
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

        ObservableList<Order> resultList = repository.getOrders(null);
        ordersTableView.setItems(resultList);

        idTableColumn.setSortType(TableColumn.SortType.ASCENDING);
        ordersTableView.getSortOrder().add(idTableColumn);
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
        if (checkUpdatingFields()) {
            int customerId = Integer.parseInt(customerIdTextField.getText());
            int carId = Integer.parseInt(carIdTextField.getText());
            int employeeId = Integer.parseInt(employeeIdTextField.getText());
            boolean isCompleted = completedRadioButton.isSelected();

            //language=SQL
            String query = "INSERT INTO orders " +
                    "VALUES (null, NOW(), " + customerId + ", " + carId + ", " + employeeId + ", " + isCompleted + ");";

            repository.executeUpdate(query);
            clearUpdateFields();
            showOrders();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Заказ добавлен.");
        }
    }

    private void applyEditButton() {
        if (checkUpdatingFields()) {
            int id = ordersTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
            int customerId = Integer.parseInt(customerIdTextField.getText());
            int carId = Integer.parseInt(carIdTextField.getText());
            int employeeId = Integer.parseInt(employeeIdTextField.getText());
            boolean isCompleted = completedRadioButton.isSelected();

            //language=SQL
            String query = "UPDATE orders SET " +
                    "CUSTOMER_ID = " + customerId + ", " +
                    "CAR_ID = " + carId + ", " +
                    "EMPLOYEE_ID = " + employeeId + ", " +
                    "IS_COMPLETED = " + isCompleted + " " +
                    "WHERE ID = " + id + ";";

            repository.executeUpdate(query);
            clearUpdateFields();
            showOrders();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Заказ " + id + " добавлен.");
        }
    }

    private boolean checkUpdatingFields() {
        ArrayList<String> errorMessages = new ArrayList<>();

        String customerIdText = customerIdTextField.getText();
        String carIdText = carIdTextField.getText();
        String employeeIdText = employeeIdTextField.getText();

        if (customerIdText.isEmpty())
            errorMessages.add("ID клиента отсутствует. ");
        if (!repository.findIdByQuery("customers", Integer.parseInt(customerIdText)))
            errorMessages.add("Клиент " + customerIdText + " не найден. ");
        if (carIdTextField.getText().isEmpty())
            errorMessages.add("ID автомобиля отсутствует. ");
        if (!repository.findIdByQuery("cars", Integer.parseInt(carIdText)))
            errorMessages.add("Автомобиль " + carIdText + " не найден. ");
        if (employeeIdTextField.getText().isEmpty())
            errorMessages.add("ID сотрудника отсутствует. ");
        if (!repository.findIdByQuery("employees", Integer.parseInt(employeeIdText)))
            errorMessages.add("Сотрудник " + employeeIdText + " не найден. ");

        if (errorMessages.size() != 0) {
            AlertDialog alert = new AlertDialog();
            alert.showErrorAlert("Обнаружена одна или несколько ошибок!", String.join(" ", errorMessages));
        }
        return errorMessages.size() == 0;
    }

    private void applyDeleteButton() {
        int id = ordersTableView.getSelectionModel().getSelectedItem().idProperty().getValue();

        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Вы уверены?",
                "Вы действительно хотите удалить заказ " + id + "?");

        if (answer) {
            //language=SQL
            String query = "DELETE FROM orders " +
                    "WHERE ID = " + id;

            repository.executeUpdate(query);
            clearUpdateFields();
            showOrders();

            alert.showInformationAlert("Изменения применены", "Заказ " + id + " удалён.");
        }
    }

    private void applyFilterButton() {
        String make = carMakeFilterTextField.getText().trim().replaceAll(" +", " ");
        String model = carModelFilterTextField.getText().trim().replaceAll(" +", " ");
        String customerSurname = customerSurnameFilterTextField.getText().trim().replaceAll(" +", " ");
        String employeeSurname = employeeSurnameFilterTextField.getText().trim().replaceAll(" +", " ");
        Boolean isCompleted = null;
        if (processingFilterRadioButton.isSelected()) isCompleted = false;
        else if (completedFilterRadioButton.isSelected()) isCompleted = true;
        LocalDate minOrderDate = minOrderDateFilterDatePicker.getValue();
        LocalDate maxOrderDate = maxOrderDateFilterDatePicker.getValue();

        ArrayList<String> filter = new ArrayList<>();
        if (!make.isEmpty()) filter.add("MAKE ='" + make + "'");
        if (!model.isEmpty()) filter.add("MODEL ='" + model + "'");
        if (!customerSurname.isEmpty()) filter.add("customers.SURNAME ='" + customerSurname + "'");
        if (!employeeSurname.isEmpty()) filter.add("employees.SURNAME ='" + employeeSurname + "'");
        if (isCompleted != null) filter.add("IS_COMPLETED =" + isCompleted);
        if (minOrderDate != null) filter.add("CAST(orders.DATE_TIME AS DATE) >= '" + minOrderDate + "'");
        if (maxOrderDate != null) filter.add("CAST(orders.DATE_TIME AS DATE) <= '" + maxOrderDate + "'");

        //language=SQL
        String filterString = null;
        if (!filter.isEmpty()) filterString = "WHERE " + String.join(" AND ", filter);

        ObservableList<Order> resultList = repository.getOrders(filterString);
        ordersTableView.setItems(resultList);
    }

    @FXML
    public void tableItemSelect() {
        Order order = ordersTableView.getSelectionModel().getSelectedItem();
        if (order != null) fillFieldsIfCellIsSelected(order);
    }

    private void fillFieldsIfCellIsSelected(Order order) {
        Toggle selectedToggle = actions.getSelectedToggle();
        boolean isEdit = editToggleButton.equals(selectedToggle);
        boolean isDelete = deleteToggleButton.equals(selectedToggle);
        if (isEdit || isDelete) {
            customerIdTextField.setText(String.valueOf(order.customerIdProperty().getValue()));
            carIdTextField.setText(String.valueOf(order.carIdProperty().getValue()));
            employeeIdTextField.setText(String.valueOf(order.employeeIdProperty().getValue()));
            if (order.statusProperty().getValue().equals("В обработке")) processingRadioButton.setSelected(true);
            else completedRadioButton.setSelected(true);

            if (isEdit) updatingBoxFieldsIsEnabled(true);
        }
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

        Order order = ordersTableView.getSelectionModel().getSelectedItem();
        if (order != null) {
            updatingBoxFieldsIsEnabled(true);
            fillFieldsIfCellIsSelected(order);
        } else {
            updatingBoxFieldsIsEnabled(false);
            clearUpdateFields();
        }
    }

    @FXML
    private void deleteToggleButtonClick() {
        applyButton.setText("Удалить");
        deleteToggleButton.setSelected(true);
        updatingBox.setVisible(true);
        filterBox.setVisible(false);
        clearButton.setVisible(false);
        updatingBoxFieldsIsEnabled(false);

        Order order = ordersTableView.getSelectionModel().getSelectedItem();
        if (order != null)
            fillFieldsIfCellIsSelected(order);
        else
            clearUpdateFields();
    }

    private void updatingBoxFieldsIsEnabled(boolean isEnabled) {
        customerIdTextField.setDisable(!isEnabled);
        carIdTextField.setDisable(!isEnabled);
        employeeIdTextField.setDisable(!isEnabled);
        processingRadioButton.setDisable(!isEnabled);
        completedRadioButton.setDisable(!isEnabled);
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
    }

    private void clearFilterFields() {
        carMakeFilterTextField.clear();
        carModelFilterTextField.clear();
        customerSurnameFilterTextField.clear();
        employeeSurnameFilterTextField.clear();
        carModelFilterTextField.clear();
        anyFilterRadioButton.setSelected(true);
        minOrderDateFilterDatePicker.getEditor().clear();
        minOrderDateFilterDatePicker.setValue(null);
        maxOrderDateFilterDatePicker.getEditor().clear();
        maxOrderDateFilterDatePicker.setValue(null);
    }

    @FXML
    private void backButtonClick() {
        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Выйти из системы?", "Вы действительно хотите вернуться на экран авторизации?");
        if (answer)
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
