module com.yaromchikv.dealership {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.yaromchikv.dealership to javafx.fxml;
    exports com.yaromchikv.dealership;
    exports com.yaromchikv.dealership.сontrollers;
    opens com.yaromchikv.dealership.сontrollers to javafx.fxml;
    exports com.yaromchikv.dealership.data;
    exports com.yaromchikv.dealership.data.models;
    opens com.yaromchikv.dealership.data to javafx.fxml;
    exports com.yaromchikv.dealership.connection;
    opens com.yaromchikv.dealership.connection to javafx.fxml;
    exports com.yaromchikv.dealership.utils;
    opens com.yaromchikv.dealership.utils to javafx.fxml;
    exports com.yaromchikv.dealership.utils.controls;
    opens com.yaromchikv.dealership.utils.controls to javafx.fxml;
}