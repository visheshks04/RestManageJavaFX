module com.example.fxdemo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;

    opens com.example.fxdemo1 to javafx.fxml;
    exports com.example.fxdemo1;
}