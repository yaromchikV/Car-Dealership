module com.yaromchikv.dealership {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.yaromchikv.dealership to javafx.fxml;
    exports com.yaromchikv.dealership;
}