package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    Texture enemyTexture;
    Sprite enemySprite;
    Rectangle enemyHitBox;

    float enemyPosx = 0;
    float enemyPosy = 0;
    float changeDir = 20;
    float enemySpeed = 80;

    public Enemy() {
        enemyTexture = new Texture("texturePathHere.png");
        enemySprite = new Sprite(enemyTexture);
        enemyHitBox = new Rectangle();
    }

    public void movement(float changedirControl) {

        changeDir -= changedirControl;
        if (changeDir <= 0){

            enemyPosx = MathUtils.random(-1, 1);
            enemyPosy = MathUtils.random(-1, 1);
            Vector2 enemyPos = new Vector2 (enemyPosx, enemyPosy);
        
            enemyPosx = enemyPos.x;
            enemyPosy = enemyPos.y;

            changeDir = MathUtils.random(1, 20);
            enemyPos.nor();
        }
            enemySprite.translate((enemyPosx * enemySpeed * changedirControl), (enemyPosy * enemySpeed * changedirControl));

        enemyHitBox.set(enemySprite.getX(), enemySprite.getY(),
            enemySprite.getWidth(), enemySprite.getHeight());
    }

}
