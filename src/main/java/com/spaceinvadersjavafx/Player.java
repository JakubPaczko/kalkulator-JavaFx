package com.spaceinvadersjavafx;

import javafx.scene.image.Image;

import java.util.List;

public class Player extends MovingEntity{

    int shootFramesTimer = 50;
    Player(int _hp, float _posX, float _posY, float _velX, float _velY, float scale, int _damage, float _acceleration) {
        super(_hp, _posX, _posY, _velX, _velY, scale, _damage, _acceleration);
        type = entType.player;
        Drag = true;
        image = new Image("file:Textures/ship.png");
    }

    @Override
    void Update() {
        super.Update();
        timer ++;
    }

    public void Shoot(List<Entity> con)
    {
//        System.out.println(timer);
        if(timer < shootFramesTimer) return;
        float bulletVelX = (float) -Math.sin(Math.toRadians(rotation)) * 20;
        float bulletVelY = (float) -Math.cos(Math.toRadians(rotation)) * 20;
        timer = 0;

//        Entity bullet = new Entity(1, posX, posY, bulletVelX, bulletVelY, 1, damage, 0, entType.bullet);
        Bullet bullet = new Bullet(1, posX, posY, bulletVelX, bulletVelY, 1, damage);
        bullet.rotation = rotation;
        con.add(bullet);
    }

    @Override
    void OnCollision(Entity ent) {
        if(ent.GetType() == entType.meteor)
        {
            Meteor meteor = (Meteor) ent;
            TakeDamage(meteor.damage);

            meteor.TakeDamage(damage);
        }
    }


}
