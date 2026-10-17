package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player{
    Texture playerTexture;
    Sprite playerSprite;
    Rectangle playerHitBox;
    Integer playerHealth;
    Inventory inventory;

    public Player() {
        playerTexture = new Texture("texturePathHere.png");
        playerSprite = new Sprite(playerTexture);
        playerHitBox = new Rectangle();
        playerHealth = 0; // Decide on max health
        inventory = new Inventory();
    }

    private void input() {
        // Movement/item use code here

        playerHitBox.set(playerSprite.getX(), playerSprite.getY(),
            playerSprite.getWidth(), playerSprite.getHeight());
    }

    private void damage() {
        // Code for taking damage here
    }

}
