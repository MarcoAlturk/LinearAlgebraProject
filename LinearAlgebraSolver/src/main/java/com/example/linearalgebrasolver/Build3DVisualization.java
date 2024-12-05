package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.View3DContainer.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;

public class Build3DVisualization {
    public static VBox leftControlPanel;
    public static VBox rightListPanel;
    public static Group mainGroup = new Group();
    public static CameraSettings cameraSettings = new CameraSettings();
    public static Group visualizationGroup = cameraSettings.getCartesianPlane();
    private static HBox activeInputSection;

    public static BorderPane build(TabPane tabPane) {
        BorderPane root = new BorderPane();

        Tab visualization3D = new Tab("3D Visualization");
        tabPane.getTabs().add(visualization3D);
        visualization3D.setClosable(false);

        // SubScene for 3D visualization
        SubScene subScene = new SubScene(
                visualizationGroup,
                700, 700,
                false, // for depth buffer what ever that is
                SceneAntialiasing.BALANCED // improve rendering
        );
        CameraSettings.handleGroupControls(mainGroup);
        subScene.setCamera(cameraSettings.getCamera());
        CameraSettings.handleGroupControls(visualizationGroup);

        leftControlPanel = new VBox(10);
        leftControlPanel.setPadding(new Insets(10));
        leftControlPanel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d0d0d0;");

        Button addPlaneButton = new Button("Add Plane");
        addPlaneButton.setMaxWidth(9999999);
        Button addPointButton = new Button("Add Point");
        addPointButton.setMaxWidth(9999999);
        Button addLineButton = new Button("Add Line");
        addLineButton.setMaxWidth(9999999);
        Button crossProductButton = new Button("Calculate Cross Product");
        crossProductButton.setMaxWidth(9999999);

        addPlaneButton.setOnAction(e -> showInputSection("Plane"));
        addPointButton.setOnAction(e -> showInputSection("Point"));
        addLineButton.setOnAction(e -> showInputSection("Line"));
        crossProductButton.setOnAction(e ->{showCrossProductInputSection();});

        leftControlPanel.getChildren().addAll(addPlaneButton, addPointButton, addLineButton, crossProductButton);
        leftControlPanel.setPrefWidth(300); // Set preferred width
        leftControlPanel.setMaxWidth(350);
        // Right List Panel to display added elements
        rightListPanel = new VBox(10);
        rightListPanel.setPadding(new Insets(10));
        rightListPanel.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #d0d0d0;");
        rightListPanel.setMinWidth(200);

        // Place SubScene in the center, Control Panel on the left, and List Panel on the right
        root.setCenter(subScene);
        root.setLeft(leftControlPanel);
        root.setRight(rightListPanel);

        visualization3D.setContent(root);
        return root;
    }

    private static void showInputSection(String type) {
        if (activeInputSection != null) {
            leftControlPanel.getChildren().remove(activeInputSection);
        }

        activeInputSection = new HBox(10);
        activeInputSection.setPadding(new Insets(10));
        activeInputSection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d0d0d0;");

        VBox inputs = new VBox(5);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: #ff0a12; -fx-font-weight: bold;"); // Set background color to orange
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> leftControlPanel.getChildren().remove(activeInputSection));

