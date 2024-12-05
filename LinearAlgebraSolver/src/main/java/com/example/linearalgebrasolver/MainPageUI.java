package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.View3DContainer.Lines;
import com.example.linearalgebrasolver.View3DContainer.Planes;
import com.example.linearalgebrasolver.View3DContainer.Points;
import com.example.linearalgebrasolver.View3DContainer.Text3D;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainPageUI {

    public static Scene mainScene() {
        TabPane tabPane = new TabPane();
        tabPane.getStylesheets().add("styles.css");

        matrixOperations(tabPane);
        pointsOptions(tabPane);
        matrixCalculation(tabPane);
        visualization(tabPane);

        return new Scene(tabPane);
    }

    public static void matrixCalculation(TabPane tabPane) {
        BuilMatrixOperations builMatrixOperations = new BuilMatrixOperations();
        Tab tabMatrixOperations = new Tab("Matrix Calculations");

        HBox layoutWithCreateButton = builMatrixOperations.createMatrixCalculations();
        layoutWithCreateButton.setAlignment(Pos.CENTER);

        VBox vBoxCurrentRootForTabs = new VBox(layoutWithCreateButton);
        tabMatrixOperations.setContent(vBoxCurrentRootForTabs);

        tabMatrixOperations.setClosable(false);
        tabPane.getTabs().add(tabMatrixOperations);
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

        VBox inputArea = new VBox(10);
        inputArea.setPadding(new Insets(10));


        distanceTypeCombo.setOnAction(e -> {
            String selected = distanceTypeCombo.getValue();
            inputArea.getChildren().clear(); // Clear existing inputs

            if (selected.equals("Plane to Point Distance")) {
                setupPlaneToPointUI(inputArea, tabPane);
            }
            if (selected.equals("Plane to Line Distance")) {
                setupPlaneToLineUI(inputArea, tabPane);
            }
            if (selected.equals("Line to Point Distance")) {
                setupLineToPointUI(inputArea, tabPane);
            }
        });


        setupPlaneToPointUI(inputArea, tabPane);

        layout.getChildren().addAll(selectLabel, distanceTypeCombo, inputArea);
        distanceTab.setContent(layout);
        tabPane.getTabs().add(distanceTab);
    }

    public static void setupPlaneToPointUI(VBox inputArea, TabPane tabPane) {
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
        Button visualizeButton = new Button("Visualize");
        visualizeButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
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


            resultLabel.setText(String.format("Distance: %.2f", distance));

            visualizeButton.setOnAction(t -> {
                PointToPlane(A, B, C, D, x, y, z, resultLabel.getText(), tabPane);
            });
        });

        grid.add(labelPlane, 0, 0);
        grid.add(planeInputs, 0, 1);
        grid.add(labelPoint, 0, 2);
        grid.add(pointInputs, 0, 3);
        grid.add(calculateButton, 0, 4);
        grid.add(resultLabel, 0, 5);
        grid.add(visualizeButton, 0, 6);

        inputArea.getChildren().add(grid);
    }

    public static void setupLineToPointUI(VBox inputArea, TabPane tabPane) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Labels and TextFields for line points
        Label labelLine = new Label("Line defined by two points:");
        TextField fieldX1 = createNumericField("X1");
        TextField fieldY1 = createNumericField("Y1");
        TextField fieldZ1 = createNumericField("Z1");
        TextField fieldX2 = createNumericField("X2");
        TextField fieldY2 = createNumericField("Y2");
        TextField fieldZ2 = createNumericField("Z2");

        HBox lineInputs = new HBox(5,
                new Label("P1 ("), fieldX1, new Label(","), fieldY1, new Label(","), fieldZ1, new Label("),"),
                new Label("P2 ("), fieldX2, new Label(","), fieldY2, new Label(","), fieldZ2, new Label(")")
        );
        lineInputs.setAlignment(Pos.CENTER);

        // Labels and TextFields for point coordinates
        Label labelPoint = new Label("Point Coordinates:");
        TextField fieldX = createNumericField("X");
        TextField fieldY = createNumericField("Y");
        TextField fieldZ = createNumericField("Z");

        HBox pointInputs = new HBox(5, fieldX, new Label("X"), fieldY, new Label("Y"), fieldZ, new Label("Z"));
        pointInputs.setAlignment(Pos.CENTER);

        // Button and output label
        Button calculateButton = new Button("Calculate Distance");
        Button visualizeButton = new Button("Visualize");
        visualizeButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );

        Label resultLabel = new Label();

        calculateButton.setOnAction(e -> {
            // Parse line point coordinates
            double x1 = Double.parseDouble(fieldX1.getText());
            double y1 = Double.parseDouble(fieldY1.getText());
            double z1 = Double.parseDouble(fieldZ1.getText());
            double x2 = Double.parseDouble(fieldX2.getText());
            double y2 = Double.parseDouble(fieldY2.getText());
            double z2 = Double.parseDouble(fieldZ2.getText());

            // Parse point coordinates
            double x = Double.parseDouble(fieldX.getText());
            double y = Double.parseDouble(fieldY.getText());
            double z = Double.parseDouble(fieldZ.getText());

            // Calculate distance
            double[] lineVector = {x2 - x1, y2 - y1, z2 - z1};
            double[] pointVector = {x - x1, y - y1, z - z1};

            // Cross product of lineVector and pointVector
            double[] crossProduct = {
                    lineVector[1] * pointVector[2] - lineVector[2] * pointVector[1],
                    lineVector[2] * pointVector[0] - lineVector[0] * pointVector[2],
                    lineVector[0] * pointVector[1] - lineVector[1] * pointVector[0]
            };

            double crossProductMagnitude = Math.sqrt(
                    crossProduct[0] * crossProduct[0] +
                            crossProduct[1] * crossProduct[1] +
                            crossProduct[2] * crossProduct[2]
            );

            double lineMagnitude = Math.sqrt(
                    lineVector[0] * lineVector[0] +
                            lineVector[1] * lineVector[1] +
                            lineVector[2] * lineVector[2]
            );

            double distance = crossProductMagnitude / lineMagnitude;

            resultLabel.setText(String.format("Distance: %.2f", distance));
            visualizeButton.setOnAction(t -> {
                lineToPointDistance(x1, y1, z1, x2, y2, z2, x, y, z, resultLabel.getText(), tabPane);
            });
        });

        grid.add(labelLine, 0, 0);
        grid.add(lineInputs, 0, 1);
        grid.add(labelPoint, 0, 2);
        grid.add(pointInputs, 0, 3);
        grid.add(calculateButton, 0, 4);
        grid.add(resultLabel, 0, 5);
        grid.add(visualizeButton, 0, 6);

        inputArea.getChildren().add(grid);
    }


    public static void setupPlaneToLineUI(VBox inputArea, TabPane tabPane) {
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

        // Labels and TextFields for line points (x1, y1, z1) and (x2, y2, z2)
        Label labelLine = new Label("Line Coordinates (Two points P1(x1, y1, z1) and P2(x2, y2, z2)):");
        TextField fieldX1 = createNumericField("X1");
        TextField fieldY1 = createNumericField("Y1");
        TextField fieldZ1 = createNumericField("Z1");

        TextField fieldX2 = createNumericField("X2");
        TextField fieldY2 = createNumericField("Y2");
        TextField fieldZ2 = createNumericField("Z2");

        HBox lineInputs = new HBox(5, fieldX1, new Label("X1"), fieldY1, new Label("Y1"), fieldZ1, new Label("Z1"));
        lineInputs.setAlignment(Pos.CENTER);
        HBox lineInputs2 = new HBox(5, fieldX2, new Label("X2"), fieldY2, new Label("Y2"), fieldZ2, new Label("Z2"));
        lineInputs2.setAlignment(Pos.CENTER);

        // Button and output label
        Button calculateButton = new Button("Calculate Distance");
        Button visualizeButton = new Button("Visualize");
        visualizeButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        Label resultLabel = new Label();

        calculateButton.setOnAction(e -> {
            // Parse the values from the input fields
            double A = Integer.parseInt(fieldA.getText());
            double B = Integer.parseInt(fieldB.getText());
            double C = Integer.parseInt(fieldC.getText());
            double D = Integer.parseInt(fieldD.getText());

            double x1 = Integer.parseInt(fieldX1.getText());
            double y1 = Integer.parseInt(fieldY1.getText());
            double z1 = Integer.parseInt(fieldZ1.getText());

            double x2 = Integer.parseInt(fieldX2.getText());
            double y2 = Integer.parseInt(fieldY2.getText());
            double z2 = Integer.parseInt(fieldZ2.getText());

            // Calculate the direction vector of the line (P1 to P2)
            double dx = x2 - x1;
            double dy = y2 - y1;
            double dz = z2 - z1;

            // Calculate the normal vector of the plane (A, B, C)
            double planeNormalMagnitude = Math.sqrt(A * A + B * B + C * C);

            // Calculate the distance from the line to the plane
            double numerator = Math.abs(A * x1 + B * y1 + C * z1 + D);
            double denominator = Math.sqrt(dx * dx + dy * dy + dz * dz) * planeNormalMagnitude;
            double distance = numerator / denominator;
            resultLabel.setText(String.format("Distance: %.2f", distance));

            visualizeButton.setOnAction(t-> {
                lineToPLaneDistance(x1,y1,z1, x2,y2,z2,A,B,C,D,resultLabel.getText(),tabPane );
            });
        });

        // Adding elements to the grid
        grid.add(labelPlane, 0, 0);
        grid.add(planeInputs, 0, 1);
        grid.add(labelLine, 0, 2);
        grid.add(lineInputs, 0, 3);
        grid.add(lineInputs2, 0, 4);
        grid.add(calculateButton, 0, 5);
        grid.add(resultLabel, 0, 6);
        grid.add(visualizeButton, 0, 7);

        inputArea.getChildren().add(grid);
    }

    public static TextField createNumericField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);

        field.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                // Save the current caret position before making changes
                int caretPosition = field.getCaretPosition();

                // Clean up the input to allow digits and a single leading "-"
                String newText = newVal.replaceAll("[^\\d-]", "");

                // Ensure "-" is only at the beginning and there is only one "-"
                if (newText.indexOf("-") > 0) {
                    newText = newText.replaceAll("-", "");
                }
                if (newText.indexOf("-") != newText.lastIndexOf("-")) {
                    newText = newText.replaceAll("-", "");
                }

                // Only update the text if it's different from the previous value
                if (!newText.equals(field.getText())) {
                    field.setText(newText);
                }

                // Safely update the caret position (do not let it go out of bounds)
                field.positionCaret(Math.min(caretPosition, newText.length()));
            } catch (Exception e) {
                // If any error occurs, log the error and do nothing
                System.err.println("Error updating text: " + e.getMessage());
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

    public static void visualization(TabPane tabPane) {

        Build3DVisualization.build(tabPane);
    }


    public static void PointToPlane(double A, double B, double C, double D, double x1, double x2, double x3, String distance, TabPane tabPane) {

        Points pointsOnPlane = generatePointOnPlane(A, B, C, D, x1, x2, x3);
        Points points = new Points(x1, x2, x3, 150);
        Planes planes = new Planes();
        planes.setNormalVector(A, B, C, D);

        Lines linesDistance = new Lines(pointsOnPlane, points);
        Group lineGroup = linesDistance.createLineWithDottedCylindersAndLabel(distance);

        Group completGroup = new Group(lineGroup, planes.createPlaneMesh(50), points.getPointLabel());
        Build3DVisualization.visualizationGroup.getChildren().addAll(completGroup);
        String label = "Plane: " + A + "x" + B + "y" + C + "z" + D + "" + "Point: " + "(" + x1 + "," + x2 + "," + x3 + ")";
        Build3DVisualization.addElementToList(label, completGroup, null);
        tabPane.getSelectionModel().select(3);
    }

    public static Points generatePointOnPlane(double A, double B, double C, double D, double x1, double x2, double x3) {
        double t = -(A * x1 + B * x2 + C * x3 + D) / (A * A + B * B + C * C);


        double x = x1 + t * A;
        double y = x2 + t * B;
        double z = x3 + t * C;

        return new Points(x, y, z, 150);
    }

    public static void lineToPointDistance(double startX1, double startX2, double startX3, double endX1, double endX2, double endx3, double x1, double x2, double x3, String distance, TabPane tabPane) {
        Points startLine = new Points(startX1, startX2, startX3, 150);
        Points endLine = new Points(endX1, endX2, endx3, 150);
        Points pointsOnLine = generatePointOnLine(startX1, startX2, startX3, endX1, endX2, endx3, x1, x2, x3);
        Points points = new Points(x1, x2, x3, 150);
        Lines lines = new Lines(startLine, endLine);
        Lines linesDistance = new Lines(points, pointsOnLine);

        Group completeGroup = new Group();
        completeGroup.getChildren().addAll(lines.createLineWithNormalVector(), pointsOnLine.getPointSphere(), pointsOnLine.getPointLabel(), points.getPointSphere(), points.getPointLabel(), linesDistance.createLineWithDottedCylindersAndLabel(distance));


        String label = lines.lineFromTo() + "|Points: " + points.getPointLabel().getTextTocheck();
        Build3DVisualization.visualizationGroup.getChildren().add(completeGroup);
        Build3DVisualization.addElementToList(label, completeGroup, null);

        tabPane.getSelectionModel().select(3);

    }

    public static Points generatePointOnLine(double startX1, double startX2, double startX3,
                                             double endX1, double endX2, double endX3,
                                             double x1, double x2, double x3) {
        double dirX = endX1 - startX1;
        double dirY = endX2 - startX2;
        double dirZ = endX3 - startX3;


        double vecToPointX = x1 - startX1;
        double vecToPointY = x2 - startX2;
        double vecToPointZ = x3 - startX3;


        double tNumerator = dirX * vecToPointX + dirY * vecToPointY + dirZ * vecToPointZ;
        double tDenominator = dirX * dirX + dirY * dirY + dirZ * dirZ;
        double t = tNumerator / tDenominator;


        double x = startX1 + t * dirX;
        double y = startX2 + t * dirY;
        double z = startX3 + t * dirZ;

        return new Points(x, y, z, 150);

    }
    static double xLine = 0,yLine = 0,zLine = 0;
    public static void lineToPLaneDistance(double startX1, double startX2, double startX3,
                                           double endX1, double endX2, double endX3, double A, double B, double C, double D, String distance, TabPane tabPane) {
        Points pointsOnPlane = generatePointThatLiesOnThePlane(A,B,C,D);
        Points generatePointsThatLiesOnLine = generatePointOnLinePerpendicularPointOnPlane(startX1, startX2, startX3, endX1, endX2, endX3,xLine, yLine,zLine);
        Points pointsStart = new Points(startX1, startX2, startX3, 150);
        Points pointsEnd = new Points(endX1, endX2, endX3, 150);
        Lines lines = new Lines(pointsStart, pointsEnd);
        Lines linesDistance  = new Lines(pointsOnPlane, generatePointsThatLiesOnLine);
        Planes planes = new Planes();
        planes.setNormalVector(A,B,C,D);
        Group completeGroup = new Group();
        completeGroup.getChildren().addAll(lines.createLineWithNormalVector(),
                linesDistance.createLineWithDottedCylindersAndLabel(distance),planes.createPlaneMesh(50), generatePointsThatLiesOnLine.getPointLabel(), generatePointsThatLiesOnLine.getPointSphere(), pointsOnPlane.getPointSphere()
        );
        String label =  lines.lineFromTo() + " Plane: " + A + "x "  + B + "y " + C + "z " + D;
        Build3DVisualization.visualizationGroup.getChildren().add(completeGroup);
        Build3DVisualization.addElementToList(label, completeGroup, null);
        tabPane.getSelectionModel().select(3);
    }

    public static Points generatePointOnLinePerpendicularPointOnPlane(double startX1, double startX2, double startX3,
                                                                      double endX1, double endX2, double endX3, double x1, double x2, double x3) { // Direction vector of the line
        double dirX = endX1 - startX1;
        double dirY = endX2 - startX2;
        double dirZ = endX3 - startX3;

        // Vector from line start to the given plane point
        double vecToPointX = x1 - startX1;
        double vecToPointY = x2 - startX2;
        double vecToPointZ = x3 - startX3;

        // Calculate the projection scalar t
        double tNumerator = dirX * vecToPointX + dirY * vecToPointY + dirZ * vecToPointZ;
        double tDenominator = dirX * dirX + dirY * dirY + dirZ * dirZ;
        double t = tNumerator / tDenominator;

        // Calculate the closest point on the line to the given point
        double closestX = startX1 + t * dirX;
        double closestY = startX2 + t * dirY;
        double closestZ = startX3 + t * dirZ;

        return new Points(closestX, closestY, closestZ, 150);
    }


    public static Points generatePointThatLiesOnThePlane(double A, double B, double C, double D){

        double x, y, z;


        if (C != 0) {
            x = Math.random() * 10 - 5;
            y = Math.random() * 10 - 5;

            z = -(A * x + B * y + D) / C;
            xLine = x;
            yLine = y;
            zLine = z;
        } else if (B != 0) {
            x = Math.random() * 10 - 5;
            z = Math.random() * 10 - 5;
            xLine = x;
            zLine = z;
            y = -(A * x + C * z + D) / B;
            yLine = y;
        } else if (A != 0) {
            y = Math.random() * 10 - 5;
            z = Math.random() * 10 - 5;


            x = -(B * y + C * z + D) / A;
            xLine = x;
            yLine = y;
            zLine = z;
        } else {

            throw new IllegalArgumentException("Invalid plane equation: A, B, and C cannot all be zero.");
        }

        return new Points(x, y, z, 150);

    }

}


