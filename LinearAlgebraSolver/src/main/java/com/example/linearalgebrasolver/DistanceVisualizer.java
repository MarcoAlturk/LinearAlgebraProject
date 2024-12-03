package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.View3DContainer.CartesianPlan;
import com.example.linearalgebrasolver.View3DContainer.Planes;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class DistanceVisualizer {

    public Stage createVisualizer(double A, double B, double C, double D, double x, double y, double z, double distance) {
        // Create the plane
        Planes planes = new Planes();
        planes.setNormalVector(A, B, C, D);

        Group arrowAndPlane = planes.normalAndPlane(400);

        CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid(400, 1);
        final Group axes = cartesianPlan.getAxes(0.5);
        Group plane = new Group(grid, axes, arrowAndPlane);

        // Create a point
        Sphere point = new Sphere(2);
        point.setTranslateX(x);
        point.setTranslateY(y);
        point.setTranslateZ(z);
        point.setMaterial(new javafx.scene.paint.PhongMaterial(Color.BLUE));

        Text pointCoordinates = new Text(String.format("Point: (%.2f, %.2f, %.2f)", x, y, z));
        pointCoordinates.setFont(Font.font(8));
        pointCoordinates.setFill(Color.GREEN);
        pointCoordinates.setTranslateX(x);
        pointCoordinates.setTranslateY(y);
        pointCoordinates.setTranslateZ(z);

        // Add plane equation
        Text planeEquation = new Text(String.format("Plane Equation: %.2fx + %.2fy + %.2fz = %.2f", A, B, C, D));
        planeEquation.setFont(Font.font(8));
        planeEquation.setFill(Color.BLUE);
        planeEquation.setTranslateX(0);
        planeEquation.setTranslateY(0);



        // Camera and scene
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.001);
        camera.setFarClip(1000);
        camera.setTranslateZ(-100);

        Scene scene = new Scene(plane, 700, 700);
        plane.getChildren().addAll(point, pointCoordinates, planeEquation);
        handleCameraControls(scene, camera);
        scene.setCamera(camera);

        // Stage
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }

    private void handleCameraControls(Scene scene, PerspectiveCamera camera) {
        final double[] mouseOldX = {0};
        final double[] mouseOldY = {0};
        final double[] angleX = {0};
        final double[] angleY = {0};

        Group cameraPivot = new Group();
        cameraPivot.getChildren().add(camera);
        camera.setTranslateZ(-300);
        ((Group) scene.getRoot()).getChildren().add(cameraPivot);

        scene.setOnMousePressed(event -> {
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseOldX[0];
            double deltaY = event.getSceneY() - mouseOldY[0];
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();

            angleY[0] -= deltaX * 0.2;
            angleX[0] += deltaY * 0.2;

            cameraPivot.setRotationAxis(Rotate.Y_AXIS);
            cameraPivot.setRotate(angleY[0]);

            Rotate rotateX = new Rotate(angleX[0], Rotate.X_AXIS);
            cameraPivot.getTransforms().setAll(rotateX);
        });

        scene.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() * 0.1;
            camera.setTranslateZ(camera.getTranslateZ() + zoomFactor);
        });
    }
}
