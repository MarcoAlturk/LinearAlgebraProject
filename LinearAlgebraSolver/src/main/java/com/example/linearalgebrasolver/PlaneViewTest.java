package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.PolyMessh.Arrow;
import com.example.linearalgebrasolver.PolyMessh.Cone;
import com.example.linearalgebrasolver.View3DContainer.*;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class PlaneViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Planes planes = new Planes();
        planes.setNormalVector(3,4,2,-100);

        Group box = planes.createPlaneMesh(50);



        Points points = new Points(100,200,4,150);
        Points points2 = new Points(1,1,-0.5,150);
        Points points3 = new Points(122,11,-0.5,150);

        Points point1 = new Points(0, 0, 0, 150);// Point at (0, 0, 0)
        Points point2 = new Points(50, 50, 50, 150);     // Point at (50, 50, 50)


        double gridSize = 150;

        Points u = new Points(5, 3, 5, 0.0001); // Example 3D vector
        Points v = new Points(12, 31, 41, 0.001);
        BuildVector buildVector = new BuildVector(u, v ,gridSize);
        Group vectorsGroup = buildVector.getVectorsGroup();






        Lines line = new Lines(point1, point2);
       // Group lineGroup = line.createLine();
      //  Group vectorDandLine = line.createLineWithNormalVector();

        Group lineDistance = line.createLineWithDottedCylindersAndLabel("50 m");

                CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid(150, 1);
        final Group axes = cartesianPlan.getAxes(0.2);
       Group plane = new Group( grid, axes, box, points.getPointSphere(), points2.getPointSphere(),lineDistance , points3.getPointSphere(),vectorsGroup);
       plane.setTranslateZ(100);
        //plane.getChildren().add(arrow);
        plane.setScaleX(4); // Scale down by 10x along the X-axis
        plane.setScaleY(4); // Scale down by 10x along the Y-axis
        plane.setScaleZ(4);


        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.001); // Ensure near clipping is very small
        camera.setFarClip(1000);


        Scene scene = new Scene(plane, 700, 700);
        handleGroupControls(plane);

        scene.setCamera(camera);
        camera.setTranslateZ(-700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void handleGroupControls(Group group) {
        final double[] mouseOldX = {0};
        final double[] mouseOldY = {0};
        final double[] angleX = {0};
        final double[] angleY = {0};

        // Set initial rotation of the group
        group.setRotationAxis(Rotate.Y_AXIS);
        group.setRotate(0);

        group.setOnMousePressed(event -> {
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();
        });

        group.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseOldX[0];
            double deltaY = event.getSceneY() - mouseOldY[0];
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();

            // Adjust angles based on mouse drag
            angleY[0] -= deltaX * 0.2; // Horizontal rotation sensitivity
            angleX[0] += deltaY * 0.2; // Vertical rotation sensitivity

            // Update group rotation
            Rotate rotateX = new Rotate(angleX[0], Rotate.X_AXIS);
            Rotate rotateY = new Rotate(angleY[0], Rotate.Y_AXIS);
            group.getTransforms().setAll(rotateY, rotateX);
        });

        group.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() * 0.1; // Adjust zoom speed as needed
            group.setTranslateZ(group.getTranslateZ() + zoomFactor);
        });
    }




}

