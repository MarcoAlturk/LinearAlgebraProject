package com.example.linearalgebrasolver;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class MainPageUI {

    public static Scene mainScene() {
        TabPane tabPane = new TabPane();

        matrixOperations(tabPane);
        pointsOptions(tabPane);

        return new Scene(tabPane);
    }

    public static void pointsOptions(TabPane tabPane) {
        Tab distanceTab = new Tab("Distance Calculations");
        distanceTab.setClosable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label selectLabel = new Label("Select Distance Type:");
        ComboBox<String> distanceTypeCombo = new ComboBox<>();
        distanceTypeCombo.getItems().addAll(
                "Plane to Point Distance",
                "Plane to Line Distance",
                "Line to Point Distance"
        );
        distanceTypeCombo.setValue("Plane to Point Distance");

        VBox inputArea = new VBox(10); // Dynamic area to add inputs
        inputArea.setPadding(new Insets(10));

        // Set up listener for ComboBox selection
        distanceTypeCombo.setOnAction(e -> {
            String selected = distanceTypeCombo.getValue();
            inputArea.getChildren().clear(); // Clear existing inputs

            if (selected.equals("Plane to Point Distance")) {
                setupPlaneToPointUI(inputArea);
            }
            // You can implement setup for other distance types here
        });

        // Default setup
        setupPlaneToPointUI(inputArea);

        layout.getChildren().addAll(selectLabel, distanceTypeCombo, inputArea);
        distanceTab.setContent(layout);
        tabPane.getTabs().add(distanceTab);
    }

    public static void setupPlaneToPointUI(VBox inputArea) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Labels and TextFields for plane equation
        Label labelPlane = new Label("Plane Equation (Ax + By + Cz + D = 0):");
        TextField fieldA = createNumericField("A");
        TextField fieldB = createNumericField("B");
        TextField fieldC = createNumericField("C");
        TextField fieldD = createNumericField("D");

        fieldA.setAlignment(Pos.CENTER_RIGHT);
        fieldB.setAlignment(Pos.CENTER_RIGHT);
        fieldC.setAlignment(Pos.CENTER_RIGHT);
        fieldD.setAlignment(Pos.CENTER_RIGHT);

        HBox planeInputs = new HBox(5, fieldA, new Label("x +"), fieldB, new Label("y +"),
                fieldC, new Label("z +"), fieldD, new Label("= 0"));
        planeInputs.setAlignment(Pos.CENTER);

        // Labels and TextFields for point coordinates
        Label labelPoint = new Label("Point Coordinates:");
        TextField fieldX = createNumericField("X");
        TextField fieldY = createNumericField("Y");
        TextField fieldZ = createNumericField("Z");

        HBox pointInputs = new HBox(5, fieldX, new Label("X"), fieldY, new Label("Y"), fieldZ, new Label("Z"));
        pointInputs.setAlignment(Pos.CENTER);

        // Button and output label
        Button calculateButton = new Button("Calculate Distance");
        Label resultLabel = new Label();

        calculateButton.setOnAction(e -> {
            double A = Integer.parseInt(fieldA.getText());
            double B = Integer.parseInt(fieldB.getText());
            double C = Integer.parseInt(fieldC.getText());
            double D = Integer.parseInt(fieldD.getText());
            double x = Integer.parseInt(fieldX.getText());
            double y = Integer.parseInt(fieldY.getText());
            double z = Integer.parseInt(fieldZ.getText());

            // Calculate distance
            double numerator = Math.abs(A * x + B * y + C * z + D);
            double denominator = Math.sqrt(A * A + B * B + C * C);
            double distance = numerator / denominator;

            DistanceVisualizer visualizer = new DistanceVisualizer();
            Stage visualizerStage = visualizer.createVisualizer(A, B, C, D, x, y, z, distance);
            visualizerStage.show();


            resultLabel.setText(String.format("Distance: %.2f", distance));
        });

        grid.add(labelPlane, 0, 0);
        grid.add(planeInputs, 0, 1);
        grid.add(labelPoint, 0, 2);
        grid.add(pointInputs, 0, 3);
        grid.add(calculateButton, 0, 4);
        grid.add(resultLabel, 0, 5);

        inputArea.getChildren().add(grid);
    }

    public static TextField createNumericField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) { // Allow only numbers
                field.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });
        return field;
    }

    public static void matrixOperations(TabPane tabPane) {
        BuilMatrixOperations builMatrixOperations = new BuilMatrixOperations();
        Tab tabMatrixOperations = new Tab("Matrix Operations");

        HBox layoutWithCreateButton = new HBox(builMatrixOperations.buildLayoutForMatrixWithCreateButton());
        layoutWithCreateButton.setAlignment(Pos.CENTER);

        VBox vBoxCurrentRootForTabs = new VBox(layoutWithCreateButton);
        tabMatrixOperations.setContent(vBoxCurrentRootForTabs);

        tabMatrixOperations.setClosable(false);
        tabPane.getTabs().add(tabMatrixOperations);
    }
}
