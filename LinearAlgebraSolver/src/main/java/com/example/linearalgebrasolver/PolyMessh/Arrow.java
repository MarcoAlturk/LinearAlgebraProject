package com.example.linearalgebrasolver.PolyMessh;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Arrow extends Group {
    private double length = 10;
    private Point3D start = new Point3D(0,0,0);
    private Point3D direction = new Point3D(0,0,0);
   public Cylinder shaft = new Cylinder();
   public Cone cone = new Cone(1,1,1,2, Color.RED);


    public double getLength() {
        return length;
    }

    public Point3D getStart() {
        return start;
    }

    public Point3D getDirection() {
        return direction;
    }

    public void setLength(double gridSize) {
        Point3D vector = this.direction.subtract(this.start).normalize();
        this.length = gridSize;
        this.shaft.setHeight(length);
    }

    public void setStart(Point3D start) {
        this.start = start;
    }

    public void setDirection(Point3D direction) {
        this.direction = direction;
    }
    public  Arrow(){}
    public Arrow(Point3D start, Point3D direction, double length, double radius) {
        this.start = start;
        this.direction = direction;
        this.length = length;

        // Configure the shaft (reuse the field)
        this.shaft.setRadius(radius);
        this.shaft.setHeight(length);
        PhongMaterial material = new PhongMaterial(Color.RED);
        this.shaft.setMaterial(material);

        // Compute rotation for the shaft
        Point3D normalizedDirection = direction.normalize();
        Point3D up = new Point3D(0, 1, 0);
        double angle = Math.toDegrees(Math.acos(normalizedDirection.dotProduct(up)));
        Point3D rotationAxis = normalizedDirection.crossProduct(up);

        if (!rotationAxis.equals(Point3D.ZERO)) {
            this.shaft.getTransforms().add(new Rotate(angle, rotationAxis));
        }

        // Configure the cone (reuse the field)
        this.cone = new Cone(radius * 2, 0, radius * 4, 36, Color.BLACK); // Adjust size
        if (!rotationAxis.equals(Point3D.ZERO)) {
            this.cone.getTransforms().add(new Rotate(angle, rotationAxis));
        }

        Point3D conePosition = start.add(normalizedDirection.multiply(length));
        this.cone.setTranslateX(conePosition.getX());
        this.cone.setTranslateY(conePosition.getY());
        this.cone.setTranslateZ(conePosition.getZ());

        // Add the shaft and cone to the group
        this.getChildren().addAll(this.shaft, this.cone);
    }


    public  void setRotation(){
        Point3D vector = this.direction.subtract(this.start);
        Point3D defaultAxis = new Point3D(0,1,0);

        Point3D crossProduct = defaultAxis.crossProduct(vector);
        double angle = Math.toDegrees(Math.atan(defaultAxis.dotProduct(vector)));

        if (!crossProduct.equals(Point3D.ZERO)) {
            Rotate rotate = new Rotate(angle, crossProduct);
            this.shaft.getTransforms().add(rotate);
            this.cone.getTransforms().add(rotate);
        }
    }

    public void setPosition() {
        // Position the shaft at the start point
        this.shaft.setTranslateX(this.direction.getX());
        this.shaft.setTranslateY(this.direction.getY());
        this.shaft.setTranslateZ(this.direction.getZ());

        // Calculate the tip of the shaft
        Point3D directionVector = this.direction.subtract(this.start); // Normalized direction
        Point3D tipPosition = this.start.add(directionVector);

        // Translate the cone to the tip position

        System.out.println(direction + "Direction vector");
        System.out.println(this.shaft.getTranslateX() + " Shaft transte x ");
        this.cone.setTranslateX(this.shaft.getTranslateX() + (this.length / 2)) ; // Adjust X proportionally
        this.cone.setTranslateY(this.shaft.getTranslateY() - (this.length / 6.5)); // Adjust Y proportionally
        this.cone.setTranslateZ(this.shaft.getTranslateZ() - this.shaft.getRadius() / 2); // Adjust Z proportionally
    }


    public void setRotation(double nx, double ny, double nz) {
        // Create the normal vector (plane's normal)
        Point3D normal = new Point3D(nx, ny, nz);

        // Define the default arrow direction (Y-axis)
        Point3D defaultDirection = new Point3D(0, 1, 0);

        // Calculate the rotation axis (cross product)
        Point3D rotationAxis = defaultDirection.crossProduct(normal);

        // Calculate the angle between the default direction and the normal vector
        double angle = Math.toDegrees(Math.acos(defaultDirection.dotProduct(normal) / (defaultDirection.magnitude() * normal.magnitude())));

        // Apply rotation only if rotationAxis is non-zero
        if (!rotationAxis.equals(Point3D.ZERO)) {
            // Create a Rotate transformation around the calculated axis and angle
            Rotate rotate = new Rotate(angle , rotationAxis);
            this.shaft.getTransforms().add(rotate);
            this.cone.getTransforms().add(rotate);
        }
    }
}

