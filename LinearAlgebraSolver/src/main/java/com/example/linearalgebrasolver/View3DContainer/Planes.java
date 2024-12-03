package com.example.linearalgebrasolver.View3DContainer;

import com.example.linearalgebrasolver.PolyMessh.Arrow;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class Planes {
    static int count = 0;

    // normal vector
    public double A, B, C;
    private double D;
    private Point3D zNormal, currentNormal;

    public Box createPlaneMesh(double gridSize, Arrow arrow) {

        double magnitude = Math.sqrt(this.A * this.A + this.B * this.B + this.C * this.C);
        double nx = this.A / magnitude;
        double ny = this.B / magnitude;
        double nz = this.C / magnitude;
        double scaledD = -this.D / magnitude;
        double offset = scaledD / gridSize;


        this.setzNormal(new Point3D(0, 0, 1));
        this.setCurrentNormal(new Point3D(nx, ny, nz));

        Box plane = new Box(gridSize, gridSize, 0.001);
        plane.setMaterial(new javafx.scene.paint.PhongMaterial(Color.RED));
        plane.setOpacity(0.2);
        plane.setTranslateX(nx * offset / 5);
        plane.setTranslateY(ny * offset / 5);
        plane.setTranslateZ(nz * offset / 5);

        // Compute the dot product between the default normal (Z-axis) and the current normal
        Point3D defaultNormal = new Point3D(0, 0, 1);
        double dotProduct = defaultNormal.dotProduct(new Point3D(nx, ny, nz));

        // Compute the angle between the two normal vectors
        double angle = Math.acos(dotProduct); // Angle between Z-axis and the plane's normal

        Point3D rotationAxis = defaultNormal.crossProduct(new Point3D(nx, ny, nz));

        // Normalize the axis of rotation
        rotationAxis = rotationAxis.normalize();

        Rotate rotate = new Rotate(Math.toDegrees(angle), rotationAxis);
        plane.getTransforms().add(rotate);

        Point3D arrowStart = new Point3D(plane.getTranslateX(), plane.getTranslateY(), plane.getTranslateZ());
        Point3D arrowDirection = this.getCurrentNormal();
        arrow.setDirection(arrowDirection);
        arrow.setStart(arrowStart);

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

