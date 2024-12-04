package com.example.linearalgebrasolver.PolyMessh;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class
VectorArrows extends Group {
    private double length = 10;
    private Point3D start = new Point3D(0,0,0);
    private Point3D direction = new Point3D(0,0,0);
    public Box shaft = new Box();
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

    public VectorArrows(double length, double width, double height) {
        // Create the shaft as a Box
        shaft = new Box(width, length, height);
        PhongMaterial shaftMaterial = new PhongMaterial(Color.BLUE);
        shaft.setMaterial(shaftMaterial);

        // Create the cone (arrowhead)
        cone = new Cone(width * 2, 0, width * 4, 360, Color.BLACK);
        cone.setTranslateY(-length / 2 - width * 2); // Position cone at the end of the box

        getChildren().addAll(shaft, cone);
    }


    public void setPosition(Point3D position) {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }

}
