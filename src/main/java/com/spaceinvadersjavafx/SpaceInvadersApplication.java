package com.spaceinvadersjavafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class SpaceInvadersApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        MainView mainView = new MainView(640, 480);

        Scene scene = new Scene(mainView, 640, 480);

        stage.setTitle("Space Invaders");
        stage.setScene(scene);

        GameLogic game = new GameLogic(scene);
        mainView.SetGameLogic(game);

        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(20), e -> game.Update(mainView.GetGraphicsContext())));
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}