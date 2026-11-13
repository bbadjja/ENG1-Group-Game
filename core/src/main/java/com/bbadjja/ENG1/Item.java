package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
//import java.util.random.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Item {
    Sprite sprite;
    String itemID; // can easily be changed to integer if needed - ask Amber
    String name;
    boolean inInventory;
    Rectangle initialPlacement;
    Rectangle hitbox;

    public static ArrayList<Item> itemList = new ArrayList<>();

    // constructor
    public Item(String name, Texture texture, boolean inInventory, Rectangle hitbox) {
        //this.itemID = itemID; // first digit is item subclass, second digit is no. within that subclass
        this.name = name;
        this.inInventory = inInventory;
        sprite = new Sprite(texture);
        hitbox = sprite.getBoundingRectangle();

        if (!inInventory) {
            this.initialPlacement = placeOnMap();
            itemList.add(this);
        }

    }

    /**
     * Creates a unique ID for every object. The first digit is specific to the
     * child class it comes from and the second digit is what number object
     * it is within that class (1, 2, 3...)
     *
     * @param itemNum the unique number for the child class
     * @param objNum the number that object is within the class
     * @return the unique ID for that instance
     */
    protected String generateItemID(String itemNum, int objNum) {
        // could put this method into item and take itemNum and objectNum
        // as parameters?
        String ID = itemNum + String.valueOf(objNum);
        objNum ++;
        return ID;
    }

    public abstract void useItem(Player player, ArrayList<Goose> gooseList);

    public Rectangle placeOnMap() {

        Random r = new Random();
        float x = -20 + r.nextFloat() * (20- -20);
        float y = -20 + r.nextFloat() * (20- -20);

        // Set sprite x and y to the parameters x and y
        this.sprite.setPosition(x,y);
        this.sprite.setSize(1, 1);
        return(new Rectangle(x, y, 1, 1));
    }

    public boolean pickUpItem(Player player) {
        if(player.inventory.size() == 5) {
            return false;
        } else {
            this.inInventory = true;
            player.inventory.add(this);
            itemList.remove(this);
            return true;
        }
    }

    public static void createMapItems() {
        for (int i = 0; i < 6; i++) {
            new SausageRoll(false);
        }

        for (int i = 0; i < 6; i++) {
            new Bread(false);
        }
    }

}
