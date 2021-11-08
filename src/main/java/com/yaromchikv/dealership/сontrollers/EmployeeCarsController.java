package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.data.models.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
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
    public TextField yearTextField;
    public TextField priceTextField;

    public HBox filterBox;
    public TextField makeFilterTextField;
    public TextField modelFilterTextField;
    public ChoiceBox<String> styleFilterChoiceBox;
    public ChoiceBox<Integer> minYearFilterChoiceBox;
    public ChoiceBox<Integer> maxYearFilterChoiceBox;
    public TextField minPriceFilterTextField;
    public TextField maxPriceFilterTextField;

    private ObservableList<String> listOfCarsNames;
    private ObservableList<Integer> listOfYears;

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
        initChoiceBoxes();
        showCars();
    }

    private void initChoiceBoxes() {
        listOfCarsNames = repository.getStyleNames();
        listOfCarsNames.add(0, "Не выбрано");

        listOfYears = FXCollections.observableArrayList();
        for (int i = 2000; i < 2022; i++) {
            listOfYears.add(i);
        }

        styleChoiceBox.setItems(listOfCarsNames);
        styleFilterChoiceBox.setItems(listOfCarsNames);
        minYearFilterChoiceBox.setItems(listOfYears);
        maxYearFilterChoiceBox.setItems(listOfYears);

        styleChoiceBox.valueProperty().setValue(listOfCarsNames.get(0));
        styleFilterChoiceBox.valueProperty().setValue(listOfCarsNames.get(0));
        minYearFilterChoiceBox.setValue(listOfYears.get(0));
        maxYearFilterChoiceBox.setValue(listOfYears.get(listOfYears.size() - 1));
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
        String styleName = styleChoiceBox.getValue();
        String make = makeTextField.getText();
        String model = modelTextField.getText();
        String year = yearTextField.getText();
        String price = priceTextField.getText();

        //language=SQL
        String query = "INSERT INTO CARS_TABLE " +
                "VALUES + (" + null + ", (SELECT ID FROM STYLES_TABLE WHERE NAME = '" + styleName + "' LIMIT 1), '" + make + "', '" + model + "', " + Integer.parseInt(year) + ", " + Double.parseDouble(price) + ");";

        System.out.println(query);

        repository.executeUpdate(query);
        showCars();
    }

    private void applyEditButton() {
        int id = carsTableView.getSelectionModel().getSelectedItem().idProperty().getValue();
        String styleName = styleChoiceBox.getValue();
        String make = makeTextField.getText();
        String model = modelTextField.getText();
        String year = yearTextField.getText();
        String price = priceTextField.getText();

        //language=SQL
        String query = "UPDATE CARS_TABLE SET " +
                "STYLE_ID = (SELECT ID FROM STYLES_TABLE WHERE NAME = '" + styleName + "' LIMIT 1), " +
                "MAKE = '" + make + "', " +
                "MODEL = '" + model + "', " +
                "YEAR = " + year + ", " +
                "PRICE = " + price + " " +
                "WHERE ID = " + id + ";";

        repository.executeUpdate(query);
        showCars();
    }

    private void applyDeleteButton() {
        int id = carsTableView.getSelectionModel().getSelectedItem().idProperty().getValue();

        //language=SQL
        String query = "DELETE FROM CARS_TABLE " +
                "WHERE ID = " + id;

        repository.executeUpdate(query);
        showCars();
    }

    private void applyFilterButton() {
        String make = makeFilterTextField.getText();
        String model = modelFilterTextField.getText();
        String styleName = styleFilterChoiceBox.getValue();
        Integer minYear = minYearFilterChoiceBox.getValue();
        Integer maxYear = maxYearFilterChoiceBox.getValue();
        String minPrice = minPriceFilterTextField.getText();
        String maxPrice = maxPriceFilterTextField.getText();

        //language=SQL
        StringBuilder filterBuilder = new StringBuilder("WHERE ");
        if (!make.isEmpty()) filterBuilder.append("MAKE ='").append(make).append("' AND ");
        if (!model.isEmpty()) filterBuilder.append("MODEL ='").append(model).append("' AND ");
        if (!styleName.equals(listOfCarsNames.get(0)))
            filterBuilder.append(" styles.NAME ='").append(styleName).append("' AND ");
        if (!minYear.equals(listOfYears.get(0)))
            filterBuilder.append("YEAR >=").append(minYear).append(" AND ");
        if (!maxYear.equals(listOfYears.get(listOfYears.size() - 1)))
            filterBuilder.append("YEAR <=").append(maxYear).append(" AND ");
        if (!minPrice.isEmpty())
            filterBuilder.append("PRICE >=").append(Double.parseDouble(minPrice)).append(" AND ");
        if (!maxPrice.isEmpty())
            filterBuilder.append("PRICE <=").append(Double.parseDouble(maxPrice)).append(" AND ");

        String filter = null;
        if (filterBuilder.length() > 5) {
            filterBuilder.setLength(filterBuilder.length() - 4);
            filter = filterBuilder.toString();
        }

        ObservableList<Car> resultList = repository.getCars(filter);
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
            yearTextField.setText(car.yearProperty().getValue().toString());
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
        yearTextField.setDisable(!isEnabled);
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
        styleChoiceBox.valueProperty().setValue(listOfCarsNames.get(0));
        yearTextField.clear();
        priceTextField.clear();
    }

    private void clearFilterFields() {
        makeFilterTextField.clear();
        modelFilterTextField.clear();
        styleFilterChoiceBox.valueProperty().setValue(listOfCarsNames.get(0));
        minYearFilterChoiceBox.setValue(listOfYears.get(0));
        maxYearFilterChoiceBox.setValue(listOfYears.get(listOfYears.size() - 1));
        minPriceFilterTextField.clear();
        maxPriceFilterTextField.clear();
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

}
