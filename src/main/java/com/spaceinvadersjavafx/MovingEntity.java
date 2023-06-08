package com.spaceinvadersjavafx;

import java.util.List;

public class MovingEntity extends Entity{

    protected boolean Drag;
    protected float dragValue = 0.98f;
    protected float velX;
    protected float velY;
    int timer = 0;
    float acceleration;
    final  float maxVelocity = 10;
    public int damage;

    MovingEntity(int _hp, float _posX, float _posY, float _velX, float _velY, float scale, int _damage, float _acceleration) {
        super(_hp, _posX, _posY, scale);
        velX = _velX;
        velY = _velY;

        damage = _damage;
        acceleration = _acceleration;
    }

    public void AddFrontVelocity()
    {
        if(velX > maxVelocity || velY > maxVelocity) return;

        velX -= Math.sin(Math.toRadians(rotation)) * acceleration;
        velY -= Math.cos(Math.toRadians(rotation)) * acceleration;
    }

    public void AddVelocity(float _velX, float _velY)
    {
        velX += _velX;
        velY += _velY;
    }
    public void SetVelocity(float _velX, float _velY)
    {
        velX = _velX;
        velY = _velY;
    }
    public float GetVelocity(int dimension)
    {
        if(dimension == 1)
        {
            return  velX;
        }
        else if (dimension == 2) {
            return  velY;
        }
        return 0f;
    }

    @Override
    void Update() {
        if(Drag)
        {
            velX *= dragValue;
            velY *= dragValue;
        }

        posX += velX;
        posY += velY;
        ;
        HandleGameBoundaries();
    }

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



}
