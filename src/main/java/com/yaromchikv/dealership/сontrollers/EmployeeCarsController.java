package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.Main;
import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Car;
import com.yaromchikv.dealership.utils.AccessLevel;
import com.yaromchikv.dealership.utils.AlertDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.utils.Constants.*;
import static com.yaromchikv.dealership.utils.Constants.ADMIN_POSITIONS_DASHBOARD;

public class EmployeeCarsController implements Initializable {

    public TableView<Car> carsTableView;
    public TableColumn<Car, Integer> idTableColumn;
    public TableColumn<Car, String> makeTableColumn;
    public TableColumn<Car, String> modelTableColumn;
    public TableColumn<Car, String> styleTableColumn;
    public TableColumn<Car, Integer> yearTableColumn;
    public TableColumn<Car, Double> priceTableColumn;

    public Button applyButton;
    public Button clearButton;

    public ToggleGroup actions;
    public ToggleButton addToggleButton;
    public ToggleButton editToggleButton;
    public ToggleButton deleteToggleButton;
    public ToggleButton filterToggleButton;

    public HBox updatingBox;
    public TextField makeTextField;
    public TextField modelTextField;
    public ChoiceBox<String> styleChoiceBox;
    public Spinner<Integer> yearSpinner;
    public TextField priceTextField;

    public HBox filterBox;
    public TextField makeFilterTextField;
    public TextField modelFilterTextField;
    public ChoiceBox<String> styleFilterChoiceBox;
    public Spinner<Integer> minYearFilterSpinner;
    public Spinner<Integer> maxYearFilterSpinner;
    public TextField minPriceFilterTextField;
    public TextField maxPriceFilterTextField;

