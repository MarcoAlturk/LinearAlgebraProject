package com.example.linearalgebrasolver.View3DContainer;

import com.example.linearalgebrasolver.PolyMessh.VectorArrows;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Lines {
    private Points startPoint;
    private Points endPoint;
    private Point3D direction;
    public Point3D originalDirection;

    public Lines(Points startPoint, Points endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        double dx = endPoint.getPointSphere().getTranslateX() - startPoint.getPointSphere().getTranslateX();
        double dy = endPoint.getPointSphere().getTranslateY() - startPoint.getPointSphere().getTranslateY();
        double dz = endPoint.getPointSphere().getTranslateZ() - startPoint.getPointSphere().getTranslateZ();
        double scaleId = 1.0 / 150 ;

        this.originalDirection = new Point3D(dx / scaleId , dy / scaleId, dz /  ( (1 -(0.001* 150 * scaleId))));
        this.direction = new Point3D(dx, dy, dz).normalize();
    }


    public Points getStartPoint() {
        return startPoint;
    }

    public Points getEndPoint() {
        return endPoint;
    }

    public Point3D getDirection() {
        return direction;
    }

    public Group createLine() {
        Group group = new Group();

        group.getChildren().add(startPoint.getPointSphere());
        group.getChildren().add(endPoint.getPointSphere());

        Point3D directionVector = new Point3D(
                endPoint.getPointSphere().getTranslateX() - startPoint.getPointSphere().getTranslateX(),
                endPoint.getPointSphere().getTranslateY() - startPoint.getPointSphere().getTranslateY(),
                endPoint.getPointSphere().getTranslateZ() - startPoint.getPointSphere().getTranslateZ()
        );


        double length = directionVector.magnitude();


        Cylinder cylinder = new Cylinder(0.1, length);


        cylinder.setMaterial(new PhongMaterial(Color.RED));


        cylinder.setTranslateX(startPoint.getPointSphere().getTranslateX() + directionVector.getX() / 2);
        cylinder.setTranslateY(startPoint.getPointSphere().getTranslateY() + directionVector.getY() );
        cylinder.setTranslateZ(startPoint.getPointSphere().getTranslateZ() + directionVector.getZ() / 2);


        cylinder.setTranslateX(cylinder.getTranslateX() );
        cylinder.setTranslateY(cylinder.getTranslateY() );
        cylinder.setTranslateZ(cylinder.getTranslateZ() );


        Point3D axis = directionVector.crossProduct(new Point3D(0, 1, 0));
        if (axis.magnitude() == 0) {
            axis = new Point3D(1, 0, 0);
        }

        double angle = Math.acos(directionVector.dotProduct(new Point3D(0, 1, 0)) /
                (directionVector.magnitude() * new Point3D(0, 1, 0).magnitude()));

        Rotate rotate = new Rotate(Math.toDegrees(angle), axis);
        cylinder.getTransforms().add(rotate);

        group.getChildren().add(cylinder);


        return group;
    }

    public Group createLineWithNormalVector() {
        Group group = new Group();
        Group baseLine = createLine();
        group.getChildren().add(baseLine);

        // Calculate the midpoint of the line
        Point3D midpoint = new Point3D(
                (startPoint.getPointSphere().getTranslateX() + endPoint.getPointSphere().getTranslateX()) / 2,
                (startPoint.getPointSphere().getTranslateY() + endPoint.getPointSphere().getTranslateY()) / 2,
                (startPoint.getPointSphere().getTranslateZ() + endPoint.getPointSphere().getTranslateZ()) / 2
        );


        VectorArrows normalArrow = new VectorArrows(10, 0.2, 0.2); // Length, width, height ratios
        normalArrow.setDirection(direction); // Set the direction to be parallel to the line
        normalArrow.setPosition(midpoint); // Set the position at the midpoint
        // Calculate the axis of rotation
        Point3D initialDirection = new Point3D(0, 1, 0);


        Point3D axis = direction.crossProduct(initialDirection);


        if (axis.magnitude() != 0) {
            double angle = Math.acos(direction.dotProduct(initialDirection) / (direction.magnitude() * initialDirection.magnitude()));

            Rotate rotate = new Rotate(Math.toDegrees(angle), axis);
            normalArrow.getTransforms().add(rotate);
        }


        group.getChildren().add(normalArrow);


        String labelText = "d(" + String.format("%.2f", this.originalDirection.getX()) + ", "
                + String.format("%.2f", this.originalDirection.getY()) + ", "
                + String.format("%.2f", this.originalDirection.getZ()) + ")";
        Text3D label = new Text3D(labelText, Color.BLACK, midpoint.getX() + 5, midpoint.getY(), midpoint.getZ(), 3); // Adjust position slightly
        group.getChildren().add(label);

        return group;
    }

    public Group createLineWithDottedCylindersAndLabel(String labelText) {
        Group group = new Group();


        group.getChildren().add(startPoint.getPointSphere());
        group.getChildren().add(endPoint.getPointSphere());

        Point3D directionVector = new Point3D(
                endPoint.getPointSphere().getTranslateX() - startPoint.getPointSphere().getTranslateX(),
                endPoint.getPointSphere().getTranslateY() - startPoint.getPointSphere().getTranslateY(),
                endPoint.getPointSphere().getTranslateZ() - startPoint.getPointSphere().getTranslateZ()
        );

        double length = directionVector.magnitude();


        int numCylinders = (int)(length / 0.5);

        for (int i = 0; i < numCylinders; i++) {
            double fraction = (i + 0.5) / numCylinders;

            double x = startPoint.getPointSphere().getTranslateX() + fraction * directionVector.getX();
            double y = startPoint.getPointSphere().getTranslateY() + fraction * directionVector.getY();
            double z = startPoint.getPointSphere().getTranslateZ() + fraction * directionVector.getZ();

            // Create a small cylinder to represent a "dot"
            Cylinder cylinder = new Cylinder(0.05, 0.2);
            cylinder.setMaterial(new PhongMaterial(Color.RED));
            cylinder.setTranslateX(x);
            cylinder.setTranslateY(y);
            cylinder.setTranslateZ(z);


            group.getChildren().add(cylinder);
        }


        Point3D midpoint = new Point3D(
                (startPoint.getPointSphere().getTranslateX() + endPoint.getPointSphere().getTranslateX()) / 2,
                (startPoint.getPointSphere().getTranslateY() + endPoint.getPointSphere().getTranslateY()) / 2,
                (startPoint.getPointSphere().getTranslateZ() + endPoint.getPointSphere().getTranslateZ()) / 2
        );

        Text3D label = new Text3D(labelText, Color.BLACK, midpoint.getX() + 5, midpoint.getY(), midpoint.getZ(), 3);
        group.getChildren().add(label);

        return group;
    }





}
