package com.yaromchikv.dealership;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {

    private static final HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;

    public static Scene getMainScene() {
        return main;
    }

    public static void setMainScene(Scene main) {
        ScreenController.main = main;
    }

    protected static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void activate(String name) {
        main.setRoot(screenMap.get(name));
    }
}