    private ObservableList<String> listOfStyleNames;

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
        initChoiceBoxes();
        showCars();
    }

    private void initChoiceBoxes() {
        listOfStyleNames = repository.getStyleNames();
        listOfStyleNames.add(0, "Не выбрано");

        styleChoiceBox.setItems(listOfStyleNames);
        styleFilterChoiceBox.setItems(listOfStyleNames);

        styleChoiceBox.valueProperty().setValue(listOfStyleNames.get(0));
        styleFilterChoiceBox.valueProperty().setValue(listOfStyleNames.get(0));
    }

    private void showCars() {
        idTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        makeTableColumn.setCellValueFactory(cellData -> cellData.getValue().makeProperty());
        modelTableColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        styleTableColumn.setCellValueFactory(cellData -> cellData.getValue().styleNameProperty());
        yearTableColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        priceTableColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        ObservableList<Car> resultList = repository.getCars(null);
        carsTableView.setItems(resultList);

        idTableColumn.setSortType(TableColumn.SortType.ASCENDING);
        carsTableView.getSortOrder().add(idTableColumn);
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
            String styleName = styleChoiceBox.getValue();
            String make = makeTextField.getText().trim().replaceAll(" +", " ");
            String model = modelTextField.getText().trim().replaceAll(" +", " ");
            Integer year = yearSpinner.getValue();
            String price = priceTextField.getText();

            //language=SQL
            String query = "INSERT INTO cars " +
                    "VALUES (" + null + ", (SELECT ID FROM styles WHERE NAME = '" + styleName + "' LIMIT 1), '" + make + "', '" + model + "', " + year + ", " + Double.parseDouble(price) + ");";

            System.out.println(query);

            repository.executeUpdate(query);
            clearUpdateFields();
            showCars();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Автомобиль " + make + ' ' + model + " добавлен.");
        }
    }

    private void applyEditButton() {
        if (checkUpdatingFields()) {
            int id = carsTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
            String styleName = styleChoiceBox.getValue();
            String make = makeTextField.getText().trim().replaceAll(" +", " ");
            String model = modelTextField.getText().trim().replaceAll(" +", " ");
            Integer year = yearSpinner.getValue();
            String price = priceTextField.getText();

            //language=SQL
            String query = "UPDATE cars SET " +
                    "STYLE_ID = (SELECT ID FROM styles WHERE NAME = '" + styleName + "' LIMIT 1), " +
                    "MAKE = '" + make + "', " +
                    "MODEL = '" + model + "', " +
                    "YEAR = " + year + ", " +
                    "PRICE = " + price + " " +
                    "WHERE ID = " + id + ";";

            repository.executeUpdate(query);
            clearUpdateFields();
            showCars();

            AlertDialog alert = new AlertDialog();
            alert.showInformationAlert("Изменения применены", "Автомобиль id" + id + " обновлён.");
        }
    }

    private boolean checkUpdatingFields() {
        ArrayList<String> errorMessages = new ArrayList<>();

        if (makeTextField.getText().trim().replaceAll(" +", " ").isEmpty())
            errorMessages.add("Марка отсутствует. ");
        if (modelTextField.getText().trim().replaceAll(" +", " ").isEmpty())
            errorMessages.add("Модель отсутствует. ");
        if (styleChoiceBox.getValue().equals(listOfStyleNames.get(0)))
            errorMessages.add("Тип кузова не выбран. ");
        if (priceTextField.getText().isEmpty())
            errorMessages.add("Цена отсутствует. ");
        else try {
            double price = Double.parseDouble(priceTextField.getText());
        } catch (Exception ex) {
            errorMessages.add("Цена введена некорректно. ");
        }

        if (errorMessages.size() != 0) {
            AlertDialog alert = new AlertDialog();
            alert.showErrorAlert("Обнаружена одна или несколько ошибок!", String.join(" ", errorMessages));
        }

        return errorMessages.size() == 0;
    }

    private void applyDeleteButton() {
        Car car = carsTableView.getSelectionModel().getSelectedItem();
        int id = car.idProperty().getValue();
        String make = car.makeProperty().getValue();
        String model = car.modelProperty().getValue();

        AlertDialog alert = new AlertDialog();
        boolean answer = alert.showConfirmationAlert("Вы уверены?",
                "Вы действительно хотите удалить автомобиль " + make + ' ' + model + "? Будут также удалены все заказы данного автомобиля.");

        if (answer) {
            //language=SQL
            String query = "DELETE FROM cars " +
                    "WHERE ID = " + id;

            repository.executeUpdate(query);
            clearUpdateFields();
            showCars();

            alert.showInformationAlert("Изменения применены", "Автомобиль " + make + ' ' + model + " удалён.");
        }
    }

    private void applyFilterButton() {
        String make = makeFilterTextField.getText().trim().replaceAll(" +", " ");
        String model = modelFilterTextField.getText().trim().replaceAll(" +", " ");
        String styleName = styleFilterChoiceBox.getValue();
        Integer minYear = minYearFilterSpinner.getValue();
        Integer maxYear = maxYearFilterSpinner.getValue();
        String minPrice = minPriceFilterTextField.getText();
        String maxPrice = maxPriceFilterTextField.getText();

        ArrayList<String> filter = new ArrayList<>();
        if (!make.isEmpty()) filter.add("MAKE ='" + make + "'");
        if (!model.isEmpty()) filter.add("MODEL ='" + model + "'");
        if (!styleName.equals(listOfStyleNames.get(0))) filter.add("STYLE ='" + styleName + "'");
        filter.add("YEAR >='" + minYear + "'");
        filter.add("YEAR <='" + maxYear + "'");
        if (!minPrice.isEmpty()) filter.add("PRICE >='" + minPrice + "'");
        if (!maxPrice.isEmpty()) filter.add("PRICE <='" + maxPrice + "'");

        //language=SQL
        String filterString = "WHERE " + String.join(" AND ", filter);

        ObservableList<Car> resultList = repository.getCars(filterString);
        carsTableView.setItems(resultList);
    }

    @FXML
    public void tableItemSelect() {
        Car car = carsTableView.getSelectionModel().getSelectedItem();
        if (car != null)
            fillFieldsIfCellIsSelected(car);
    }

    private void fillFieldsIfCellIsSelected(Car car) {
        Toggle selectedToggle = actions.getSelectedToggle();
        boolean isEdit = editToggleButton.equals(selectedToggle);
        boolean isDelete = deleteToggleButton.equals(selectedToggle);
        if (isEdit || isDelete) {
            makeTextField.setText(car.makeProperty().getValue());
            modelTextField.setText(car.modelProperty().getValue());
            styleChoiceBox.setValue(car.styleNameProperty().getValue());
            yearSpinner.getValueFactory().setValue(car.yearProperty().getValue());
            priceTextField.setText(car.priceProperty().getValue().toString());
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

        Car car = carsTableView.getSelectionModel().getSelectedItem();
        if (car != null) {
            updatingBoxFieldsIsEnabled(true);
            fillFieldsIfCellIsSelected(car);
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

        Car car = carsTableView.getSelectionModel().getSelectedItem();
        if (car != null)
            fillFieldsIfCellIsSelected(car);
        else
            clearUpdateFields();
    }

    private void updatingBoxFieldsIsEnabled(boolean isEnabled) {
        makeTextField.setDisable(!isEnabled);
        modelTextField.setDisable(!isEnabled);
        styleChoiceBox.setDisable(!isEnabled);
        yearSpinner.setDisable(!isEnabled);
        priceTextField.setDisable(!isEnabled);
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
        makeTextField.clear();
        modelTextField.clear();
        styleChoiceBox.valueProperty().setValue(listOfStyleNames.get(0));
        yearSpinner.getValueFactory().setValue(2021);
        priceTextField.clear();
    }

    private void clearFilterFields() {
        makeFilterTextField.clear();
        modelFilterTextField.clear();
        styleFilterChoiceBox.valueProperty().setValue(listOfStyleNames.get(0));
        minYearFilterSpinner.getValueFactory().setValue(1990);
        maxYearFilterSpinner.getValueFactory().setValue(2021);
        minPriceFilterTextField.clear();
        maxPriceFilterTextField.clear();
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
