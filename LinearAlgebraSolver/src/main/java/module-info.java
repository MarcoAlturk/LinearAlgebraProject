module com.example.linearalgebrasolver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.linearalgebrasolver to javafx.fxml;
    exports com.example.linearalgebrasolver;
}