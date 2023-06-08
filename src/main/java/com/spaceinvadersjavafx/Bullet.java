package com.spaceinvadersjavafx;

import javafx.scene.image.Image;

import java.util.List;

public class Bullet extends MovingEntity{

    private List<Entity> con;

    Bullet(int _hp, float _posX, float _posY, float _velX, float _velY, float scale, int _damage) {
        super(_hp, _posX, _posY, _velX, _velY, scale, _damage, 0);
        type = entType.bullet;
        image = new Image("file:Textures/bullet.png");
    }
    @Override
    void HandleCollision(GameLogic game)
    {
        if(con == null) con = game.Container;

        for (Entity ent : game.Container) {
            if(ent == this) continue;
            if(CheckCollider(ent)) {
//                game.SpawnExplosion(posX, posY, 1);
//                SpawnExplosion(game.Container, posX, posY ,1);
                OnCollision(ent);
            }
        }
    }
    @Override
    void OnCollision(Entity ent) {
        System.out.println("explosion spawned");
        System.out.println(con);


        if(ent.GetType() == entType.meteor && ent.isAlive)
        {
            if(con != null)
            {
                System.out.println("explosion spawned");
            }

            Meteor meteor = (Meteor) ent;

            isAlive = false;
            meteor.TakeDamage(damage);
        }
    }
}
