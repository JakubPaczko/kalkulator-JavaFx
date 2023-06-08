package com.spaceinvadersjavafx;

import javafx.scene.image.Image;

public class Expolsion extends Entity{

    final float explosionFrameDuration = 10;
    Expolsion(int _hp, float _posX, float _posY, float scale) {
        super(_hp, _posX, _posY, scale);
        type = entType.explosion;
        hasCollider = false;
        image = new Image("file:Textures/explosion.png");
    }

    float explosionTimer = 0;

    @Override
    void Update() {
        super.Update();
        explosionTimer ++;

        if(explosionTimer > explosionFrameDuration)
        {
            isAlive = false;
        }
    }
}
