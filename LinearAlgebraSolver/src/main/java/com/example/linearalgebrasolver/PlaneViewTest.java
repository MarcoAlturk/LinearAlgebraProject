package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.View3DContainer.CartesianPlan;
import com.example.linearalgebrasolver.View3DContainer.Planes;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class PlaneViewTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Planes planes = new Planes();
        planes.setNormalVector(-80,-466,1111,-19);
        System.out.println(planes.B);
        Box planeMesh = planes.createPlaneMesh(10);
       // MeshView planeMesh2 = planes.createPlaneMesh(-8,-4,11,-19,100);
        System.out.println(planeMesh.getTranslateX() + " " + planeMesh.getTranslateY() + " " + planeMesh.getTranslateZ() + "Ok");


        CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid(250, 1);
        final Group axes = cartesianPlan.getAxes(0.5);
        Group plane = new Group( planeMesh, grid, axes);



        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1); // Ensure near clipping is very small
        camera.setFarClip(1000);
 // Move the camera back along the Z-axis
        camera.setTranslateZ(-100);

        Scene scene = new Scene(plane);

        handleCameraControls(scene, camera);
        scene.setCamera(camera);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void handleCameraControls(Scene scene, PerspectiveCamera camera) {
        final double[] mouseOldX = {0};
        final double[] mouseOldY = {0};
        final double[] cameraRotX = {0};
        final double[] cameraRotY = {0};

        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        camera.getTransforms().addAll(rotateX, rotateY);

        scene.setOnMousePressed(event -> {
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseOldX[0];
            double deltaY = event.getSceneY() - mouseOldY[0];
            mouseOldX[0] = event.getSceneX();
            mouseOldY[0] = event.getSceneY();

            cameraRotY[0] -= deltaX * 0.1;
            cameraRotX[0] += deltaY * 0.1;
            rotateY.setAngle(cameraRotY[0]);
            rotateX.setAngle(cameraRotX[0]);
        });

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> camera.setTranslateZ(camera.getTranslateZ() + 10);
                case S -> camera.setTranslateZ(camera.getTranslateZ() - 10);
                case A -> camera.setTranslateX(camera.getTranslateX() - 10);
                case D -> camera.setTranslateX(camera.getTranslateX() + 10);
                case UP -> camera.setTranslateY(camera.getTranslateY() - 10);
                case DOWN -> camera.setTranslateY(camera.getTranslateY() + 10);
            }
        });

    }
}

