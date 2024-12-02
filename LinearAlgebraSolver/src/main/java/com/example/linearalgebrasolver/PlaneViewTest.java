package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.PolyMessh.Arrow;
import com.example.linearalgebrasolver.PolyMessh.Cone;
import com.example.linearalgebrasolver.View3DContainer.CartesianPlan;
import com.example.linearalgebrasolver.View3DContainer.Planes;
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
        planes.setNormalVector(5,-10,4,-19);
        System.out.println(planes.B);
        Arrow arrow = new Arrow(0.9, Color.PURPLE, 100);

        arrow.setLength(50);


        Box planeMesh = planes.createPlaneMesh(400, arrow);

        System.out.println(arrow.shaft.getTranslateX() + "  shaft x " + arrow.shaft.getTranslateY() +"   shaft y " + arrow.shaft.getTranslateZ() + " shaft z ");
        System.out.println(arrow.cone.getTranslateX() + "  cone x " + arrow.cone.getTranslateY() +"   cone y " + arrow.cone.getTranslateZ() + " cone z ");


        double radius = 0.2;
        System.out.println(arrow.cone.getTranslateX() + "  cone x " + arrow.cone.getTranslateY() +"   cone y " + arrow.cone.getTranslateZ() + " z");



        CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid(400, 1);
        final Group axes = cartesianPlan.getAxes(0.5);
       Group plane = new Group( grid, axes,planeMesh ,arrow);
        //plane.getChildren().add(arrow);



        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.001); // Ensure near clipping is very small
        camera.setFarClip(1000);
        camera.setTranslateZ(-100);

        Scene scene = new Scene(plane, 700, 700);

        handleCameraControls(scene, camera);
        scene.setCamera(camera);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void handleCameraControls(Scene scene, PerspectiveCamera camera) {
        final double[] mouseOldX = {0};
        final double[] mouseOldY = {0};
        final double[] angleX = {0};
        final double[] angleY = {0};

        // A group to pivot the camera around the origin
        Group cameraPivot = new Group();
        cameraPivot.getChildren().add(camera);

        // Initial camera position
        camera.setTranslateZ(-300);

        // Add the pivot to the root node
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

            angleY[0] -= deltaX * 0.2; // Horizontal rotation sensitivity
            angleX[0] += deltaY * 0.2; // Vertical rotation sensitivity

            cameraPivot.setRotationAxis(Rotate.Y_AXIS);
            cameraPivot.setRotate(angleY[0]);

            Rotate rotateX = new Rotate(angleX[0], Rotate.X_AXIS);
            cameraPivot.getTransforms().setAll(rotateX);
        });

        scene.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() * 0.1; // Adjust zoom speed as needed
            camera.setTranslateZ(camera.getTranslateZ() + zoomFactor);
        });
    }



}

