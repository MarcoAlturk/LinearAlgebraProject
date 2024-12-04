package com.example.linearalgebrasolver.View3DContainer;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Points  {
    private double X, Y, Z;
    private static int count = 0;
    private static final Color[] colors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE
    };
    private Color pointColor;
    private Sphere pointSphere;


    public Points(double X, double Y, double Z, double gridSize) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        double scaleId = 1.0 / (gridSize );

        pointColor = colors[count % colors.length];

        pointSphere = new Sphere(0.3);


        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(pointColor);
        material.setSpecularColor(Color.WHITE);

        pointSphere.setMaterial(material);

        // Update the position of the point based on the grid size
        pointSphere.setTranslateX(X * scaleId);
        pointSphere.setTranslateY(Y * scaleId);
        pointSphere.setTranslateZ(Z * ( (1 -(0.001* gridSize * scaleId))));

        count++;
    }


    public Sphere getPointSphere() {
        return pointSphere;
    }

    public Point3D getPointsCoordinates(){
        return new Point3D(this.getPointSphere().getTranslateX(),this.getPointSphere().getTranslateY(), this.getPointSphere().getTranslateZ());
    }
    public void setCoordinates(Point3D point3D){
        this.getPointSphere().setTranslateX(this.getPointSphere().getTranslateX());
        this.getPointSphere().setTranslateY(this.getPointSphere().getTranslateY());
        this.getPointSphere().setTranslateZ(this.getPointSphere().getTranslateZ());
    }
}
