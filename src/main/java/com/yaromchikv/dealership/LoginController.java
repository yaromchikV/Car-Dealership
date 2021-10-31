package com.yaromchikv.dealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button logInButton;

    Stage prevStage;

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    protected void onLogInButtonClick() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("New stage");

        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("log_in_view.fxml")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        prevStage.close();
        stage.show();
    }
}