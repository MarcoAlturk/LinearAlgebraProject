package com.example.linearalgebrasolver.View3DContainer;

import com.example.linearalgebrasolver.PolyMessh.VectorArrows;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class BuildVector {
    private VectorArrows uArrow;
    private VectorArrows vArrow;
    private VectorArrows wArrow;
    private Points u, v, w;

    public BuildVector(Points u, Points v, double gridSize) {
        this.u = u;
        this.v = v;

        Point3D wCoordinates = u.getPointsCoordinates().crossProduct(v.getPointsCoordinates());
        this.w = new Points(wCoordinates.getX(), wCoordinates.getY(), wCoordinates.getZ(), gridSize);

        uArrow = createArrow(u.getPointsCoordinates(), gridSize, Color.RED);
        vArrow = createArrow(v.getPointsCoordinates(), gridSize, Color.GREEN);
        wArrow = createArrow(wCoordinates, gridSize, Color.BLUE);
        addTextLabels();

    }

    private VectorArrows createArrow(Point3D vector, double gridSize, Color color) {

        double length = calculateMagnitude(vector);
        System.out.println(length + " length");


        VectorArrows arrow = new VectorArrows(length, 0.2, 0.2);
        arrow.shaft.setMaterial(new PhongMaterial(color));

        arrow.setLength(length  * gridSize);

        Point3D axis = new Point3D(0, 1, 0);
        Point3D normalizedVector = vector.normalize();
        double angle = Math.toDegrees(Math.acos(axis.dotProduct(normalizedVector)));
        Point3D rotationAxis = axis.crossProduct(normalizedVector);

        if (!rotationAxis.equals(Point3D.ZERO)) {
            arrow.getTransforms().add(new Rotate(angle, rotationAxis));
        }
        System.out.println(arrow.getTranslateX() + " shaft translate x");
        System.out.println(arrow.getTranslateY() + " shaft translate y");
        System.out.println(arrow.getTranslateZ() + " shaft translate z");

        arrow.shaft.setTranslateX(0 );
        arrow.shaft.setTranslateY(0 );
        arrow.shaft.setTranslateY(0 + (arrow.shaft.getHeight() * 0.5));

        arrow.cone.setTranslateX(0 );
        arrow.cone.setTranslateY(0 );
        arrow.cone.setTranslateY(0 + (arrow.shaft.getHeight() ));
        arrow.cone.getTransforms().add(new Rotate(180, Rotate.Z_AXIS));

        return arrow;
    }

    public Group getVectorsGroup() {
        Group vectorsGroup = new Group();
        vectorsGroup.getChildren().addAll(uArrow, vArrow, wArrow);
        return vectorsGroup;
    }

    private double calculateMagnitude(Point3D point3D) {
        Point3D coordinates = point3D.normalize();
        return Math.sqrt(
                Math.pow(coordinates.getX(), 2) +
                        Math.pow(coordinates.getY(), 2) +
                        Math.pow(coordinates.getZ(), 2)
        )/10;
    }


    private void addTextLabels() {

        Text3D uText = new Text3D("û", Color.RED, 0, uArrow.getLength() + 0.2, 0,2.5);
        Text3D vText = new Text3D("v̂", Color.GREEN, 0, vArrow.getLength() + 0.2, 0,2.5);
        Text3D wText = new Text3D("ŵ", Color.BLUE, 0, wArrow.getLength() + 0.2, 0,3.5);
        Rotate rotateTextU = new Rotate(180, Rotate.X_AXIS);
        Rotate rotateTextV = new Rotate(180, Rotate.X_AXIS);
        Rotate rotateTextW = new Rotate(180, Rotate.X_AXIS);

        // Add the rotation to the text to ensure it's upright
        uText.getTransforms().add(rotateTextU);
        vText.getTransforms().add(rotateTextV);
        wText.getTransforms().add(rotateTextW);

        // Add the text labels to the group containing the arrows
        uArrow.getChildren().add(uText);
        vArrow.getChildren().add(vText);
        wArrow.getChildren().add(wText);
    }
}

