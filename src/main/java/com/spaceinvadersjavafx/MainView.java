package com.spaceinvadersjavafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
public class MainView extends AnchorPane{

    private Button restartButton;
    private Button pauseButton;

    private Canvas canvas;

    private GameLogic gameLogic;

    public MainView(int width, int height)
    {
        pauseButton = new Button("PAUSE");
        restartButton = new Button("RESTART");
        canvas = new Canvas(width, height);
        this.getChildren().addAll(canvas, restartButton, pauseButton);

        restartButton.setOnAction(actionEvent -> {gameLogic.onRestartButtonClick();});
        pauseButton.setOnAction(actionEvent -> {gameLogic.onPauseButtonClick();});

//        pauseButton.setL

        restartButton.setLayoutY(height - 30);
        restartButton.setLayoutX(width/2 - 50);
        pauseButton.setLayoutY(height - 30);
        pauseButton.setLayoutX(width/2 + 20);

        //        pauseButton.snapPositionY(height);

//        pauseButton.

//        pauseButton.snapPositionX((float)width/2);

    }

    public void SetGameLogic(GameLogic _gameLogic)
    {
        gameLogic = _gameLogic;
    }
    public GraphicsContext GetGraphicsContext()
    {
        return canvas.getGraphicsContext2D();
    }
}
