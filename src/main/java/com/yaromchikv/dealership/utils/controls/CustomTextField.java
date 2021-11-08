package com.yaromchikv.dealership.utils.controls;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    private int maxLength = 0;
    private boolean allowDigits = true;
    private boolean allowLetters = true;
    private boolean isDouble = false;
    private boolean isEmail = false;

    public CustomTextField() {
        this.textProperty().addListener((ov, oldValue, newValue) -> {
            String regex = "";
            if (isDouble) regex = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
            else if (isEmail)
                regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            else {
                if (allowLetters && allowDigits) regex = "[A-Za-z0-9А-Яа-я]*";
                else if (allowDigits) regex = "[0-9]*";
                else if (allowLetters) regex = "[A-Za-zА-Яа-я]*";
            }

            boolean matches = false;
            if (!regex.isEmpty()) matches = get().matches(regex);

            if (get().length() > maxLength || !matches) {
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

    public boolean getAllowDigits() {
        return this.allowDigits;
    }

    public boolean getAllowLetters() {
        return this.allowLetters;
    }

    public void setAllowDigits(boolean isAllow) {
        this.allowDigits = isAllow;
    }

    public void setAllowLetters(boolean isAllow) {
        this.allowLetters = isAllow;
    }

    public boolean getIsDouble() {
        return this.isDouble;
    }

    public void setIsDouble(boolean isDouble) {
        this.isDouble = isDouble;
    }

    public boolean getIsEmail() {
        return this.isEmail;
    }

    public void setIsEmail(boolean isEmail) {
        this.isEmail = isEmail;
    }
};
