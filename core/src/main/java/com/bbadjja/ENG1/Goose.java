package com.bbadjja.ENG1;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import java.util.concurrent.TimeUnit;

public class Goose {
    Texture texture;
    Sprite sprite;
    Rectangle hitBox;
    Rectangle target;
    int speed;

    static ArrayList<Goose> gooseList = new ArrayList<>();

    public Goose() {
        texture = new Texture("goose_temp.png");
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        hitBox = new Rectangle();
        target = calculateTarget();;
        speed = 4;

        gooseList.add(this);
    }

    private void movement() {
        // Geese should move constantly at x speed towards their set target

        //

        hitBox.set(sprite.getX(), sprite.getY(),
            sprite.getWidth(), sprite.getHeight());
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
            goose.target = tempTarget;
        }

        // TODO instead of this do a call to System.currentTimeMillis()
        //  and save this to a variable. Then add to the movement method a way
        //  to check if current time > this time + 3 second. If so, reset
        //  target to player hitbox.

//        try {
//            Thread.sleep(time);
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
//
//        for(Goose goose : gooseList) {
//            goose.target = calculateTarget();
//        }
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

    private void changeSpeed(int amount, int time) {
        for(Goose goose : gooseList) {
            goose.speed = goose.speed + amount;
        }

        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        for(Goose goose : gooseList) {
            goose.speed = 1; // whatever is abritrary speed
        }
    }

    private Rectangle calculateTarget() {
        // calculate target based on player location
        return new Rectangle();
    }

    public void onPlayerCollision(Player player) {
        // when the player's hitbox overlaps with any of the geese hitboxes
        // decrement player health
        player.changeHealth(-5);
    }

}
