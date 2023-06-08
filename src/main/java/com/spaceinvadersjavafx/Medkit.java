package com.spaceinvadersjavafx;

import javafx.scene.image.Image;

public class Medkit extends MovingEntity{
    Medkit(int _hp, float _posX, float _posY, float _velX, float _velY, float scale, int _damage) {
        super(_hp, _posX, _posY, _velX, _velY, scale, _damage, 0);
        velX = _velX;
        velY = _velY;

        image = new Image("file:Textures/medkit.png");
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
        if(ent.GetType() == entType.player)
        {
            Player player = (Player) ent;

            player.TakeDamage(-GetHP());

            isAlive = false;
        }
    }
}
