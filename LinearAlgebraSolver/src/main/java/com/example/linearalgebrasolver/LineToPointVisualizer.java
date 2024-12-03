package com.example.linearalgebrasolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.geometry.Point3D;

public class LineToPointVisualizer {

    private double initialMouseX, initialMouseY;
    private double initialAngleX = 0, initialAngleY = 0;
    private double zoomFactor = 1.0;

    public Stage createVisualizer(double x1, double y1, double z1, double x2, double y2, double z2, double px, double py, double pz, double distance) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, true);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        scene.setCamera(camera);

        // Set the camera's position and orientation
        camera.setTranslateZ(-300); // Move the camera back so the objects are visible
        camera.setTranslateX(100);  // Move the camera slightly on the X-axis
        camera.setTranslateY(100);  // Move the camera slightly on the Y-axis

        // Create 3D grid (XY plane)
        createGrid(root);

        // Create a line between two points (A(x1, y1, z1) and B(x2, y2, z2))
        Line line = createLine(new Point3D(x1, y1, z1), new Point3D(x2, y2, z2));
        root.getChildren().add(line);

        // Create a point at specific coordinates
        Rectangle point = createPoint(px, py, pz);
        root.getChildren().add(point);

        // Set up drag handling for rotating the scene
        scene.setOnMousePressed(this::onMousePressed);
        scene.setOnMouseDragged(this::onMouseDragged);

        // Set up mouse wheel handling for zooming
        scene.setOnScroll(this::onMouseScrolled);

        // Set up the stage
        Stage visualizerStage = new Stage();
        visualizerStage.setTitle("3D Line and Point Visualizer");
        visualizerStage.setScene(scene);
        return visualizerStage;
    }

    private void createGrid(Group root) {
        // Create grid lines on the XY plane
        int gridSize = 20;
        for (int i = -10; i <= 10; i++) {
            Line lineX = new Line(i * gridSize, -10 * gridSize, i * gridSize, 10 * gridSize);
            lineX.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(lineX);

            Line lineY = new Line(-10 * gridSize, i * gridSize, 10 * gridSize, i * gridSize);
            lineY.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(lineY);
        }
    }

    private Line createLine(Point3D p1, Point3D p2) {
        Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.RED);
        return line;
    }

    private Rectangle createPoint(double x, double y, double z) {
        Rectangle point = new Rectangle(5, 5, Color.BLUE);
        point.setTranslateX(x);
        point.setTranslateY(y);
        point.setTranslateZ(z);
        return point;
    }

    private void onMousePressed(MouseEvent event) {
        initialMouseX = event.getSceneX();
        initialMouseY = event.getSceneY();
    }

    private void onMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - initialMouseX;
        double deltaY = event.getSceneY() - initialMouseY;

        initialAngleX += deltaY * 0.1;
        initialAngleY += deltaX * 0.1;

        // Apply rotations
        Rotate rotateX = new Rotate(initialAngleX, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(initialAngleY, Rotate.Y_AXIS);

        // Ensure the root is of type Group
        if (event.getSource() instanceof Group) {
            Group root = (Group) event.getSource();
            root.getTransforms().clear();
            root.getTransforms().add(rotateX);
            root.getTransforms().add(rotateY);
        }

        initialMouseX = event.getSceneX();
        initialMouseY = event.getSceneY();
    }

    private void onMouseScrolled(javafx.scene.input.ScrollEvent event) {
        zoomFactor += event.getDeltaY() * 0.01;
        zoomFactor = Math.max(zoomFactor, 0.1); // Prevent zooming out too much

        // Ensure the root is of type Group
        if (event.getSource() instanceof Group) {
            Group root = (Group) event.getSource();
            Scale scale = new Scale(zoomFactor, zoomFactor, zoomFactor);
            root.getTransforms().clear();
            root.getTransforms().add(scale);
        }
    }
}
