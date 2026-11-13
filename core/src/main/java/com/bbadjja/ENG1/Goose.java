package com.bbadjja.ENG1;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    // target is player hitbox, which refreshes every frame in the render
    // however when target is changed this needs to be changed temporarily

    public void movement(float delta, Player player) {
        // decrement speedTimer and check if it's over
        if(speedTimer > 0) {
            speedTimer = speedTimer - delta;
        }
        else {
            changeSpeed(4, 300000);
        }

        // decrement targetTimer and check if it's over
        if(targetTimer > 0) {
            targetTimer = targetTimer - delta;
        }
        else {
            this.target.set(player.getHitBox().getX() + player.getHitBox().getWidth() /2,
                player.getHitBox().getY() + player.getHitBox().getHeight() /2);

        }

        Vector2 direction = new Vector2(target).sub(goosePos);
        float distance = direction.len();

        // move the goose in that direction by speed * delta
        if(distance > 0) {
            direction.nor();

            goosePos.mulAdd(direction, speed * delta);
            this.sprite.setPosition(goosePos.x, goosePos.y);
        }

        // update the goose hitbox to the new position
        this.hitbox = sprite.getBoundingRectangle();
    }

        /**
     * This method changes the targets of all geese to a different one for a
     * set amount of milliseconds before returning it to its original target
     * of the player. It is used for when items such as Seeds are used.
     *
     * @param tempTarget where the geese's target is temporarily changed to
     * @param time how long the tempTarget should be kept for in milliseconds
     */

    public void changeTarget(Rectangle tempTarget, float time) {
        this.target.set(tempTarget.x, tempTarget.y);
        targetTimer = time;
    }

    /**
     * This method changes the speed of every Goose instance for a
     * fixed amount of time. The change can be either an increase
     * or decrease.
     *
     * @param amount how much the speed should be changed by, can
     * be either a positive or negative value
     * @param time how long in milliseconds the speed should be
     * altered for
     */

    public void changeSpeed(int newSpeed, float time) {
        this.speed = newSpeed;
        speedTimer = time;
    }


    public void onPlayerCollision(Player player) {
        player.changeHealth(-5);
    }
}
