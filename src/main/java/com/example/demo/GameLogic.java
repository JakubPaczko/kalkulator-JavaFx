package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
public class GameLogic {
    Scene _scene;
    Image playerImage = new Image("file:Textures/ship.png");
    Image meteorImage = new Image("file:Textures/rock.png");
    Image bulletImage = new Image("file:Textures/bullet.png");
    List<Entity> Container = new ArrayList<Entity>();

    int playerHP = 0;
    int Score = 0;
    GameLogic(Scene scene)
    {
        _scene = scene;
        Entity bullet = new Entity(10, 10, 10, 1, 1, 1, 10, 0.001f, entType.player);
        Container.add(bullet);
        Entity bullet2 = new Entity(1, 300, 300, 0.5f, 0.5f, 2, 2, 0.0001f, entType.meteor);
        Container.add(bullet2);

    }
    public void Update(GraphicsContext gc)
    {
        for (int i = 0; i < Container.size(); i++) {
            Container.get(i).Update();
            Container.get(i).HandleCollision(this);

            if(!Container.get(i).isAlive)
            {
                Container.remove(i);
                continue;
            }

            if(Container.get(i).GetType() == entType.player)
            {
                HandleInput(Container.get(i));
                playerHP = Container.get(i).GetHP();
            }
        }
        Display(gc);
        DisplayUI(gc);
    }

    public void HandleInput(Entity _ent)
    {
//        if(front) _ent.AddFrontVelocity();
//        _ent.rotate(left, right);

        _scene.addEventHandler(KeyEvent.ANY, (key) -> {
                    if (key.getCode() == KeyCode.W) {
//                System.out.println("You pressed enter");
                        _ent.AddFrontVelocity();
                    }
                });
        _scene.addEventHandler(KeyEvent.ANY, (key) -> {
                if (key.getCode() == KeyCode.D) {
                    _ent.rotate(false, true);
                }
            });
        _scene.addEventHandler(KeyEvent.ANY, (key) -> {
            if(key.getCode()== KeyCode.A) {
                _ent.rotate(true, false);
            }

        });

        _scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.SPACE) {
                _ent.Shoot(Container);
            }
        });
    }


    private Image drawImager = null;
    public void Display(GraphicsContext gc)
    {
//        System.out.println(Container.size());

        gc.setFill( Color.BLACK );
        gc.fillRect(0, 0, 1000, 1000);

        for (Entity ent : Container) {

            if(ent.GetType() == entType.player)
            {
                drawImager = playerImage;
            }
            else if (ent.GetType() == entType.meteor) {
                drawImager = meteorImage;
            }
            else if (ent.GetType() == entType.bullet) {
                drawImager = bulletImage;
            }

            float s = (float) Math.sin(Math.toRadians(ent.rotation));
            float c = (float) Math.cos(Math.toRadians(ent.rotation));

            // translate point back to origin:
            float px = ent.posX - (ent.sizeX/2);
            float py = ent.posY - (ent.sizeY/2);

            // rotate point
            float xnew = px * c - py * s;
            float ynew = px * s + py * c;

            // translate point back:
            px = xnew;
            py = ynew;

            gc.rotate(-ent.rotation);
            gc.drawImage(drawImager, px - (ent.sizeX/2), py - (ent.sizeY/2), ent.sizeX, ent.sizeY);
            gc.rotate(ent.rotation);
        }
    }

    void DisplayUI(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.fillText("PlayerScore: " + Score, 10, 20);
        gc.fillText("PlayerHP: " + playerHP, 10, 10);

    }

    public void AddScore(int score)
    {
        Score += score;
    }

    private void SpawnMeteor()
    {

    }
}
