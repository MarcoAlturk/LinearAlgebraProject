package com.example.linearalgebrasolver.PolyMessh;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

public class Cone extends Group {
        int rounds = 360;
        int r1 = 100;
        int r2 = 50;
        int h = 100;

    public Cone(double r1, double r2, double h, int rounds, Color color) {
        Group cone = new Group();
        PhongMaterial material = new PhongMaterial(color);

        // Prepare points and faces
        float[] points = new float[rounds * 12];
        float[] textCoords = {0.5f, 0, 0, 1, 1, 1};
        int[] faces = new int[rounds * 12];

        // Generate geometry for cone
        for (int i = 0; i < rounds; i++) {
            int index = i * 12;
            double angle1 = Math.toRadians(i * (360.0 / rounds));
            double angle2 = Math.toRadians((i + 1) * (360.0 / rounds));

            // Vertex positions for the quad
            points[index] = (float) (Math.cos(angle1) * r2);          // Bottom outer vertex 1 (x)
            points[index + 1] = (float) (Math.sin(angle1) * r2);      // Bottom outer vertex 1 (y)
            points[index + 2] = (float) (h / 2);                     // Bottom outer vertex 1 (z)

            points[index + 3] = (float) (Math.cos(angle1) * r1);      // Bottom inner vertex 1 (x)
            points[index + 4] = (float) (Math.sin(angle1) * r1);      // Bottom inner vertex 1 (y)
            points[index + 5] = (float) (-h / 2);                    // Bottom inner vertex 1 (z)

            points[index + 6] = (float) (Math.cos(angle2) * r1);      // Bottom inner vertex 2 (x)
            points[index + 7] = (float) (Math.sin(angle2) * r1);      // Bottom inner vertex 2 (y)
            points[index + 8] = (float) (-h / 2);                    // Bottom inner vertex 2 (z)

            points[index + 9] = (float) (Math.cos(angle2) * r2);      // Bottom outer vertex 2 (x)
            points[index + 10] = (float) (Math.sin(angle2) * r2);     // Bottom outer vertex 2 (y)
            points[index + 11] = (float) (h / 2);                    // Bottom outer vertex 2 (z)
        }

        // Define faces for the cone
        for (int i = 0; i < rounds; i++) {
            int index = i * 12;
            faces[index] = i * 4;
            faces[index + 1] = 0;
            faces[index + 2] = i * 4 + 1;
            faces[index + 3] = 1;
            faces[index + 4] = i * 4 + 2;
            faces[index + 5] = 2;

            faces[index + 6] = i * 4;
            faces[index + 7] = 0;
            faces[index + 8] = i * 4 + 2;
            faces[index + 9] = 1;
            faces[index + 10] = i * 4 + 3;
            faces[index + 11] = 2;
        }

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(textCoords);
        mesh.getFaces().addAll(faces);

        // Add circular caps if necessary
        Cylinder circle1 = new Cylinder(r1, 0.1);
        circle1.setMaterial(material);
        circle1.setTranslateZ(-h / 2);
        circle1.setRotationAxis(Rotate.X_AXIS);
        circle1.setRotate(90);

        Cylinder circle2 = new Cylinder(r2, 0.1);
        circle2.setMaterial(material);
        circle2.setTranslateZ(h / 2);
        circle2.setRotationAxis(Rotate.X_AXIS);
        circle2.setRotate(90);

        MeshView meshView = new MeshView(mesh);
        meshView.setMesh(mesh);
        meshView.setMaterial(material);
        cone.getChildren().addAll(meshView);

        // Optional: Adjust rotation to align the cone properly
        Rotate r3 = new Rotate(90, Rotate.X_AXIS);

        cone.getTransforms().add(r3);

        getChildren().addAll(cone);
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
