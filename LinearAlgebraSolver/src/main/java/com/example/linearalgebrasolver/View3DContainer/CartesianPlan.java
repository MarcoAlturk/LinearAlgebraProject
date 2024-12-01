package com.example.linearalgebrasolver.View3DContainer;

import com.example.linearalgebrasolver.PolyMessh.PolygonMesh;
import com.example.linearalgebrasolver.PolyMessh.PolygonMeshView;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class CartesianPlan {
    private PolygonMesh createQuadrilateralMesh(float width, float height, int subDivX, int subDivY) {
        final float minX = -width / 2f;
        final float minY = -height / 2f;
        final float maxX = width / 2f;
        final float maxY = height / 2f;

        final int pointSize = 3;
        final int texCoordSize = 2;
        final int faceSize = 8; // 4 point indices and 4 texCoord indices per face
        int numDivX = subDivX + 1;
        int numVerts = (subDivY + 1) * numDivX;
        float points[] = new float[numVerts * pointSize];
        float texCoords[] = new float[numVerts * texCoordSize];
        int faces[][] = new int[subDivX * subDivY][faceSize];

        // Create points and texCoords
        for (int y = 0; y <= subDivY; y++) {
            float dy = (float) y / subDivY;
            float fy = (1 - dy) * minY + dy * maxY;

            for (int x = 0; x <= subDivX; x++) {
                float dx = (float) x / subDivX;
                float fx = (1 - dx) * minX + dx * maxX;

                int index = y * numDivX * pointSize + (x * pointSize);
                points[index] = fx;
                points[index + 1] = fy;
                points[index + 2] = 0.0f;

                index = y * numDivX * texCoordSize + (x * texCoordSize);
                texCoords[index] = dx;
                texCoords[index + 1] = dy;
            }
        }

        // Create faces
        int index = 0;
        for (int y = 0; y < subDivY; y++) {
            for (int x = 0; x < subDivX; x++) {
                int p00 = y * numDivX + x;
                int p01 = p00 + 1;
                int p10 = p00 + numDivX;
                int p11 = p10 + 1;
                int tc00 = y * numDivX + x;
                int tc01 = tc00 + 1;
                int tc10 = tc00 + numDivX;
                int tc11 = tc10 + 1;

                faces[index][0] = p00;
                faces[index][1] = tc00;
                faces[index][2] = p10;
                faces[index][3] = tc10;
                faces[index][4] = p11;
                faces[index][5] = tc11;
                faces[index][6] = p01;
                faces[index++][7] = tc01;
            }
        }

        int[] smooth = new int[faces.length];
        PolygonMesh mesh = new PolygonMesh(points, texCoords, faces);
        mesh.getFaceSmoothingGroups().addAll(smooth);
        return mesh;
    }

    public Group createGrid(float size, float delta) {
        if (delta < 1) delta = 1;
        // Create separate meshes for each plane
        PolygonMesh planeXY = createQuadrilateralMesh(size, size, (int) (size / delta), (int) (size / delta));
        PolygonMesh planeXZ = createQuadrilateralMesh(size, size, (int) (size / delta), (int) (size / delta));
        PolygonMesh planeYZ = createQuadrilateralMesh(size, size, (int) (size / delta), (int) (size / delta));

        // Create the XY plane
        PolygonMeshView meshViewXY = new PolygonMeshView(planeXY);
        meshViewXY.setDrawMode(DrawMode.LINE);
        meshViewXY.setCullFace(CullFace.NONE);
        meshViewXY.getTransforms().add(new Rotate(270.5, Rotate.X_AXIS));

        // Create the XZ plane
        PolygonMeshView meshViewXZ = new PolygonMeshView(planeXZ);
        meshViewXZ.setDrawMode(DrawMode.LINE);
        meshViewXZ.setCullFace(CullFace.NONE);
        meshViewXZ.getTransforms().add(new Rotate(270.5, Rotate.X_AXIS));

        // Create the YZ plane
        PolygonMeshView meshViewYZ = new PolygonMeshView(planeYZ);
        meshViewYZ.setDrawMode(DrawMode.LINE);
        meshViewYZ.setCullFace(CullFace.NONE);
        meshViewYZ.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));

        // Combine all grids into one group
        Group grid = new Group(meshViewXY, meshViewXZ, meshViewYZ);

        // Apply a scale transform to ensure the grid matches other objects
        grid.getTransforms().add(new Scale(0.5, 0.5, 0.5));

        return grid;
    }
    public Group getAxes(double scale) {
        Cylinder axisX = new Cylinder(0.1, 200);
        axisX.getTransforms().addAll(new Rotate(90, Rotate.Z_AXIS), new Translate(0, -100, 0));
        axisX.setMaterial(new PhongMaterial(Color.RED));
        Text textX = create3DText("X", 200,0, 0,Color.RED);

        Cylinder axisY = new Cylinder(0.1, 200);
        axisY.getTransforms().add(new Translate(0, 100, 0));
        axisY.setMaterial(new PhongMaterial(Color.GREEN));
        Text textY = create3DText("Y",0, 200, 0 ,Color.GREEN);

        Cylinder axisZ = new Cylinder(0.1, 200);
        axisZ.setMaterial(new PhongMaterial(Color.BLUE));
        axisZ.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Translate(0, 100, 0));
        Text textZ = create3DText("Z",0, 0, 200, Color.BLUE);


        Group group = new Group(axisX, axisY, axisZ, textX,textY, textZ);
        group.getTransforms().add(new Scale(scale, scale, scale));
        return group;
    }
    public Text create3DText(String letter, double x, double y, double z, Color color){
        Text text3D = new Text(letter);
        text3D.setFill(color);
        text3D.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        text3D.getTransforms().add(new Translate(x,y,z));
        return text3D;
    }

}
