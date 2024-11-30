module com.example.linearalgebrasolver {
    requires javafx.controls;
    requires javafx.fxml;
    requires ejml.simple;


    opens com.example.linearalgebrasolver to javafx.fxml;
    exports com.example.linearalgebrasolver;
    exports com.example.linearalgebrasolver.OperationsMatrices;
    opens com.example.linearalgebrasolver.OperationsMatrices to javafx.fxml;
    exports com.example.linearalgebrasolver.PolyMessh;
    opens com.example.linearalgebrasolver.PolyMessh to javafx.fxml;
}