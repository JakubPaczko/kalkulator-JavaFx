package com.spaceinvadersjavafx;
import java.util.List;

enum entType {bullet, player, meteor}

public class Entity {
    private int level = 1;
    final  float maxVelocity = 10;
    final float drag = 0.99f;
    public final float defaultEntSize = 32f;
    float rotation = 0;
    float rotationSpeed = 0.05f;//(float) Math.toRadians(45.0);
    private entType type;
    public float posX;
    public float posY;
    public float velX;
    public float velY;
    public float sizeX;
    public float sizeY;
    public int damage;
    float acceleration;
    boolean isAlive = true;
    private int HP;
    private final int shootFramesTimer = 50;
    Entity(int _hp, float _posX, float _posY, float _velX, float _velY, float scale, int _damage, float _acceleration, entType _type)
    {
        acceleration = _acceleration;

        type = _type;

        HP = _hp;
        damage = _damage;

        posX = _posX;
        posY = _posY;

        velX = _velX;
        velY = _velY;

        sizeX = defaultEntSize * scale;
        sizeY = defaultEntSize * scale;

        rotation = 0f;
    }

//    Entity(int _hp, float _posX, float _posY, float _velX, float scale, int _damage)
//    {
//        type = entType.meteor;
//
//        HP = _hp;
//        damage = _damage;
//
//        posX = _posX;
//        posY = _posY;
//
//        velX = _velX;
//
//        sizeX = defaultEntSize * scale;
//        sizeY = defaultEntSize * scale;
//
//        rotation = 45f;
//    }
    int timer = 0;
    void Update()
    {
        timer ++;

        posX += velX;
        posY += velY;

        if(type == entType.player)
        {
            velX *= drag;
            velY *= drag;
        }

        if(type == entType.meteor)
        {
            rotation += 0.1f;
        }

        HandleGameBoundaries();
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

    public void TakeDamage(int damage)
    {
        HP -= damage;

        if(HP <= 0) isAlive = false;
    }

    boolean CheckCollider(Entity ent)
    {
        if(ent == this) return false;


        if(posX < ent.posX + ent.sizeX &&
                posX + sizeX > ent.posX &&
                posY < ent.posY + ent.sizeY &&
                posY + ent.sizeY > ent.posY) {
            if(ent.GetType() == entType.meteor && type == entType.bullet){
                ent.TakeDamage(damage);
                isAlive = false;
            }
            else if(ent.GetType() == entType.meteor && type == entType.player)
            {
                TakeDamage(ent.damage);
            }
            return true;
        }
        return  false;
    }

    void HandleCollision(GameLogic game)
    {
        for (Entity ent : game.Container) {
            if(ent == this) continue;


            if(posX < ent.posX + ent.sizeX &&
                    posX + sizeX > ent.posX &&
                    posY < ent.posY + ent.sizeY &&
                    posY + ent.sizeY > ent.posY) {
                if(ent.GetType() == entType.meteor && type == entType.bullet){
                    game.AddScore(1);
                    ent.TakeDamage(damage);
                    isAlive = false;
                }
                else if(ent.GetType() == entType.meteor && type == entType.player)
                {
                    TakeDamage(ent.damage);
                    ent.TakeDamage(damage);
                }
            }
        }
    }

    void rotate(float value)
    {
            rotation += value;
    }
    public void Shoot(List<Entity> con)
    {
//        System.out.println(timer);
        if(timer < shootFramesTimer) return;
        float bulletVelX = (float) -Math.sin(Math.toRadians(rotation)) * 20;
        float bulletVelY = (float) -Math.cos(Math.toRadians(rotation)) * 20;
        timer = 0;

        Entity bullet = new Entity(1, posX, posY, bulletVelX, bulletVelY, 1, damage, 0, entType.bullet);
        bullet.rotation = rotation;
        con.add(bullet);
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

    public int GetHP()
    {
        return HP;
    }
}
