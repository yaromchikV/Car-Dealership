package com.yaromchikv.dealership;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Main stage!");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/log_in_view.fxml"));
        //LoginController controller = fxmlLoader.getController();

        //controller.setPrevStage(primaryStage);

        Scene myScene = new Scene(fxmlLoader.load());
        primaryStage.setScene(myScene);
        primaryStage.show();
    }
}