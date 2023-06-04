package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label xPosLabel;
    @FXML
    private Label yPosLabel;

    private int xPos;
    private int yPos;

//    @FXML
//    protected void onHelloButtonClick() {
//        ccounter ++;
//        welcomeText.setText("dupa" + ccounter);
//    }
//    @FXML
//    protected void onGoodbyeButtonClick() {
//        goodbyeText.setText("goodbye");
//    }
    @FXML
    protected void onLeftButtonClick()
    {
        xPos--;
        xPosLabel.setText("X position: " + xPos);
    }
    @FXML
    protected void onRightButtonClick()
    {
        xPos++;
        xPosLabel.setText("X position: " + xPos);
    }
}