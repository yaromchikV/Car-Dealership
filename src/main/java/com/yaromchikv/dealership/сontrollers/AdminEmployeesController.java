package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Employee;
import com.yaromchikv.dealership.utils.AlertDialog;
import com.yaromchikv.dealership.utils.Hash;
import com.yaromchikv.dealership.utils.controls.CustomTextField;
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

public class AdminEmployeesController implements Initializable {

    public TableView<Employee> employeesTableView;
    public TableColumn<Employee, Integer> idTableColumn;
    public TableColumn<Employee, String> surnameTableColumn;
    public TableColumn<Employee, String> nameTableColumn;
    public TableColumn<Employee, String> middleNameTableColumn;
    public TableColumn<Employee, String> birthDateTableColumn;
    public TableColumn<Employee, String> phoneNumberTableColumn;
    public TableColumn<Employee, String> positionTableColumn;
    public TableColumn<Employee, Double> salaryTableColumn;
    public TableColumn<Employee, String> startDateTableColumn;
    public TableColumn<Employee, String> usernameTableColumn;
    public TableColumn<Employee, String> passwordTableColumn;

    public HBox updatingBox;
    public CustomTextField surnameTextField;
    public CustomTextField nameTextField;
    public CustomTextField middleNameTextField;
    public DatePicker birthDatePicker;
    public CustomTextField phoneNumberTextField;
    public ChoiceBox<String> positionChoiceBox;
    public DatePicker startDatePicker;
    public CustomTextField usernameTextField;
    public CustomTextField passwordTextField;
    public CustomTextField password2TextField;

    public HBox filterBox;
    public CustomTextField surnameFilterTextField;
    public CustomTextField nameFilterTextField;
    public CustomTextField middleNameFilterTextField;
    public DatePicker minBirthFilterDatePicker;
    public DatePicker maxBirthFilterDatePicker;
    public CustomTextField phoneNumberFilterTextField;
    public ChoiceBox<String> positionFilterChoiceBox;
    public DatePicker minStartDateFilterDatePicker;
    public DatePicker maxStartDateFilterDatePicker;
    public CustomTextField usernameFilterTextField;

    private ObservableList<String> listOfPositionsNames;

