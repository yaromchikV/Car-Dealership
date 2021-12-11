package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.Main;
import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.utils.AccessLevel;
import com.yaromchikv.dealership.utils.AlertDialog;
import com.yaromchikv.dealership.utils.Hash;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.utils.Constants.*;
import static com.yaromchikv.dealership.utils.Constants.ADMIN_PASSWORD;

public class AuthScreenController implements Initializable {

    public PasswordField passwordField;
    public TextField usernameField;

    private Repository repository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repository = new Repository();
    }

    @FXML
    private void logInButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                Main.myAccessLevel = AccessLevel.ADMIN;
                ScreenController.activate(ADMIN_EMPLOYEES_DASHBOARD);
            } else {
                //language=SQL
                String query = "SELECT ID FROM accounts WHERE USERNAME = '" + username + "'";
                Integer id = repository.getIdByQuery(query);
                if (id != null) {
                    password = Hash.convert(id, password);
                    if (repository.checkUsernameAndPassword(username, password)) {
                        Main.myAccessLevel = AccessLevel.EMPLOYEE;
                        ScreenController.activate(EMPLOYEE_ORDERS_DASHBOARD);
                    } else {
                        AlertDialog alert = new AlertDialog();
                        alert.showWarningAlert("Внимание!", "Введён неверный пароль!");
                    }
                } else {
                    AlertDialog alert = new AlertDialog();
                    alert.showWarningAlert("Внимание!", "Пользователь не найден!");
                }
            }
        } else {
            AlertDialog alert = new AlertDialog();
            alert.showWarningAlert("Внимание!", "Не введён логин и/или пароль!");
        }

        usernameField.clear();
        passwordField.clear();
    }
}
