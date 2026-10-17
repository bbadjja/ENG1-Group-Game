package com.bbadjja.ENG1;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Item> items;
    Integer maxItems;

    public Inventory() {
        items = new ArrayList<>(); // Start with no items/specific items/random items?
        maxItems = 0; // Decide max inventory size / no max?
    }
}
