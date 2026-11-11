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
    public final float MAX_HEALTH = 100.0f;

    Sprite sprite;
    Rectangle hitbox;
    List<Item> inventory;
    private float health;
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
        inventory = new ArrayList<>(INVENTORY_SIZE);
        health = MAX_HEALTH;
        movementSpeed = 5; // TODO Decide on start movementSpeed
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
     * Translates inputs from {@code PlayerInputListener} into player movement
     *
     * @param delta the time in seconds since the previous frame
     */
    public void move(float delta) {
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

        // Allows diagonal movement and prevents this being faster than normal
        Vector2 velocity = direction.scl(scaledSpeed).setLength(scaledSpeed);

        sprite.setX(sprite.getX()+velocity.x);
        sprite.setY(sprite.getY()+velocity.y);

        hitbox.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Uses the item in the specified inventory slot
     *
     * @param slot the slot where the item resides in inventory, zero-indexed
     */
    public void useItem(int slot) {
        inventory.get(slot).useItem(this);
        GameLogic.itemsUsed++;
    }

    /**
     * Changes the player's health
     *
     * @param amount the amount to change the health by, can be negative
     */
    public void changeHealth(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
    }

    public float getHealth() {
        return this.health;
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

}
