package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.Main;
import com.yaromchikv.dealership.ScreenController;
import com.yaromchikv.dealership.data.Repository;
import com.yaromchikv.dealership.utils.AccessLevel;
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
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    private void logInButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(username + ' ' + password);

        if (!username.isEmpty() && !password.isEmpty()) {
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                Main.myAccessLevel = AccessLevel.ADMIN;
                ScreenController.activate(ADMIN_EMPLOYEES_DASHBOARD);
            } else {
                Integer id = repository.getIdByUsername(username);
                if (id != null) {
                    password = Hash.convert(id, password);
                    Main.myAccessLevel = repository.checkUsernameAndPassword(username, password);

                    if (Main.myAccessLevel == AccessLevel.EMPLOYEE) {
                        ScreenController.activate(EMPLOYEE_ORDERS_DASHBOARD);
                    } else {
                        // Неверный пароль
                    }
                } else {
                    // Не найден логин
                }
            }
        } else {
            // Не введён логин или пароль
        }
    }
}
