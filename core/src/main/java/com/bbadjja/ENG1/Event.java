package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.sql.Array;
import java.util.ArrayList;

public abstract class Event {
    public String name;
    public Rectangle triggerArea;
    public boolean manualTrigger;
    public Sprite sprite;

    public static ArrayList<Event> events = new ArrayList<>();

    public Event(String name, Rectangle triggerArea, boolean manualTrigger) {
        this.name = name;
        this.triggerArea = triggerArea;
        this.manualTrigger = manualTrigger;
        this.sprite = null;

        events.add(this);
    }

    public Event(String name, Rectangle triggerArea, boolean manualTrigger, Texture texture) {
        this(name, triggerArea, manualTrigger);

        this.sprite = new Sprite(texture);
        this.sprite.setSize(1,1);
        this.sprite.setCenter(triggerArea.x,triggerArea.y);
    }

    public void onPlayerEnter(Player player) {
        if (!this.manualTrigger) {
            this.onTrigger(player);
        }
    }

    public abstract void onPlayerExit(Player player);

    public abstract void onTrigger(Player player);

    public static void createEvents() {
        for (int i = 0; i < Puddle.puddleCount; i++) {
            new Puddle();
        }

        for (int i = 0; i < Mud.mudCount; i++) {
            new Mud();
        }
    }

}
