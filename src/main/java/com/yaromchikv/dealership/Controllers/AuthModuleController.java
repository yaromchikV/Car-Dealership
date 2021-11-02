package com.yaromchikv.dealership.Controllers;

import com.yaromchikv.dealership.ScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static com.yaromchikv.dealership.Constants.EMPLOYEE_CUSTOMERS_DASHBOARD;

public class AuthModuleController {
    
    public Button logInButton;
    public PasswordField passwordField;
    public TextField loginField;

    @FXML
    public void logInButtonClick() {
        ScreenController.activate(EMPLOYEE_CUSTOMERS_DASHBOARD);
    }
}
