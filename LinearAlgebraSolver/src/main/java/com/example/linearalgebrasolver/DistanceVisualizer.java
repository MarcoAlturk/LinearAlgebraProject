package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.PolyMessh.Arrow;
import com.example.linearalgebrasolver.View3DContainer.Planes;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class DistanceVisualizer {

    private final Group root = new Group();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private double anchorX, anchorY, anchorAngleX = 0, anchorAngleY = 0;

    public Stage createVisualizer(double a, double b, double c, double d, double x, double y, double z, double distance) {
        // Create the stage and scene
        Stage stage = new Stage();
        SubScene subScene = setup3DScene(a, b, c, d, x, y, z, distance);
        Planes planes = new Planes();
        planes.setNormalVector(a, b, c, d);
        Box pane = planes.createPlaneMesh(100, new Arrow(2, Color.RED, 250));
        Group groupPlane =  new Group(pane);
        groupPlane.setTranslateZ(0);
        groupPlane.setTranslateX(0);
        groupPlane.setTranslateY(0);

        Group container = new Group(subScene, groupPlane);
        Scene scene = new Scene(container, 800, 600, true);

        // Add rotation controls
        addRotationControls(scene);

        stage.setTitle("Plane to Point Visualization");
        stage.setScene(scene);
        return stage;
    }

    private SubScene setup3DScene(double a, double b, double c, double d, double x, double y, double z, double distance) {
        // Configure the camera
        camera.setTranslateZ(-500);
        camera.setNearClip(0.1);
        camera.setFarClip(2000);

        // Create the plane
        Box plane = new Box(200, 200, 1);
        plane.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
        double planeZ = -d / Math.sqrt(a * a + b * b + c * c); // Position plane using d
        plane.getTransforms().addAll(new Rotate(-Math.toDegrees(Math.atan2(b, a)), Rotate.Y_AXIS), new Translate(0, 0, planeZ));

        // Add the point
        Sphere point = new Sphere(5);
        point.setMaterial(new PhongMaterial(Color.RED));
        point.setTranslateX(x);
        point.setTranslateY(-y); // Flip y-axis for 3D space
        point.setTranslateZ(z);

        // Add dashed line from point to plane
        Cylinder line = createDashedLine(x, y, z, a, b, c, d);

        // Add to the root group
        root.getChildren().addAll(plane, point, line);

        // Create the SubScene
        SubScene subScene = new SubScene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        return subScene;
    }

    private Cylinder createDashedLine(double x, double y, double z, double a, double b, double c, double d) {
        // Closest point on the plane from the given point
        double scale = -(a * x + b * y + c * z + d) / (a * a + b * b + c * c);
        double cx = x + scale * a;
        double cy = y + scale * b;
        double cz = z + scale * c;

        // Compute 3D line properties
        Point3D start = new Point3D(x, -y, z);
        Point3D end = new Point3D(cx, -cy, cz);

        Point3D diff = end.subtract(start);
        double height = diff.magnitude();
        Point3D midpoint = start.midpoint(end);
        double angle = Math.toDegrees(Math.acos(diff.normalize().dotProduct(new Point3D(0, 1, 0))));

        // Create cylinder to represent the line
        Cylinder line = new Cylinder(0.5, height);
        line.setMaterial(new PhongMaterial(Color.GRAY));
        line.getTransforms().addAll(
                new Translate(midpoint.getX(), midpoint.getY(), midpoint.getZ()),
                new Rotate(angle, new Point3D(diff.getZ(), 0, -diff.getX()))
        );

        return line;
    }

    private void addRotationControls(Scene scene) {
        // Track mouse for rotation
        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();
        });

        scene.setOnMouseDragged(event -> {
            rotateX.setAngle(anchorAngleX - (anchorY - event.getSceneY()));
            rotateY.setAngle(anchorAngleY + (anchorX - event.getSceneX()));
        });

        root.getTransforms().addAll(rotateX, rotateY);
    }
}
