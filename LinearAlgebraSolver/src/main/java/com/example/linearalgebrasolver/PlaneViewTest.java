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
        planes.setNormalVector(101,-12,56,4);

        Box box = planes.createPlaneMesh(50, new Arrow(1,Color.BLUE,10));
        Sphere sphere = new Sphere(100);
        //sphere.setTranslateX(0);
        //sphere.setTranslateY(0);
        //sphere.setTranslateZ(-0);
        CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid(150, 1);
        final Group axes = cartesianPlan.getAxes(0.2);
       Group plane = new Group( grid, axes, box);
       plane.setTranslateZ(100);
        //plane.getChildren().add(arrow);
        plane.setScaleX(0.5); // Scale down by 10x along the X-axis
        plane.setScaleY(0.5); // Scale down by 10x along the Y-axis
        plane.setScaleZ(0.5);


        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.001); // Ensure near clipping is very small
        camera.setFarClip(1000);
        camera.setTranslateZ(-100);

        Scene scene = new Scene(plane, 700, 700);
        handleGroupControls(plane);

        scene.setCamera(camera);
        camera.setTranslateZ(-10);
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

