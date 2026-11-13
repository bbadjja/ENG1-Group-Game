package com.bbadjja.ENG1;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Player{
    private final Texture TEXTURE = new Texture("player_front_idle.png");
    private final int NORMAL_SPEED = 5;
    public final int MAX_HEALTH = 100;
    public final int INVENTORY_SIZE = 5;

    public Sprite sprite;
    public Rectangle hitbox;
    public ArrayList<Item> inventory;
    public boolean hidden;
    public Set<Integer> currentInputs;

    private int health;
    private float movementSpeed;
    private long lastDamageTime;

    /**
     * Creates a {@code Player} which can move and interact with other entities
     */
    public Player() {
        sprite = new Sprite(TEXTURE);
        sprite.setSize(1,1);
        hitbox = sprite.getBoundingRectangle(); // can adjust width and height if necessary
        inventory = new ArrayList<>(INVENTORY_SIZE);
        health = MAX_HEALTH;
        movementSpeed = NORMAL_SPEED;
        hidden = false;
        currentInputs = new HashSet<>();
        lastDamageTime = System.currentTimeMillis();

        addInitialItems();
    }

    /**
     * Instantiates the player's initial items and fills the inventory with them
     */
    public void addInitialItems() {
        inventory.add(0,new Seeds(true));
        inventory.add(1,new SausageRoll(true));
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

        hitbox = sprite.getBoundingRectangle(); // can adjust width and height if necessary
    }

    /**
     * Uses the item in the specified inventory slot
     *
     * @param slot the slot where the item resides in inventory, zero-indexed
     */
    public void useItem(int slot) {
        if (slot < inventory.size()) {
            Item item = inventory.get(slot);
            item.useItem(this);
            inventory.remove(item);
            GameLogic.itemsUsed++;
        }
    }

    // TODO might have moved to Item class
    /**
     * Changes the player's health
     *
     * @param amount the amount to change the health by, can be negative
     */
    public void changeHealth(int amount) {
        if (amount < 0 && System.currentTimeMillis()-lastDamageTime > 250) {
            lastDamageTime = System.currentTimeMillis();
        } else {
            return;
        }

        health += amount;
        if (health > 100) {
            health = 100;
        } else if (health <= 0) {
            GameLogic.endGame(false);
        }
    }

    public float getHealth() {
        return this.health;
    }

    /**
     * Changes the player's movement speed to a percentage of its normal speed
     *
     * @param percentage the percentage to change speed to
     * @throws  IllegalArgumentException if percentage < 0
     */
    public void applySpeedPercentage(float percentage) throws IllegalArgumentException {
        if (percentage >= 0) {
            movementSpeed = NORMAL_SPEED * percentage;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public float getSpeed() {
        return this.movementSpeed;
    }

    public Rectangle getHitBox() {
        return this.hitbox;
    }

}
