package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
import java.util.ArrayList;

/**
 * An abstract entity item that can be picked up and used by the player
 * that can affect the Player or Goose class.
 */
public abstract class Item {
    Sprite sprite;
    String itemID;
    String name;
    boolean inInventory;
    Rectangle initialPlacement;
    Rectangle hitbox;

    public static ArrayList<Item> itemList = new ArrayList<>();

    /**
     * Creates a {@code Item} which can interact with other entities.
     * 
     * @param name name of the item type
     * @param texture image of the item to be displayed
     * @param inInventory if the item is in the player's inventory
     */
    public Item(String name, Texture texture, boolean inInventory) {
        this.name = name;
        this.inInventory = inInventory;
        sprite = new Sprite(texture);

        if (!inInventory) {
            this.initialPlacement = placeOnMap();
            itemList.add(this);

            if (this.name.equals("Bread")){
                this.hitbox = new Rectangle(sprite.getX()+0.25f,sprite.getY()+0.3f,sprite.getWidth()-0.5f,sprite.getHeight()-0.55f);
            } else if (this.name.equals("SausageRoll")){
                this.hitbox = new Rectangle(sprite.getX()+0.1f,sprite.getY()+0.1f,sprite.getWidth()-0.2f,sprite.getHeight()-0.2f);
            } else {
                this.hitbox = sprite.getBoundingRectangle();
            }

        }

    }

    /**
     * Creates a unique ID for every object using the item number and 
     * object number.
     *
     * @param itemNum the unique number for the Item child class
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

    /**
     * Carries out the effects of using an item.
     * 
     * @param player the player entity
     * @param gooseList the list of all Goose instances
     */
    public abstract void useItem(Player player, ArrayList<Goose> gooseList);

    /**
     * Places the item randomly on the map.
     * 
     * @return a rectangle of where the item is on the map
     */
    public Rectangle placeOnMap() {

        Random r = new Random();
        float x = -20 + r.nextFloat() * (20- -20);
        float y = -20 + r.nextFloat() * (20- -20);

        this.sprite.setPosition(x,y);
        this.sprite.setSize(1, 1);
        return(new Rectangle(x, y, 1, 1));
    }

    /**
     * Checks if the player's inventory is full, adds the item to it
     * if it isn't.
     * 
     * @param player
     * @return true if the item has successfully been added, otherwise
     * false
     */
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

    /**
     * Creates and places the initial items for Ass. 1.
     */
    public static void createMapItems() {
        for (int i = 0; i < 6; i++) {
            new SausageRoll(false);
        }

        for (int i = 0; i < 6; i++) {
            new Bread(false);
        }
    }

}
