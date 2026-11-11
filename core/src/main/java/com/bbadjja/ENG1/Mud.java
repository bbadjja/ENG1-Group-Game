package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.Random;

public class Mud extends Event{
    private final static Texture TEXTURE = new Texture("char_temp.png");
    private static int eventNum = 0;
    private static final List<Rectangle> positions = List.of(
        new Rectangle(-3,-3,1,1),
        new Rectangle(-5,-5,1,1)
    );
    public final static int mudCount = positions.size();

    private float speedPercent;

    public Mud() {
        super("Puddle",positions.get(eventNum),false,TEXTURE);
        eventNum++;

        Random r = new Random();
        speedPercent = 0.4f + r.nextFloat() * (0.6f-0.4f);
    }

    @Override
    public void onPlayerExit(Player player) {
        player.applySpeedPercentage(1);
    }

    @Override
    public void onTrigger(Player player) {
        player.applySpeedPercentage(speedPercent);
    }
}
