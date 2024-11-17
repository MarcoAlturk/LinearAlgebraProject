package com.example.linearalgebrasolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Linear Algebra Solver");

        // Main Title Button (optional, can add styling)
        Button titleButton = new Button("Linear Algebra Solver");
        titleButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #f0f0f0;");

        // Buttons for different operations
        Button rrefButton = new Button("RREF Solver");
        Button multiplicationButton = new Button("Matrix Multiplication");
        Button vectorAdditionButton = new Button("Vector Addition");
        Button scalarMultiplicationButton = new Button("Scalar Multiplication");
        Button planePointDistanceButton = new Button("Plane to Point Distance");
        Button planePlaneDistanceButton = new Button("Plane to Plane Distance");
        Button planeLineDistanceButton = new Button("Plane to Line Distance");
        Button linePointDistanceButton = new Button("Line to Point Distance");

        // Styling for buttons
        Button[] buttons = {
                rrefButton, multiplicationButton, vectorAdditionButton, scalarMultiplicationButton,
                planePointDistanceButton, planePlaneDistanceButton, planeLineDistanceButton, linePointDistanceButton
        };

        for (Button btn : buttons) {
            btn.setPrefWidth(200);
            btn.setStyle("-fx-font-size: 14px;");
        }

        // HBoxes for grouping buttons horizontally
        HBox row1 = new HBox(10, rrefButton, multiplicationButton);
        HBox row2 = new HBox(10, vectorAdditionButton, scalarMultiplicationButton);
        HBox row3 = new HBox(10, planePointDistanceButton, planePlaneDistanceButton);
        HBox row4 = new HBox(10, planeLineDistanceButton, linePointDistanceButton);

        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        row4.setAlignment(Pos.CENTER);

        // VBox for organizing rows vertically
        VBox mainLayout = new VBox(15, titleButton, row1, row2, row3, row4);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #ffffff;");

        // Add event handlers for each button (example placeholders)
        rrefButton.setOnAction(e -> System.out.println("RREF Solver clicked"));
        multiplicationButton.setOnAction(e -> System.out.println("Matrix Multiplication clicked"));
        vectorAdditionButton.setOnAction(e -> System.out.println("Vector Addition clicked"));
        scalarMultiplicationButton.setOnAction(e -> System.out.println("Scalar Multiplication clicked"));
        planePointDistanceButton.setOnAction(e -> System.out.println("Plane to Point Distance clicked"));
        planePlaneDistanceButton.setOnAction(e -> System.out.println("Plane to Plane Distance clicked"));
        planeLineDistanceButton.setOnAction(e -> System.out.println("Plane to Line Distance clicked"));
        linePointDistanceButton.setOnAction(e -> System.out.println("Line to Point Distance clicked"));

        // Set the scene and show the stage
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch();
    }
}