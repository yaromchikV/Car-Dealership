package com.yaromchikv.dealership;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.yaromchikv.dealership.Constants.*;

public class MainStage extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        MyConnection.setConnection();

        fillTheScreenController();
        ScreenController.activate(AUTH_SCREEN);

        primaryStage.setTitle(APP_NAME);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/app-icon.png"));
        primaryStage.setScene(ScreenController.getMainScene());
        primaryStage.show();
    }

    private void fillTheScreenController() throws IOException {
        ScreenController.setMainScene(new Scene(getPaneByPath("/screens/empty-screen.fxml")));

        ScreenController.addScreen(ADMIN_EMPLOYEES_DASHBOARD, getPaneByPath("/screens/admin-employees-dashboard.fxml"));
        ScreenController.addScreen(ADMIN_POSITIONS_DASHBOARD, getPaneByPath("/screens/admin-positions-dashboard.fxml"));
        ScreenController.addScreen(AUTH_SCREEN, getPaneByPath("/screens/auth-screen.fxml"));
        ScreenController.addScreen(EMPLOYEE_CARS_DASHBOARD, getPaneByPath("/screens/employee-cars-dashboard.fxml"));
        ScreenController.addScreen(EMPLOYEE_CUSTOMERS_DASHBOARD, getPaneByPath("/screens/employee-customers-dashboard.fxml"));
        ScreenController.addScreen(EMPLOYEE_ORDERS_DASHBOARD, getPaneByPath("/screens/employee-orders-dashboard.fxml"));
        ScreenController.addScreen(EMPLOYEE_STYLES_DASHBOARD, getPaneByPath("/screens/employee-styles-dashboard.fxml"));
    }

    private Pane getPaneByPath(String path) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Connection is broken.");
        MyConnection.closeConnection();
        super.stop();
    }
}