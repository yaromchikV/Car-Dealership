package com.yaromchikv.dealership.utils.controls;

import javafx.scene.control.PasswordField;

public class CustomPasswordField extends PasswordField {

    private int maxLength = 0;

    public CustomPasswordField() {
        this.textProperty().addListener((ov, oldValue, newValue) -> {
            String regex = "[A-Za-z0-9А-Яа-я.]*";
            if (get().length() > maxLength || !get().matches(regex)) {
                String string = get().substring(0, get().length() - 1);
                set(string);
            }
        });
    }

    private String get() {
        return this.getText();
    }

    private void set(String text) {
        this.setText(text);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
