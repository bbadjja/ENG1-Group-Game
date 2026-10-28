package com.bbadjja.ENG1;

// import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Goose {
    Texture texture;
    Sprite sprite;
    Rectangle hitBox;
    Rectangle target;
    int speed;

    public Goose() {
        texture = new Texture("texturePathHere.png");
        sprite = new Sprite(texture);
        hitBox = new Rectangle();
        target = calculateTarget();;
        speed = 0;
    }

    private void movement() {
        // Movement code here

        hitBox.set(sprite.getX(), sprite.getY(),
            sprite.getWidth(), sprite.getHeight());
    }

    private Rectangle calculateTarget() {
        // calculate target based on player location
        return new Rectangle();
    }

    public void changeTarget(Rectangle newTarget, int millisecondPause) {
        // how to apply to all goose objects?
        this.target = newTarget;

        try {
            Thread.sleep(millisecondPause);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        this.target = new Rectangle();
    }

}
