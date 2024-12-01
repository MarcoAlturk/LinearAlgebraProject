package com.example.linearalgebrasolver.View3DContainer;

import com.example.linearalgebrasolver.PolyMessh.Arrow;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Planes {
    static int count = 0;

    // normal vector
    public double A, B, C;
    private double D;
    private Point3D zNormal, currentNormal;

    public Box createPlaneMesh(double gridSize, Arrow arrow) {

       double magnitude = Math.sqrt(this.A * this.A + this.B * this.B + this.C * this.C);
        System.out.println(magnitude);

        double nx = this.A / magnitude;
        double ny = this.B / magnitude;
        double nz = this.C / magnitude;
        double scaledD = this.D / magnitude;
        double offset = scaledD / gridSize;
        System.out.println("Normal : " + nx + " " +  ny + " " + nz);
        this.setzNormal(new Point3D(0,0,1));
        this.setCurrentNormal(new Point3D(nx, ny, nz));

        Box plane = new Box(gridSize / 5 , gridSize / 3, 0.001);
        plane.setMaterial(new javafx.scene.paint.PhongMaterial(Color.RED));
        plane.setOpacity(0.5);

        plane.setTranslateX(nx *offset);
        plane.setTranslateY(ny * offset);
        plane.setTranslateZ(nz * offset);

        System.out.println(plane.getTranslateX());

        //Rotate rotate = createRotation();
       // Rotate rotate = new Rotate(35, 23,1,3);
       // Rotate rotateX = new Rotate(Math.toDegrees(Math.atan2(nz, ny)), Rotate.X_AXIS);
       // Rotate rotateY = new Rotate(Math.toDegrees(Math.atan2(nx, nz)), Rotate.Y_AXIS);
        // Calculate the angle in the 2D X-Y plane for the rotation
        double angle = Math.toDegrees(Math.atan2(ny, nx));


        Rotate rotateZ = new Rotate(-angle, Rotate.Z_AXIS);
        Point3D arrowStart = new Point3D(plane.getTranslateX(), plane.getTranslateY(), plane.getTranslateZ());
        double angleX = Math.toDegrees(Math.atan2(nz, ny));

        double angleY = Math.toDegrees(Math.atan2(nx, Math.sqrt(ny * ny + nz * nz)));

        Rotate rotateX = new Rotate(-angleX, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(-angleY, Rotate.Y_AXIS);

        Point3D arrowDirection = this.getCurrentNormal();
        arrow.setDirection(arrowDirection);
        arrow.setStart(arrowStart);

        arrow.setNormal(new Point3D(nx, ny, nz));
        arrow.setPosition();
        arrow.setRotation();

        plane.getTransforms().addAll( rotateX, rotateY, rotateZ);
        count++;
        return plane;
    }


    public void setNormalVector(double aX, double aY, double aZ , double d){
        this.A = aX;
        this.B = aY;
        this.C = aZ;
        this.D = d;
    }

    public Color color(){
        Color color = Color.color(250, 250, 250);
        return color;
    }

    public Rotate createRotation() {
        // Calculate the axis of rotation (cross product)
        Point3D axis = this.zNormal.crossProduct(this.currentNormal);

        // If the cross product is a zero vector, it means the vectors are already aligned
        if (axis.magnitude() == 0) {
            return new Rotate(0, 0, 0, 0); // No rotation needed
        }

        // Normalize the axis of rotation
        axis = axis.normalize();

        // Calculate the angle using atan2 on the components of the axis
        double angle = Math.atan2(axis.magnitude(), this.zNormal.dotProduct(this.currentNormal));

        // Apply the rotation (around the axis by the angle)
        return new Rotate(Math.toDegrees(angle), axis.getX(), axis.getY(), axis.getZ());
    }

    private void setzNormal(Point3D zNormal) {
        this.zNormal = zNormal;
    }

    private void setCurrentNormal(Point3D currentNormal) {
        this.currentNormal = currentNormal;
    }

    public Point3D getzNormal() {
        return zNormal;
    }

    public Point3D getCurrentNormal() {
        return currentNormal;
    }
}

