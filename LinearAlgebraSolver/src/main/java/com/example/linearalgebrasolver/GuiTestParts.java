package com.example.linearalgebrasolver;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiTestParts extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setFullScreen(true);
        primaryStage.setScene(MainPageUI.mainScene());
        primaryStage.setTitle("Linear Algebra Calculator");
        primaryStage.show();
    }
}