    public Button applyButton;
    public Button clearButton;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
        initChoiceBoxes();
        showEmployees();
    }

    private void initChoiceBoxes() {
        listOfPositionsNames = repository.getPositionNames();
        listOfPositionsNames.add(0, "Не выбрано");

        positionChoiceBox.setItems(listOfPositionsNames);
        positionFilterChoiceBox.setItems(listOfPositionsNames);

        positionChoiceBox.valueProperty().setValue(listOfPositionsNames.get(0));
        positionFilterChoiceBox.valueProperty().setValue(listOfPositionsNames.get(0));
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
        usernameTableColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordTableColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        ObservableList<Employee> resultList = repository.getEmployees(null);
        employeesTableView.setItems(resultList);

        idTableColumn.setSortType(TableColumn.SortType.ASCENDING);
        employeesTableView.getSortOrder().add(idTableColumn);
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
            String surname = surnameTextField.getText();
            String name = nameTextField.getText();
            String middleName = middleNameTextField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneNumberTextField.getText();
            String positionName = positionChoiceBox.getValue();
            LocalDate startDate = startDatePicker.getValue();
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            int id = repository.getLastUserId() + 1;

            password = Hash.convert(id, password);

            //language=SQL
            String employeeQuery =
                    "INSERT INTO employees " +
                            "VALUES (" + null + ", '" + surname + "', '" + name + "', '" + middleName + "', '" + birthDate + "', '" + phoneNumber + "', " + "(SELECT ID FROM positions WHERE NAME = '" + positionName + "' LIMIT 1)," + "'" + startDate + "');";
            //language=SQL
            String accountQuery =
                    "INSERT INTO accounts " +
                            "VALUES (last_insert_id(), '" + username + "', '" + password + "');";

            repository.executeUpdate(employeeQuery);
            repository.executeUpdate(accountQuery);

            clearUpdateFields();
            showEmployees();

            AlertDialog alertDialog = new AlertDialog();
            alertDialog.showInformationAlert("Изменения применены", "Сотрудник " + getFullname(surname, name, middleName) + " добавлен.");
        }
    }

    private void applyEditButton() {
        if (checkUpdatingFields()) {
            int id = employeesTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
            String surname = surnameTextField.getText();
            String name = nameTextField.getText();
            String middleName = middleNameTextField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String phoneNumber = phoneNumberTextField.getText();
            String positionName = positionChoiceBox.getValue();
            LocalDate startDate = startDatePicker.getValue();
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            //language=SQL
            String employeeQuery = "UPDATE employees SET " +
                    "SURNAME = '" + surname + "', " +
                    "NAME = '" + name + "', " +
                    "MIDDLE_NAME = '" + middleName + "', " +
                    "DATE_OF_BIRTH = '" + birthDate + "', " +
                    "PHONE_NUMBER = '" + phoneNumber + "', " +
                    "POSITION_ID = " + "(SELECT ID FROM positions WHERE NAME = '" + positionName + "' LIMIT 1)," +
                    "START_DATE = '" + startDate + "' " +
                    "WHERE ID = " + id + ";";

            //language=SQL
            String accountQuery = "UPDATE accounts SET " +
                    "USERNAME = '" + username + "', " +
                    "PASSWORD = '" + password + "' " +
                    "WHERE ID = " + id + ";";

            repository.executeUpdate(employeeQuery);
            repository.executeUpdate(accountQuery);

            clearUpdateFields();
            showEmployees();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Данные сотрудника id" + id + " обновлены.");
        }
    }

    private boolean checkUpdatingFields() {
        ArrayList<String> errorMessages = new ArrayList<>();

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String password2 = password2TextField.getText();

        if (surnameTextField.getText().isEmpty())
            errorMessages.add("Фамилия отсутствует.");
        if (nameTextField.getText().isEmpty())
            errorMessages.add("Имя отсутствует.");
        if (middleNameTextField.getText().isEmpty())
            errorMessages.add("Отчество отсутствует.");
        if (birthDatePicker.getValue() == null)
            errorMessages.add("Дата рождения не выбрана.");
        if (phoneNumberTextField.getText().isEmpty())
            errorMessages.add("Номер телефона отсутствует.");
        if (positionChoiceBox.getValue().equals(listOfPositionsNames.get(0)))
            errorMessages.add("Должность не выбрана.");
        if (startDatePicker.getValue() == null)
            errorMessages.add("Дата начала работы не выбрана.");
        if (username.isEmpty())
            errorMessages.add("Логин отсутствует.");
        else if (username.length() < 4)
            errorMessages.add("Логин слишком короткий.");
        if (repository.getIdByUsername(username) != null)
            errorMessages.add("Логин занят другим сотрудником.");
        if (password.isEmpty())
            errorMessages.add("Пароль отсутствует.");
        if (!password.isEmpty() && password.length() < 6)
            errorMessages.add("Пароль слишком короткий.");
        if (password2.isEmpty())
            errorMessages.add("Пароль не введён повторно.");
        else if (!password.isEmpty() && !password.equals(password2))
            errorMessages.add("Пароли не совпадают.");

        if (errorMessages.size() != 0) {
            AlertDialog alert = new AlertDialog();
            alert.showErrorAlert("Обнаружена одна или несколько ошибок!", String.join(" ", errorMessages));
        }
        return errorMessages.size() == 0;
    }

    private void applyDeleteButton() {
        Employee employee = employeesTableView.getSelectionModel().getSelectedItem();

        int id = employee.idProperty().getValue();
        String fullname = getFullname(employee.surnameProperty().getValue(), employee.nameProperty().getValue(), employee.middleNameProperty().getValue());

        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Вы уверены?",
                "Вы действительно хотите удалить сотрудника " + fullname + "?");

        if (answer) {
            //language=SQL
            String query = "DELETE FROM employees " +
                    "WHERE ID = " + id;

            repository.executeUpdate(query);
            clearUpdateFields();
            showEmployees();

            alert.showInformationAlert("Изменения применены", "Сотрудник " + fullname + " удалён.");
        }
    }

    private String getFullname(String surname, String name, String middleName) {
        return surname + ' ' + name.charAt(0) + ". " + middleName.charAt(0) + ".";
    }

    private void applyFilterButton() {
        String surname = surnameFilterTextField.getText();
        String name = nameFilterTextField.getText();
        String middleName = middleNameFilterTextField.getText();
        LocalDate minBirthDate = minBirthFilterDatePicker.getValue();
        LocalDate maxBirthDate = maxBirthFilterDatePicker.getValue();
        String phoneNumber = phoneNumberFilterTextField.getText();
        String positionName = positionFilterChoiceBox.getValue();
        LocalDate minStartDate = minStartDateFilterDatePicker.getValue();
        LocalDate maxStartDate = maxStartDateFilterDatePicker.getValue();
        String username = usernameFilterTextField.getText();

        ArrayList<String> filter = new ArrayList<>();
        if (!surname.isEmpty()) filter.add("SURNAME ='" + surname + "'");
        if (!name.isEmpty()) filter.add("NAME ='" + name + "'");
        if (!middleName.isEmpty()) filter.add("MIDDLE_NAME ='" + middleName + "'");
        if (minBirthDate != null) filter.add("DATE_OF_BIRTH >= ='" + minBirthDate + "'");
        if (maxBirthDate != null) filter.add("DATE_OF_BIRTH <= ='" + maxBirthDate + "'");
        if (!phoneNumber.isEmpty()) filter.add("PHONE_NUMBER ='" + phoneNumber + "'");
        if (!positionName.equals(listOfPositionsNames.get(0))) filter.add("positions.NAME ='" + positionName + "'");
        if (minStartDate != null) filter.add("START_DATE >= ='" + minStartDate + "'");
        if (maxStartDate != null) filter.add("START_DATE <= ='" + maxStartDate + "'");
        if (!username.isEmpty()) filter.add("USERNAME ='" + username + "'");

        //language=SQL
        String filterString = null;
        if (!filter.isEmpty()) filterString = "WHERE " + String.join(" AND ", filter);

        ObservableList<Employee> resultList = repository.getEmployees(filterString);
        employeesTableView.setItems(resultList);
    }

    @FXML
    public void tableItemSelect() {
        Employee employee = employeesTableView.getSelectionModel().getSelectedItem();
        if (employee != null)
            fillFieldsIfCellIsSelected(employee);
    }

    private void fillFieldsIfCellIsSelected(Employee employee) {
        Toggle selectedToggle = actions.getSelectedToggle();
        boolean isEdit = editToggleButton.equals(selectedToggle);
        boolean isDelete = deleteToggleButton.equals(selectedToggle);
        if (isEdit || isDelete) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            surnameTextField.setText(employee.surnameProperty().getValue());
            nameTextField.setText(employee.nameProperty().getValue());
            middleNameTextField.setText(employee.middleNameProperty().getValue());
            birthDatePicker.setValue(LocalDate.parse(employee.dateOfBirthProperty().getValue(), formatter));
            birthDatePicker.getEditor().setText(employee.dateOfBirthProperty().getValue());
            phoneNumberTextField.setText(employee.phoneNumberProperty().getValue());
            positionChoiceBox.setValue(employee.positionNameProperty().getValue());
            startDatePicker.setValue(LocalDate.parse(employee.startDateProperty().getValue(), formatter));
            startDatePicker.getEditor().setText(employee.startDateProperty().getValue());
            usernameTextField.setText(employee.usernameProperty().getValue());
            passwordTextField.setText("");
            password2TextField.setText("");
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

        Employee employee = employeesTableView.getSelectionModel().getSelectedItem();
        if (employee != null) {
            updatingBoxFieldsIsEnabled(true);
            fillFieldsIfCellIsSelected(employee);
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

        Employee employee = employeesTableView.getSelectionModel().getSelectedItem();
        if (employee != null)
            fillFieldsIfCellIsSelected(employee);
        else
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
        usernameTextField.setDisable(!isEnabled);
        passwordTextField.setDisable(!isEnabled);
        password2TextField.setDisable(!isEnabled);
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
        positionChoiceBox.valueProperty().setValue(listOfPositionsNames.get(0));
        startDatePicker.getEditor().clear();
        startDatePicker.setValue(null);
        usernameTextField.clear();
        passwordTextField.clear();
        password2TextField.clear();
    }

    private void clearFilterFields() {
        surnameFilterTextField.clear();
        nameFilterTextField.clear();
        middleNameFilterTextField.clear();
        minBirthFilterDatePicker.getEditor().clear();
        minBirthFilterDatePicker.setValue(null);
        maxBirthFilterDatePicker.getEditor().clear();
        maxBirthFilterDatePicker.setValue(null);
        phoneNumberFilterTextField.clear();
        positionFilterChoiceBox.valueProperty().setValue(listOfPositionsNames.get(0));
        minStartDateFilterDatePicker.getEditor().clear();
        minStartDateFilterDatePicker.setValue(null);
        maxStartDateFilterDatePicker.getEditor().clear();
        maxStartDateFilterDatePicker.setValue(null);
        usernameFilterTextField.clear();
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
    private void ordersMenuButtonClick() {
        ScreenController.activate(EMPLOYEE_ORDERS_DASHBOARD);
    }

    @FXML
    public void positionsMenuButtonClick() {
        ScreenController.activate(ADMIN_POSITIONS_DASHBOARD);
    }

}
