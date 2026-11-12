package com.bbadjja.ENG1;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Player{
    private static final HashMap<String,Texture> TEXTURES = new HashMap<>();
    private final int NORMAL_SPEED = 5;
    public final int MAX_HEALTH = 100;
    public final int INVENTORY_SIZE = 5;

    public Sprite sprite;
    public Rectangle hitbox;
    public Rectangle feetHitbox;
    public ArrayList<Item> inventory;
    public boolean hidden;
    public boolean slipping;
    public Set<Integer> currentInputs;
    public Set<Integer> slippingInputs = new HashSet<>();

    private int health;
    private float movementSpeed;
    private long lastDamageTime;

    private int lastStepNum = 1;
    private long lastTextureTime;

    /**
     * Creates a {@code Player} which can move and interact with other entities
     */
    public Player() {
        createTextures();
        sprite = new Sprite(TEXTURES.get("idle_front"));
        sprite.setSize(1.5f,1.5f);

        // Update hitboxes to fit inside texture
        // feetHitbox for collisions with ground objects e.g. Puddle/Mud
        hitbox = new Rectangle(sprite.getX()+0.35f,sprite.getY()+0.15f,sprite.getWidth()-0.75f,sprite.getHeight()-0.2f);
        feetHitbox = new Rectangle(sprite.getX()+0.35f,sprite.getY()+0.15f,sprite.getWidth()-0.75f,sprite.getHeight()-1.2f);

        inventory = new ArrayList<>(INVENTORY_SIZE);
        health = MAX_HEALTH;
        movementSpeed = NORMAL_SPEED;
        hidden = false;
        currentInputs = new HashSet<>();
        lastDamageTime = System.currentTimeMillis();
        lastTextureTime = System.currentTimeMillis();

        addInitialItems();
    }

    /**
     * Convenience method to create a hash map of all the textures
     */
    private static void createTextures() {
        List<String> directions = List.of("front","back","left","right");
        for (String direction : directions) {
            TEXTURES.put("idle_"+direction,new Texture("player_" + direction + "_idle.png"));
            TEXTURES.put("step1_"+direction,new Texture("player_" + direction + "_step1.png"));
            TEXTURES.put("step2_"+direction,new Texture("player_" + direction + "_step2.png"));
        }
    }

    /**
     * Instantiates the player's initial items and fills the inventory with them
     */
    private void addInitialItems() {
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
        Set<Integer> inputs;

        if (slipping) {
            inputs = slippingInputs;
        } else {
            inputs = currentInputs;
        }

        if (inputs.contains(Input.Keys.RIGHT)) {
            direction.x += 1;
        }
        if (inputs.contains(Input.Keys.LEFT)) {
            direction.x -= 1;
        }
        if (inputs.contains(Input.Keys.UP)) {
            direction.y += 1;
        }
        if (inputs.contains(Input.Keys.DOWN)) {
            direction.y -= 1;
        }

        if (slipping) {
            Random r = new Random();
            if (direction.x >= 0) {
                direction.x += 0.2f + r.nextFloat() * (0.3f-0.2f);
            } else {
                direction.x -= 0.2f + r.nextFloat() * (0.3f-0.2f);
            }

            if (direction.y >= 0) {
                direction.y += 0.2f + r.nextFloat() * (0.3f-0.2f);
            } else {
                direction.y -= 0.2f + r.nextFloat() * (0.3f-0.2f);
            }
        }

        // Allows diagonal movement and prevents this being faster than normal
        Vector2 velocity = direction.scl(scaledSpeed).setLength(scaledSpeed);

        updateTexture(direction);

        sprite.setX(sprite.getX()+velocity.x);
        sprite.setY(sprite.getY()+velocity.y);

        hitbox.x = sprite.getX()+0.35f;
        hitbox.y = sprite.getY()+0.2f;
        feetHitbox.x = sprite.getX()+0.35f;
        feetHitbox.y = sprite.getY()+0.2f;
    }

    /**
     * Convenience method to update the current texture
     * @param direction the direction the player is moving in
     */
    private void updateTexture(Vector2 direction) {
        if (System.currentTimeMillis() - lastTextureTime > 250) {
            if (lastStepNum == 1) {
                lastStepNum = 2;
            } else {
                lastStepNum = 1;
            }
            lastTextureTime = System.currentTimeMillis();
        }

        if (direction.x > 0) {
            sprite.setTexture(TEXTURES.get("step"+lastStepNum+"_right"));
        } else if (direction.x < 0) {
            sprite.setTexture(TEXTURES.get("step"+lastStepNum+"_left"));
        } else if (direction.y > 0) {
            sprite.setTexture(TEXTURES.get("step"+lastStepNum+"_back"));
        } else if (direction.y < 0){
            sprite.setTexture(TEXTURES.get("step"+lastStepNum+"_front"));
        }

        if (slipping) {
            sprite.setTexture(TEXTURES.get("idle_front"));
        }
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
     * Attempts to pick up an item the player has collided with
     *
     * @param item the item to be picked up
     * @return whether the item could be picked up, false if inventory is full
     */
    public boolean pickupItem(Item item) {
        // TODO Called by GameLogic when player's hitbox collides with item's hitbox

        if (inventoryFull()) {
            return false;
        } else {
            inventory.add(item);
            return true;
        }
    }

    public boolean inventoryFull() {
        return inventory.size() < INVENTORY_SIZE;
    }

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
    public void changeSpeed(float percentage) throws IllegalArgumentException {
        if (percentage >= 0) {
            movementSpeed = NORMAL_SPEED * percentage;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public float getSpeed() {
        return this.movementSpeed;
    }

}
