package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;

public class Bread extends Item {
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("char_temp.png");
    
    public Bread(boolean inInventory) {
        super("Bread", TEXTURE, true, null, null);

        this.itemID = generateItemID("2",itemNumber);
    }

    /**    
     * Stops all goose movement for a fixed amount of time and removes
     * the item from the player's inventory as well as the gamescreen
     * 
     * @see com.bbadjja.ENG1.Item#useItem(com.bbadjja.ENG1.Player, com.bbadjja.ENG1.Goose)
     */
    @Override
    public void useItem(Player player, Goose goose) {
        // TODO Auto-generated method stub
        goose.changeSpeed(0, 500);

        this.inInventory = false;
        player.inventory.remove(this);
    }
}
