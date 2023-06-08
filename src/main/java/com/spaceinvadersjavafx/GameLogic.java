package com.spaceinvadersjavafx;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

enum gameState {paused, running, finished}
public class GameLogic {
    Scene _scene;
    Image playerImage = new Image("file:Textures/ship.png");
    Image meteorImage = new Image("file:Textures/rock.png");
    Image bulletImage = new Image("file:Textures/bullet.png");
    List<Entity> Container = new ArrayList<Entity>();

    int playerHP = 0;
    int Score = 0;

    gameState state;
    int baseMeteorSpawnTime = 150;
    int baseMedkitSpawnTime = 600;

    GameLogic(Scene scene)
    {
        _scene = scene;
        SpawnPlayer();
        state = gameState.running;

        _scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.P) {
                DebugMode = !DebugMode;
            }
        });
    }

    public void Update(GraphicsContext gc)
    {
        if(state == gameState.running){
            for (int i = 0; i < Container.size(); i++) {
                Container.get(i).Update();
                Container.get(i).HandleCollision(this);

                if(Container.get(i).GetType() == entType.player)
                {
                    if(!Container.get(i).isAlive) state = gameState.finished;
                    playerHP = Container.get(i).GetHP();
                }

                if(!Container.get(i).isAlive)
                {
                    Container.remove(i);
                }

            }
            SpawnMeteor();
            SpawnMedkit();
        }

        Display(gc);
        DisplayUI(gc);
    }

    public void HandleInput(int entId)
    {
        Player _ent = GetPlayer(entId);

        _scene.addEventHandler(KeyEvent.ANY, (key) -> {
            if (key.getCode() == KeyCode.W) {
                _ent.AddFrontVelocity();
            }
            if (key.getCode() == KeyCode.X) {
                _ent.Shoot(Container);
            }
            if(key.getCode()== KeyCode.A) {
                _ent.rotate(15f);
            }
            if (key.getCode() == KeyCode.D) {
                _ent.rotate(-15f);
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

            float px = ent.posX + (ent.sizeX/2);
            float py = ent.posY + (ent.sizeY/2);

            float xnew = px * c - py * s;
            float ynew = px * s + py * c;

            px = xnew;
            py = ynew;



            if(DebugMode)
            {
                gc.setFill(Color.WHITE);
                gc.fillRect(ent.posX, ent.posY, ent.sizeX, ent.sizeY);
                gc.setFill(Color.BLACK);
                gc.fillRect(ent.posX+1, ent.posY+1, ent.sizeX-2, ent.sizeY-2);

            }

            gc.rotate(-ent.rotation);
            gc.drawImage(ent.getImage(), px - (ent.sizeX/2), py - (ent.sizeY/2), ent.sizeX, ent.sizeY);
            gc.rotate(ent.rotation);

        }
    }
    boolean DebugMode = true;
    void DisplayUI(GraphicsContext gc){
        gc.setFill(Color.WHITE);

        gc.fillText("[WAD] - fly, [X] - shoot, [P] - toggle debug mode", 10, screenHeight - 20);

        gc.fillText("PlayerScore: " + Score, 10, 20);
        gc.fillText("PlayerHP: " + playerHP, 10, 10);

        if(DebugMode)
        {
            gc.fillText("Meteor spawn: " + meteorDelayTimer, 10, 40);
            gc.fillText("Entities: " + Container.size(), 10, 50);
            gc.fillText("Game status: " + state , 10, 60);
        }

        if(state == gameState.paused)
        {
            gc.fillText("Game Paused", (screenWidth/2) - 50.f, 60);
        }
        else if(state == gameState.finished)
        {
            gc.fillText("You died", (screenWidth/2) - 50.f, 40);
            gc.fillText("Your score is: " + Score, (screenWidth/2) - 50.f, 60);
        }
    }

    public void AddScore(int score)
    {
        Score += score;
    }

    int meteorDelayTimer = 0;

    final int screenWidth = 640;
    final int screenHeight = 480;
    private void SpawnMeteor()
    {
        meteorDelayTimer++;

        if(meteorDelayTimer < baseMeteorSpawnTime) return;

        meteorDelayTimer = 0;

        float xPos = 0;
        float yPos = 0;

        int i = (int) (Math.random() * 10) % 4;

        if(i == 0)
        {
            yPos = screenHeight;
            xPos = (int) (Math.random() * 1000) % screenWidth;
        }
        else if(i == 1)
        {
            yPos = 0;
            xPos = (int) (Math.random() * 1000) % screenWidth;
        }
        else if(i == 2)
        {
            xPos = screenHeight;
            yPos = (int) (Math.random() * 1000) % screenHeight;
        }
        else
        {
            xPos = 0;
            yPos = (int) (Math.random() * 1000) % screenHeight;
        }

        float targetPosX = (float) screenWidth/2;
        float targetPosY = (float) screenHeight/2;

        float xVel = -(xPos - targetPosX) / 100;
        float yVel = -(yPos - targetPosY) / 100;

        float scale = 2;
        int damage = (int) (1 + (0.1 * (Score + 1)));
        int hp = (int) (5 + (1 * (Score + 1)));

//        System.out.println(xVel);
//        System.out.println(yVel);


//        Entity meteor = new Entity(hp, xPos, yPos, xVel, yVel,scale , damage, 0, entType.meteor);
        Meteor meteor = new Meteor(hp, xPos, yPos, xVel, yVel,scale , damage, 0);
        Container.add(meteor);
    }
    int medkitDelayTimer = 0;

    private void SpawnMedkit()
    {
        medkitDelayTimer++;

        if(medkitDelayTimer < baseMedkitSpawnTime) return;

        medkitDelayTimer = 0;

        float xPos = 0;
        float yPos = 0;

        int i = (int) (Math.random() * 10) % 4;

        if(i == 0)
        {
            yPos = screenHeight;
            xPos = (int) (Math.random() * 1000) % screenWidth;
        }
        else if(i == 1)
        {
            yPos = 0;
            xPos = (int) (Math.random() * 1000) % screenWidth;
        }
        else if(i == 2)
        {
            xPos = screenHeight;
            yPos = (int) (Math.random() * 1000) % screenHeight;
        }
        else
        {
            xPos = 0;
            yPos = (int) (Math.random() * 1000) % screenHeight;
        }

        float targetPosX = (float) screenWidth/2;
        float targetPosY = (float) screenHeight/2;

        float xVel = -(xPos - targetPosX) / 300;
        float yVel = -(yPos - targetPosY) / 300;

        float scale = 1;
        int damage = (int) (1 + (0.1 * (Score + 1)));
        int hp = (int) (2 + (int)(Score/10));


        Medkit medkit = new Medkit(hp * 2, xPos, yPos, xVel, yVel,scale , damage);
        Container.add(medkit);
    }

//    @FXML
    public void onPauseButtonClick()
    {
        if(state == gameState.finished) return;
        if(state == gameState.paused) state = gameState.running;
        else if(state == gameState.running) state = gameState.paused;
    }
//
//    @FXML
    public void onRestartButtonClick()
    {
        state = gameState.running;
        restart();
    }

    private void restart()
    {
        Container.clear();
        SpawnPlayer();
        Score = 0;
        meteorDelayTimer = 0;
        System.out.println("Restart");
    }

    private void SpawnPlayer(){
        Player player = new Player(10, 100, 100, 0, 0, 1, 10, 0.5f);
        Container.add(player);

        HandleInput(0);
    }

    Meteor GetMeteor(int id){
        return (Meteor) Container.get(id);
    }

    Player GetPlayer(int id){
        return (Player) Container.get(id);
    }

    Bullet GetBullet(int id){
        return (Bullet) Container.get(id);
    }
    Meteor GetMedtkit(int id){
        return (Meteor) Container.get(id);
    }

    public void SpawnExplosion(float posX, float posY, float scale){
        Expolsion ex = new Expolsion(1, posX, posY, scale);

        Container.add(ex);
    }

}
