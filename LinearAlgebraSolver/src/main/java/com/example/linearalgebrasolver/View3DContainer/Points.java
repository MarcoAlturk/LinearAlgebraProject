package com.example.linearalgebrasolver.View3DContainer;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;

public class Points  {
    private double X, Y, Z;
    private static int count = 0;
    private static final Color[] colors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE
    };
    private static final char[] labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private Text3D pointLabel;
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
        pointSphere.setTranslateZ(Z * ( (1 -(0.5* gridSize * scaleId))));

        System.out.println(pointSphere.getTranslateZ());

        char labelChar = labels[count % labels.length];
        String xFormat = String.format("%.2f", X);
        String yFormat = String.format("%.2f", Y);
        String zFormat = String.format("%.2f", Z);
        String labelText = labelChar + " (" + xFormat + ", " + yFormat + ", " + zFormat + ")";
        pointLabel = new Text3D(labelText, pointColor,0,0,0,2.5);

        // Position the label relative to the point
        pointLabel.setTranslateX(pointSphere.getTranslateX() + 0.5); // Slight offset for readability
        pointLabel.setTranslateY(pointSphere.getTranslateY() - 0.5);
        pointLabel.setTranslateZ(pointSphere.getTranslateZ());


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

        pointLabel.getTransforms().clear();
        pointLabel.getTransforms().add(new javafx.scene.transform.Translate(
                point3D.getX() + 0.5,
                point3D.getY() - 0.5,
                point3D.getZ()
        ));
    }

    public Text3D getPointLabel() {
        return pointLabel;
    }
    public Point3D getTrueCoordinates(){
        double ratio1 = ( 1/50 );
        double ratio2 =  (1 -(0.001));
        return new Point3D(this.getPointsCoordinates().getX() / ratio1, this.getPointsCoordinates().getY() /ratio1 , this.getPointsCoordinates().getZ() / ratio2);
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }
}
