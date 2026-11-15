package com.bbadjja.ENG1;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * An enemy entity representing a goose which follows and attacks the
 * player.
 */
public class Goose {
    Texture texture;
    Sprite sprite;

    Rectangle hitbox;
    Vector2 target;
    int speed;
    Vector2 goosePos;

    float speedTimer;
    float targetTimer;

    static ArrayList<Goose> gooseList = new ArrayList<>();

    /**
     * Creates a {@code Goose} which follows and attacks the player.
     */
    public Goose() {
        texture = new Texture("goose.png");
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        hitbox = new Rectangle();
        speed = 4;
        target = new Vector2();

        goosePos = new Vector2();

        gooseList.add(this);
    }

    /**
     * Moves a Goose entity towards a fixed target at a fixed speed 
     * whilst also checking if either the target or speed attributes
     * need to be reverted.
     * 
     * @param delta
     * @param player
     */
    public void movement(float delta, Player player) {
        // Decrement speedTimer and check if timer is over.
        if(speedTimer > 0) {
            speedTimer = speedTimer - delta;
        }
        else {
            changeSpeed(4, 300000);
        }

        // Decrement targetTimer and check if timer is over.
        if(targetTimer > 0) {
            targetTimer = targetTimer - delta;
        }
        else {
            this.target.set(player.getHitBox().getX() + player.getHitBox().getWidth() /2,
                player.getHitBox().getY() + player.getHitBox().getHeight() /2);

        }

        Vector2 direction = new Vector2(target).sub(goosePos);
        float distance = direction.len();

        // Move the goose in that direction by speed * delta.
        if(distance > 0) {
            direction.nor();

            goosePos.mulAdd(direction, speed * delta);
            this.sprite.setPosition(goosePos.x, goosePos.y);
        }

        // Update the goose hitbox to the new position.
        this.hitbox = sprite.getBoundingRectangle();
    }

    /**
     * Changes the target of a Goose entity and sets the targetTimer.
     *
     * @param tempTarget Goose entity's temporary target
     * @param time how long to change the target for (secs)
     */
    public void changeTarget(Rectangle tempTarget, float time) {
        this.target.set(tempTarget.x, tempTarget.y);
        targetTimer = time;
    }

    /**
     * Changes a Goose entity's speed and sets the speedTimer.
     *
     * @param newSpeed the new speed
     * @param time how long to change the speed for (secs)
     */
    public void changeSpeed(int newSpeed, float time) {
        this.speed = newSpeed;
        speedTimer = time;
    }

    /**
     * Decrements the player's health.
     * 
     * @param player the player entity
     */
    public void onPlayerCollision(Player player) {
        player.changeHealth(-5);
    }
}
