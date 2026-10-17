package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    Texture enemyTexture;
    Sprite enemySprite;
    Rectangle enemyHitBox;

    public Enemy() {
        enemyTexture = new Texture("texturePathHere.png");
        enemySprite = new Sprite(enemyTexture);
        enemyHitBox = new Rectangle();
    }

    private void movement() {
        // Movement code here

        enemyHitBox.set(enemySprite.getX(), enemySprite.getY(),
            enemySprite.getWidth(), enemySprite.getHeight());
    }

}
