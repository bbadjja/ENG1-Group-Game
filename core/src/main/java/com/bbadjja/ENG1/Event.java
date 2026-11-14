package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * A template that any hidden event must implement to function.
 */
public abstract class Event {
    public String name;
    public Rectangle triggerArea;
    public boolean manualTrigger;
    public Sprite sprite;
    public boolean triggered;

    public static ArrayList<Event> eventList = new ArrayList<>();

    /**
     * Creates a hidden event and adds it to the list to be interacted with
     *
     * @param name the name of the event
     * @param initialRect the place it should be drawn on the map
     * @param manualTrigger true if the event requires an input from the
     *                     player to be triggered, else false
     */
    public Event(String name, Rectangle initialRect, boolean manualTrigger) {
        this.name = name;
        this.triggerArea = initialRect;
        this.manualTrigger = manualTrigger;
        this.sprite = null;
        this.triggered = false;

        eventList.add(this);
    }

    /**
     * Creates a hidden event and adds it to the list to be interacted with
     *
     * @param name the name of the event
     * @param initialRect the place it should be drawn on the map
     * @param manualTrigger true if the event requires an input from the
     *                     player to be triggered, else false
     * @param texture the texture that the hidden event uses when triggered
     */
    public Event(String name, Rectangle initialRect, boolean manualTrigger, Texture texture) {
        this(name, initialRect, manualTrigger);

        this.sprite = new Sprite(texture);
        this.sprite.setSize(initialRect.width,initialRect.height);
        this.sprite.setOriginCenter();

        if (name.equals("Mud") || name.equals("Puddle")) {
            Random r = new Random();
            float degree = 0 + r.nextFloat() * (360);
            this.sprite.rotate(degree);
        }

        this.sprite.setX(initialRect.x);
        this.sprite.setY(initialRect.y);

        Rectangle rect = this.sprite.getBoundingRectangle();
        this.triggerArea = new Rectangle(rect.x,rect.y,rect.width*0.4f,rect.height*0.4f);

        this.triggerArea.setCenter(rect.getCenter(new Vector2()));
    }

    /**
     * Called when a player enters the event hitbox
     *
     * @param player the {@code Player} that entered the event
     */
    public void onPlayerEnter(Player player) {
        if (!this.manualTrigger) {
            this.onTrigger(player);
        }
    }

    /**
     * Called when a player exits the event hitbox
     *
     * @param player the {@code Player} that exited the event
     */
    public abstract void onPlayerExit(Player player);

    /**
     * Called when a player triggers the event, potentially manually
     *
     * @param player {@code Player} that triggered the event
     */
    public abstract void onTrigger(Player player);

    /**
     * A convenience method to create some instances of various events
     */
    public static void createEvents() {
        for (int i = 0; i < Puddle.PUDDLE_COUNT; i++) {
            new Puddle();
        }

        for (int i = 0; i < Mud.MUD_COUNT; i++) {
            new Mud();
        }
    }

}
