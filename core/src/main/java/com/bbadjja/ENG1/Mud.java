package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

/**
 * A hidden event consisting of mud which slows the {@code Player} when stepped in
 */
public class Mud extends Event{
    private final static Texture TEXTURE = new Texture("mud.png");
    private static final List<Rectangle> POSITIONS = List.of(
        new Rectangle(-3,-3,1,1),
        new Rectangle(-5,-10,2,2),
        new Rectangle(-7,3.5f,1,1),
        new Rectangle(6,-4,2,2),
        new Rectangle(3,4.5f,2,2),
        new Rectangle(12,1,1,1)
    );
    public final static int MUD_COUNT = POSITIONS.size();

    private static int mudNum = 0;

    private float speedPercent;

    /**
     * Creates a {@code Mud} instance and randomises its speed reduction
     */
    public Mud() {
        super("Mud", POSITIONS.get(mudNum),false,TEXTURE);
        mudNum++;

        Random r = new Random();
        speedPercent = 0.4f + r.nextFloat() * (0.6f-0.4f);
    }

    @Override
    public void onTrigger(Player player) {
        player.changeSpeed(speedPercent);
        GameLogic.eventsTriggered++;
    }

    @Override
    public void onPlayerExit(Player player) {
        player.changeSpeed(1);
    }
}
