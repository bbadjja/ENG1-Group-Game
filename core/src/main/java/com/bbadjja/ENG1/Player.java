package com.bbadjja.ENG1;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player{
    private final Texture TEXTURE = new Texture("char_temp.png");
    Sprite sprite;
    Rectangle hitbox;
    List<Item> inventory;
    private int health;
    private int movementSpeed;
    boolean hidden;

    private final int INVENTORY_SIZE = 5;

    Set<Integer> currentInputs;

    /**
     * Creates a {@code Player} which can move and interact with other entities
     */
    public Player() {
        sprite = new Sprite(TEXTURE);
        sprite.setSize(1,1);
        hitbox = new Rectangle();
        inventory = new ArrayList<>(INVENTORY_SIZE); // empty list size 5
        health = 100; // Decide on max health
        movementSpeed = 5; // Decide on start movementSpeed
        hidden = false;
        currentInputs = new HashSet<>();
    }

    /**
     * Instantiates the player's initial items and fills the inventory with them
     */
    private void fillInventory() {
        // TODO Initialise initial items and place into inventory
        //  E.g. inventory.set(0,Seed());
    }

    /**
     * Takes input from the user and translates it into player movement
     */
    public void move(float delta) {
        // Called by GameScreen / GameLogic in a function that runs every frame e.g. render

        float scaledSpeed = movementSpeed * delta;
        Vector2 direction = new Vector2(0,0);

        if (currentInputs.contains(Input.Keys.RIGHT)) {
            direction.x += 1;
        }
        if (currentInputs.contains(Input.Keys.LEFT)) {
            direction.x -= 1;
        }
        if (currentInputs.contains(Input.Keys.UP)) {
            direction.y += 1;
        }
        if (currentInputs.contains(Input.Keys.DOWN)) {
            direction.y -= 1;
        }

        Vector2 velocity = direction.scl(scaledSpeed).setLength(scaledSpeed);

        sprite.setX(sprite.getX()+velocity.x);
        sprite.setY(sprite.getY()+velocity.y);

        hitbox.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Takes input from the user and uses the corresponding item
     */
    public void useItem(int slot) {
        // Called by PlayerInputAdaptor
        inventory.get(slot).onUse();
    }

    /**
     * Attempts to pick up an item the player has collided with
     *
     * @param item the item to be picked up
     * @return whether the item could be picked up, false if inventory is full
     */
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

    /**
     * Changes the player's health and notifies GameScreen to update the healthbar
     *
     * @param amount the amount to change the health by, can be negative
     */
    public void changeHealth(int amount) {
        health += amount;
        // TODO Notify other classes that health has changed - e.g. healthbar on screen
    }

    /**
     * Changes the player's movement speed
     *
     * @param amount the amount to change movement speed by, can be negative
     */
    public void changeSpeed(int amount) {
        movementSpeed += amount;
    }

    public int getSpeed() {
        return this.movementSpeed;
    }

    // TODO ask whether getting player input should be moved into its own function
    //  This function would then call move() and useItem() accordingly
    //  Would also result in only 1 function being called from GameScreen every frame

}
