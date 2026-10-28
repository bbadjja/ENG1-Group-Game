package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Goose {
    Texture texture;
    Sprite sprite;
    Rectangle hitbox;

    static ArrayList<Goose> gooseList = new ArrayList<>();

    public Goose() {
        texture = new Texture("goose_temp.png");
        sprite = new Sprite(texture);
        sprite.setSize(1,1);
        hitbox = new Rectangle();

        gooseList.add(this);
    }

    private void movement() {
        // Movement code here

        hitbox.set(sprite.getX(), sprite.getY(),
            sprite.getWidth(), sprite.getHeight());
    }

}
