package com.yaromchikv.dealership.сontrollers;

import com.yaromchikv.dealership.ScreenController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.yaromchikv.dealership.Constants.ADMIN_EMPLOYEES_DASHBOARD;

public class AuthScreenController implements Initializable {
    
    public Button logInButton;
    public PasswordField passwordField;
    public TextField usernameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //
    }

    @FXML
    private void logInButtonClick() {
        ScreenController.activate(ADMIN_EMPLOYEES_DASHBOARD);
    }

}
