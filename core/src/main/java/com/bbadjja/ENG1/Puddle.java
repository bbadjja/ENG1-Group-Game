package com.bbadjja.ENG1;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Vector;

public class Puddle extends Event{
    private final static Texture TEXTURE = new Texture("puddle.png");
    private static int eventNum = 0;
    private final static List<Rectangle> positions = List.of(
        new Rectangle(3,3,1,1),
        new Rectangle(5,5,2,2)
    );
    public final static int puddleCount = positions.size();

    private final static int damage = 5;
    private final static float distance = 1f;

    public Puddle() {
        super("Puddle",positions.get(eventNum),false,TEXTURE);
        eventNum++;
    }

    @Override
    public void onTrigger(Player player) {
        player.changeHealth(-damage);
        player.slipping = true;
        player.slippingInputs.addAll(player.currentInputs);
        GameLogic.eventsTriggered++;
    }

    @Override
    public void onPlayerExit(Player player) {
        player.slipping = false;
        player.slippingInputs.clear();
    }
}
