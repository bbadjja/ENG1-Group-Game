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
    boolean altTarget;
    Vector2 goosePos;

    static ArrayList<Goose> gooseList = new ArrayList<>();

    public Goose() {
        texture = new Texture("goose_temp.png");
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        hitbox = new Rectangle();
        //target = calculateTarget(Player player);
        speed = 4;
        altTarget = false;
        target = new Vector2();
        goosePos = new Vector2();

        gooseList.add(this);
    }
    
    // target is player hitbox, which refreshes every frame in the render
    // however when target is changed this needs to be changed temporarily

    private void movement(float delta, Player player) {
        // loop through all geese - won't this take forever?
        for(Goose goose : gooseList) {
            // is altTarget false? then calculate target based on player location
            // otherwise carry on straight to the movement
            if(altTarget == false) {
                goose.target.set(player.getHitBox().getX() + player.getHitBox().getWidth() /2,
                player.getHitBox().getY() + player.getHitBox().getHeight() /2);
            }

            // find the goose coords and centre them
            //goosePos.set(goose.hitbox.getX() + goose.hitbox.getWidth() /2, 
            //goose.hitbox.getY() + goose.hitbox.getHeight());

            // calculate the direction and distance between the two points
            Vector2 direction = new Vector2(target).sub(goosePos);
            float distance = direction.len();

            // move the goose in that direction by speed * delta
            if(distance > 0) {
                direction.nor();

                goosePos.mulAdd(direction, speed * delta);
                goose.sprite.setPosition(goosePos.x, goosePos.y);
            }

            // update the goose hitbox to the new position
            goose.hitbox = sprite.getBoundingRectangle();
        }
    }

        /**
     * This method changes the targets of all geese to a different one for a
     * set amount of milliseconds before returning it to its original target
     * of the player. It is used for when items such as Seeds are used.
     *
     * @param tempTarget where the geese's target is temporarily changed to
     * @param time how long the tempTarget should be kept for in milliseconds
     */

    public void changeTarget(Rectangle tempTarget, int time) {
        for(Goose goose : gooseList) {
            this.altTarget = true;
            goose.target = tempTarget;
        }

        // TODO instead of this do a call to System.currentTimeMillis()
        //  and save this to a variable. Then add to the movement method a way
        //  to check if current time > this time + 3 second. If so, reset
        //  target to player hitbox.


        // will only ever be a fixed target
        for(Goose goose : gooseList) {
            this.altTarget = false;
        }
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

    public void changeSpeed(int amount, int time) {
        for(Goose goose : gooseList) {
            goose.speed = goose.speed + amount;
        }

        /**
        TODO instead of this do a call to System.currentTimeMillis()
        and save this to a variable. Then add to the movement method a way
        to check if current time > this time + 3 second. If so, reset
        target to player hitbox.


         try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        for(Goose goose : gooseList) {
            goose.speed = 1; // whatever is abritrary speed
        }
         */
    }

    private Rectangle calculateTarget(Player player) {
        // calculate target based on player location
        return new Rectangle();
    }

    private void onPlayerCollision(Player player) {
        // when the player's hitbox overlaps with any of the geese hitboxes
        // decrement player health
        player.changeHealth(-5);
    }
}