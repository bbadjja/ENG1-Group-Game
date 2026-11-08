package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item {
    Sprite sprite;
    String itemID; // can easily be changed to integer if needed - ask Amber
    String name;
    boolean inInventory;
    Rectangle initialPlacement;

    // constructor
    public Item(String name, Texture texture, boolean inInventory, Rectangle initialPlacement) {
        //this.itemID = itemID; // first digit is item subclass, second digit is no. within that subclass
        this.name = name;
        this.inInventory = inInventory;
        this.sprite = new Sprite(texture);

        // TODO Need to calculate initialPlacement some way
        //  E.g. maybe each item has preset spawn locations in a list
        //  and you choose the index of the currrent itemNumber to pass to
        //  the super constructor

        if (!inInventory) {
            placeOnMap(this.initialPlacement);
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

    public abstract void useItem(Player player);

    public void placeOnMap(Rectangle placement) {
        // This should not be abstract as same code for all items
        // Just place in a position, coords x and y

        // Set sprite x and y to the parameters x and y
    }

    public void pickUpItem(Player player) {
        if(player.pickupItem(this)) {
            this.inInventory = true;
            this.sprite = null;
        }
    }

}
