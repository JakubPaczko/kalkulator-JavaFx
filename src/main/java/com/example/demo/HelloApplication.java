package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(640, 480);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        stage.setTitle("Space Invaders");
        stage.setScene(scene);

        GameLogic game = new GameLogic(scene);

        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(20), e -> game.Update(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}