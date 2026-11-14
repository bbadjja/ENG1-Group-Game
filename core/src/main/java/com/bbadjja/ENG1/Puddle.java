package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

/**
 * A hidden event consisting of a puddle which trips the {@code Player}
 *   causing damage and temporary loss of control
 */
public class Puddle extends Event{
    private final static Texture TEXTURE = new Texture("puddle.png");
    private final static List<Rectangle> POSITIONS = List.of(
        new Rectangle(-2.5f,-5,2,2),
        new Rectangle(-12,0,2,2),
        new Rectangle(-4,7.5f,2,2),
        new Rectangle(6.5f,9,1.5f,1.5f),
        new Rectangle(1,2.5f,1.5f,1.5f),
        new Rectangle(9,3,1.5f,1.5f)
    );
    public final static int PUDDLE_COUNT = POSITIONS.size();

    private static int puddleNum = 0;

    private int damage;

    /**
     * Creates a {@code Puddle} instance and sets its damage amount
     */
    public Puddle() {
        super("Puddle", POSITIONS.get(puddleNum),false,TEXTURE);
        this.damage = 5;
        puddleNum++;
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
