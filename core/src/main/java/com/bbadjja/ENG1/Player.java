package com.bbadjja.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Player{
    private final Texture TEXTURE = new Texture("texturePathHere.png");
    Sprite sprite;
    Rectangle hitbox;
    List<Item> inventory;
    private int health;
    private int movementSpeed;
    boolean hidden;

    private final int INVENTORY_SIZE = 5;

    public Player() {
        sprite = new Sprite(TEXTURE);
        hitbox = new Rectangle();
        inventory = new ArrayList<>(INVENTORY_SIZE); // empty list size 5
        health = 100; // Decide on max health
        movementSpeed = 20; // Decide on start movementSpeed
        hidden = false;
    }

    private void fillInventory() {
        // TODO Initialise initial items and place into inventory
        //  E.g. inventory.set(0,Seed());
    }

    public void move() {
        // Called by GameScreen / GameLogic in a function that runs every frame e.g. render

        float delta = Gdx.graphics.getDeltaTime();
        float scaledSpeed = movementSpeed * delta;
        Vector2 direction = new Vector2(0,0);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            direction.x = 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            direction.x = -1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            direction.y = 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            direction.y = -1;
        }

        Vector2 velocity = direction.scl(scaledSpeed).setLength(scaledSpeed);

        sprite.setX(sprite.getX()+velocity.x);
        sprite.setY(sprite.getY()+velocity.y);

        hitbox.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void useItem() {
        // Called by GameScreen / GameLogic in a function that runs every frame e.g. render
        try {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                inventory.get(0).onUse();
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                inventory.get(1).onUse();
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                inventory.get(2).onUse();
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
                inventory.get(3).onUse();
            } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
                inventory.get(4).onUse();
            }
        } catch (IndexOutOfBoundsException e) {
            // Tried to use an empty inventory slot - can ignore exception
        }

    }

    public boolean pickupItem(Item item) {
        // Called by GameScreen / GameLogic when player's hitbox collides with
        //  item's hitbox/location?

        // TODO Check inventory.size() = number of items currently in inventory and not 5
        if (inventory.size() == INVENTORY_SIZE) {
            return false;
        } else {
            inventory.add(item);
            return true;
        }

    }

    public void changeHealth(int amount) {
        health += amount;
        // TODO Notify other classes that health has changed - e.g. healthbar on screen
    }

    public void changeSpeed(int amount) {
        movementSpeed += amount;
    }

}
