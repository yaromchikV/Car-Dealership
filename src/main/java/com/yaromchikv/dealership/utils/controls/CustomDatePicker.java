package com.yaromchikv.dealership.utils.controls;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDatePicker extends DatePicker {

    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CustomDatePicker() {
        this.setConverter(new StringConverter<>() {
            {
                setPromptText("дд.мм.гггг");
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else return null;
            }
        });
    }
}