        if (type.equals("Point")) {
            Label labelPoint = new Label("Plane Equation: aX + bY + cZ - d = 0");
            labelPoint.setWrapText(true);
            inputs.getChildren().add(labelPoint);

            TextField xField = new TextField();
            xField.setPromptText("x");
            xField.setPrefWidth(60); // Set preferred width
            xField.setMaxWidth(80);
            TextField yField = new TextField();
            yField.setPromptText("y");
            yField.setPrefWidth(60); // Set preferred width
            yField.setMaxWidth(80);
            TextField zField = new TextField();
            zField.setPromptText("z");
            zField.setPrefWidth(60); // Set preferred width
            zField.setMaxWidth(80);

            Button addButton = new Button("Add Point");
            addButton.setOnAction(e -> {
                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                double z = Double.parseDouble(zField.getText());

                Points point = new Points(x, y, z, cameraSettings.size);
                Sphere pointSphere = point.getPointSphere();

                visualizationGroup.getChildren().addAll(pointSphere, point.getPointLabel());


                addElementToList(point.getPointLabel().getTextTocheck(), pointSphere, point.getPointLabel());
            });

            inputs.getChildren().addAll(xField, yField, zField, addButton);
        } else if (type.equals("Plane")) {

            Label labelPlane = new Label("Plane Equation: aX + bY + cZ - d = 0");
            labelPlane.setWrapText(true);
            inputs.getChildren().add(labelPlane);

            TextField aField = new TextField();
            aField.setPromptText("a");
            aField.setPrefWidth(60); // Set preferred width
            aField.setMaxWidth(80);
            TextField bField = new TextField();
            bField.setPromptText("b");
            bField.setPrefWidth(60); // Set preferred width
            bField.setMaxWidth(80);
            TextField cField = new TextField();
            cField.setPromptText("c");
            cField.setPrefWidth(60); // Set preferred width
            cField.setMaxWidth(80);
            TextField dField = new TextField();
            dField.setPromptText("d");
            dField.setPrefWidth(60); // Set preferred width
            dField.setMaxWidth(80);

            Button addButton = new Button("Add Plane");
            addButton.setOnAction(e -> {
                double a = Double.parseDouble(aField.getText());
                double b = Double.parseDouble(bField.getText());
                double c = Double.parseDouble(cField.getText());
                double d = Double.parseDouble(dField.getText());

                Planes plane = new Planes();
                plane.setNormalVector(a, b, c, d);
                Group planeMesh = plane.createPlaneMesh(50);

                // Add to the visualization
                visualizationGroup.getChildren().add(planeMesh);

                addElementToList("Plane: " + a + "X + " + b + "Y + " + c + "Z - " + d + " = 0", planeMesh, null);
            });

            inputs.getChildren().addAll(aField, bField, cField, dField, addButton);
        } else if (type.equals("Line")) {
            Label labelPoints = new Label("Line: Enter Two Points (x1, y1, z1) and (x2, y2, z2):");
            labelPoints.setWrapText(true);
            inputs.getChildren().add(labelPoints);

            TextField x1Field = new TextField();
            x1Field.setPromptText("x1");
            x1Field.setPrefWidth(60); // Set preferred width
            x1Field.setMaxWidth(80);
            TextField y1Field = new TextField();
            y1Field.setPromptText("y1");
            y1Field.setPrefWidth(60); // Set preferred width
            y1Field.setMaxWidth(80);
            TextField z1Field = new TextField();
            z1Field.setPromptText("z1");
            z1Field.setPrefWidth(60); // Set preferred width
            z1Field.setMaxWidth(80);
            TextField x2Field = new TextField();
            x2Field.setPromptText("x2");
            x2Field.setPrefWidth(60); // Set preferred width
            x2Field.setMaxWidth(80);
            TextField y2Field = new TextField();
            y2Field.setPrefWidth(60); // Set preferred width
            y2Field.setMaxWidth(80);
            y2Field.setPromptText("y2");
            TextField z2Field = new TextField();
            z2Field.setPromptText("z2");
            z2Field.setPrefWidth(60); // Set preferred width
            z2Field.setMaxWidth(80);

            Button addButton = new Button("Add Line");
            addButton.setOnAction(e -> {
                double x1 = Double.parseDouble(x1Field.getText());
                double y1 = Double.parseDouble(y1Field.getText());
                double z1 = Double.parseDouble(z1Field.getText());
                double x2 = Double.parseDouble(x2Field.getText());
                double y2 = Double.parseDouble(y2Field.getText());
                double z2 = Double.parseDouble(z2Field.getText());

                Points start = new Points(x1, y1, z1, cameraSettings.size);
                Points end = new Points(x2, y2, z2, cameraSettings.size);


                Lines line = new Lines(start, end);
                Group lineMesh = line.createLineWithNormalVector();
                visualizationGroup.getChildren().add(lineMesh);

                // Add to the right list
                addElementToList(
                        "Line: (" + x1 + ", " + y1 + ", " + z1 + ") to (" + x2 + ", " + y2 + ", " + z2 + ")",
                        lineMesh,
                        null
                );
            });

            inputs.getChildren().addAll(x1Field, y1Field, z1Field, x2Field, y2Field, z2Field, addButton);
        }


