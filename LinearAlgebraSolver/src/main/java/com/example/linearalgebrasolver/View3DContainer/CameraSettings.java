package com.example.linearalgebrasolver.View3DContainer;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class CameraSettings {

    private final PerspectiveCamera camera;
    public Group cartesianPlane = new Group();
    public final double size =  150;

    public CameraSettings() {

        CartesianPlan cartesianPlan = new CartesianPlan();
        final Group grid = cartesianPlan.createGrid((float) size, 1);
        final Group axes = cartesianPlan.getAxes(0.2);

        cartesianPlane.getChildren().addAll(grid, axes);

        // Apply transformations to the cartesianPlane
        this.cartesianPlane.setTranslateZ(100);
        this.cartesianPlane.setScaleX(4);
        this.cartesianPlane.setScaleY(4);
        this.cartesianPlane.setScaleZ(4);

        // Initialize and configure the camera
        this.camera = new PerspectiveCamera(true);
        this.camera.setNearClip(0.001);
        this.camera.setFarClip(1000);
        this.camera.setTranslateZ(-700);
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public Group getCartesianPlane() {
        return cartesianPlane;
    }
    public static void handleGroupControls(Group group) {
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


            angleY[0] -= deltaX * 0.2;
            angleX[0] += deltaY * 0.2;


            Rotate rotateX = new Rotate(angleX[0], Rotate.X_AXIS);
            Rotate rotateY = new Rotate(angleY[0], Rotate.Y_AXIS);
            group.getTransforms().setAll(rotateY, rotateX);
        });

        group.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() * 0.1;
            group.setTranslateZ(group.getTranslateZ() + zoomFactor);
        });
    }

}
