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
    exports com.yaromchikv.dealership.data.tableModels;
    opens com.yaromchikv.dealership.data to javafx.fxml;
}