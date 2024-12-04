package com.example.linearalgebrasolver.View3DContainer;

import com.example.linearalgebrasolver.PolyMessh.Arrow;
import com.example.linearalgebrasolver.PolyMessh.VectorArrows;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class Planes {
    static int count = 0;

    // normal vector
    public double A, B, C;
    private double D;
    private Point3D zNormal, currentNormal;

    VectorArrows normalBox;

    Group plane = new Group();

    public Group createPlaneMesh(double gridSize) {
        Color color = PlanesColorManager.getNextColor();

        double magnitude = Math.sqrt(this.A * this.A + this.B * this.B + this.C * this.C);
        double nx = this.A / magnitude;
        double ny = this.B / magnitude;
        double nz = this.C / magnitude;
        double scaledD = -this.D / magnitude;
        double offset = scaledD / gridSize;


        this.setzNormal(new Point3D(0, 0, 1));
        this.setCurrentNormal(new Point3D(nx, ny, nz));

        Box mainBox = new Box(gridSize, gridSize, 0.001); // A thin box as the plane
        PhongMaterial boxMaterial = new PhongMaterial();
        boxMaterial.setDiffuseColor(color);
        mainBox.setMaterial(boxMaterial);


        normalBox = new VectorArrows(10, 10, 100); // Width, Height, Depth for the normal vector



        normalBox.cone.setTranslateZ(-mainBox.getDepth() * 0.5/ 2 - normalBox.shaft.getDepth() / 2);

        normalBox.cone.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS));

        normalBox.cone.setTranslateY(1);

        normalBox.getTransforms().add(new Scale(0.18,0.1, 0.1));
        normalBox.setTranslateZ((-mainBox.getDepth() / 2 - normalBox.shaft.getDepth() / 2) / (gridSize / 2));
        normalBox.setTranslateX(-normalBox.getLength());
        double xText = normalBox.cone.getTranslateX() * 0.18 - 20;
        double yText =  normalBox.cone.getTranslateY() * 0.1;
        double zText =  normalBox.cone.getTranslateZ() * 0.1;




        Text3D normalText = new Text3D("Normal: (" + A + ", " + B + ", " + C + ")", color, xText, yText, zText, 3);;

        Rotate rotate1 = new Rotate(90, Rotate.Y_AXIS);
        mainBox.getTransforms().add(rotate1);


        normalBox.getTransforms().add(rotate1);
        normalText.getTransforms().addAll(new Rotate(-60, 0,0,1), new Rotate(-10, 0,1,1) );
        plane.getChildren().addAll(mainBox, normalBox, normalText);



        plane.setTranslateX(nx * offset );
        plane.setTranslateY(ny * offset );
        plane.setTranslateZ(nz * offset );

        // C dot product between the default normal (Z-axis) and the current normal which is the one given by the equation
        Point3D defaultNormal = new Point3D(0, 0, 1);
        double dotProduct = defaultNormal.dotProduct(new Point3D(nx, ny, nz));

        double angle = Math.acos(dotProduct);

        Point3D rotationAxis = defaultNormal.crossProduct(new Point3D(nx, ny, nz));

        rotationAxis = rotationAxis.normalize();

        Rotate rotate = new Rotate(Math.toDegrees(angle), rotationAxis);




        plane.getTransforms().addAll(rotate);
        normalText.getTransforms().addAll(rotate);

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
    public class PlanesColorManager {
        private static final ArrayList<Color> colors = new ArrayList<>() {{
            add(Color.DODGERBLUE);  // Default Dodger Blue
            add(Color.LIGHTCORAL); // Light red
            add(Color.LIGHTGREEN); // Light green
            add(Color.LIGHTGOLDENRODYELLOW); // Light orange
        }};
        private static int colorIndex = 0; // Tracks the current color index

        // Get the next color based on the count
        public static Color getNextColor() {
            Color nextColor = colors.get(colorIndex); // Fetch current color
            colorIndex++; // Increment the index
            if (colorIndex >= colors.size()) {
                colorIndex = 0; // Reset if we exceed available colors
            }
            return nextColor;
        }
    }
}

