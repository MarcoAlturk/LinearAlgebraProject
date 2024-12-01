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
    private double length = 0;
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
        double rawLength = Math.sqrt(Math.pow(this.direction.getX() - this.start.getX(), 2) +
                Math.pow(this.direction.getY() - this.start.getY(), 2) +
                Math.pow(this.direction.getZ() - this.start.getZ(), 2));
        double gridPortions =  gridSize * Math.sqrt(3);
        this.length = rawLength * gridSize;
        this.shaft.setHeight(length);


        System.out.println("Lenght " + this.length + " Raw "  + rawLength );
    }

    public void setStart(Point3D start) {
        this.start = start;
    }

    public void setDirection(Point3D direction) {
        this.direction = direction;
    }
    public  Arrow(){}

    public Arrow(double radius, Color color, double gridSize) {
        this();
        shaft = new Cylinder(radius, length);
        shaft.setRadius(20);
        this.shaft.setMaterial(new PhongMaterial(Color.MEDIUMPURPLE));


        this.cone = new Cone(radius  , radius * 4, radius * 8, 16, color);



        this.getChildren().addAll(shaft,cone);
    }

    public  void setRotation(){
        Point3D vector = this.direction.subtract(this.start);
        Point3D defaultAxis = new Point3D(0,1,0);

        Point3D crossProduct = defaultAxis.crossProduct(vector);
        double angle = Math.toDegrees(Math.acos(defaultAxis.dotProduct(vector)));

        if (!crossProduct.equals(Point3D.ZERO)) {
            Rotate rotate = new Rotate(angle, crossProduct);
            this.shaft.getTransforms().add(rotate);
            this.cone.getTransforms().add(rotate);
        }
    }

    public void setPosition() {
        System.out.println("Seeting positon to : " + this.start);
        this.shaft.getTransforms().add(new Translate(this.start.getX(), this.start.getY(), this.start.getZ()));
        this.cone.getTransforms().add(new Translate(this.start.getX() , 7, this.start.getZ()));
    }

    public void setNormal(Point3D normal) {
        this.direction = normal;
        setRotation();
    }
}