        activeInputSection.getChildren().addAll(inputs, closeButton);
        leftControlPanel.getChildren().add(activeInputSection);
    }
    private static String calculateCrossProductString(double ux, double uy, double uz, double vx, double vy, double vz) {
        // Compute the cross product
        double wx = uy * vz - uz * vy;
        double wy = uz * vx - ux * vz;
        double wz = ux * vy - uy * vx;

        return "û X v̂ = ŵ(" + wx + ", " + wy + ", " + wz + ")";
    }

        private static void showCrossProductInputSection() {
        if (activeInputSection != null) {
            leftControlPanel.getChildren().remove(activeInputSection);
        }

        activeInputSection = new HBox(10);
        activeInputSection.setPadding(new Insets(10));
        activeInputSection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d0d0d0;");

        VBox inputs = new VBox(5);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: #ff0a12; -fx-font-weight: bold;");
        closeButton.setOnAction(e -> leftControlPanel.getChildren().remove(activeInputSection));

        // Input for vector u
        inputs.getChildren().add(new Label("û([ux], [uy], [uz])"));

        TextField uxField = new TextField();
        uxField.setPrefWidth(60);
        uxField.setMaxWidth(80);
        uxField.setPromptText("ux");
        TextField uyField = new TextField();
        uyField.setPromptText("uy");
        uyField.setPrefWidth(60);
        uyField.setMaxWidth(80);
        TextField uzField = new TextField();
        uzField.setPromptText("uz");
        uzField.setPrefWidth(60);
        uzField.setMaxWidth(80);

        // Input for vector v
        inputs.getChildren().add(new Label(" X v̂([vx], [vy], [vz])"));

        TextField vxField = new TextField();
        vxField.setPromptText("vx");
        vxField.setPrefWidth(60);
        vxField.setMaxWidth(80);
        TextField vyField = new TextField();
        vyField.setPromptText("vy");
        vyField.setPrefWidth(60);
        vyField.setMaxWidth(80);
        TextField vzField = new TextField();
        vzField.setPromptText("vz");
        vzField.setPrefWidth(60);
        vzField.setMaxWidth(80);

        Button calculateButton = new Button("Calculate Cross Product");
        calculateButton.setOnAction(e -> {
            double ux = Double.parseDouble(uxField.getText());
            double uy = Double.parseDouble(uyField.getText());
            double uz = Double.parseDouble(uzField.getText());
            double vx = Double.parseDouble(vxField.getText());
            double vy = Double.parseDouble(vyField.getText());
            double vz = Double.parseDouble(vzField.getText());

            // Create Points for u and v
            Points u = new Points(ux, uy, uz, 150);
            Points v = new Points(vx, vy, vz, 150);


            BuildVector buildVector = new BuildVector(u, v, cameraSettings.size);

            // Get the vectors' group (containing arrows and labels)
            Group vectorsGroup = buildVector.getVectorsGroup();

            // Add the resulting vectors to the visualization
            visualizationGroup.getChildren().add(vectorsGroup);
            String resultCross = calculateCrossProductString(ux, uy, uz, vx, vy, vz);


            // Add to the right list (you can also add labels here)
            addElementToList("Cross Product: " + resultCross , vectorsGroup, null);
        });

        inputs.getChildren().addAll(uxField, uyField, uzField, vxField, vyField, vzField, calculateButton);
        activeInputSection.getChildren().addAll(inputs, closeButton);
        leftControlPanel.getChildren().add(activeInputSection);
    }

    private static void addElementToList(String label, Node visualNode, Text3D labelNode) {
        HBox elementBox = new HBox(10);
        elementBox.setPadding(new Insets(5));
        elementBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d0d0d0;");
        Label elementLabel = new Label(label);

        Button removeButton = new Button("X");
        removeButton.setStyle("-fx-font-weight: bold; -fx-background-color: #ff0a12");
        removeButton.setOnAction(e -> {

            visualizationGroup.getChildren().remove(visualNode);
            if (labelNode != null) {
                visualizationGroup.getChildren().remove(labelNode);
            }
         // remove the items all of them
            rightListPanel.getChildren().remove(elementBox);
        });

        elementBox.getChildren().addAll(elementLabel, removeButton);
        rightListPanel.getChildren().add(elementBox);
    }

}


