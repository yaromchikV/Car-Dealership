package com.yaromchikv.dealership.utils.controls;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

    private int maxLength = 0;
    private boolean allowDigits = true;
    private boolean allowLetters = true;
    private boolean allowDot = false;
    private boolean allowAt = false;

    public CustomTextField() {
        this.textProperty().addListener((ov, oldValue, newValue) -> {
            StringBuilder regex = new StringBuilder("[");
            if (allowDigits) regex.append("0-9");
            if (allowLetters) regex.append("A-Za-zА-Яа-я");
            if (allowDot) regex.append(".");
            if (allowAt) regex.append("@");
            regex.append("]*");

            boolean matches = false;
            if (!(regex.toString().equals("[]*"))) matches = get().matches(regex.toString());

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

    public void setAllowDot(boolean allowDot) {
        this.allowDot = allowDot;
    }

    public void setAllowAt(boolean allowAt) {
        this.allowAt = allowAt;
    }

    public boolean getAllowDot() {
        return this.allowDot;
    }

    public boolean getAllowAt() {
        return this.allowAt;
    }
};
