package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

public abstract class Event {
    public String name;
    public Rectangle triggerArea;
    public boolean manualTrigger;
    public Sprite sprite;

    public static ArrayList<Event> eventList = new ArrayList<>();

    public Event(String name, Rectangle initialRect, boolean manualTrigger) {
        this.name = name;
        this.triggerArea = initialRect;
        this.manualTrigger = manualTrigger;
        this.sprite = null;

        eventList.add(this);
    }

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

    public void onPlayerEnter(Player player) {
        if (!this.manualTrigger) {
            this.onTrigger(player);
        }
    }

    public abstract void onPlayerExit(Player player);

    public abstract void onTrigger(Player player);

    public static void createEvents() {
        for (int i = 0; i < Puddle.PUDDLE_COUNT; i++) {
            new Puddle();
        }

        for (int i = 0; i < Mud.MUD_COUNT; i++) {
            new Mud();
        }
    }

}
