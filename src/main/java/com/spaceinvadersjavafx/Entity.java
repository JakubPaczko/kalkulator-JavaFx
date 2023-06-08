package com.spaceinvadersjavafx;
import javafx.scene.image.Image;

import java.util.List;

enum entType {bullet, player, meteor, explosion, medkit}

public class Entity {

    protected boolean hasCollider = true;
    protected Image image = null;
    public final float defaultEntSize = 32f;
    protected float rotation = 0;
    float rotationSpeed = (float) Math.toRadians(5.0);
    protected entType type;
    public float posX;
    public float posY;
    public float sizeX;
    public float sizeY;
    boolean isAlive = true;
    private int HP;
    private final int shootFramesTimer = 50;
    Entity(int _hp, float _posX, float _posY, float scale)
    {

        type = null;

        HP = _hp;

        posX = _posX;
        posY = _posY;


        sizeX = defaultEntSize * scale;
        sizeY = defaultEntSize * scale;

        rotation = 0f;
    }

     void Update() {}

    public void TakeDamage(int damage)
    {
        HP -= damage;

        if(HP <= 0) isAlive = false;
    }

    boolean CheckCollider(Entity ent)
    {
        if(ent == this) return false;
        if(!hasCollider || !ent.hasCollider) return  false;

        if(posX < ent.posX + ent.sizeX &&
                posX + sizeX > ent.posX &&
                posY < ent.posY + ent.sizeY &&
                posY + sizeY > ent.posY) {

            return true;
        }
        return  false;
    }

    void HandleCollision(GameLogic game)
    {
        for (Entity ent : game.Container) {
            if(ent == this) continue;

            if(CheckCollider(ent)) {
                OnCollision(ent);
            }
        }
    }

    void OnCollision(Entity ent)
    {

    }

    void rotate(float value)
    {
            rotation += value;
    }


    public entType GetType()
    {
        return type;
    }

    final int screenWidth = 640;
    final int screenHeight = 480;
    void HandleGameBoundaries()
    {
        if(type == entType.player)
        {
            if(posX < 0) posX = screenWidth;
            else if(posX > screenWidth) posX = 0;

            if(posY < 0) posY = screenHeight;
            else if(posY > screenHeight) posY = 0;
        }
        else
        {
            if(posX < -200 || posX > screenWidth + 200) isAlive = false;
            if(posY < -200 || posY > screenHeight + 200) isAlive = false;
        }
    }

    public Image getImage()
    {
        return  image;
    }
    public int GetHP()
    {
        return HP;
    }


}
