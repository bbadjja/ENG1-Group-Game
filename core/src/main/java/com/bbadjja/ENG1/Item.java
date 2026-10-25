package com.bbadjja.ENG1;

public abstract class Item {
    Integer itemID;
    String name;

    public Item(String name) {
        itemID = 0; // Add code to make 1 more than previously defined
        this.name = name;

        // Presumably we will extend this class for each new item
        // Make this class abstract?
        // Possible Functions:
        //  - OnUse
        //  -
    }

    public abstract void onUse();

}
