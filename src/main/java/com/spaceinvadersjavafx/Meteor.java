package com.spaceinvadersjavafx;

import javafx.scene.image.Image;

public class Meteor extends MovingEntity{

    Meteor(int _hp, float _posX, float _posY, float _velX, float _velY, float scale, int _damage, float _acceleration) {
        super(_hp, _posX, _posY, _velX, _velY, scale, _damage, _acceleration);
        velX = _velX;
        velY = _velY;

        image = new Image("file:Textures/rock.png");
    }

    @Override
    void HandleCollision(GameLogic game)
    {
        for (Entity ent : game.Container) {
            if(ent == this) continue;
            if(CheckCollider(ent)) {
                OnCollision(ent);
            }
        }
    }

    @Override
    void OnCollision(Entity ent) {
        if(ent.GetType() == entType.bullet)
        {
            Bullet bullet = (Bullet) ent;

            TakeDamage(bullet.damage);

            bullet.isAlive = false;
        }
        if(ent.GetType() == entType.player)
        {
            Player player = (Player) ent;

            TakeDamage(player.damage);

            player.TakeDamage(damage);
        }
    }
}
