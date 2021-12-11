package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.Main;
import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Customer;
import com.yaromchikv.dealership.utils.AccessLevel;
import com.yaromchikv.dealership.utils.AlertDialog;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        idTableColumn.setSortType(TableColumn.SortType.ASCENDING);
        customersTableView.getSortOrder().add(idTableColumn);
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
            String surname = surnameTextField.getText().trim().replaceAll(" +", " ");
            String name = nameTextField.getText().trim().replaceAll(" +", " ");
            String middleName = middleNameTextField.getText().trim().replaceAll(" +", " ");
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText().trim().replaceAll(" +", " ");

            //language=SQL
            String query = "INSERT INTO customers " +
                    "VALUES (null, '" + surname + "', '" + name + "', '" + middleName + "', '" + birthDate + "', '" + phoneNumber + "', '" + email + "');";

            repository.executeUpdate(query);
            clearUpdateFields();
            showCustomers();

            AlertDialog alertDialog = new AlertDialog();
            alertDialog.showInformationAlert("Изменения применены", "Клиент " + getFullname(surname, name, middleName) + " добавлен.");
        }
    }

    private void applyEditButton() {
        if (checkUpdatingFields()) {
            int id = customersTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
            String surname = surnameTextField.getText().trim().replaceAll(" +", " ");
            String name = nameTextField.getText().trim().replaceAll(" +", " ");
            String middleName = middleNameTextField.getText().trim().replaceAll(" +", " ");
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneNumberTextField.getText();
            String email = emailTextField.getText().trim().replaceAll(" +", " ");

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
            clearUpdateFields();
            showCustomers();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Данные клиента id" + id + " обновлены.");
        }
    }

    private boolean checkUpdatingFields() {
        ArrayList<String> errorMessages = new ArrayList<>();

        if (surnameTextField.getText().trim().replaceAll(" +", " ").isEmpty())
            errorMessages.add("Фамилия отсутствует. ");
        if (nameTextField.getText().trim().replaceAll(" +", " ").isEmpty())
            errorMessages.add("Имя отсутствует. ");
        if (middleNameTextField.getText().trim().replaceAll(" +", " ").isEmpty())
            errorMessages.add("Отчество отсутствует. ");
        if (birthDatePicker.getValue() == null)
            errorMessages.add("Дата рождения не выбрана. ");
        if (phoneNumberTextField.getText().isEmpty())
            errorMessages.add("Номер телефона отсутствует. ");
        if (emailTextField.getText().trim().replaceAll(" +", " ").isEmpty())
            errorMessages.add("Электронная почта отсутствует. ");

        if (errorMessages.size() != 0) {
            AlertDialog alert = new AlertDialog();
            alert.showErrorAlert("Обнаружена одна или несколько ошибок!", String.join(" ", errorMessages));
        }
        return errorMessages.size() == 0;
    }

    private void applyDeleteButton() {
        Customer customer = customersTableView.getSelectionModel().getSelectedItem();

        int id = customer.idProperty().getValue();
        String fullname = getFullname(customer.surnameProperty().getValue(), customer.nameProperty().getValue(), customer.middleNameProperty().getValue());

        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Вы уверены?",
                "Вы действительно хотите удалить клиента " + fullname + "? Будут также удалены все его заказы.");

        if (answer) {
            //language=SQL
            String query = "DELETE FROM customers" +
                    " WHERE ID = " + id;

            repository.executeUpdate(query);
            clearUpdateFields();
            showCustomers();

            alert.showInformationAlert("Изменения применены", "Клиент " + fullname + " удалён.");
        }
    }

    private String getFullname(String surname, String name, String middleName) {
        return surname + ' ' + name.charAt(0) + ". " + middleName.charAt(0) + ".";
    }

    private void applyFilterButton() {
        String surname = surnameFilterTextField.getText().trim().replaceAll(" +", " ");
        String name = nameFilterTextField.getText().trim().replaceAll(" +", " ");
        String middleName = middleNameFilterTextField.getText().trim().replaceAll(" +", " ");
        LocalDate minBirthDate = minBirthDateFilterDatePicker.getValue();
        LocalDate maxBirthDate = maxBirthDateFilterDatePicker.getValue();
        String phoneNumber = phoneNumberFilterTextField.getText();
        String email = emailFilterTextField.getText().trim().replaceAll(" +", " ");

        ArrayList<String> filter = new ArrayList<>();
        if (!surname.isEmpty()) filter.add("SURNAME ='" + surname + "'");
        if (!name.isEmpty()) filter.add("NAME ='" + name + "'");
        if (!middleName.isEmpty()) filter.add("MIDDLE_NAME ='" + middleName + "'");
        if (minBirthDate != null) filter.add("DATE_OF_BIRTH >= '" + minBirthDate + "'");
        if (maxBirthDate != null) filter.add("DATE_OF_BIRTH <= '" + maxBirthDate + "'");
        if (!phoneNumber.isEmpty()) filter.add("PHONE_NUMBER ='" + phoneNumber + "'");
        if (!email.isEmpty()) filter.add("EMAIL ='" + email + "'");

        //language=SQL
        String filterString = null;
        if (!filter.isEmpty()) filterString = "WHERE " + String.join(" AND ", filter);

        ObservableList<Customer> resultList = repository.getCustomers(filterString);
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
        birthDatePicker.setValue(null);
        phoneNumberTextField.clear();
        emailTextField.clear();
    }

    private void clearFilterFields() {
        surnameFilterTextField.clear();
        nameFilterTextField.clear();
        middleNameFilterTextField.clear();
        minBirthDateFilterDatePicker.getEditor().clear();
        minBirthDateFilterDatePicker.setValue(null);
        maxBirthDateFilterDatePicker.getEditor().clear();
        maxBirthDateFilterDatePicker.setValue(null);
        phoneNumberFilterTextField.clear();
        emailFilterTextField.clear();
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
