package com.yaromchikv.dealership.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertDialog {

    public void showInformationAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Уведомление");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showWarningAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Уведомление");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Уведомление");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public boolean showConfirmationAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Уведомление");
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType ok = new ButtonType("ОК");
        ButtonType cancel = new ButtonType("Отменить");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ok, cancel);

        Optional<ButtonType> option = alert.showAndWait();
        return option.isPresent() && option.get() == ok;
    }
}
