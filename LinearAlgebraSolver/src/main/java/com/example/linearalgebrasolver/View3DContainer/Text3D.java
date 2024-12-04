package com.example.linearalgebrasolver.View3DContainer;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

public class Text3D extends Group {
    public Text3D(String text, Color color, double x, double y, double z, double size) {
    Text meshText = new Text(text);
    meshText.setFill(color);
    meshText.setStyle("-fx-font-size: " + size + "; -fx-font-weight: bold;");
    this.getChildren().add(meshText);

    Translate translate = new Translate(x, y, z);
    this.getTransforms().add(translate);
}
}
