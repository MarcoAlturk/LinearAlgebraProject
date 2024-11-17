package com.example.linearalgebrasolver;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MainPageUI {

    public static Scene mainScene(){

        TabPane tabPane = new TabPane();

        matrixOperations(tabPane);


        pointsOptions(tabPane);


        Scene scene = new Scene(tabPane);
        return scene;
    }
    public static void pointsOptions(TabPane tabPane){
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
        layout.getChildren().addAll(selectLabel, distanceTypeCombo);
        distanceTab.setContent(layout);
        tabPane.getTabs().add(distanceTab);
    }

    public static  void matrixOperations(TabPane tabPane){
        Tab tabMatrixOperations = new Tab("Matrix Operations");
        tabMatrixOperations.setClosable(false);
        tabPane.getTabs().add(tabMatrixOperations);
    }
}
