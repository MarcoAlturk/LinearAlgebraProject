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
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
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
//        System.out.println(planes.B);
        Arrow arrow =  new Arrow();

        arrow.setLength(50);


        Group arrowAndPlane = planes.normalAndPlane(400);






        CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid(400, 1);
        final Group axes = cartesianPlan.getAxes(0.5);
       Group plane = new Group( grid, axes,arrowAndPlane);
        //plane.getChildren().add(arrow);

        Sphere point = new Sphere(2); // Create a small sphere with radius 2
        point.setTranslateX(50); // Set X position
        point.setTranslateY(50); // Set Y position
        point.setTranslateZ(50); // Set Z position
        point.setMaterial(new javafx.scene.paint.PhongMaterial(Color.BLUE)); // Make the sphere red
         // Add the sphere to the scene

        Text pointCoordinates = new Text("Point: (50, 50, 50)");
        pointCoordinates.setFont(Font.font(8)); // Set font size
        pointCoordinates.setFill(Color.GREEN); // Set text color
        pointCoordinates.setTranslateX(50); // Position the text near the point
        pointCoordinates.setTranslateY(50);
        pointCoordinates.setTranslateZ(50);

        Text planeEquation = new Text("Plane Equation: 5x - 10y + 4z = -19");
        planeEquation.setFont(Font.font(8)); // Set font size
        planeEquation.setFill(Color.BLUE); // Set text color
        planeEquation.setTranslateX(0); // Position the text near the plane
        planeEquation.setTranslateY(0);
        planeEquation.setTranslateZ(0);

        double[] closestPointCoordinates = ClosestPointOnPlane.closestPointOnPlane(5, -10, 4, 19, 50, 50,50);
        Sphere closestPoint = new Sphere(2);
        closestPoint.setTranslateX(closestPointCoordinates[0]);
        closestPoint.setTranslateY(closestPointCoordinates[1]);
        closestPoint.setTranslateZ(closestPointCoordinates[2]);
        closestPoint.setMaterial(new javafx.scene.paint.PhongMaterial(Color.GREEN));

        Text closestPointCoordinatesText = new Text(String.format("(%2s,%2s,%2s)", closestPointCoordinates[0], closestPointCoordinates[1], closestPointCoordinates[2]));
        closestPointCoordinatesText.setFont(Font.font(8)); // Set font size
        closestPointCoordinatesText.setFill(Color.BLUE); // Set text color
        closestPointCoordinatesText.setTranslateX(closestPointCoordinates[0]); // Position the text near the plane
        closestPointCoordinatesText.setTranslateY(closestPointCoordinates[1]);
        closestPointCoordinatesText.setTranslateZ(closestPointCoordinates[2]);

        System.out.println("Closest point : " + closestPointCoordinates[0] + "," + closestPointCoordinates[1] + "," + closestPointCoordinates[2]);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.001); // Ensure near clipping is very small
        camera.setFarClip(1000);
        camera.setTranslateZ(-100);

        Scene scene = new Scene(plane, 700, 700);
        ((Group) scene.getRoot()).getChildren().add(point);
        ((Group) scene.getRoot()).getChildren().add(pointCoordinates);
        ((Group) scene.getRoot()).getChildren().add(closestPoint);
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

