package com.example.linearalgebrasolver.View3DContainer;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.transform.Rotate;

public class Planes {
    static int count = 0;

    // normal vector
    public double A, B, C;
    private double D;

    public Rectangle createPlaneMesh( double gridSize) {
        // Normalize the normal vector
        double magnitude = Math.sqrt(this.A * this.A + this.B * this.B + this.C * this.C);
        double nx = this.A / magnitude;
        double ny = this.B / magnitude;
        double nz = this.C / magnitude;
        double scaledD = this.D / magnitude;
        double offset = scaledD / gridSize;

        Rectangle plane = new Rectangle(gridSize* 3 , gridSize * 4 );
        plane.setFill(Color.RED);
        plane.setOpacity(1);

        plane.setTranslateX(nx * offset);
        plane.setTranslateY(ny * offset);
        plane.setTranslateZ(nz * offset);

        Rotate rotate = createRotation(new Point3D(0,0,1), new Point3D(nx, ny, nz));
        plane.getTransforms().addAll(rotate);
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

    public Rotate createRotation(Point3D zNormal, Point3D currentNormal){
        Point3D axis  = zNormal.crossProduct(currentNormal); // convert into unit vector
        double angle = Math.acos(zNormal.dotProduct(currentNormal) / (zNormal.magnitude() * currentNormal.magnitude()));
        return new Rotate(Math.toDegrees(angle), axis.getX(), axis.getY(), axis.getZ());
    }
}

