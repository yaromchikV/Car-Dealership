package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Customer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.utils.Constants.*;
import static com.yaromchikv.dealership.utils.Constants.ADMIN_POSITIONS_DASHBOARD;

public class EmployeeCustomersController implements Initializable {

    public TableView<Customer> customersTableView;
    public TableColumn<Customer, Integer> idTableColumn;
    public TableColumn<Customer, String> surnameTableColumn;
    public TableColumn<Customer, String> nameTableColumn;
    public TableColumn<Customer, String> middleNameTableColumn;
    public TableColumn<Customer, String> birthDateTableColumn;
    public TableColumn<Customer, String> phoneNumberTableColumn;
    public TableColumn<Customer, String> emailTableColumn;

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
    public TextField middleNameTextField;
    public DatePicker birthDatePicker;
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

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
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

        ObservableList<Customer> resultList = repository.getCustomers(null);
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
        String surname = surnameTextField.getText();
        String name = nameTextField.getText();
        String middleName = middleNameTextField.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();

        //language=SQL
        String query = "INSERT INTO customers " +
                "VALUES (null, '" + surname + "', '" + name + "', '" + middleName + "', '" + birthDate + "', '" + phoneNumber + "', '" + email + "');";

        repository.executeUpdate(query);
        showCustomers();
    }

    private void applyEditButton() {
        int id = customersTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
        String surname = surnameTextField.getText();
        String name = nameTextField.getText();
        String middleName = middleNameTextField.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();

        //language=SQL
        String query = "UPDATE customers SET " +
                "SURNAME = '" + surname + "', " +
                "NAME = '" + name + "', " +
                "MIDDLE_NAME = '" + middleName + "', " +
                "DATE_OF_BIRTH = '" + birthDate + "', " +
                "PHONE_NUMBER = '" + phoneNumber + "', " +
                "EMAIL = '" + email + "' " +
                "WHERE ID = " + id + ";";

        repository.executeUpdate(query);
        showCustomers();
    }

    private void applyDeleteButton() {
        int id = customersTableView.getSelectionModel().getSelectedItem().idProperty().getValue();

        //language=SQL
        String query = "DELETE FROM customers" +
                " WHERE ID = " + id;

        repository.executeUpdate(query);
        showCustomers();
    }

    private void applyFilterButton() {
        String surname = surnameFilterTextField.getText();
        String name = nameFilterTextField.getText();
        String middleName = middleNameFilterTextField.getText();
        LocalDate minBirthDate = minBirthDateFilterDatePicker.getValue();
        LocalDate maxBirthDate = maxBirthDateFilterDatePicker.getValue();
        String phoneNumber = phoneNumberFilterTextField.getText();
        String email = emailFilterTextField.getText();

        //language=SQL
        StringBuilder filterBuilder = new StringBuilder("WHERE ");
        if (!surname.isEmpty()) filterBuilder.append("SURNAME ='").append(surname).append("' AND ");
        if (!name.isEmpty()) filterBuilder.append("NAME ='").append(name).append("' AND ");
        if (!middleName.isEmpty()) filterBuilder.append("MIDDLE_NAME ='").append(middleName).append("' AND ");
        if (minBirthDate != null)
            filterBuilder.append("DATE_OF_BIRTH >='").append(minBirthDate).append("' AND ");
        if (maxBirthDate != null)
            filterBuilder.append("DATE_OF_BIRTH <='").append(maxBirthDate).append("' AND ");
        if (!phoneNumber.isEmpty()) filterBuilder.append("PHONE_NUMBER ='").append(phoneNumber).append("' AND ");
        if (!email.isEmpty()) filterBuilder.append("USERNAME ='").append(email).append("' AND ");

        String filter = null;
        if (filterBuilder.length() > 6) {
            filterBuilder.setLength(filterBuilder.length() - 4);
            filter = filterBuilder.toString();
        }

        ObservableList<Customer> resultList = repository.getCustomers(filter);
        customersTableView.setItems(resultList);
    }

    @FXML
    public void tableItemSelect() {
        Customer customer = customersTableView.getSelectionModel().getSelectedItem();
        if (customer != null)
            fillFieldsIfCellIsSelected(customer);
    }

    private void fillFieldsIfCellIsSelected(Customer customer) {
        Toggle selectedToggle = actions.getSelectedToggle();
        boolean isEdit = editToggleButton.equals(selectedToggle);
        boolean isDelete = deleteToggleButton.equals(selectedToggle);
        if (isEdit || isDelete) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            surnameTextField.setText(customer.surnameProperty().getValue());
            nameTextField.setText(customer.nameProperty().getValue());
            middleNameTextField.setText(customer.middleNameProperty().getValue());
            birthDatePicker.setValue(LocalDate.parse(customer.dateOfBirthProperty().getValue(), formatter));
            birthDatePicker.getEditor().setText(customer.dateOfBirthProperty().getValue());
            phoneNumberTextField.setText(customer.phoneNumberProperty().getValue());
            emailTextField.setText(customer.emailProperty().getValue());
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

        Customer customer = customersTableView.getSelectionModel().getSelectedItem();
        if (customer != null) {
            updatingBoxFieldsIsEnabled(true);
            fillFieldsIfCellIsSelected(customer);
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

        Customer customer = customersTableView.getSelectionModel().getSelectedItem();
        if (customer != null)
            fillFieldsIfCellIsSelected(customer);
        else
            clearUpdateFields();
    }

    private void updatingBoxFieldsIsEnabled(boolean isEnabled) {
        surnameTextField.setDisable(!isEnabled);
        nameTextField.setDisable(!isEnabled);
        middleNameTextField.setDisable(!isEnabled);
        birthDatePicker.setDisable(!isEnabled);
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
        birthDatePicker.getEditor().clear();
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

}
